package com.udacity.jwdnd.course1.cloudstorage.Mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CredentialsMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    List<Credentials> getUserCredentials(Integer userid);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES (#{url},#{username},#{key},#{password},#{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int addCredentials(Credentials credentials);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    Credentials getCredentials(Integer credentialid);

    @Update("UPDATE CREDENTIALS SET url=#{url}, key=#{key}, password=#{password}, username=#{username} WHERE credentialid=#{credentialid}")
    int updateCredentails(Credentials credentials);

    @Delete("DELETE CREDENTIALS WHERE credentialid = #{credentialid}")
    void deleteCredentials(Integer credentialid);
}
