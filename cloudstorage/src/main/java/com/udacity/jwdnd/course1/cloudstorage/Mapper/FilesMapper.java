package com.udacity.jwdnd.course1.cloudstorage.Mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface FilesMapper {

    @Select("SELECT * FROM FILES WHERE fileid = #{fileid} AND userid = #{userid}")
    Files getFiles(Integer fileid, Integer userid);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{filename},#{contenttype},#{filesize},#{userid},#{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int addFiles(Files files);

    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<Files> getuserFiles(Integer userid);

    @Update("UPDATE FILES SET filename = #{filename}, contenttype=#{contenttype}, filesize = #{filesize}, userid=#{userid}, filedata=#{filedate} where fileid=#{fileid}")
    void updateFile(Files files);

    @Select("SELECT * FROM files WHERE filename = #{filename}")
    public Files findByFilename(String filename);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileid}")
    void deleteFile(Integer fileid);
}
