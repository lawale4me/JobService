/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ahmed
 */

@XmlRootElement
public class Response {
    boolean status;
    String Description;
    int statuscode;

    public Response(boolean status, String Description,int statuscode) {
        this.status = status;
        this.Description = Description;
        this.statuscode=statuscode;
    }
    
    public Response() {
        this.status = status;
        this.Description = Description;
        this.statuscode=statuscode;
    }

    
    
    
    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }
    
    
    
}
