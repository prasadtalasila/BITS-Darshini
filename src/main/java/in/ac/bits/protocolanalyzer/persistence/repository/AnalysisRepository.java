package in.ac.bits.protocolanalyzer.persistence.repository;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Component;

@Component
public class AnalysisRepository {

    @Autowired
    private SaveRepository saveRepo;

    private ExecutorService executorService;
    private Queue<IndexQuery> queries;

    private Timer pullTimer;
    private boolean isFinished = false;

    private ArrayList<IndexQuery> currentBucket;
    private int bucketCapacity = 50000;

    public void configure() {
        this.queries = new ConcurrentLinkedQueue<IndexQuery>();
        executorService = Executors.newFixedThreadPool(2);
        currentBucket = new ArrayList<IndexQuery>();
        pullTimer = new Timer("pullTimer");
        saveRepo.configure();
    }

    public void isFinished() {
        this.isFinished = true;
    }

    public void save(IndexQuery query) {
        queries.add(query);
    }

    public void start() {
        System.out.println("Starting analysis repository...");
        TimerTask pull = new TimerTask() {

            @Override
            public void run() {
                while (!queries.isEmpty()) {
                    int size = currentBucket.size();
                    if (size < bucketCapacity) {
                        while (!queries.isEmpty() && size < bucketCapacity) {
                            currentBucket.add(queries.poll());
                            size++;
                        }
                    } else {
                        saveRepo.setBucket(currentBucket);
                        if (!saveRepo.isRunning()) {
                            executorService.execute(saveRepo);
                        }
                        currentBucket = new ArrayList<IndexQuery>();
                    }
                }
                if (isFinished) {
                    saveRepo.setBucket(currentBucket);
                    if (!saveRepo.isRunning()) {
                        executorService.execute(saveRepo);
                    }
                    isFinished = false;
                }
            }
        };
        pullTimer.schedule(pull, 0, 10);
    }

}
