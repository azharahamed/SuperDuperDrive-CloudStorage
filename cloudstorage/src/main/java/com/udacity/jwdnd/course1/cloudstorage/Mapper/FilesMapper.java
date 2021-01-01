package com.udacity.jwdnd.course1.cloudstorage.Mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FilesMapper {

    @Select("SELECT * FROM FILES WHERE fileid = #{fileid}")
    Files getFiles(Integer fileid);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{filename},#{contenttype},#{filesize},#{userid},#{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileid")
    int addFiles(Files files);

}
