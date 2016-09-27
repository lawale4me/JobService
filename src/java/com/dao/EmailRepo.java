/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.core.ProcessingException;
import com.entities.Customer;
import com.entities.Email;
import com.entities.Outmessages;
import com.util.ResponseCode;
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
public class EmailRepo extends AbstractDAO<Email> {

    public EmailRepo() {
        super(Email.class);
    }
    
    public void sendEmail(String header, String message,Customer cust,String destAddress,Date sendDate) throws ProcessingException{
        Email out=new Email();
        out.setHeader(header);
        out.setMessage(message+ResponseCode.EMAIL_SIGNATURE);
        out.setDestAddress(destAddress);
        out.setMsgDate(new Date());
        out.setSendDate(sendDate!=null?sendDate:new Date());
       // out.setSenderId(cust.getCustomerId());
        out.setStatus(SMSStatus.PENDING.ordinal());
        out.setMessageType(SMSType.MT.ordinal());
        create(out);
    }
    
    public List<Email> searchByDate(Date sDate,Date eDate,int status){
         
         EntityManager em = getEntityManager();
        List<Email> outmsgs = null;
        try {
            outmsgs = (List<Email>) em.createQuery("SELECT i FROM Email i WHERE i.status = :status AND i.sendDate BETWEEN :startdate AND :enddate order by i.msgId desc", Email.class).setParameter("status", status).setParameter("startdate", sDate).setParameter("enddate", eDate).getResultList();
            if (outmsgs != null) {
                return outmsgs;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outmsgs;
        
    }

    public List<Email> findBySenderId(Integer customerId) {
        EntityManager em = getEntityManager();
        List<Email> outmsgs = new ArrayList();
        try {
            outmsgs =  em.createNamedQuery("Email.findBySenderId").setParameter("customerId", customerId).getResultList();
            if (outmsgs != null && !outmsgs.isEmpty()) {
                return outmsgs;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Email> searchPending(Date start, Date end, int status) {
         EntityManager em = getEntityManager();
        List<Email> outmsgs = null;
        try {
            outmsgs = (List<Email>) em.createQuery("SELECT t FROM Email t WHERE t.status = :status AND t.sendDate BETWEEN :startdate AND :enddate", Email.class).setParameter("startdate", start).setParameter("enddate", end).setParameter("status", SMSStatus.PENDING.ordinal()).getResultList();
            if (outmsgs != null) {
                return outmsgs;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outmsgs;        
    }
    
    
}
