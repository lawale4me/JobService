/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.dto.Response;
import com.entities.Adminusers;
import com.entities.Customer;
import com.util.GeneralUser;
import com.util.ResponseCode;
import com.util.Util;
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
public class AdminRepo extends AbstractDAO<Adminusers> {

    private static final Logger logger = Logger.getLogger(AdminRepo.class.getName());

    public AdminRepo() {
        super(Adminusers.class);
    }

    public Adminusers findByUsername(String username) {
        EntityManager em = getEntityManager();
        List<Adminusers> users;
        try {
            users = (List<Adminusers>) em.createNamedQuery("Adminusers.findByUsername").setParameter("username", username).getResultList();
            if (users != null && !users.isEmpty()) {
                return users.get(0);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "exception caught", e);
        }
        return null;
    }

    public Adminusers findByUserId(Integer userId) {
        EntityManager em = getEntityManager();
        Adminusers user = null;
        try {
            user = (Adminusers) em.createNamedQuery("Adminusers.findByUserId").setParameter("userId", userId).getResultList().get(0);
            if (user != null) {
                return user;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "exception caught", e);
        }
        return user;
    }

    public Adminusers validate(String username, String passwd) {        
        EntityManager em = getEntityManager();                
        try {
            String query = "SELECT u FROM Adminusers u WHERE u.username = :username and u.password = :password";
            List<Adminusers> adminUser = em.createQuery(query, Adminusers.class).setParameter("username", username).setParameter("password", passwd).getResultList();
            
            
            if (adminUser != null && !adminUser.isEmpty()) {                 
                Adminusers user=adminUser.get(0);                
                 return user;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "exception caught", e);
        }
        return null;
    }

    public Response login(String userid, String password) throws Exception {
        System.out.println("Attempt to logon to the application " + userid);
        Response authres = new Response();

        try {
            password = Util.hash(password);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Password hashing issue " + userid);
            throw new Exception("Password hashing issue");
        }

        Adminusers adminUser = validate(userid, password);
        if (adminUser != null) {
            if (true) {
                if (adminUser.getStatus() == 1) {
                    authres.setStatus(true);
                    authres.setStatuscode(ResponseCode.ACTIVE);
                    authres.setDescription("User exists");
                    System.out.println("cleared to logon");
                } else {
                    authres.setStatuscode(ResponseCode.USER_DISABLED);
                    authres.setDescription("User is disabled");
                }
            } else {
                authres.setStatuscode(ResponseCode.USER_EXPIRED);
                authres.setDescription("User password has expired");
            }
        } else {
            authres.setStatuscode(ResponseCode.USER_NOT_FOUND);
            authres.setDescription("Invalid username or password");
        }
        System.out.println("Login:" + authres.getDescription());
        return authres;
    }

    public Customer validateCustomer(String userid, String password) {
        EntityManager em = getEntityManager();        
        try {
            String query = "SELECT c FROM Customer c WHERE c.username = :username and c.password = :password";        
            List<Customer> adminUser = em.createQuery(query, Customer.class).setParameter("username", userid).setParameter("password", password).getResultList();
            if (adminUser != null && !adminUser.isEmpty()) {
                return adminUser.get(0);                
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "exception caught", e);
        }
        return null;
    }    

}
