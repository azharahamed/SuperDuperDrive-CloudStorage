package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CredentailController {

    private CredentialsService credentialsService;
    private UserService userService;

    public CredentailController(CredentialsService credentialsService, UserService userService) {
        this.credentialsService = credentialsService;
        this.userService = userService;
    }

    @PostMapping("/addcredential")
    public String saveCredentails(Credentials credentials, Model model){
        Integer loggedUserId = userService.getCurrentUserId();
        if(null != credentials && null != loggedUserId){
            if((null != credentials.getCredentialid()) && (0 != this.credentialsService.updateCredentials(credentials))){
                model.addAttribute("result","success");
            } else if (0 != this.credentialsService.addCredentials(credentials,loggedUserId)){
                model.addAttribute("result","success");
            }
        }
        return "result";
    }

    @GetMapping("/deletecredential/{credentialid}")
    public String deleteCredentails(@PathVariable Integer credentialid, Model model){
        Integer loggedUserId = userService.getCurrentUserId();
        if(null != loggedUserId && this.credentialsService.deleteCredentials(credentialid)){
            model.addAttribute("result","success");
        }
        return "result";
    }
}
