package in.ac.bits.protocolanalyzer.analyzer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import in.ac.bits.protocolanalyzer.persistence.entity.MasterEntity;

@Component
public class TimedStorage {

    @Autowired
    private WebApplicationContext context;

    private Timer timer;
    private Map<Class<? extends ElasticsearchRepository<? extends MasterEntity, String>>, Set<MasterEntity>> entityMap;

    public void configure(
            Class<? extends ElasticsearchRepository<MasterEntity, String>> clazz,
            MasterEntity entity) {
        Set<MasterEntity> entitySet = new HashSet<MasterEntity>();
        entitySet.add(entity);
        entityMap.put(clazz, entitySet);

    }

    public synchronized void saveEntities(
            Class<? extends ElasticsearchRepository<? extends MasterEntity, String>> clazz,
            MasterEntity entity) {
        Set<MasterEntity> entitySet = entityMap.get(clazz);
        entitySet.add(entity);
        entityMap.put(clazz, entitySet);

    }

    public void start() {
        timer = new Timer();
        TimerTask saveToDB = new TimerTask() {
            @Override
            public void run() {
                /*for (Entry<Class<? extends ElasticsearchRepository<? extends MasterEntity, String>>, Set<MasterEntity>> entry : entityMap
                        .entrySet()) {
                    Class<? extends ElasticsearchRepository<? extends MasterEntity, String>> clazz = entry
                            .getKey();
                    ElasticsearchRepository<? extends MasterEntity, String> repo = context
                            .getBean(clazz);
                    Set<? extends MasterEntity> entities = entry.getValue();
                    Iterator<? extends MasterEntity> iterator = entities
                            .iterator();
                    while (iterator.hasNext()) {
                        repo.save(iterator.next());
                    }
                }*/
            }
        };
        timer.schedule(saveToDB, 100, 10);

    }

    public Timer getTimer() {
        return this.timer;
    }
}
