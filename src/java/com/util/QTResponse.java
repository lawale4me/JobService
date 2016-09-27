/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

/**
 *
 * @author Ahmed
 */
public class QTResponse {
    private String Operation,responseCode,Description,status,amount;    
    private boolean success,honour; 

    public String getOperation() {
        return Operation;
    }

    public void setOperation(String Operation) {
        this.Operation = Operation;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public boolean isHonour() {
        return honour;
    }

    public void setHonour(boolean honour) {
        this.honour = honour;
    }
    
    
    
}
