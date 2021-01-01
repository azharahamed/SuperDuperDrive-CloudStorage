package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private NotesService notesService;
    private UserService userService;
    private CredentialsService credentialsService;

    public HomeController(NotesService notesService, UserService userService, CredentialsService credentialsService){
        this.userService = userService;
        this.notesService = notesService;
        this.credentialsService = credentialsService;
    }

    @GetMapping
    public String getHomePage(Notes notes, Files files, Credentials credentials, Model model){
        Integer loggedUserId = userService.getCurrentUserId();
        if(null != loggedUserId){
            model.addAttribute("noteslist", this.notesService.getNotes(loggedUserId));
            model.addAttribute("credentiallist", this.credentialsService.getCredentials(loggedUserId));
        }
        return "home";
    }

}
