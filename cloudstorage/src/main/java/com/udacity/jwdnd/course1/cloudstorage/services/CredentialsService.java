package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.springframework.stereotype.Service;


import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialsService {

    private CredentialsMapper credentialsMapper;
    private EncryptionService encryptionService;

    public CredentialsService(CredentialsMapper credentialsMapper, EncryptionService encryptionService) {
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
    }

    public int addCredentials(Credentials credentials, Integer loggedUserId){
        if(null != loggedUserId) {
            credentials.setUserid(loggedUserId);
            setSaltAndPassword(credentials);
            return credentialsMapper.addCredentials(credentials);
        }
        return 0;
    }

    private void setSaltAndPassword(Credentials credentials) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        credentials.setKey(encodedSalt);
        credentials.setPassword(encryptionService.encryptValue(credentials.getPassword(),credentials.getKey()));
    }

    public List<Credentials> getCredentials(Integer userId) {
        if(null != userId){
            List<Credentials> credentials = credentialsMapper.getUserCredentials(userId);
            for(Credentials credential : credentials){
                credential.setCryptedPassword(credential.getPassword());
                credential.setPassword(encryptionService.decryptValue(credential.getPassword(),credential.getKey()));
            }
            return credentials;
        }
        return new ArrayList<Credentials>();
    }

    public int updateCredentials(Credentials credentials) {
            setSaltAndPassword(credentials);
            return credentialsMapper.updateCredentails(credentials);
    }

    public boolean deleteCredentials(Integer credentialid) {
        if(null != this.credentialsMapper.getCredentials(credentialid)){
            this.credentialsMapper.deleteCredentials(credentialid);
            return true;
        }
        return false;
    }
}
