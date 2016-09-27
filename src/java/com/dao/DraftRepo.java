/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.core.ProcessingException;
import com.entities.Addressbook;
import com.entities.Adminusers;
import com.entities.Auditreport;
import com.entities.Customer;
import com.entities.Draft;
import java.util.Date;
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
public class DraftRepo extends AbstractDAO<Draft> {
    
    private static final Logger logger = Logger.getLogger(AddressBookRepo.class.getName());

    public DraftRepo() {
        super(Draft.class);
    }
    
    public void draft(Customer cust, String draft) throws ProcessingException{
        Draft ar = new Draft();
        ar.setCustomer(cust);
        ar.setDraft(draft);        
        create(ar);
    }

    public Draft findDraft(Integer draft) {         
        EntityManager em = getEntityManager();
        List<Draft> user = null;
        try {
            user = (List<Draft>) em.createNamedQuery("Draft.findByDraftId").setParameter("draftId", draft).getResultList();
            if (user != null&&!user.isEmpty()) {
                return user.get(0);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "exception caught", e);
        }
        return null;    
    }

    public List<Draft> findByCustomer(Integer customerId) {
        EntityManager em = getEntityManager();
        List<Draft> user = null;
        try {
            user = (List<Draft>) em.createNamedQuery("Draft.findByUserId").setParameter("customerId", customerId).getResultList();
            if (user != null&&!user.isEmpty()) {
                return user;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "exception caught", e);
        }
        return null;
    }
    
    
}
