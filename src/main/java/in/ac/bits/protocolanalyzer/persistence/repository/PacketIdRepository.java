/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.ac.bits.protocolanalyzer.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.ac.bits.protocolanalyzer.persistence.entity.PacketIdEntity;

/**
 *
 * @author amit
 */
public interface PacketIdRepository extends JpaRepository<PacketIdEntity, Long> {

    @Query(nativeQuery = true, value = "SELECT last_value FROM packet_id_packet_id_seq")
    public long findSequenceValue();

}
