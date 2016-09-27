/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbeans;

import com.core.ProcessingException;
import com.dao.CustomerRepo;
import com.dao.SMSRepo;
import com.entities.Customer;
import com.util.CustomerStatus;
import com.util.ResponseCode;
import java.util.Date;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author Ahmed
 */
@Named(value = "verifyMBean")
@RequestScoped
public class VerifyMBean {

    @Inject
    CustomerRepo custrepo;
    @Inject
    SMSRepo smsrepo;
    
    String verifycode,email,username,uname;
    
    /**
     * Creates a new instance of VerifyMBean
     */
    public VerifyMBean() {
    }

    public String getVerifycode() {
        return verifycode;
    }

    public void setVerifycode(String verifycode) {
        this.verifycode = verifycode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }        
    
    
     public String verify() {
        
        String resmsg="Your username  does not exist "+username;
        Customer cust=custrepo.findByUsername(username);     
        
        if(cust!=null)
        {
        
            if(cust.getActivationCode().equals(verifycode))
            {
                resmsg="Your account has been verified and you can now login ";        
                cust.setStatus(CustomerStatus.ACTIVE.ordinal());                
                custrepo.edit(cust);        
                FacesMessage msg = new FacesMessage(resmsg);   
                FacesContext.getCurrentInstance().addMessage(null, msg); 
                FacesContext context = FacesContext.getCurrentInstance();
                 context.getExternalContext().getFlash().setKeepMessages(true);
                return "index.xhtml?faces-redirect=true";
            }else{
                resmsg="Invalid Activation code";
            }
                        
        
        }
        FacesMessage msg = new FacesMessage(resmsg);   
        FacesContext.getCurrentInstance().addMessage(null, msg);         
 
        username = "";verifycode="";
        return null;
    }
     
     public String resendCode() {
        
        String resmsg="Your username  does not exist "+uname;
        Customer cust=custrepo.findByUsername(uname);     
        
        if(cust!=null)
        {
        
        if(cust.getStatus()==CustomerStatus.INACTIVE.ordinal())
        {
         //SENDING EMAIL
//        SendEmail sendObject=new SendEmail();
//        Email mail=new Email();
//        mail.setEmailAddress(cust.getEmail());
//        mail.setMessage("Dear "+cust.getUsername()+", Your verification code is: "+cust.getActivationCode()+". Thank you");
//        mail.setSubject("SMS Verification code");
//        sendObject.sendSimpleMail(mail);
        //END OF EMAIL
        
        String SMSMESSAGE="Dear "+cust.getUsername()+", Your verification code is: "+cust.getActivationCode()+". Thank you";
                try {
                    smsrepo.sendSMS(ResponseCode.HEADER, SMSMESSAGE, cust, cust.getPhone(), new Date());
                } catch (ProcessingException ex) {
                    FacesMessage msg = new FacesMessage(ex.getMessage());   
                FacesContext.getCurrentInstance().addMessage(null, msg);  
                }
                
                resmsg="Your verification code has been resent";                                                   
                FacesMessage msg = new FacesMessage(resmsg);   
                FacesContext.getCurrentInstance().addMessage(null, msg);               
            }else{
                resmsg="Your account has already been verified";
            }
                        
        
        }
        FacesMessage msg = new FacesMessage(resmsg);   
        FacesContext.getCurrentInstance().addMessage(null, msg);         
 
        uname = "";
        return null;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
    
     
     
     
}
