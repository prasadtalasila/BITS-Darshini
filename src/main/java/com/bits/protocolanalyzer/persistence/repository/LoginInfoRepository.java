package com.bits.protocolanalyzer.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bits.protocolanalyzer.persistence.entity.LoginInfoEntity;

public interface LoginInfoRepository
        extends JpaRepository<LoginInfoEntity, Long> {

    public LoginInfoEntity findByEmail(String email);

}
