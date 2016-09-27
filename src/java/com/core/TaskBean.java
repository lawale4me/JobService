/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core;

import com.dao.CustomerRepo;
import com.dao.EmailRepo;
import com.dao.QTRepo;
import com.dao.SMSRepo;
import com.dao.SettingsRepo;
import com.dao.TransactionRepo;
import com.entities.Customer;
import com.entities.QuicktellerTransactions;
import com.entities.Transactions;
import com.util.QTResponse;
import com.util.QTStatus;
import com.util.ResponseCode;
import java.util.Calendar;
import static java.util.Calendar.HOUR;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author Ahmed
 */
@Stateless
public class TaskBean implements Job{
    @EJB
    QTRepo qtrepo;
    @EJB
    CustomerRepo custrepo;
    @EJB
    TransactionRepo tranxrepo;
    @EJB
    SMSRepo smsrepo;
    @EJB
    SettingsRepo settingsrepo;
    @EJB
    EmailRepo emailrepo;
    
    List<QuicktellerTransactions> qtTranx;
    
    /**
     * Creates a new instance of TaskBean
     */
    public TaskBean() {        
        try {
            Context context = new InitialContext();            
            qtrepo= (QTRepo) context.lookup("java:global/JobService/QTRepo");
            custrepo= (CustomerRepo) context.lookup("java:global/JobService/CustomerRepo");
            smsrepo= (SMSRepo) context.lookup("java:global/JobService/SMSRepo");
            emailrepo= (EmailRepo) context.lookup("java:global/JobService/EmailRepo");
            settingsrepo= (SettingsRepo) context.lookup("java:global/JobService/SettingsRepo");
            tranxrepo= (TransactionRepo) context.lookup("java:global/JobService/TransactionRepo");
        } catch (NamingException ex) {
            Logger.getLogger(TaskBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {                          
                                
             List<QuicktellerTransactions> pendingList=qtrepo.getAllPending();
             if(pendingList!=null){
             for(QuicktellerTransactions qt:pendingList){
                 if(qt.getTxRef()!=null){
                 QTResponse resp= ServerConnector.getQtResponse(qt.getTxRef());
                 if(resp.isHonour()&&resp.isSuccess())
                 {
                     try {
                         Customer cust= custrepo.findByUsername(qt.getCustomerId());
                         Transactions Rtx=new Transactions();
                         
                         qt.setRespCode(resp.getResponseCode());
                         qt.setRequestStatus(QTStatus.TREATED.ordinal());
                         if(resp.getDescription()!=null&&!resp.getDescription().isEmpty()&&resp.getDescription().trim().length()>4){
                         qt.setRespDesc(resp.getDescription());
                         }
                         
                         Calendar cal = Calendar.getInstance();
                         cal.add(Calendar.DAY_OF_MONTH, 30);
                         cust.setExpiryDate(cal.getTime());
                         cust.setPaymentStatus(1);
                         Rtx.setAmount(Double.parseDouble(resp.getAmount()));
                         Rtx.setCreditType("QT TopUp");
                         Rtx.setCustomer(cust.getUsername());
                         
                         Rtx.setTransDate(new Date());
                         Rtx.setExtra(qt.getTxRef());
                         tranxrepo.create(Rtx);
                         custrepo.edit(cust);
                         
                         
                         String msg= "Dear "+cust.getFname()+" "+cust.getLname();
                         String surfix="";
                         Calendar calendar = Calendar.getInstance();    
                         calendar.set(HOUR, 10);
                         if (new Date().after(calendar.getTime())) {
                             surfix=", you have successfully subscribed to Vault Jobs service and "
                                     + "you will start receiving alerts from "
                                     + "now for 30days. Congratulations!";
                         }else{
                             surfix=", you have successfully subscribed to Vault Jobs"
                                     + " service and you will start receiving alerts for "
                                     + "30days from tomorrow. Congratulations!";
                         }
                         
                         smsrepo.sendSMS(ResponseCode.HEADER, msg+surfix, cust, cust.getPhone(), new Date());
                         emailrepo.sendEmail("Vaultjobs Subscription Notification", msg+surfix, null,cust.getEmail(),new Date());
                         
                     } catch (ProcessingException ex) {
                         Logger.getLogger(TaskBean.class.getName()).log(Level.SEVERE, null, ex);
                     }
                 }
                 else if(resp.isHonour()){
                     qt.setRespCode(resp.getResponseCode());                     
                     qt.setRequestStatus(QTStatus.FAILED.ordinal());
                     if(resp.getDescription()!=null&&!resp.getDescription().isEmpty()&&resp.getDescription().trim().length()>4){
                     qt.setRespDesc(resp.getDescription());
                     }
                 }                 
                 
                 }else{
                     qt.setRequestStatus(QTStatus.ERROR.ordinal());
                 }
                 qtrepo.edit(qt);
             }
             
             }
             else{
                 System.out.println("No pending tranx");
             }
         
        }
        
    
    
}
