/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.mvc.repository;

import com.bits.protocolanalyzer.persistence.entity.NetworkAnalyzer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author amit
 */
public interface NetworkAnalyzerRepository extends JpaRepository<NetworkAnalyzer, Long>{
	
}
