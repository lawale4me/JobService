/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.util;


import com.dao.BatchRepo;
import com.dao.StateRepo;
import com.entities.States;
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
@FacesConverter(value = "stateConverter")
public class StateConverter  implements Converter
{

    /**
     * Creates a new instance of ProductConverter
     */
    public StateConverter() {
    }
         
    @Inject 
    StateRepo staterepo; 



    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        System.out.println("Value:"+value);
        return staterepo.findState(new Integer(value));        
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        System.out.println("Object:"+o);
        return ((States) o).getStateId().toString(); 
    }
    
    
    
}
