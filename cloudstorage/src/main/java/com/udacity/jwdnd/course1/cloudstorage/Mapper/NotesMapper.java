package com.udacity.jwdnd.course1.cloudstorage.Mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface NotesMapper {

    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    List<Notes> getUserNotes(Integer userid);

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteid}")
    Notes getNote(Integer noteid);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES (#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int addNotes(Notes notes);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    void deleteNote(Integer noteid);

    @Update("UPDATE NOTES SET notetitle = #{notetitle}, notedescription = #{notedescription} WHERE noteid = #{noteid}")
    int updateNotes(Notes notes);
}
