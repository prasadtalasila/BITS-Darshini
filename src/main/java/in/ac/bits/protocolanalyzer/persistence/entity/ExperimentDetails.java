package in.ac.bits.protocolanalyzer.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(indexName = "protocol", type = "info", shards = 1, replicas = 0)
public class ExperimentDetails {

    @Id
    private String id;

    private String experimentName;

    private String description;

    private String experimenter;

    private String testid;

    private String pcapfiles;

}
