package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NotesController {

    private UserService userService;
    private NotesService notesService;

    public NotesController(UserService userService, NotesService notesService) {
        this.userService = userService;
        this.notesService = notesService;
    }

    @PostMapping("addnote")
    public String saveNotes(Notes notes, Model model) {
        Integer loggedUserId = userService.getCurrentUserId();
        if(null != notes && null == notes.getNoteid() && null != loggedUserId){
            if(0 != this.notesService.addNotes(notes, loggedUserId)){
                model.addAttribute("result", "success");
            }
        } else if(null != notes && null != notes.getNoteid() && null != loggedUserId){
            if(0 != this.notesService.updateNotes(notes, loggedUserId)){
                model.addAttribute("result","success");
            }
        }
        return "result";
    }

    @GetMapping(value="/deletenote/{noteid}")
    public String deleteNote(@PathVariable Integer noteid, Model model){
        Integer loggedUserId = userService.getCurrentUserId();
        if(null != loggedUserId && notesService.deleteNote(noteid)){
            model.addAttribute("result", "success");
        }
        return "result";
    }

}
