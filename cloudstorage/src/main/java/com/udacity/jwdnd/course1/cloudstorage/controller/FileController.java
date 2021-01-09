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
            try {
                if(null == files.getFileId()){
                        if(fileService.isDuplicateFileName(file.getOriginalFilename())){
                            model.addAttribute("result","error");
                            model.addAttribute("message","Duplicate File Names are not allowed");
                            return "result";
                        }
                        if(fileService.addFiles(file, loggedUserId)){
                            model.addAttribute("result","success");
                        } else {
                            model.addAttribute("result","error");
                            model.addAttribute("message","File upload failed - Please check whether the file is not empty and in correct format with allowed size!");
                        }
                }
            } catch (Exception e) {
                model.addAttribute("result","error");
                model.addAttribute("message","File upload failed - Please check whether the file is not empty and in correct format with allowed size!");
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
