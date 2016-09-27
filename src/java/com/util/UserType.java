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
public enum UserType {
    ResellerCustomer,
    Reseller,
    Customer;
 
        
    @Override
    public String toString()
    {
        switch (this)
        {
            case ResellerCustomer:
                return "ResellerCustomer";
            case Reseller:
                return "Reseller";
            case Customer:
                return "Customer";       
            default:
                return "Unknown";
        }
    }
    
    
}