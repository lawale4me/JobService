/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.entities.Credential;
import com.entities.Discipline;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

/**
 *
 * @author Ahmed
 */
@Stateless
public class CredentialRepo extends AbstractDAO<Credential> {
    
    private static final Logger logger = Logger.getLogger(AddressBookRepo.class.getName());

    public CredentialRepo() {
        super(Credential.class);
    }        

        
    public Credential getCredential(Integer cust) {
         EntityManager em = getEntityManager();
        List<Credential> cred = null;
        try {
            cred = (List<Credential>) em.createNamedQuery("Credential.findByCustomer").setParameter("customer", cust).getResultList();
            if (cred != null&&!cred.isEmpty()) {
                return cred.get(0);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "exception caught", e);
        }
        return null;
    }

    public Credential findByEmail(String email) {
         EntityManager em = getEntityManager();
        List<Credential> cred = null;
        try {
            cred = (List<Credential>) em.createNamedQuery("Credential.findByEmail").setParameter("email", email).getResultList();
            if (cred != null&&!cred.isEmpty()) {
                return cred.get(0);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "exception caught", e);
        }
        return null;
    }

    public Credential findByPhone(String phone) {
         EntityManager em = getEntityManager();
        List<Credential> cred = null;
        try {
            cred = (List<Credential>) em.createNamedQuery("Credential.findByPhone").setParameter("phone", phone).getResultList();
            if (cred != null&&!cred.isEmpty()) {
                return cred.get(0);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "exception caught", e);
        }
        return null;
    }

    public List<Credential> findByDiscipline(Discipline discipline) {
        EntityManager em = getEntityManager();
        List<Credential> cred = null;
        try {
            cred = (List<Credential>) em.createNamedQuery("Credential.findByDisc").setParameter("discipline", discipline).getResultList();
            if (cred != null&&!cred.isEmpty()) {
                return cred;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "exception caught", e);
        }
        return null;
    }
    
    public List<Credential> findActiveDiscipline(Discipline discipline) {
        EntityManager em = getEntityManager();
        List<Credential> cred = null;
        try {
            cred = (List<Credential>) em.createNamedQuery("Credential.findByDisc").setParameter("discipline", discipline).getResultList();
            if (cred != null&&!cred.isEmpty()) {
                return cred;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "exception caught", e);
        }
        return null;
    }
    
    
}
