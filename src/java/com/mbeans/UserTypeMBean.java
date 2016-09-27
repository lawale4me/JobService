/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mbeans;

import com.util.UserType;
import com.entities.Adminusers;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Ahmed
 */
@ManagedBean
@ApplicationScoped
public class UserTypeMBean {
    
        private List<Adminusers> adminUsers =new ArrayList();


    /**
     * Creates a new instance of UserTypeMBean
     */
    public UserTypeMBean() {
    }
    
    public UserType[] getUserTypes() {
        return UserType.values();
    }
        

    public List<Adminusers> getAdminUsers() {
        return adminUsers;
    }

    public void setAdminUsers(List<Adminusers> adminUsers) {
        this.adminUsers = adminUsers;
    }
    
    
    
    
}
