/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.entities.Addressbook;
import com.entities.Customer;
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
public class AddressBookRepo   extends AbstractDAO<Addressbook>{

    private static final Logger logger = Logger.getLogger(AddressBookRepo.class.getName());

    public AddressBookRepo() {
        super(Addressbook.class);
    }
    

    public List<Addressbook> findByBatchId(String batchId) {
        EntityManager em = getEntityManager();
        List<Addressbook> users;
        try {
            users = (List<Addressbook>) em.createNamedQuery("Addressbook.finByBatchId").setParameter("batchId", batchId).getResultList();
            if (users != null&&!users.isEmpty()) {
                return users;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "exception caught", e);
        }
        return null;
    }
    
    public Addressbook findByPhonenumber(String phonenumber) {
        EntityManager em = getEntityManager();
        List<Addressbook> user = null;
        try {
            user = (List<Addressbook>) em.createNamedQuery("Addressbook.findByPhonenumber").setParameter("phonenumber", phonenumber).getResultList();
            if (user != null&&!user.isEmpty()) {
                return user.get(0);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "exception caught", e);
        }
        return null;
    }

    public List<Addressbook> getContacts(Customer ownerId) {
        EntityManager em = getEntityManager();
        List<Addressbook> users;
        try {
            users = (List<Addressbook>) em.createNamedQuery("Addressbook.findByOwnerId").setParameter("ownerId", ownerId).getResultList();
            if (users != null) {
                return users;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "exception caught", e);
        }
        return null;
    }
          
    
    
}
