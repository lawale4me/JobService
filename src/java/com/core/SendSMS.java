/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core;

import java.util.Calendar;
import static java.util.Calendar.HOUR;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ahmed
 */
public class SendSMS 
{
  
    public static void main(String args[]){        
    
    
    }
    
    public static boolean sendSMS(String message,String destAddr)
    {
        boolean status=false;
        try {            
            String smsurl="http://localhost:8180/BulkSMS/SendSMS";
            Map<String,String> params=new HashMap<String,String>();
            Map<String,String> header=new HashMap<String,String>();
            
            params.put("username", GeneralProperty.SMSUSERNAME);
            params.put("password", GeneralProperty.SMSPASSWORD);
            params.put("header", GeneralProperty.HEADER);
            params.put("message", message);
            params.put("destAddr", destAddr);
            
            
            String resp=ServerConnector.GET(smsurl, params, header);
            if(resp.equalsIgnoreCase("Sms Sent")){
                status=true;
            }
            
            System.out.println("SMS Status"+status+ " to"+destAddr);
                    
            return status;
        } catch (Exception ex) {
            Logger.getLogger(SendSMS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
}
