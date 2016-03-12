/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.mvc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bits.protocolanalyzer.persistence.entity.LoginInfoEntity;
import com.bits.protocolanalyzer.persistence.repository.LoginInfoRepository;

/**
 *
 * @author amit
 * @author crygnus
 */
@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    LoginInfoRepository loginInfoRepo;

    @RequestMapping
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView("home");
        return mav;
    }

    @RequestMapping(value = "signin", method = RequestMethod.POST)
    public @ResponseBody String signin(@RequestBody LoginInfoEntity loginInfo,
            HttpServletRequest request) {
        String email = loginInfo.getEmail();
        LoginInfoEntity lie = loginInfoRepo.findByEmail(email);
        if (lie == null) {
            return "failure";
        } else if(!lie.getPassword().equals(loginInfo.getPassword())) {
            return "failure";
        }
        return "success";
    }

    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public @ResponseBody String signup(@RequestBody LoginInfoEntity loginInfo,
            HttpServletRequest request) {
        LoginInfoEntity lie = loginInfoRepo.findByEmail(loginInfo.getEmail());
        if (lie == null) {
            loginInfoRepo.save(loginInfo);
            return "success";
        } else {
            return "failure";
        }
    }

    @RequestMapping("experiment")
    public ModelAndView experimentDetails() {
        ModelAndView mav = new ModelAndView("first");
        return mav;
    }
}
