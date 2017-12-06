/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sait.businesslogic;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.naming.NamingException;
import sait.dataaccess.AwaitingDB;
import sait.dataaccess.NotesDBException;
import sait.domainmodel.AwaitingRegistration;
import sait.domainmodel.User;

/**
 *
 * @author 733196
 */
public class AwaitingService {
    
    private AwaitingDB awaitingDB;
    
    public AwaitingService(){
        awaitingDB = new AwaitingDB();
    }
     public void startRegistration(String email, User toRetrive, String path) throws NotesDBException {

        String uuid = UUID.randomUUID().toString();
        String hashed = hashUUID(uuid);
        AwaitingRegistration ar = new AwaitingRegistration();
        ar.setHashed(hashed);
        ar.setUserID(toRetrive.getUsername());
        ar.setUser(toRetrive);
        awaitingDB.insert(ar);
        sendRegistrationEmail(toRetrive, uuid,path);
    }
    
     public User completeRegistration(String uuid) throws NotesDBException{
         
        String hashed = hashUUID(uuid);
        AwaitingRegistration ar = awaitingDB.get(hashed);
        if (ar == null) {          
            return null;
        }
        awaitingDB.delete(ar);
        User user = ar.getUser();
        user.setRegistered(true);
        return user;
     }
    
    private void sendRegistrationEmail(User user, String uuid, String path) {
        WebMailService wms = new WebMailService();
        HashMap<String,String> content = new HashMap<String,String>();
        content.put("link", "cprg352notekeepr2017.tk/register?complete=" + uuid);
        path = path+ "/emailtemplates/completeregistration.html";
        
        try {
            wms.sendMail(user.getEmail(), "Complete Registration",path,content);
        } catch (MessagingException ex) {
            Logger.getLogger(PasswordChangeService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(PasswordChangeService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PasswordChangeService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private String hashUUID(String uuid) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(PasswordChangeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte[] hash = digest.digest(uuid.getBytes(StandardCharsets.UTF_8));
        return new String(Base64.getEncoder().encode(hash));
    }
}
