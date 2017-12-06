/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sait.businesslogic;

import sait.dataaccess.NotesDBException;
import sait.dataaccess.RetrieveDB;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import sait.domainmodel.PasswordChangeRequest;
import sait.domainmodel.User;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import javax.mail.MessagingException;
import javax.naming.NamingException;

/**
 *
 * @author 733196
 */
public class PasswordChangeService {

    private final static long MILLIS_PER_DAY = 24 * 60 * 60 * 1000L;
    private RetrieveDB retDB;

    public PasswordChangeService() {
        retDB = new RetrieveDB();
    }
    
    public PasswordChangeRequest get(String uuid) throws NotesDBException{
        String hashed = hashUUID(uuid);
        PasswordChangeRequest pcr = retDB.get(hashed);
        return pcr;
    }
    
    public void delete (PasswordChangeRequest pcr){
        try {
            retDB.delete(pcr);
        } catch (NotesDBException ex) {
            Logger.getLogger(PasswordChangeService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void startPasswordRetrieval(String email, User toRetrive, String path) throws NotesDBException {

        String uuid = UUID.randomUUID().toString();
        String hashed = hashUUID(uuid);

        Date now = new Date();
        PasswordChangeRequest psr = new PasswordChangeRequest(hashed, now);
        psr.setUserID(toRetrive);
        retDB.insert(psr);
        sendRetrievalEmail(toRetrive, uuid,path);
    }

    public User completePasswordRetrieval(String uuid) throws NotesDBException {

        String hashed = hashUUID(uuid);
        PasswordChangeRequest pcr = retDB.get(hashed);
        Date now = new Date();
        if (pcr == null || Math.abs(now.getTime() - pcr.getTime().getTime()) > MILLIS_PER_DAY) {          
            return null;
        }
        return pcr.getUserID();
    }

    private void sendRetrievalEmail(User user, String uuid, String path) {
        WebMailService wms = new WebMailService();
        HashMap<String,String> content = new HashMap<String,String>();
        content.put("link", "cprg352notekeepr2017.tk/forgot?ret=" + uuid);
        path = path+ "/emailtemplates/retrieveLink.html";
        
        try {
            wms.sendMail(user.getEmail(), "Password Retrieval",path,content);
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
