/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.core.ProcessingException;
import com.entities.Customer;
import com.entities.Outmessages;
import com.util.SMSStatus;
import com.util.SMSType;
import java.util.ArrayList;
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
public class SMSRepo extends AbstractDAO<Outmessages> {

    public SMSRepo() {
        super(Outmessages.class);
    }
    
    public void sendSMS(String header, String message,Customer cust,String destAddress,Date sendDate) throws ProcessingException{
        Outmessages out=new Outmessages();
        out.setHeader(header);
        out.setMessage(message);
        out.setDestAddress(destAddress);
        out.setMsgDate(new Date());
        out.setSendDate(sendDate!=null?sendDate:new Date());
        out.setSenderId(cust);
        out.setStatus(SMSStatus.PENDING.ordinal());
        out.setMessageType(SMSType.MT.ordinal());
        create(out);
    }
    
    public List<Outmessages> searchByDate(Date sDate,Date eDate,int status){
         
         EntityManager em = getEntityManager();
        List<Outmessages> outmsgs = null;
        try {
            outmsgs = (List<Outmessages>) em.createQuery("SELECT i FROM Outmessages i WHERE i.status = :status AND i.sendDate BETWEEN :startdate AND :enddate order by i.msgId desc", Outmessages.class).setParameter("status", status).setParameter("startdate", sDate).setParameter("enddate", eDate).getResultList();
            if (outmsgs != null) {
                return outmsgs;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outmsgs;        
    }

    public List<Outmessages> findBySenderId(Integer customerId) {
        EntityManager em = getEntityManager();
        List<Outmessages> outmsgs = new ArrayList();
        try {
            outmsgs =  em.createNamedQuery("Outmessages.findBySenderId").setParameter("customerId", customerId).getResultList();
            if (outmsgs != null && !outmsgs.isEmpty()) {
                return outmsgs;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    public List<Outmessages> searchPending(Date sDate,Date eDate,int status)
    {         
        EntityManager em = getEntityManager();
        List<Outmessages> outmsgs = null;
        try {
            outmsgs = (List<Outmessages>) em.createQuery("SELECT t FROM Outmessages t WHERE t.status = :status AND t.sendDate BETWEEN :startdate AND :enddate", Outmessages.class).setParameter("startdate", sDate).setParameter("enddate", eDate).setParameter("status", SMSStatus.PENDING.ordinal()).getResultList();
            if (outmsgs != null) {
                return outmsgs;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outmsgs;        
    }

    public void updatePhone(String oldPhone, String phone) {
        EntityManager em = getEntityManager();
        List<Outmessages> outmsgs = new ArrayList();
        try {
            outmsgs =  em.createNamedQuery("Outmessages.findByDestAddress").setParameter("destAddress", oldPhone).getResultList();
            if (outmsgs != null && !outmsgs.isEmpty()) {
                for(Outmessages out:outmsgs){
                    out.setDestAddress(phone);
                    edit(out);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
            
    
    
}
