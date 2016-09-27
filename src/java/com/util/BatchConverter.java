/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.util;


import com.dao.BatchRepo;
import com.entities.Batch;
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
public class BatchConverter  implements Converter
{

    /**
     * Creates a new instance of ProductConverter
     */
    public BatchConverter() {
    }
         
    @Inject 
    BatchRepo batchrepo; 



    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        System.out.println("Value:"+value);
        return batchrepo.getBatch(new Integer(value));        
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        System.out.println("Object:"+o);
        return ((Batch) o).getBatchId().toString(); 
    }
    
    
    
}
