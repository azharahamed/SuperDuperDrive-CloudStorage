package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

@Controller
public class FileController {

    private UserService userService;
    private FileService fileService;

    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PostMapping("/uploadfile")
    public String saveFiles(Files files, @RequestParam("fileUpload") MultipartFile file, Model model){
        Integer loggedUserId = userService.getCurrentUserId();
        if(null != loggedUserId){
            if(null == files.getFileId()){
                fileService.addFiles(file, loggedUserId);
                model.addAttribute("result","success");
            }
        }
        return "result";
    }

    @GetMapping("/deletefile/{fileid}")
    public String deleteFile(@PathVariable Integer fileid, Model model){
        Integer loggedUserId = userService.getCurrentUserId();
        if(null != loggedUserId && fileid != null){
            fileService.deleteFile(fileid);
            model.addAttribute("result","success");
        }
        return "result";
    }

    @GetMapping("/viewfile/{fileid}")
    public ResponseEntity<Resource> viewFile(@PathVariable Integer fileid, Model model){
        Integer loggerUserId = userService.getCurrentUserId();
        Files file = fileService.getFile(fileid, loggerUserId);
        InputStreamResource resource =
                new InputStreamResource(new ByteArrayInputStream(file.getFiledata()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + file.getFilename())
                .body(resource);

    }


}
