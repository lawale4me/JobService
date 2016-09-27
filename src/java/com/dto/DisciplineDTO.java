/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dto;

import java.io.Serializable;

/**
 *
 * @author Ahmed
 */
public class DisciplineDTO implements Serializable{
    
    private String name;
    private Integer active;
    private Integer inactive;

    public DisciplineDTO(String name, Integer active, Integer inactive) {
        this.name = name;
        this.active = active;
        this.inactive = inactive;
    }

    public DisciplineDTO() {        
    }

    
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getInactive() {
        return inactive;
    }

    public void setInactive(Integer inactive) {
        this.inactive = inactive;
    }
    
    
    
}
