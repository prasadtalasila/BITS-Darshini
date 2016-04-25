package in.ac.bits.protocolanalyzer.persistence.repository;

import javax.annotation.Resource;

import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.node.NodeBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "in.ac.bits.protocolanalyzer.persistence.repository")
@PropertySource(value = "classpath:META-INF/elasticsearch.properties")
public class ElasticsearchConfig {

    @Resource
    private Environment environment;

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        String clusterName = environment
                .getProperty("elasticsearch.cluster.name");
        String nodeName = environment.getProperty("elasticsearch.node.name");
        ImmutableSettings.Builder settingsBuilder = ImmutableSettings
                .settingsBuilder().put("cluster.name", clusterName)
                .put("node.name", nodeName).put("node.data", true)
                .put("index.number_of_shards", 1)
                .put("index.number_of_replicas", 0);
        NodeBuilder builder = NodeBuilder.nodeBuilder().local(true)
                .settings(settingsBuilder.build());
        return new ElasticsearchTemplate(builder.node().client());
    }

}
