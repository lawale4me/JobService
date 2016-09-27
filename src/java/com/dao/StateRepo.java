/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.entities.Discipline;
import com.entities.States;
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
public class StateRepo extends AbstractDAO<States> {
    
    private static final Logger logger = Logger.getLogger(AddressBookRepo.class.getName());

    public StateRepo() {
        super(States.class);
    }        
    
    
     public States findState(Integer stateId) {    
         EntityManager em = getEntityManager();
        List<States> cred = null;
        try {
            cred = (List<States>) em.createNamedQuery("States.findByStateId").setParameter("stateId", stateId).getResultList();
            if (cred != null&&!cred.isEmpty()) {
                return cred.get(0);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "exception caught", e);
        }
        return null;
    
    }
   
}
