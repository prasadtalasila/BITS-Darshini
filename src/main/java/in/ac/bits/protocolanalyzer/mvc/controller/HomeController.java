/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.ac.bits.protocolanalyzer.mvc.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import in.ac.bits.protocolanalyzer.persistence.entity.ExperimentDetails;
import in.ac.bits.protocolanalyzer.persistence.entity.LoginInfoEntity;
import in.ac.bits.protocolanalyzer.persistence.repository.DetailsRepository;
import in.ac.bits.protocolanalyzer.persistence.repository.LoginInfoRepository;
import in.ac.bits.protocolanalyzer.utils.Security;

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

    @Autowired
    DetailsRepository detailsRepo;

    @RequestMapping
    public String home() {
        return "view/index.html";
    }

    @RequestMapping(value = "auth", method = RequestMethod.GET)
    public @ResponseBody String authenticate(
            @RequestParam("loginHash") String loginHash,
            @RequestParam("user") String email) {
        LoginInfoEntity lie = loginInfoRepo.findByEmail(email);
        if (lie == null) {
            return "failure";
        } else if (!lie.getLoginHash().equals(loginHash)) {
            return "failure";
        } else {
            System.out.println("Login id entity = \n" + lie.toString());
            System.out.println("ID = " + lie.getId());
            return "success";
        }

    }

    @RequestMapping(value = "sessioninfo", method = RequestMethod.POST)
    public @ResponseBody String saveSessionInfo(
            @RequestBody ExperimentDetails details,
            HttpServletRequest request) {
        JSONObject response = new JSONObject();
        if (detailsRepo
                .findByExperimentName(details.getExperimentName()) == null) {
            response.put("status", "success");
            response.put("remark", "none");
            detailsRepo.save(details);
        } else {
            response.put("status", "failure");
            response.put("remark",
                    "Experiment name already exists. Should be unique!");
        }
        return response.toString();
    }

    @RequestMapping(value = "signin", method = RequestMethod.POST)
    public @ResponseBody String signin(@RequestBody LoginInfoEntity loginInfo,
            HttpServletRequest request) {
        String email = loginInfo.getEmail();
        JSONObject response = new JSONObject();
        String hashStr = "";
        LoginInfoEntity lie = loginInfoRepo.findByEmail(email);
        if (lie == null) {
            response.put("status", "failure");
        } else if (!lie.getPassword()
                .equals(Security.createHash(loginInfo.getPassword()))) {
            response.put("status", "failure");
        } else {
            response.put("status", "success");
            hashStr = Security.createHash(
                    loginInfo.getEmail() + ";" + loginInfo.getPassword());
            lie.setLoginHash(hashStr);
            loginInfoRepo.save(lie);
        }
        response.put("loginHash", hashStr);
        return response.toString();

    }

    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public @ResponseBody String signup(@RequestBody LoginInfoEntity loginInfo,
            HttpServletRequest request) {
        LoginInfoEntity lie = loginInfoRepo.findByEmail(loginInfo.getEmail());
        if (lie == null) {
            loginInfo.setPassword(Security.createHash(loginInfo.getPassword()));
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
