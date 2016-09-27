/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core;

import com.dao.AuditRepo;
import com.dao.CredentialRepo;
import com.dao.CustomerRepo;
import com.dao.DisciplineRepo;
import com.dao.EmailRepo;
import com.dao.QTRepo;
import com.dao.SMSRepo;
import com.dao.TransactionRepo;
import com.dao.VacancyRepo;
import com.entities.Credential;
import com.entities.Customer;
import com.entities.Discipline;
import com.entities.Vacancy;
import com.util.ResponseCode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
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
public class ScheduleBean implements Job{
    @EJB
    QTRepo qtrepo;
    @EJB
    CustomerRepo custrepo;
    @EJB
    TransactionRepo tranxrepo;
    @EJB
    SMSRepo smsrepo;
    @EJB
    VacancyRepo vacancyrepo;
    @Inject
    CredentialRepo credrepo;
    @Inject 
    DisciplineRepo disciplinerepo;
    @Inject
    EmailRepo emailrepo;
    @Inject
    AuditRepo auditrepo;
    
    
    Discipline discpiline;
    List<Credential> credList;
    List<Customer> custList;
    List<Customer> custExpList;
    
    /**
     * Creates a new instance of TaskBean
     */
    public ScheduleBean() {        
        try {
            Context context = new InitialContext();            
            disciplinerepo= (DisciplineRepo) context.lookup("java:global/JobService/DisciplineRepo");
            credrepo= (CredentialRepo) context.lookup("java:global/JobService/CredentialRepo");
            custrepo= (CustomerRepo) context.lookup("java:global/JobService/CustomerRepo");
            smsrepo= (SMSRepo) context.lookup("java:global/JobService/SMSRepo");
            auditrepo= (AuditRepo) context.lookup("java:global/JobService/AuditRepo");
            emailrepo= (EmailRepo) context.lookup("java:global/JobService/EmailRepo");
            vacancyrepo= (VacancyRepo) context.lookup("java:global/JobService/VacancyRepo");            
        } catch (NamingException ex) {
            Logger.getLogger(ScheduleBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {                          
        System.out.println("ScheduleBean running");        
        List<Vacancy> vacantList=vacancyrepo.getPending();
        expiry();
        if(vacantList!=null&&!vacantList.isEmpty()){
            System.out.println("Vacancy size"+vacantList.size());
            for(Vacancy v:vacantList)
            {
                try {
                    broadcast(v.getTarget(),v.getMessage());
                    v.setStatus(1);
                } catch (ProcessingException ex) {
                    Logger.getLogger(ScheduleBean.class.getName()).log(Level.SEVERE, null, ex);
                    v.setStatus(2);
                    System.out.println("Error broadcasting vacancy");
                }                
                vacancyrepo.edit(v);
            }
        }else{
            System.out.println("No Vacancy to Send");
        }                
        threeDays();
        
     }
        
    
    public String broadcast(Integer disciplineId,String vacancy) throws ProcessingException
    {                              
        discpiline= disciplinerepo.findDiscpiline(disciplineId);
        if(discpiline!=null)
        {
        credList = credrepo.findByDiscipline(discpiline);
        custList = custrepo.findActive();
        ArrayList<Integer> custNames=new ArrayList();
        if(custList!=null){
         for(Customer c:custList){
             custNames.add(c.getCustomerId());
         }        
        }
        if(credList!=null&&!credList.isEmpty())
        {       
            for(Credential cred:credList)
            {
            if(custNames.contains(cred.getCustomer()))
            {
                String prefix="Dear "+cred.getFirstname()+" "+cred.getSurname()+", ";
            try
            {
            smsrepo.sendSMS(ResponseCode.HEADER, prefix+vacancy, null,cred.getPhone(),new Date());
            emailrepo.sendEmail(ResponseCode.HEADER, prefix+vacancy, null,cred.getEmail(),new Date());
            }catch(Exception ex){ex.printStackTrace();}
            }
            System.out.println("Messages has broadcasted to Customer asscoiated with the discipline");
            }
        }
        else
        {
         System.out.println(" No customer asscoiated with the discipline");        
          return null;
        }               
                
          
        }
        return "";
    }

    public List<Credential> getCredList() {
        return credList;
    }

    public void setCredList(List<Credential> credList) {
        this.credList = credList;
    }

    public List<Customer> getCustList() {
        return custList;
    }

    public void setCustList(List<Customer> custList) {
        this.custList = custList;
    }
    
    public List<Customer> getCustExpList() {        
        return custExpList;
    }

    public void setCustExpList(List<Customer> custExpList) {
        this.custExpList = custExpList;
    }

    private void expiry() {
        custExpList = custrepo.findExpiring();
        if(custExpList!=null&&!custExpList.isEmpty()){
            for(Customer cu:custExpList){
                try {
                    cu.setPaymentStatus(0);
                    String msg="Dear "+cu.getFname()+" "+cu.getLname()+", please note that your subscription"
                            + " has expired today. Kindly visit www.vaultjobs.net "
                            + "to renew your account for more alerts. Details,"
                            + " call 013428341. Thanks.";
                    smsrepo.sendSMS(ResponseCode.HEADER, msg, null,cu.getPhone(),new Date());
                    emailrepo.sendEmail(ResponseCode.HEADER, msg, null,cu.getEmail(),new Date());
                    custrepo.edit(cu);
                } catch (ProcessingException ex) {
                    Logger.getLogger(ScheduleBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void threeDays() {
        custExpList = custrepo.findThreeDays();
        if(custExpList!=null&&!custExpList.isEmpty()){
            for(Customer cu:custExpList){
                try {
                    cu.setStatus(0);
                    String msg="Dear "+cu.getFname()+" "+cu.getLname()+", this is to notify you that your "
                            + "subscription will expire in 3days. "
                            + "Kindly make plans to renew and enjoy more job alerts. "
                            + "Details, call 013428341. Thanks.";
                    smsrepo.sendSMS(ResponseCode.HEADER, msg, null,cu.getPhone(),new Date());
                    emailrepo.sendEmail(ResponseCode.HEADER, msg, null,cu.getEmail(),new Date());
                    custrepo.edit(cu);
                } catch (ProcessingException ex) {
                    Logger.getLogger(ScheduleBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    
    
}
