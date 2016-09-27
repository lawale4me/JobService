/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mbeans;


import com.dao.AdminRepo;
import com.dao.CustomerRepo;
import com.entities.Adminusers;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ahmed
 */
@ManagedBean
@ViewScoped
public class NewUserMBean {

    String username;
    Adminusers adminUser,selectedUser;
    List<Adminusers> adminusers,filteredAdminusers;
    
    @Inject
    AdminRepo adminrepo;

    
    
    /**
     * Creates a new instance of NewUserMBean
     */        
    
    public NewUserMBean() {
    }
    
    @PostConstruct
    public void init() {
        
        FacesContext context = FacesContext.getCurrentInstance();  
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();  
        HttpSession httpSession = request.getSession(false);          
        username=(String) httpSession.getAttribute("username");  
        adminUser=adminrepo.findByUsername(username);                
        adminusers = adminrepo.findAll();
        
    }

 

    public Adminusers getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(Adminusers selectedUser) {
        this.selectedUser = selectedUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Adminusers getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(Adminusers adminUser) {
        this.adminUser = adminUser;
    }

    public List<Adminusers> getAdminusers() {
        return adminusers;
    }

    public void setAdminusers(List<Adminusers> adminusers) {
        this.adminusers = adminusers;
    }

    public List<Adminusers> getFilteredAdminusers() {
        return filteredAdminusers;
    }

    public void setFilteredAdminusers(List<Adminusers> filteredAdminusers) {
        this.filteredAdminusers = filteredAdminusers;
    }


    
    
    public void onRowSelect(){
        
    }
    
    
}
