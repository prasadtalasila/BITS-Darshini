/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.mvc.formatter;

import java.text.ParseException;
import java.util.Locale;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import com.bits.protocolanalyzer.persistence.entity.LinkAnalyzerEntity;
import com.bits.protocolanalyzer.repository.LinkAnalyzerRepository;

/**
 *
 * @author amit
 */
public class LinkFormatter implements Formatter<LinkAnalyzerEntity> {

    @Autowired
    private LinkAnalyzerRepository linkAnalyzerRepository;

    public String print(LinkAnalyzerEntity object, Locale locale) {
        return Objects.toString(object.getId());
    }

    public LinkAnalyzerEntity parse(String string, Locale locale)
            throws ParseException {
        return linkAnalyzerRepository.findOne(Long.valueOf(string));
    }

}
