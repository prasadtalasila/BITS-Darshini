package in.ac.bits.protocolanalyzer.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Corresponding entity class for ethernet analysis. Corresponding table columns
 * are source_addr, destination_addr and ether_type
 * 
 * @author crygnus
 *
 */

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "ethernet_analysis")
public class EthernetEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="eth_entity_seq")
    @SequenceGenerator(name="eth_entity_seq", sequenceName="ETH_ENTITY_SEQ")
    private Long id;

    @OneToOne
    private PacketIdEntity packetIdEntity;

    @Column(name = "source_addr")
    private String sourceAddr;

    @Column(name = "destination_addr")
    private String dstAddr;

    @Column(name = "ether_type")
    private String etherType;

}
