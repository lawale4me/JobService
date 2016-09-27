/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mbeans;


import com.dao.CredentialRepo;
import com.dao.CustomerRepo;
import com.dao.SMSRepo;
import com.entities.Credential;
import com.entities.Customer;
import com.entities.Outmessages;
import com.util.SMSStatus;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ahmed
 */
@ManagedBean
@RequestScoped
public class ReportMBean {

    /**
     * Creates a new instance of ProductMBean
     */
    public ReportMBean() {
    }
    
    boolean checked;
    private String username;
    Customer customer;
    private List<Customer> customerList ;
    private List<Outmessages> recievedList ;
    Credential credential;
    
    String userName,phone,passwd,lname,fname,email,cpasswd;
        int accountType;
        int status;
        double reward;
    
    @Inject 
    CustomerRepo customerrepo;
    @Inject
    SMSRepo smsrepo;
    @Inject
    CredentialRepo credrepo;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();  
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();  
        HttpSession httpSession = request.getSession(false);          
        username=(String) httpSession.getAttribute("username");
        customer=customerrepo.findByUsername(username);     
        if(customer!=null){         
        credential=credrepo.getCredential(customer.getCustomerId());        
        recievedList = customerrepo.getRecievedMsg(customer.getPhone());        
        }
    }                      
   
    
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public List<Outmessages> getRecievedList() {
        return recievedList;
    }

    public void setRecievedList(List<Outmessages> recievedList) {
        this.recievedList = recievedList;
    }

    public CustomerRepo getCustomerrepo() {
        return customerrepo;
    }

    public void setCustomerrepo(CustomerRepo customerrepo) {
        this.customerrepo = customerrepo;
    }

    

    
    
    
}
