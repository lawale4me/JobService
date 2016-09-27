/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.core.ProcessingException;
import com.entities.QuicktellerTransactions;
import com.util.QTStatus;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

/**
 *
 * @author Ahmed
 */
@Stateless
public class QTRepo extends AbstractDAO<QuicktellerTransactions> {

    public QTRepo() {
        super(QuicktellerTransactions.class);
    }
    
    public void log(Double amount, String TxRef, String customerId,String respcode,String respDesc,String status) throws ProcessingException{
        QuicktellerTransactions ar=new QuicktellerTransactions();
        ar.setAmount(amount);
        ar.setCustomerId(customerId);
        ar.setRequestStatus(QTStatus.PENDING.ordinal());
        ar.setRespCode(respcode);
        ar.setRespDesc(respDesc);
        ar.setStatus(status);
        ar.setTxRef(TxRef);
        ar.setTranxDate(new Date());
        create(ar);
    }
    
    public List<QuicktellerTransactions> getAllPending() {
        EntityManager em = getEntityManager();
        List<QuicktellerTransactions> tranx = null;
        try {
            tranx =  em.createNamedQuery("QuicktellerTransactions.findByRequestStatus").setParameter("requestStatus", QTStatus.PENDING.ordinal()).getResultList();
            if (tranx != null && !tranx.isEmpty()) {
                return tranx;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<QuicktellerTransactions> findAllDesc() {
        EntityManager em = getEntityManager();
        List<QuicktellerTransactions> device = new ArrayList();
        try {
            device =  em.createNamedQuery("QuicktellerTransactions.findAll").getResultList();
            if (device != null && !device.isEmpty()) {
                return device;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
}
