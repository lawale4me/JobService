/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.util;


import com.dao.BatchRepo;
import com.dao.CustomerRepo;
import com.entities.Batch;
import com.entities.Customer;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;


/**
 *
 * @author Ahmed
 */
@ManagedBean
@RequestScoped
@FacesConverter(value = "batchConverter")
public class customerConverter  implements Converter
{

    /**
     * Creates a new instance of ProductConverter
     */
    public customerConverter() {
    }
         
    @Inject 
    CustomerRepo customerrepo; 



    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        System.out.println("Value:"+value);
        return customerrepo.findByUserId(new Integer(value));        
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        System.out.println("Object:"+o);
        return ((Customer) o).getCustomerId().toString(); 
    }
    
    
    
}
