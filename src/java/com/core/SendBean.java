/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core;

import com.dao.CustomerRepo;
import com.dao.SMSRepo;
import com.dao.SettingsRepo;
import com.dao.TransactionRepo;
import com.entities.Outmessages;
import com.entities.QuicktellerTransactions;
import com.entities.Settings;
import com.util.SMSStatus;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
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
public class SendBean implements Job{    
    @EJB
    CustomerRepo custrepo;
    @EJB
    TransactionRepo tranxrepo;
    @EJB
    SMSRepo smsrepo;
    @EJB
    SettingsRepo settingsrepo;
    
    List<QuicktellerTransactions> qtTranx;
    
    /**
     * Creates a new instance of TaskBean
     */
    public SendBean() {        
        try {
            Context context = new InitialContext();                        
            custrepo= (CustomerRepo) context.lookup("java:global/JobService/CustomerRepo");
            smsrepo= (SMSRepo) context.lookup("java:global/JobService/SMSRepo");
            settingsrepo= (SettingsRepo) context.lookup("java:global/JobService/SettingsRepo");
            tranxrepo= (TransactionRepo) context.lookup("java:global/JobService/TransactionRepo");
        } catch (NamingException ex) {
            Logger.getLogger(SendBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {                          
        
        //READING PROPERTIES
        Settings SMSUSERNAME=settingsrepo.findByName("SMSUSERNAME");
        Settings SMSPASSWORD=settingsrepo.findByName("SMSPASSWORD");
        Settings HEADER=settingsrepo.findByName("HEADER");
        
        
       GeneralProperty.SMSUSERNAME=SMSUSERNAME==null?null:SMSUSERNAME.getValue();
       GeneralProperty.SMSPASSWORD=SMSPASSWORD==null?null:SMSPASSWORD.getValue();
       GeneralProperty.HEADER=HEADER==null?null:HEADER.getValue();
        //END 
        
       
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DATE, -1);
                c.set(Calendar.HOUR_OF_DAY, 23);
                c.set(Calendar.MINUTE, c.getMinimum(Calendar.MINUTE));
                c.set(Calendar.SECOND, c.getMinimum(Calendar.SECOND));
                c.set(Calendar.MILLISECOND, c.getMinimum(Calendar.MILLISECOND));
                Date start = c.getTime();

                c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, c.getMaximum(Calendar.HOUR_OF_DAY));
                c.set(Calendar.MINUTE, c.getMaximum(Calendar.MINUTE));
                c.set(Calendar.SECOND, c.getMaximum(Calendar.SECOND));
                c.set(Calendar.MILLISECOND, c.getMaximum(Calendar.MILLISECOND));
                Date end = new Date();
        
             List<Outmessages> pendingList=smsrepo.searchPending(start, end, SMSStatus.PENDING.ordinal());
             if(pendingList!=null&&!pendingList.isEmpty())
             {
                 
              System.out.println("Found some SMS to send: "+pendingList.size());
              for(Outmessages out:pendingList)
              {
              boolean b = false;
                  try 
                  {
                      b = SendSMS.sendSMS(URLEncoder.encode(out.getMessage(),"UTF-8"), out.getDestAddress());
                  } catch (UnsupportedEncodingException ex) {
                      Logger.getLogger(SendBean.class.getName()).log(Level.SEVERE, null, ex);
                  }
              if(b){out.setStatus(SMSStatus.SENT.ordinal());}else{out.setStatus(SMSStatus.FAILED.ordinal());}
               smsrepo.edit(out);
              }
              
             }
             else{
                 System.out.println("No pending SMS to send");
             }
         
        }
        
    
    
}
