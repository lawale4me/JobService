/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbeans;

import com.core.ProcessingException;
import com.core.TaskBean;
import com.dao.AdminRepo;
import com.dao.AuditRepo;
import com.dao.CustomerRepo;
import com.dao.EmailRepo;
import com.dao.SMSRepo;
import com.dao.TransactionRepo;
import com.entities.Adminusers;
import com.entities.Customer;
import com.entities.Transactions;
import com.util.ResponseCode;
import java.util.Calendar;
import static java.util.Calendar.HOUR;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
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
public class CreditAdminMBean {

    /**
     * Creates a new instance of ProductMBean
     */
    public CreditAdminMBean() {
    }

    boolean checked;
    private String username, purpose, amount;
    Customer cust;
    Adminusers au;
    private List<Customer> customerList;
    @ManagedProperty(value = "#{loginMBean}")
    private LoginMBean loginMBean;

    int accountType;
    int status;
    double reward;
    Integer customerId;

    @Inject
    CustomerRepo customerrepo;
    @Inject
    AdminRepo adminrepo;
    @Inject
    SMSRepo smsrepo;
    @Inject
    AuditRepo auditrepo;
    @Inject
    TransactionRepo tranxrepo;
    @EJB
    EmailRepo emailrepo;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession(false);
        username = (String) httpSession.getAttribute("adminadmin");
        au = adminrepo.findByUsername(username);
        if (au != null) {
            customerList = customerrepo.findInActive();
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
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

    public void subscribe() throws ProcessingException 
    {
        String title, msgs;
        try 
        {
            Customer cust = customerrepo.findByUserId(customerId);
            Transactions Rtx = new Transactions();
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, 30);
            cust.setExpiryDate(cal.getTime());
            cust.setPaymentStatus(1);
            Rtx.setAmount(500d);
            Rtx.setCreditType("Bank Deposit");
            Rtx.setCustomer(cust.getUsername());
            Rtx.setTransDate(new Date());
            Rtx.setExtra(purpose);
            tranxrepo.create(Rtx);
            customerrepo.edit(cust);

            title = "Successful";
            msgs = "User has been subscribed";
            String msg = "Dear " + cust.getFname() + " " + cust.getLname();
            String surfix = "";
            Calendar calendar = Calendar.getInstance();
            calendar.set(HOUR, 10);
            if (new Date().after(calendar.getTime())) {
                surfix = ", you have successfully subscribed to Vault Jobs service and "
                        + "you will start receiving alerts from "
                        + "now for 30days. Congratulations!";
            } else {
                surfix = ", you have successfully subscribed to Vault Jobs"
                        + " service and you will start receiving alerts for "
                        + "30days from tomorrow. Congratulations!";
            }

            smsrepo.sendSMS(ResponseCode.HEADER, msg + surfix, cust, cust.getPhone(), new Date());
            emailrepo.sendEmail("Vaultjobs Subscription Notification", msg + surfix, null, cust.getEmail(), new Date());

        } catch (ProcessingException ex) {
            title = "Error";
            msgs = ex.getMessage();
            Logger.getLogger(TaskBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        purpose = null;
        FacesMessage message = new FacesMessage(title, msgs);
        FacesContext.getCurrentInstance().addMessage(null, message);
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

    public Adminusers getAu() {
        return au;
    }

    public void setAu(Adminusers au) {
        this.au = au;
    }

}
