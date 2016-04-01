/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.ac.bits.protocolanalyzer.persistence.entity;

import java.sql.Timestamp;

import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author amit
 * @author crygnus
 */
@Getter
@Setter
@Document(indexName = "protocol", type = "link", shards=1, replicas=0)
public class LinkAnalyzerEntity {

    private long packetId;

    private Timestamp timestamp;

}
