/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mbeans;


import com.dao.CustomerRepo;
import com.dao.SMSRepo;
import com.dao.TransactionRepo;
import com.entities.Customer;
import com.entities.Outmessages;
import com.entities.Transactions;
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
public class TransactionMBean {

    /**
     * Creates a new instance of ProductMBean
     */
    public TransactionMBean() {
    }
    
    boolean checked;
    private String username;
    Customer customer;
    private List<Customer> customerList ;
    private List<Transactions> tranxList ;
    private Transactions tranx;
    
        
    @Inject 
    CustomerRepo customerrepo;
    @Inject
    TransactionRepo tranxrepo;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();  
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();  
        HttpSession httpSession = request.getSession(false);          
        username=(String) httpSession.getAttribute("username");
        customer=customerrepo.findByUsername(username);
        tranxList=tranxrepo.findByUsername(username);
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

    public List<Transactions> getTranxList() {
        return tranxList;
    }

    public void setTranxList(List<Transactions> tranxList) {
        this.tranxList = tranxList;
    }

    public Transactions getTranx() {
        return tranx;
    }

    public void setTranx(Transactions tranx) {
        this.tranx = tranx;
    }

 
    

    
    
    
}
