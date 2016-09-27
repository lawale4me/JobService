/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.core.ProcessingException;
import com.entities.Adminusers;
import com.entities.Auditreport;
import com.entities.Outmessages;
import com.entities.Transactions;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

/**
 *
 * @author Ahmed
 */
@Stateless
public class TransactionRepo extends AbstractDAO<Transactions> {

    public TransactionRepo() {
        super(Transactions.class);
    }

    public List<Transactions> findByUsername(String username) {
        
        EntityManager em = getEntityManager();
        List<Transactions> tranx = null;
        try {
            tranx = (List<Transactions>) em.createNamedQuery("Transactions.findByCustomer").setParameter("customer", username).getResultList();            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
       return tranx;
    }

    public List<Transactions> searchByDate(Date startdate, Date enddate, String type) {
          EntityManager em = getEntityManager();
        List<Transactions> outmsgs = null;
        try {
            outmsgs = (List<Transactions>) em.createQuery("SELECT i FROM Transactions i WHERE i.creditType = :type AND i.transDate BETWEEN :startdate AND :enddate  order by i.transactionId desc", Transactions.class).setParameter("type", type).setParameter("startdate", startdate).setParameter("enddate", enddate).getResultList();
            if (outmsgs != null) {
                return outmsgs;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outmsgs;
    }
   
    
    
}
