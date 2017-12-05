/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sait.dataaccess;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import sait.domainmodel.Company;

/**
 *
 * @author 733196
 */
public class CompanyDB {
    
    public List<Company> getAll() throws NotesDBException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {

            List<Company> companies = em.createNamedQuery("Company.findAll", Company.class).getResultList();
            return companies;
        } catch (Exception ex) {
            Logger.getLogger(NoteDB.class.getName()).log(Level.SEVERE, "Cannot get list of companies", ex);
            throw new NotesDBException("Error getting Companies");
        } finally {
            em.close();
        }
    }
    
    public Company get(String company) throws NotesDBException {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {

            Company comp = em.find(Company.class, Integer.parseInt(company));
            return comp;
        } catch (Exception ex) {
            Logger.getLogger(NoteDB.class.getName()).log(Level.SEVERE, "Cannot get list of companies", ex);
            throw new NotesDBException("Error getting Companies");
        } finally {
            em.close();
        }
    }
    
    public int update(Company company) throws Exception {

        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.merge(company);
            trans.commit();
            return 1;
        } catch (Exception ex) {
            Logger.getLogger(NoteDB.class.getName()).log(Level.SEVERE, "Cannot update " + company.toString(), ex);
            throw new NotesDBException("Error updating company");
        } finally {
            em.close();
        }
    }

    public int add(Company company) throws Exception {

        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();
            em.persist(company);
            trans.commit();
            return 1;
        } catch (Exception ex) {
            Logger.getLogger(NoteDB.class.getName()).log(Level.SEVERE, "Cannot insert " + company.toString(), ex);
            throw new NotesDBException("Error inserting company");
        } finally {
            em.close();
        }

    }
    
}
