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
import com.entities.Batch;
import com.entities.Customer;
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
public class BatchRepo extends AbstractDAO<Batch> {
    
    private static final Logger logger = Logger.getLogger(AddressBookRepo.class.getName());

    public BatchRepo() {
        super(Batch.class);
    }        

    public List<Batch> getBatchList(Customer batchowner) {
         EntityManager em = getEntityManager();
        List<Batch> user = null;
        try {
            user = (List<Batch>) em.createNamedQuery("Batch.findByBatchowner").setParameter("batchowner", batchowner).getResultList();
            if (user != null&&!user.isEmpty()) {
                return user;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "exception caught", e);
        }
        return user;
    }
    
    public Batch getBatch(Integer batchId) {
         EntityManager em = getEntityManager();
        List<Batch> batch = null;
        try {
            batch = (List<Batch>) em.createNamedQuery("Batch.findByBatchId").setParameter("batchId", batchId).getResultList();
            if (batch != null&&!batch.isEmpty()) {
                return batch.get(0);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "exception caught", e);
        }
        return null;
    }
    
}
