/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author amit
 */
@Component
public class PrototypeBeanFactory {
	
	@Autowired
	private ApplicationContext context;
	
	public Object create(Class<?> c){
		return context.getBean(c);
	}
}
