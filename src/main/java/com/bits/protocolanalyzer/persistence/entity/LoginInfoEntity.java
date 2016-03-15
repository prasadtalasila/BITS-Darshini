package com.bits.protocolanalyzer.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "login_info")
public class LoginInfoEntity {

    @Id
    private String email;

    private String password;

    private String loginHash;
}
