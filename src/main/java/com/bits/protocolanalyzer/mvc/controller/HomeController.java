/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bits.protocolanalyzer.mvc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bits.protocolanalyzer.analyzer.LoginInfo;

/**
 *
 * @author amit
 */
@Controller
@RequestMapping("/")
public class HomeController {

    @RequestMapping
    public ModelAndView home() {
        /*
         * ClassLoader cl = ClassLoader.getSystemClassLoader(); URL[] urls =
         * ((URLClassLoader) cl).getURLs();
         */
        ModelAndView mav = new ModelAndView("home");
        /* mav.addObject("paths", urls); */
        return mav;
    }

    @RequestMapping(value = "signin", method = RequestMethod.POST)
    public @ResponseBody String login(@RequestBody LoginInfo loginInfo,
            HttpServletRequest request) {
        String email = loginInfo.getEmail();
        String password = loginInfo.getPassword();
        System.out.println("Email = " + email + " and Password = " + password);
        return "success";
    }

    @RequestMapping("experiment")
    public ModelAndView experimentDetails() {
        ModelAndView mav = new ModelAndView("first");
        return mav;
    }
}
