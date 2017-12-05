/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sait.dataaccess;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import sait.businesslogic.UserServices;
import sait.dataaccess.DBUtil;
import sait.dataaccess.NotesDBException;
import sait.domainmodel.PasswordChangeRequest;
import sait.domainmodel.User;

/**
 *
 * @author 733196
 */
public class RetrieveDB {
    
    public PasswordChangeRequest get(String hashed) throws NotesDBException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            PasswordChangeRequest pcr = em.find(PasswordChangeRequest.class, hashed);
            return pcr;
        } catch (Exception ex) {
            Logger.getLogger(RetrieveDB.class.getName()).log(Level.SEVERE, "Cannot read pcrs", ex);
            throw new NotesDBException("Error getting pcr");
        } finally {
            em.close();
        }
    }
    
     public int insert(PasswordChangeRequest psr) throws NotesDBException {
         

        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        User owner = psr.getUserID();
        owner.getPasswordChangeRequestCollection().add(psr);
        
        try {
            trans.begin();
            em.persist(psr);
            em.merge(owner);
            trans.commit();
            
            return 1;
        } catch (Exception ex) {
            Logger.getLogger(RetrieveDB.class.getName()).log(Level.SEVERE, "Cannot insert " + psr.toString(), ex);
            throw new NotesDBException("Error inserting psr");
        } finally {
            em.close();
        }
    }
     
     public int delete(PasswordChangeRequest psr) throws NotesDBException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        User owner = psr.getUserID();
        owner.getPasswordChangeRequestCollection().remove(psr);

        try {
            trans.begin();
            em.merge(owner);
            em.remove(em.merge(psr));
            
            trans.commit();
            return 1;
        } catch (Exception ex) {
            Logger.getLogger(RetrieveDB.class.getName()).log(Level.SEVERE, "Cannot delete " + psr.toString(), ex);
            throw new NotesDBException("Error deleting psr");
        } finally {
            em.close();
        }
    }
}
