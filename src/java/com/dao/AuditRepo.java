/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.core.ProcessingException;
import com.entities.Adminusers;
import com.entities.Auditreport;
import java.util.Date;
import javax.ejb.Stateless;

/**
 *
 * @author Ahmed
 */
@Stateless
public class AuditRepo extends AbstractDAO<Auditreport> {

    public AuditRepo() {
        super(Auditreport.class);
    }
    
    public void audit(String Username, String Action,String IPADDRESS) throws ProcessingException{
        Auditreport ar=new Auditreport();
        ar.setAction(Action);
        ar.setActionDate(new Date());
        ar.setIpAddress(IPADDRESS);
        ar.setUserName(Username);
        create(ar);
    }
    
    
}
