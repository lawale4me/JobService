/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core;

import com.util.Email;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

/**
 *
 * @author Ahmed
 */
public class SendEmail {
    
      public static boolean sendSimpleMail(Email msg)
    {
    // Create The Email
        boolean status=false;
        MultiPartEmail email = new MultiPartEmail();
        try 
        {               
            email.setHostName("mail.vaultjobs.net");
            email.setSmtpPort(25);
            email.setAuthenticator(new DefaultAuthenticator("support@vaultjobs.net", "admin2015"));            
            email.setFrom("support@vaultjobs.net");
            email.setSubject(msg.getSubject());
            email.setMsg(msg.getMessage());
            email.addTo(msg.getEmailAddress());
//            email.setTLS(true);        
            System.out.println("******Sending Email********");
            String res= email.send();           
            status=true;
            System.out.println("Response:"+res);            
        }
        catch (EmailException ee) {           
        ee.printStackTrace();        
        }
        System.out.println("******"+status+"********");
        return status;
    }
    
    
}
