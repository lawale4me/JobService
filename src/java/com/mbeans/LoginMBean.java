package com.mbeans;


import com.core.ProcessingException;
import com.dao.AdminRepo;
import com.dao.AuditRepo;
import com.dao.CustomerRepo;
import com.dto.Response;
import com.entities.Adminusers;
import com.entities.Auditreport;
import com.entities.Customer;
import com.util.CustomerStatus;
import com.util.Email;
import com.util.ResponseCode;
import com.util.ResponseDetails;
import com.util.SendEmail;
import com.util.UserType;
import com.util.Util;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Ahmed
 */
@ManagedBean(name = "loginMBean")
@SessionScoped
public class LoginMBean implements Serializable {

    String userid,auserid;
    String Balance;
    String password,apassword;
    String rrole="";
    String clientId;
    String LastLogonDate,expiryDate;
    String currentPW, newPW,cnewPW;    
    Adminusers au;    
    boolean admin=false;    
    boolean adminUser;
    boolean Customer;
    boolean Reseller;    
    UserType usertype;
    String email,phone,fname,lname;
    Customer adminUserr;
    Integer paymentStatus;
    
   @Inject
   AdminRepo adminrepo;
   @Inject
   AuditRepo auditrepo;
   
   @Inject
   CustomerRepo custrepo;
    

    public LoginMBean() {
        InputStream stream = null;
        
            if (au == null) {
                au = new Adminusers();
            }
            try 
            {
            stream = new FileInputStream("/etc/VAULTJOBS_USER_MANUAL.pdf");
            file = new DefaultStreamedContent(stream, "application/pdf", "VAULTJOBS_USER_MANUAL.pdf");
            InputStream astream = new FileInputStream("/etc/VAULTJOBS_ADMIN_MANUAL.pdf");
            afile = new DefaultStreamedContent(astream, "application/pdf", "VAULTJOBS_ADMIN_MANUAL.pdf");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LoginMBean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            
        }
    }
    
    
 

    

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCurrentPW() {
        return currentPW;
    }

    public void setCurrentPW(String currentPW) {
        this.currentPW = currentPW;
    }

    public String getNewPW() {
        return newPW;
    }

    public void setNewPW(String newPW) {
        this.newPW = newPW;
    }

    public String getLastLogonDate() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, Object> params = fc.getExternalContext().getSessionMap();
        if (params.get("lastLogonDate") == null) {
            return new Date().toString();
        }
        return params.get("lastLogonDate").toString();
    }

    public void setLastLogonDate(String LastLogonDate) {
        this.LastLogonDate = LastLogonDate;
    }

    
    public String loginAdmin()
    {
        FacesMessage msg;
        try 
        {                
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, Object> params = fc.getExternalContext().getSessionMap();                            

        Response result = adminLogin(auserid,apassword);
        
        if (result.getStatus()) 
        {                    
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", auserid);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            
            //get Http Session and store username
            HttpSession session = Util.getSession();
            session.setAttribute("adminadmin", auserid);    
                                                   
            session.setAttribute("rrole", rrole);
            session.setAttribute("usertype", "admin");                        
            
            audit(auserid, "Successful logon","IPADDRESS");            
            return "pretty:adminHome";
          }
           else 
          {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", result.getDescription());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
          }
        }catch (Exception ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            ex.printStackTrace();
            return null;
        }
    }
    

    public String login()
    {
        FacesMessage msg;
        try 
        {                
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, Object> params = fc.getExternalContext().getSessionMap();                            

        Response result = Login2(userid,password);
        
        if (result.getStatus()) 
        {                    
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", userid);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            
            //get Http Session and store username
            HttpSession session = Util.getSession();
            session.setAttribute("username", userid);    
                            
                     
            session.setAttribute("rrole", "");                                   
            
            audit(userid, "Successful logon","IPADDRESS");            
            return "pretty:home";
          }else if(CustomerStatus.INACTIVE.ordinal()== result.getStatuscode())
          {
            System.out.println("Redirecting to verifycode");
            return "pretty:verifycode";  
          }
           else 
          {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", result.getDescription());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return null;
          }
        }catch (Exception ex) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public void redirector(String url) {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        try {
            ec.redirect(url);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void forwarder(String url) {
        FacesContext fc = FacesContext.getCurrentInstance();
        try {
            fc.getExternalContext().dispatch(url);
        } catch (Exception ex) {
            System.out.println("Exception in trying to forward to [" + url + "] due to " + ex.getMessage());
        }
    }


    public void logout() {
        HttpServletRequest hs = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        String loginPage = hs.getContextPath() + "/faces/index.xhtml?redirect=true";
        redirector(loginPage);
    }

    public void idleListener() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                "Your session has expired", "You have been idle for at least 5 minutes"));
        HttpServletRequest hs = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        String loginPage = hs.getContextPath() + "/faces/logout.xhtml";
        redirector(loginPage);
    }

    public void activeListener() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                "Welcome Back", "That's a long coffee break!"));
    }

    public String userInitiatelogout() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fc.getExternalContext().getSession(false);
        Map<String, Object> params = fc.getExternalContext().getSessionMap();
        String userSessionId = (String) params.get("userid");
        String sessionId = session.getId();
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        sessionId = null;
        if (userSessionId != null) {
            userSessionId = null;
        }
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Logout Request", "User successfully logout");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        return "pretty:logout";
    }

    public String homePage() {
        return "pretty:home";
    }
    public String pwResetPage() {
        return "pretty:pwreset";
    }                    
          

    public String getRrole() {
        return rrole;
    }

    public void setRrole(String rrole) {
        this.rrole = rrole;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }


    public Response adminLogin(String userid, String password) throws Exception 
    {
        System.out.println("Attempt to logon to the application " + userid);
        System.out.println("Password: " + password);
        Response authres = new Response();
        
        try {
              password = Util.hash(password);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Password hashing issue " + userid);            
        }        
        
        
         au = adminrepo.validate(userid, password);
         if(au!=null){
             phone=au.getPhonenumber();
             email=au.getEmail();
             fname=au.getFirstname();
             lname=au.getLastname();                          
         }
        
        
        
        if (au != null) 
        { 
            
                if (au.getStatus() == CustomerStatus.ACTIVE.ordinal()) {
                    authres.setStatus(true);
                    admin=true;
                    authres.setStatuscode(CustomerStatus.ACTIVE.ordinal());
                    authres.setDescription("User exists");
                    System.out.println("cleared to logon");
                } else if(au.getStatus() == CustomerStatus.INACTIVE.ordinal()){
                    authres.setStatus(false);
                    authres.setStatuscode(CustomerStatus.INACTIVE.ordinal());
                    authres.setDescription("User not verified yet");
                    System.out.println("User not verified yet");
                }
                else {
                    authres.setStatuscode(ResponseCode.USER_DISABLED);
                    authres.setDescription("User is disabled");
                }
          
        } else {
            authres.setStatuscode(ResponseCode.USER_NOT_FOUND);
            authres.setDescription("Invalid username or password");
        }                
        return authres;
    }   
    
  
    public Response Login2(String userid, String password) throws Exception 
    {
        System.out.println("Attempt to logon to the application " + userid);
        Response authres = new Response();
        
        try {
              password = Util.hash(password);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Password hashing issue " + userid);            
        }        
                
         adminUserr = adminrepo.validateCustomer(userid, password);
         if(adminUserr!=null){
             phone=adminUserr.getPhone();
             email=adminUserr.getEmail();
             fname=adminUserr.getFname();
             lname=adminUserr.getLname();
             expiryDate=adminUserr.getExpiryDate()==null?null:adminUserr.getExpiryDate().toGMTString();
             paymentStatus=adminUserr.getPaymentStatus();
         }
        
        
        
        if (adminUserr != null) 
        { 
            
                if (adminUserr.getStatus() == CustomerStatus.ACTIVE.ordinal()) {
                    authres.setStatus(true);
                    authres.setStatuscode(CustomerStatus.ACTIVE.ordinal());
                    authres.setDescription("User exists");
                    System.out.println("cleared to logon");
                } else if(adminUserr.getStatus() == CustomerStatus.INACTIVE.ordinal()){
                    authres.setStatus(false);
                    authres.setStatuscode(CustomerStatus.INACTIVE.ordinal());
                    authres.setDescription("User not verified yet");
                    System.out.println("User not verified yet");
                }
                else {
                    authres.setStatuscode(ResponseCode.USER_DISABLED);
                    authres.setDescription("User is disabled");
                }
          
        } else {
            authres.setStatuscode(ResponseCode.USER_NOT_FOUND);
            authres.setDescription("Invalid username or password");
        }                
        return authres;
    }   
    
    public void addUser(Adminusers user) throws ProcessingException {        
        adminrepo.create(user);         
    }
    
    public void audit(String username, String action, String ipaddress) throws ProcessingException {        
        Auditreport ar = new Auditreport();
        ar.setAction(action);
        ar.setActionDate(new Date());
        ar.setIpAddress(ipaddress);
        ar.setUserName(username);
        auditrepo.create(ar);
    }


    public ResponseDetails createUser(Adminusers user) throws ProcessingException, Exception {        
        try {
            String pass = new Util().genPass();
            String actualpass = pass;
            pass = Util.hash(pass);

            Adminusers exist = adminrepo.findByUserId(user.getUserId());

            if (exist != null) {
                System.out.println("Admin user already exists " + user.getUsername());
                throw new ProcessingException("Admin User Already exists");
            }


            user.setPassword(pass);
            ResponseDetails authres = createUser(user);
            return authres;
        } catch (ProcessingException ex) {            
            System.out.println(ex.getMessage());
            throw new ProcessingException(ex.getMessage());
        }
    }
    
    
     public UserType[] getUserTypes() {
        return UserType.values();
    }

    public UserType getUsertype() {
        return usertype;
    }

    public void setUsertype(UserType usertype) {
        this.usertype = usertype;
    }

    public String getBalance() {        
        return Balance;
    }

    public void setBalance(String Balance) {
        this.Balance = Balance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }
    
    
    
    public void editProfile(){        
        Customer cust =custrepo.findByUsername(userid);
        cust.setEmail(email);        
        cust.setPhone(phone);
        custrepo.edit(cust);           
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Profile changed", "Successfuly changed");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void editAdminProfile(){        
        Adminusers cust =adminrepo.findByUsername(auserid);
        cust.setEmail(email);        
        cust.setPhonenumber(phone);
        adminrepo.edit(cust);           
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Profile changed", "Successfuly changed");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public Customer getAdminUserr() {
        return adminUserr;
    }

    public void setAdminUserr(Customer adminUserr) {
        this.adminUserr = adminUserr;
    }

    public String getCnewPW() {
        return cnewPW;
    }

    public void setCnewPW(String cnewPW) {
        this.cnewPW = cnewPW;
    }
    
    
    public void resetPasswd() throws ProcessingException{
        RequestContext context = RequestContext.getCurrentInstance();
        String resmsg="Your email address does not exist "+email;
        Customer cust=custrepo.findByUsername(userid);             
        if(cust!=null)
        {
        if(cust.getPassword().equals(Util.hash(currentPW)))
        {
        cust.setPassword(Util.hash(newPW));
        String msg="You have successfuly changed your password  ";        
        custrepo.edit(cust);
     //   smsrepo.sendSMS(ResponseCode.HEADER, msg, cust, cust.getPhone(), new Date());
        
        //SENDING EMAIL
        SendEmail sendObject=new SendEmail();
        Email mail=new Email();
        mail.setEmailAddress(cust.getEmail());
        mail.setMessage("Dear "+cust.getUsername()+", you have successfully changed your password"
                + " from your profile.\n "
                + "Thank you for choosing Vaultjobs");
        mail.setSubject("Password Changed");
        sendObject.sendSimpleMail(mail);
        //END OF EMAIL
        
        newPW=null;
        currentPW=null;
        cnewPW=null;
        resmsg=msg;
        }
        else{
            resmsg="You have entered an Invalid Password";
        }
        }
        FacesMessage msg = new FacesMessage(resmsg);   
        FacesContext.getCurrentInstance().addMessage(null, msg); 
        context.addCallbackParam("loggedIn", true);
 
        email = "";
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getAuserid() {
        return auserid;
    }

    public void setAuserid(String auserid) {
        this.auserid = auserid;
    }

    public String getApassword() {
        return apassword;
    }

    public void setApassword(String apassword) {
        this.apassword = apassword;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
 
    
    private StreamedContent file;
    private StreamedContent afile;
                 
    
 
    public StreamedContent getFile() {
        return file;
    }
    
    public StreamedContent getAFile() {
        return afile;
    }
    
    
}
