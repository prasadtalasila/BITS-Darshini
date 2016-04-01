package in.ac.bits.protocolanalyzer.persistence.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Component;

@Component
public class AnalysisRepository {

    @Autowired
    private ElasticsearchTemplate template;

    private Queue<IndexQuery> queries;
    private ConcurrentLinkedQueue<ArrayList<IndexQuery>> buckets;
    private Timer saveTimer;
    private Timer pullTimer;
    private Timer killTimer;
    
    private boolean pullStopped = false;
    private int bucketCapacity = 10000;

    public void configure() {
        this.queries = new ConcurrentLinkedQueue<IndexQuery>();
        this.buckets = new ConcurrentLinkedQueue<ArrayList<IndexQuery>>();
        saveTimer = new Timer("saveTimer");
        pullTimer = new Timer("pullTimer");
        killTimer = new Timer("killTimer");
    }

    public void save(IndexQuery query) {
        queries.add(query);
    }

    public void start() {
        System.out.println("Starting analysis repository...");
        TimerTask save = new TimerTask() {

            @Override
            public void run() {
                int count = 0;
                if (!pullStopped) {
                    while (buckets.size() > 1 && count < 100) {
                        List<IndexQuery> queryList = buckets.poll();
                        template.bulkIndex(queryList);
                        count++;
                    }
                    count = 0;
                } else {
                    while (!buckets.isEmpty() && count < 100) {
                        List<IndexQuery> queryList = buckets.poll();
                        template.bulkIndex(queryList);
                    }
                    count = 0;
                }
            }
        };
        saveTimer.schedule(save, 100, 200);

        TimerTask pull = new TimerTask() {

            @Override
            public void run() {
                while (!queries.isEmpty()) {
                    pullStopped = false;
                    ArrayList<IndexQuery> bucket = new ArrayList<IndexQuery>();
                    buckets.add(bucket);
                    /*System.out.println(
                            "Adding bucket number - " + buckets.size());*/
                    for (int i = 0; i < 10000; i++) {
                        if(queries.isEmpty()) {
                            pullStopped = true;
                            break;
                        }
                        bucket.add(queries.poll());
                    }
                    /*System.out.println("Added bucket! Size = " + bucket.size());*/
                }
            }
        };
        pullTimer.schedule(pull, 20, 200);
    }

    public void terminate() {
        TimerTask kill = new TimerTask() {

            @Override
            public void run() {
                /*System.out.println("Kill timer activated!");*/
                if (buckets.isEmpty()) {
                    saveTimer.cancel();
                    System.out.println("Killed save task..");
                    pullTimer.cancel();
                    killTimer.cancel();
                }
            }
        };

        killTimer.schedule(kill, 5000, 5000);
    }

}
