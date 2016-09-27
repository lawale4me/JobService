/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mbeans;


import com.core.ProcessingException;
import com.dao.AdminRepo;
import com.dao.AuditRepo;
import com.dto.Response;
import com.entities.Adminusers;
import com.util.Log;
import com.util.UserType;
import com.util.Util;
import static com.util.Util.hash;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIViewRoot;
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
public class UserMBean {

    
    String username,email,phone,name,passwd,fname;
    UserType role;
    String adminUsername;
    Adminusers adminUser;
    List<String> branchnames;    
    @Inject
    AdminRepo adminrepo;
    @Inject
    AuditRepo auditrepo;
    
    String newPassword;
    String newPassword1;
    String oldPassword;
    
    
    /**
     * Creates a new instance of UserMBean
     */
    public UserMBean() {
    }

    @PostConstruct
    public void init() {        
        
        FacesContext context = FacesContext.getCurrentInstance();  
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();  
        HttpSession httpSession = request.getSession(false);          
        adminUsername=(String) httpSession.getAttribute("adminadmin");  
        adminUser=adminrepo.findByUsername(adminUsername);               
    }
    
    

   

    public UserType getRole() {
        return role;
    }

    public void setRole(UserType role) {
        this.role = role;
    }      
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    
    
    public String create() throws ProcessingException
    {                      
        if(adminrepo.findByUsername(username)==null){
        Adminusers user =new Adminusers();
        user.setEmail(email);
        user.setUsername(username);
        user.setFirstname(fname);
        user.setLastname(name); 
        user.setPhonenumber(phone);
        try {
            passwd=Util.hash(passwd);
        } catch (Exception ex) {
            Logger.getLogger(UserMBean.class.getName()).log(Level.SEVERE, null, ex);
            throw new ProcessingException(ex.getMessage());
        }
        user.setPassword(passwd);  
        user.setStatus(1);
        user.setDatecreated(new Date());
                        
        adminrepo.create(user);
        System.out.println("User "+user.getUsername()+" created");
        
        role=null;
        email=null;
        username=null;
        name=null;
        passwd=null;
        fname=null;
        phone=null;

        
         auditrepo.audit(adminUser.getUsername(), "New User created  "+user.getUsername(),"IPADDRESS");        
        
        FacesMessage message = new FacesMessage("Succesful", user.getUsername()+" user has been Created.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        }
        else{           
           FacesMessage message = new FacesMessage("failed", "Username "+username +new ProcessingException(" exists").getMessage());
           FacesContext.getCurrentInstance().addMessage(null, message);
        }       
        return "";
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
    
    
    
    public void changePassword(String username, String oldpass, String newpassword) throws ProcessingException, Exception
    {
                
        String pwPolicy = Util.employPasswordPolicy(newpassword);
    
         if (!pwPolicy.equals("0")) {
             Log.l.infoLog.info("change password failed "+username +" policy:"+pwPolicy);
            throw new ProcessingException(pwPolicy);
         }
        //ResponseDetails res =authenticate(username, oldpass);  
        Response res = adminrepo.login(username, oldpass);
        if(res.getStatus()){
            
            Adminusers adminUser = adminrepo.findByUsername(username);
            if(adminUser!=null)
            {            
            adminUser.setPassword(hash(newpassword));
//          adminUser.setPwdExpired(false);
//          adminUser.setPwdDate(DateUtils.addDays(new Date(), 30));
            adminrepo.edit(adminUser);            
            }
            else
            {
                Log.l.infoLog.info("Admin User Not Found whilse changing password "+username);
                throw new ProcessingException("User not found");                                        
            }
        }
//        else if(res.getErrorCode()== ResponseCodes.USER_EXPIRED){           
//            AdminUser adminUser = adminrepo.getUser(username);            
//            if(adminUser!=null){
//            adminUser.setPassword(hash(newpassword));
//            adminUser.setPwdExpired(false);
//            adminUser.setPwdDate(DateUtils.addDays(new Date(), 30));
//            adminrepo.update(adminUser);            
//            }
//            else
//            {
//            Log.l.infoLog.info("User Not Found "+username +" while changing password");
//            throw new ProcessingException("User not found");                                        
//            }
//        }
        else
        {            
            Log.l.infoLog.info("password doesnt match "+username);
            throw new ProcessingException("Password does not match:"+res.getDescription());
        }        
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword1() {
        return newPassword1;
    }

    public void setNewPassword1(String newPassword1) {
        this.newPassword1 = newPassword1;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }
    


}