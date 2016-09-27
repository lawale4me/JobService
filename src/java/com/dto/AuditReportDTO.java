/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dto;



import com.entities.Auditreport;
import java.util.Date;
import java.io.Serializable;

/**
 *
 * @author Ahmed
 */
public class AuditReportDTO implements Serializable 
{
    
    private int ID;    
    private String username;
    private String action;
    private String ipAddress;    
    private Date actionDate;

    public AuditReportDTO(Auditreport c) {
        ID=c.getId();
        username=c.getUserName();
        action=c.getAction();
        ipAddress=c.getIpAddress();
        actionDate=c.getActionDate();                                                
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }
    
    
}
