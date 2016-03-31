/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.ac.bits.protocolanalyzer.persistence.entity;

import java.io.Serializable;

import javax.persistence.Id;

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
@Document(indexName = "protocol", type = "network")
public class NetworkAnalyzerEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    private long packetId;

}
