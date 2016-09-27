/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mbeans;


import com.dao.AuditRepo;
import com.dao.CustomerRepo;
import com.dao.SMSRepo;
import com.dao.TransactionRepo;
import com.entities.Customer;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
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
public class SubscriptionMBean {

    /**
     * Creates a new instance of ProductMBean
     */
    public SubscriptionMBean() {
    }
    
    boolean checked;
    private String username,purpose,amount,cUsername,desc;
    Customer customer,cust;    
    @ManagedProperty(value="#{loginMBean}")
    private LoginMBean loginMBean; 
        
        int accountType;
        int status;
        double reward;
        Integer customerId;
    
    @Inject 
    CustomerRepo customerrepo;
    @Inject
    SMSRepo smsrepo;
    @Inject
    AuditRepo auditrepo;
    @Inject
    TransactionRepo tranxrepo;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();  
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();  
        HttpSession httpSession = request.getSession(false);          
        username=(String) httpSession.getAttribute("username");
        customer=customerrepo.findByUsername(username);            
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


    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
    
  
    public Customer getCust() {
        return cust;
    }

    public void setCust(Customer cust) {
        this.cust = cust;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public LoginMBean getLoginMBean() {
        return loginMBean;
    }

    public void setLoginMBean(LoginMBean loginMBean) {
        this.loginMBean = loginMBean;
    }

    public String getcUsername() {
        return cUsername;
    }

    public void setcUsername(String cUsername) {
        this.cUsername = cUsername;
    }

    
    
     public void reset() {     
        cUsername=null;
        amount=null;
        purpose=null;        
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void subscribe(){
        
    }
     
    public void optout(){
        
    }
    
}
