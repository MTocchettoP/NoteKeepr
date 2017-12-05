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
import javax.persistence.TypedQuery;
import sait.domainmodel.AwaitingRegistration;
import sait.domainmodel.User;
import sait.businesslogic.UserServices;

/**
 *
 * @author 733196
 */
public class AwaitingDB {
    
    
    public AwaitingRegistration get(String hashed) throws NotesDBException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        
        try {
            TypedQuery<AwaitingRegistration> query = em.createNamedQuery("AwaitingRegistration.findByHashed", AwaitingRegistration.class);
            query.setParameter("hashed", hashed);
            AwaitingRegistration ar = query.getSingleResult();
            
            
            
            return ar;
        } catch (Exception ex) {
            Logger.getLogger(AwaitingDB.class.getName()).log(Level.SEVERE, "Cannot read ar", ex);
            throw new NotesDBException("Error getting ar");
        } finally {
            em.close();
        }
    }
    
    public int insert(AwaitingRegistration ar) throws NotesDBException {

        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.persist(ar);
            trans.commit();

            return 1;
        } catch (Exception ex) {
            Logger.getLogger(AwaitingDB.class.getName()).log(Level.SEVERE, "Cannot insert " + ar.toString(), ex);
            throw new NotesDBException("Error inserting ar");
        } finally {
            em.close();
        }
    }

    public int delete(AwaitingRegistration ar) throws NotesDBException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        User owner = ar.getUser();

        try {
            trans.begin();
            em.merge(owner);
            em.remove(em.merge(ar));

            trans.commit();
            return 1;
        } catch (Exception ex) {
            Logger.getLogger(AwaitingDB.class.getName()).log(Level.SEVERE, "Cannot delete " + ar.toString(), ex);
            throw new NotesDBException("Error deleting ar");
        } finally {
            em.close();
        }
    }
}
