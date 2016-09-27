/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.entities.Customer;
import com.entities.Outmessages;
import com.util.SMSStatus;
import java.util.ArrayList;
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
public class CustomerRepo     extends AbstractDAO<Customer>{

    private static final Logger logger = Logger.getLogger(CustomerRepo.class.getName());

    public CustomerRepo() {
        super(Customer.class);
    }
    

    public Customer findByUsername(String username) {
        EntityManager em = getEntityManager();
        List<Customer> device = new ArrayList();
        try {
            device =  em.createNamedQuery("Customer.findByUsername").setParameter("username", username).getResultList();
            if (device != null && !device.isEmpty()) {
                return device.get(0);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "exception caught", e);
        }
        return null;
    }
    
    public Customer findByUserId(Integer userId) {
        EntityManager em = getEntityManager();
        Customer user = null;
        try {
            user = (Customer) em.createNamedQuery("Customer.findByCustomerId").setParameter("customerId", userId).getResultList().get(0);
            if (user != null) {
                return user;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "exception caught", e);
        }
        return user;
    }
    
    
    public Customer validate(String username,String passwd) {
        EntityManager em = getEntityManager();
        Customer user = null;
        try {
            String query = "SELECT u FROM Customer u WHERE u.username = :username and u.password = :password";
        List<Customer> adminUser = em.createQuery(query, Customer.class).setParameter("username", username).setParameter("password", passwd).getResultList();
        return adminUser.isEmpty() ? null : adminUser.get(0);          
        } catch (Exception e) {
            logger.log(Level.SEVERE, "exception caught", e);
        }
        return user;
    }

    public List<Customer> findMyCustomers(Integer customerId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Customer findByEmail(String email) {
        EntityManager em = getEntityManager();
        List<Customer> device = new ArrayList();
        try {
            device =  em.createNamedQuery("Customer.findByEmail").setParameter("email", email).getResultList();
            if (device != null && !device.isEmpty()) {
                return device.get(0);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "exception caught", e);
        }
        return null;
    }


    public List<Outmessages> getOutMsg(SMSStatus smsStatus, Integer customerId) {
        EntityManager em = getEntityManager();
        List<Outmessages> device = new ArrayList();
        try {
            device =  em.createNamedQuery("Outmessages.getOutMsg").setParameter("status", smsStatus.ordinal()).setParameter("senderId", customerId).getResultList();
            if (device != null && !device.isEmpty()) {
                return device;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "exception caught", e);
        }
        return null;
    }      

    public Customer findByPhone(String phone) {
        EntityManager em = getEntityManager();
        List<Customer> device = new ArrayList();
        try {
            device =  em.createNamedQuery("Customer.findByPhone").setParameter("phone", phone).getResultList();
            if (device != null && !device.isEmpty()) {
                return device.get(0);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "exception caught", e);
        }
        return null; 
    }
    
    public List<Customer> findAllDesc() {
        EntityManager em = getEntityManager();
        List<Customer> device = new ArrayList();
        try {
            device =  em.createNamedQuery("Customer.findAll").getResultList();
            if (device != null && !device.isEmpty()) {
                return device;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "exception caught", e);
        }
        return null; 
    }

    public List<Outmessages> getRecievedMsg(String destAddress) {
        EntityManager em = getEntityManager();
        List<Outmessages> device = null;
        try {
            device =  em.createNamedQuery("Outmessages.findByDestAddress").setParameter("destAddress", destAddress).getResultList();
            if (device != null && !device.isEmpty()) {
                return device;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "exception caught", e);
        }
        return null;
    }

    public List<Customer> findActive() {
        EntityManager em = getEntityManager();
        List<Customer> device = new ArrayList();
        try {
            device =  em.createNamedQuery("Customer.findByPaymentStatus").setParameter("paymentStatus", 1).getResultList();
            if (device != null && !device.isEmpty()) {
                return device;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "exception caught", e);
        }
        return null; 
    }

    public List<Customer> findExpiring() {
        EntityManager em = getEntityManager();
        List<Customer> custList = new ArrayList();
        try {
            String query = "SELECT c FROM Customer c WHERE c.paymentStatus=1 and c.expiryDate < now()";
              custList = em.createQuery(query, Customer.class).getResultList();
            if (custList != null && !custList.isEmpty()) {
                return custList;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "exception caught", e);
        }
        return null; 
    }

    public List<Customer> findThreeDays() {
      EntityManager em = getEntityManager();
        List<Customer> custList = new ArrayList();
        try {
            String query = "SELECT u FROM Customer u WHERE u.status=1 and datediff(u.expiryDate,curdate())=3";
              custList = em.createQuery(query, Customer.class).getResultList();
            if (custList != null && !custList.isEmpty()) {
                return custList;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "exception caught", e);
        }
        return null;    
    }

    public List<Customer> findInActive() {
        EntityManager em = getEntityManager();
        List<Customer> device = new ArrayList();
        try {
            device =  em.createNamedQuery("Customer.findByPaymentStatus").setParameter("paymentStatus", 0).getResultList();
            if (device != null && !device.isEmpty()) {
                return device;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "exception caught", e);
        }
        return null; 
    }
    
    
}
