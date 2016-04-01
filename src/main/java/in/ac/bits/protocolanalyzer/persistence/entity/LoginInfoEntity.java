package in.ac.bits.protocolanalyzer.persistence.entity;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(indexName = "protocol", type = "credentials", shards=1, replicas=0)
public class LoginInfoEntity {

    @Id
    private String id;

    private String email;

    private String password;

    private String loginHash;
}
