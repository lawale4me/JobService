/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mbeans;


import com.core.ProcessingException;
import com.dao.CredentialRepo;
import com.dao.CustomerRepo;
import com.dao.DisciplineRepo;
import com.dao.SMSRepo;
import com.dao.StateRepo;
import com.entities.Credential;
import com.entities.Customer;
import com.entities.Discipline;
import com.entities.States;
import com.util.Email;
import com.util.SendEmail;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Ahmed
 */
@ManagedBean
@RequestScoped
public class CredentialMBean {

    /**
     * Creates a new instance of ProductMBean
     */
    public CredentialMBean() {
    }
    
    boolean checked;
    private String username,myfile;
    Credential credential;
    private List<Customer> customerList ;
    private Discipline discpiline;
    private List<Discipline> discpilineList;
    private States states;
    private List<States> statesList;
    private Date dob;
    String userName,phone,passwd,fname,lname,email,cpasswd,address,discipline,state,qualification,cvurl;    
    int status;    
    private Customer customer;
    private UploadedFile  file;
    
    private StreamedContent afile;
    
    
    @Inject 
    CredentialRepo credentialrepo;
    @Inject 
    CustomerRepo customerrepo;
    @Inject
    SMSRepo smsrepo;
    @Inject
    StateRepo staterepo;
    @Inject
    DisciplineRepo discrepo;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();  
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();  
        HttpSession httpSession = request.getSession(false);          
        username=(String) httpSession.getAttribute("username");     
        discpilineList = discrepo.findAll();
        statesList = staterepo.findAll();
        customer=customerrepo.findByUsername(username);
        if(customer!=null){
        credential=credentialrepo.getCredential(customer.getCustomerId());
        }
        if(credential!=null){
            fname=credential.getFirstname();
            lname=credential.getSurname();
            email=credential.getEmail();
            phone=credential.getPhone();
            qualification=credential.getQualification();
            discpiline=credential.getDiscipline();
            states=credential.getState();
            myfile=credential.getCvurl();
            FileDownloadView();
        }
    }           
           
 
    public String addCredentials() throws ProcessingException {
        
        
        if(credential==null)
        {
            //UPLOADING CODE
            if(file!=null){
            String directory = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("uploadDirectory"); 
              String filedetails=directory+file.getFileName();
    		try {    			
    			FileUtils.writeByteArrayToFile(new File(directory+file.getFileName()), file.getContents());
                        cvurl=filedetails;
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}  
            }
            //ENDS HERE
            
            
            
        Credential cred=new Credential();
        cred.setCustomer(customer.getCustomerId());
        cred.setDiscipline(discpiline);
        cred.setDob(dob);
        cred.setFirstname(fname);
        cred.setPhone(phone);
        cred.setState(states);
        cred.setDiscipline(discpiline);
        cred.setSurname(lname);
        cred.setCvurl(cvurl);
        cred.setQualification(qualification);
        cred.setEmail(customer.getEmail());
        customer.setExtra(cred.getDiscipline().getName());
        credentialrepo.create(cred);
        
        smsrepo.updatePhone(customer.getPhone(),phone);
        customer.setPhone(phone);
        customer.setCvurl(cvurl);
        customerrepo.edit(customer);
        
       
        
        //SENDING EMAIL
        SendEmail sendObject=new SendEmail();
        Email mail=new Email();
        mail.setEmailAddress(customer.getEmail());
        mail.setMessage("Dear "+fname+ " "+lname +", your credential has been successfully updated. Kindly login to your account and click Pay/Renew Subscription to subscribe for your 30-day daily alerts. Thank you");
        mail.setSubject("Vaultjobs Credentials updated");
        sendObject.sendSimpleMail(mail);
        //END OF EMAIL
        
        FacesMessage msg = new FacesMessage("Credential Updated. Click Pay/Renew Subscription to subscribe for your daily alerts");   
        FacesContext.getCurrentInstance().addMessage(null, msg);  
        return null;
        }   
        else{
         //UPLOADING CODE
            if(file!=null){
            String directory = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("uploadDirectory"); 
              String filedetails=directory+file.getFileName();
    		try {    			
    			FileUtils.writeByteArrayToFile(new File(directory+file.getFileName()), file.getContents());
                        cvurl=filedetails;
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}  
            }
            //ENDS HERE
            
                                        
        credential.setDiscipline(discpiline);
        credential.setDob(dob);        
        credential.setFirstname(fname);
        credential.setPhone(phone);
        credential.setState(states);
        credential.setDiscipline(discpiline);
        credential.setSurname(lname);
        if(cvurl!=null){credential.setCvurl(cvurl);};
        credential.setQualification(qualification);
                
        credential.setEmail(customer.getEmail());
        
        credentialrepo.edit(credential);
        
        smsrepo.updatePhone(customer.getPhone(),phone);
        if(cvurl!=null){customer.setCvurl(cvurl);}
        customer.setExtra(credential.getDiscipline().getName());
        customer.setPhone(phone);
        customerrepo.edit(customer);
        
        
        //SENDING EMAIL
        SendEmail sendObject=new SendEmail();
        Email mail=new Email();
        mail.setEmailAddress(customer.getEmail());
        mail.setMessage("Dear "+fname+ " "+lname +", your credential has been successfully updated. "
                + "Kindly login to your account and click Pay/Renew Subscription"
                + " to subscribe for your 30-day daily alerts. Thank you");
        mail.setSubject("Vaultjobs Credentials updated");
        sendObject.sendSimpleMail(mail);
        //END OF EMAIL
        
        
        FacesMessage msg = new FacesMessage("Credential Updated. Click Pay/Renew Subscription to subscribe for your daily alerts");   
        FacesContext.getCurrentInstance().addMessage(null, msg); 
         
        return null;
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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
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

    

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public String getCpasswd() {
        return cpasswd;
    }

    public void setCpasswd(String cpasswd) {
        this.cpasswd = cpasswd;
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

    public Discipline getDiscpiline() {
        return discpiline;
    }

    public void setDiscpiline(Discipline discpiline) {
        this.discpiline = discpiline;
    }

    public List<Discipline> getDiscpilineList() {
        return discpilineList;
    }

    public void setDiscpilineList(List<Discipline> discpilineList) {
        this.discpilineList = discpilineList;
    }

    public States getStates() {
        return states;
    }

    public void setStates(States states) {
        this.states = states;
    }

    public List<States> getStatesList() {
        return statesList;
    }

    public void setStatesList(List<States> statesList) {
        this.statesList = statesList;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    
    
       
     
    public void FileDownloadView() {        
        
        if(myfile!=null)
        {
            InputStream stream=null;
            try {
                File ff=new File(myfile);
                stream = new FileInputStream(ff);
                afile = new DefaultStreamedContent(stream, FacesContext.getCurrentInstance().getExternalContext().getMimeType(ff.getName()), ff.getName());
                System.out.println(afile.getContentType());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CredentialMBean.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }

    public StreamedContent getAfile() {
        return afile;
    }
               
    
    
    
}
