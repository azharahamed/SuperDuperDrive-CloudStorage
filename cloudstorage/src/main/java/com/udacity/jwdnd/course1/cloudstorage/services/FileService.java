package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private FilesMapper filesMapper;

    public FileService(FilesMapper filesMapper) {
        this.filesMapper = filesMapper;
    }

    public List<Files> getFiles(Integer loggedUserId) {
        return filesMapper.getuserFiles(loggedUserId);
    }

    public boolean addFiles(MultipartFile file, Integer loggerUserId){
        try{
            Files newFile = new Files();
            newFile.setUserid(loggerUserId);
            newFile.setFilename(file.getOriginalFilename());
            newFile.setFilesize(file.getSize());
            newFile.setContenttype(file.getContentType());
            newFile.setFiledata(file.getBytes());
            filesMapper.addFiles(newFile);
        } catch (IOException e){
            return false;
        }
        return false;
    }

    public void updateFiles(Files files) {
        filesMapper.updateFile(files);
    }

    public Files getFile(Integer fileId, Integer userid) {
        return filesMapper.getFiles(fileId, userid);
    }

    public void deleteFile(Integer fileid) {
        filesMapper.deleteFile(fileid);
    }
}
