/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mbeans;


import com.core.ProcessingException;
import com.dao.AdminRepo;
import com.dao.CredentialRepo;
import com.dao.CustomerRepo;
import com.dao.SMSRepo;
import com.entities.Adminusers;
import com.entities.Credential;
import com.entities.Customer;
import com.entities.Outmessages;
import com.util.CustomerStatus;
import com.util.Email;
import com.util.ResponseCode;
import com.util.SendEmail;
import com.util.UserType;
import com.util.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Ahmed
 */
@ManagedBean
@ViewScoped
public class CustomerAdminMBean {

   
    public CustomerAdminMBean() {
    }
    
    boolean checked;
    private String username,myusername;
    Customer customer;
    Adminusers au;
    private List<Customer> customerList ;
    private Date dob;
    String userName,phone,passwd,email,cpasswd,address,lname,fname,discipline,state,qualification,cvurl;
    int accountType;
    int status;
    double reward;
    UserType usertype;
    
    @Inject 
    CustomerRepo customerrepo;
    @Inject 
    AdminRepo adminrepo;
    @Inject
    SMSRepo smsrepo;
    @Inject
    CredentialRepo credentialrepo;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();  
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();  
        HttpSession httpSession = request.getSession(false);          
        username=(String) httpSession.getAttribute("adminadmin");
        au=adminrepo.findByUsername(username);             
        customerList = customerrepo.findAllDesc();        
    }           
           
 
    public String addAction() throws ProcessingException {
        if(StringUtils .containsWhitespace(userName))
        {
            FacesMessage msg = new FacesMessage("Username contains white spaces");   
            FacesContext.getCurrentInstance().addMessage(null, msg);                         
            return null;
        }
         if(customerrepo.findByUsername(userName)!=null)
        {
            FacesMessage msg = new FacesMessage("Username already exists");   
            FacesContext.getCurrentInstance().addMessage(null, msg);                         
            return null;
        }
        else if(customerrepo.findByEmail(email)!=null)
        {
            FacesMessage msg = new FacesMessage("Email already exists");   
            FacesContext.getCurrentInstance().addMessage(null, msg);                         
            return null;
        }
        if(customerrepo.findByPhone(phone)!=null)
        {
            FacesMessage msg = new FacesMessage("Phone already exists");   
            FacesContext.getCurrentInstance().addMessage(null, msg);                         
            return null;
        }
        else{
        RequestContext context = RequestContext.getCurrentInstance();
        Customer cust = new Customer();                
        cust.setUsername(userName);
        cust.setStatus(CustomerStatus.INACTIVE.ordinal());
        cust.setPhone(phone);
        try {
            cust.setPassword(Util.hash(passwd));
        } catch (ProcessingException ex) {
            Logger.getLogger(CustomerMBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        cust.setLname(lname);        
        cust.setFname(fname);
        cust.setEmail(email);        
        String code = Util.generateCode();
        cust.setActivationCode(code);        
        cust.setRegDate(new Date());        
        cust.setPaymentStatus(0);
        String messsg="";
        try {        
            customerrepo.create(cust);
        } catch (ProcessingException ex) {
            messsg=ex.getMessage();
            FacesMessage msg = new FacesMessage(messsg);   
            FacesContext.getCurrentInstance().addMessage(null, msg); 
            return null;
        }
        
        //SENDING EMAIL
        SendEmail sendObject=new SendEmail();
        Email mail=new Email();
        mail.setEmailAddress(email);
        mail.setMessage("Dear "+fname+", Vauljobs welcomes you. "
                + "Your sms account has been successfully created. "
                + "Your Username is "+userName+" and your password is "+passwd+" ."
                + " Your number verification code has been sent to your phone. \n" +
"Kindly click on the link below to verify your number and activate your account:\n" +
"http://www.vaultjobs.net:8180/Jobs/verifycode\n" +
"Thank you for your patronage.");
        mail.setSubject("Vaultjobs Account Created");
        sendObject.sendSimpleMail(mail);
        //END OF EMAIL
        
        String SMSMESSAGE="Your Vaultjobs account has been created successfully."
                + " Username = "+userName+" and"
                + " password = "+passwd+" . Your verification code is "+code+"."
                + " Kindly confirm your phone to log in";
        smsrepo.sendSMS(ResponseCode.HEADER, SMSMESSAGE, cust, phone, new Date());
                
        
        messsg="Account Created successfully";
        FacesMessage msg = new FacesMessage(messsg);   
        FacesContext.getCurrentInstance().addMessage(null, msg); 
        context.addCallbackParam("loggedIn", true);
 
        email = "";
        phone = "";        
        lname = "";
        fname = "";
        address=null;
        status=0;        
        return null;
        }       
    }
    
    
    public String addAction2() throws ProcessingException  {
        
        if(StringUtils .containsWhitespace(userName))
        {
            FacesMessage msg = new FacesMessage("Username contains white spaces");   
            FacesContext.getCurrentInstance().addMessage(null, msg);                         
            return null;
        }
        if(customerrepo.findByUsername(userName)!=null)
        {
            FacesMessage msg = new FacesMessage("Username already exists");   
            FacesContext.getCurrentInstance().addMessage(null, msg);                         
            return null;
        }
        else if(customerrepo.findByEmail(email)!=null)
        {
            FacesMessage msg = new FacesMessage("Email already exists");   
            FacesContext.getCurrentInstance().addMessage(null, msg);                         
            return null;
        }
        if(customerrepo.findByPhone(phone)!=null)
        {
            FacesMessage msg = new FacesMessage("Phone already exists");   
            FacesContext.getCurrentInstance().addMessage(null, msg);                         
            return null;
        }
        else{
        RequestContext context = RequestContext.getCurrentInstance();
        Customer cust = new Customer();                
        cust.setUsername(userName);
        cust.setStatus(CustomerStatus.INACTIVE.ordinal());        
        cust.setPhone(phone);
        try {
            cust.setPassword(Util.hash(passwd));
        } catch (ProcessingException ex) {
            Logger.getLogger(CustomerAdminMBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        cust.setFname(fname);
        cust.setLname(lname);
        cust.setEmail(email);        
        String code = Util.generateCode();
        cust.setActivationCode(code);        
        cust.setRegDate(new Date());
        cust.setPaymentStatus(0);
        String messsg="";
        try {        
            customerrepo.create(cust);
        } catch (ProcessingException ex) {
            messsg=ex.getMessage();
        }
        
        //SENDING EMAIL
        SendEmail sendObject=new SendEmail();
        Email mail=new Email();
        mail.setEmailAddress(email);
        mail.setMessage("Dear "+fname+", Vauljobs welcomes you. "
                + "Your sms account has been successfully created. "
                + "Your Username is "+userName+" and your password is "+passwd+" ."
                + " Your number verification code has been sent to your phone. \n" +
"Kindly click on the link below to verify your number and activate your account:\n" +
"http://www.vaultjobs.net:8180/Jobs/verifycode\n" +
"Thank you for your patronage.");
        mail.setSubject("SMS Account Created");
        sendObject.sendSimpleMail(mail);
        //END OF EMAIL
        
        String SMSMESSAGE="Your SMS account has been created successfully."
                + " Username = "+userName+" and"
                + " password = "+passwd+" . Your verification code is "+code+"."
                + " Kindly confirm your phone to log in";
        smsrepo.sendSMS(ResponseCode.HEADER, SMSMESSAGE, cust, phone, new Date());
                
        
        messsg="Account Created successfully";
        FacesMessage msg = new FacesMessage(messsg);   
        FacesContext.getCurrentInstance().addMessage(null, msg); 
        context.addCallbackParam("loggedIn", true);
 
        email = "";
        phone = "";
        fname = "";
        lname = "";
        state = "";
        status=0;        
        return null;
        }
    }
    
    public void onEdit(RowEditEvent event) {  
        Customer ccust=(Customer) event.getObject();                
        FacesMessage msg = new FacesMessage("Customer Edited",ccust.getFname());                  
        Customer cust=customerrepo.findByUserId(ccust.getCustomerId());                        
        
        cust.setPhone(ccust.getPhone());
        cust.setFname(ccust.getFname());
        cust.setLname(ccust.getLname());
        cust.setEmail(ccust.getEmail());
        
        Credential cred=credentialrepo.getCredential(cust.getCustomerId());
        if(cred!=null)
        {
        cred.setPhone(ccust.getPhone());
        cred.setEmail(ccust.getEmail());
        cred.setSurname(ccust.getLname());
        cred.setFirstname(ccust.getFname());
        credentialrepo.edit(cred);
        }
        customerrepo.edit(cust);          
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }  
       
    public void onCancel(RowEditEvent event) {  
        FacesMessage msg = new FacesMessage("Customer deleted");           
        Customer pr=(Customer) event.getObject();
        pr=customerrepo.findByUserId(pr.getCustomerId());
        List<Outmessages> outmsg= smsrepo.findBySenderId(pr.getCustomerId());
        for(Outmessages out:outmsg){
            smsrepo.remove(out);
        }
        Credential credential=credentialrepo.getCredential(pr.getCustomerId());
        if(credential!=null){credentialrepo.remove(credential);}
        customerrepo.remove(pr);
        customerList.remove(pr);
        FacesContext.getCurrentInstance().addMessage(null, msg); 
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

   

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getReward() {
        return reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }

    public String getCpasswd() {
        return cpasswd;
    }

    public void setCpasswd(String cpasswd) {
        this.cpasswd = cpasswd;
    }
    
    
    
    public void resetPasswd() throws ProcessingException{
        RequestContext context = RequestContext.getCurrentInstance();
        String resmsg="Your username address does not exist "+myusername;
        Adminusers cust=adminrepo.findByUsername(myusername);     
        String pwd=new Util().genPass();
        if(cust!=null)
        {
        cust.setPassword(Util.hash(pwd));
        String msg="Your account has been reset your new password is: "+pwd+" and your username is: "+cust.getUsername();        
        adminrepo.edit(cust);
        smsrepo.sendSMS(ResponseCode.HEADER, msg, null, cust.getPhonenumber(), new Date());
        
        //SENDING EMAIL
//        SendEmail sendObject=new SendEmail();
//        Email mail=new Email();
//        mail.setEmailAddress(email);
//        mail.setMessage("Dear "+cust.getUsername()+", you have requested for your "
//                + "login detail which are below:\n" +
//                "Username = "+cust.getUsername()+"\n" +
//                "Password = "+pwd+"\n" +
//                "Kindly change your password from your profile page after signing in successfully.\n "
//                + "Thank you for choosing Vaultjobs");
//        mail.setSubject("Password Reset Details");
//        sendObject.sendSimpleMail(mail);
        //END OF EMAIL
        
        
        resmsg="Password reset sent to your email and phone";
        }
        FacesMessage msg = new FacesMessage(resmsg);   
        FacesContext.getCurrentInstance().addMessage(null, msg); 
        context.addCallbackParam("loggedIn", true);
 
        email = "";
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    
    public void onSelect(AjaxBehaviorEvent event) {
    checked = ((HtmlSelectBooleanCheckbox)event.getSource()).isSelected();
    System.out.println("checked:"+checked);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public UserType[] getUserTypes() {
        return UserType.values();
    }

    public Adminusers getAu() {
        return au;
    }

    public void setAu(Adminusers au) {
        this.au = au;
    }

    public String getMyusername() {
        return myusername;
    }

    public void setMyusername(String myusername) {
        this.myusername = myusername;
    }

    public UserType getUsertype() {
        return usertype;
    }

    public void setUsertype(UserType usertype) {
        this.usertype = usertype;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }   

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getCvurl() {
        return cvurl;
    }

    public void setCvurl(String cvurl) {
        this.cvurl = cvurl;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }
    
    
    //ADDED FOR MULTIPLE DOWNLOADS
    private StreamedContent file;
    private InputStream stream;

    public InputStream getStream() {
        return stream;
    }

    public void setStream(InputStream stream) {
        this.stream = stream;
    }

    public void prepareFile(String attachment){
        if(attachment!=null){
        try {
                File ff=new File(attachment);
                stream = new FileInputStream(ff);
                file = new DefaultStreamedContent(stream, FacesContext.getCurrentInstance().getExternalContext().getMimeType(ff.getName()), ff.getName());
                stream=null;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CustomerAdminMBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }

    public StreamedContent getFile() {        
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
    }
    //END OF MULTIPLE DOWNLOADS
    
    public String getXStatus(Integer status){
        return status==1?"Active":"Inactive";
    }
    
    
    public void reset(Integer custId) throws ProcessingException{
        RequestContext context = RequestContext.getCurrentInstance();
        String resmsg="Internal Error";
        Customer cust=customerrepo.findByUserId(custId);     
        String pwd=new Util().genPass();
        if(cust!=null)
        {
        cust.setPassword(Util.hash(pwd));
        String msg="Your account has been reset. Your new password is: "+pwd+" and your username is: "+cust.getUsername();        
        customerrepo.edit(cust);
        smsrepo.sendSMS(ResponseCode.HEADER, msg, null, cust.getPhone(), new Date());    
        
                //SENDING EMAIL
        SendEmail sendObject=new SendEmail();
        Email mail=new Email();
        mail.setEmailAddress(cust.getEmail());
        mail.setMessage("Dear "+cust.getFname()+",\n Your account has been reset. Your new password is: "+pwd+
                " and your username is: "+cust.getUsername()            
                + "Thank you for choosing SMS SOLUTIONS");
        mail.setSubject("Password Reset Details");
        sendObject.sendSimpleMail(mail);
        //END OF EMAIL
        
        resmsg="New password has been sent to customers phone";
        }
        System.out.println("Here:"+resmsg);
        FacesMessage msg = new FacesMessage(resmsg);   
        FacesContext.getCurrentInstance().addMessage(null, msg); 
        context.addCallbackParam("loggedIn", true);       
    }
    
    
}
