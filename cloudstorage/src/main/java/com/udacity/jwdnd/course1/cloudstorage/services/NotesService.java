package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.Mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotesService {

    private NotesMapper notesMapper;

    public NotesService(NotesMapper notesMapper, UserMapper userMapper) {
        this.notesMapper = notesMapper;
    }

    public int addNotes(Notes notes, Integer loggedUserId){
        if(null != loggedUserId) {
            notes.setUserid(loggedUserId);
            return notesMapper.addNotes(notes);
        }
        return 0;
    }

    public List<Notes> getNotes(Integer userId) {
        if(null != userId){
            return notesMapper.getUserNotes(userId);
        }
        return new ArrayList<Notes>();
    }

    public Notes getNote(Integer noteid){
        if(null != noteid){
            return notesMapper.getNote(noteid);
        }
        return null;
    }

    public boolean deleteNote(Integer noteid) {
        if(null != getNote(noteid)){
            try {
                notesMapper.deleteNote(noteid);
                return true;
            } catch (Exception e){
                return false;
            }
        }
        return false;
    }

    public int updateNotes(Notes notes, Integer loggedUserId) {
        if(null != loggedUserId){
           return notesMapper.updateNotes(notes);
        }
        return 0;
    }
}
