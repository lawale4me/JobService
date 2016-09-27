/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mbeans;


import com.core.ProcessingException;
import com.dao.AdminRepo;
import com.dao.AuditRepo;
import com.dao.BatchRepo;
import com.dao.CredentialRepo;
import com.dao.CustomerRepo;
import com.dao.DisciplineRepo;
import com.dao.DraftRepo;
import com.dao.EmailRepo;
import com.dao.SMSRepo;
import com.dao.VacancyRepo;
import com.entities.Adminusers;
import com.entities.Batch;
import com.entities.Credential;
import com.entities.Customer;
import com.entities.Discipline;
import com.entities.Draft;
import com.entities.Vacancy;
import com.util.ResponseCode;
import com.util.UserType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Ahmed
 */
@ManagedBean
@ViewScoped
public class VacancyMBean {

    
    String vacancy;    
    Batch batch;
    List<Batch> batchList;
    UserType role;
    String username,selectedMenu,rangeTo;
    Discipline discpiline;
    Adminusers adminUser;
    List<String> branchnames;    
    List<Credential> credList;
    Adminusers au;
    List<Vacancy> vacancyList;
    Date startdate;
    Date enddate;
    Vacancy vvacancy;
    List<Discipline> discpilineList;
    
    @Inject
    AdminRepo adminrepo;
    @Inject
    AuditRepo auditrepo;
    @Inject
    SMSRepo smsrepo;
    @Inject
    EmailRepo emailrepo;
    @Inject
    CustomerRepo customerrepo;
    @Inject
    CredentialRepo credrepo;
    @Inject
    BatchRepo batchrepo;
    @Inject 
    DraftRepo draftrepo;    
    @Inject 
    VacancyRepo vacancyrepo;
    @Inject 
    DisciplineRepo disciplinerepo;
    
    Customer customer;
    Integer idraft;    
    Draft selectedDraft;
    List<Draft> drafts;
    List<Customer> customerList;
    
    @ManagedProperty(value="#{loginMBean}")
    private LoginMBean loginMBean; 
    
    /**
     * Creates a new instance of UserMBean
     */
    public VacancyMBean() {
    }

    @PostConstruct
    public void init() {        
        
        FacesContext context = FacesContext.getCurrentInstance();  
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();  
        HttpSession httpSession = request.getSession(false);          
        username=(String) httpSession.getAttribute("adminadmin");
        au=adminrepo.findByUsername(username);       
        discpilineList = disciplinerepo.findAll();
    }
        
   

    public UserType getRole() {
        return role;
    }
    
    public String getDiscipline(Integer dId) {
        return disciplinerepo.findDiscpiline(dId).getName();
    }

    public void setRole(UserType role) {
        this.role = role;
    }      
    
           
    
    
    public String uploadVacancy() throws ProcessingException
    {                              
//        credList = credrepo.findByDiscipline(discpiline);
//        if(credList!=null&&!credList.isEmpty())
//        {          
          Vacancy vacant=new Vacancy();
          vacant.setMessage(vacancy);
          vacant.setTarget(discpiline.getDisciplineId());
          vacant.setVacancyDate(new Date());
          vacant.setStatus(0);
          vacancyrepo.create(vacant);
          vacancy=null;        
//        }
//        else
//        {
//        FacesMessage message = new FacesMessage("Error", " No customer asscoiated with the discipline");
//        FacesContext.getCurrentInstance().addMessage(null, message); 
//            return null;
//        }
        auditrepo.audit(au.getUsername(), "Broadcast has been submitted ","IPADDRESS");      
        customerrepo.edit(customer);        
        
        FacesMessage message = new FacesMessage("Succesful","Message(s) have been submitted");
        FacesContext.getCurrentInstance().addMessage(null, message);        
        return "";
    }
    
      
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }      

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public List<Batch> getBatchList() {
        return batchList;
    }

    public void setBatchList(List<Batch> batchList) {
        this.batchList = batchList;
    }
      
    public Integer getIdraft() {
        return idraft;
    }

    public void setIdraft(Integer idraft) {
        this.idraft = idraft;
    }

    public Draft getSelectedDraft() {
        return selectedDraft;
    }

    public void setSelectedDraft(Draft selectedDraft) {
        this.selectedDraft = selectedDraft;
    }

    public List<Draft> getDrafts() {        
        return drafts;
    }

    public void setDrafts(List<Draft> drafts) {
        this.drafts = drafts;
    }
    
    
    public void reset() {
     vacancy=null;
    }

    public LoginMBean getLoginMBean() {
        return loginMBean;
    }

    public void setLoginMBean(LoginMBean loginMBean) {
        this.loginMBean = loginMBean;
    }

    public Discipline getDiscpiline() {
        return discpiline;
    }

    public void setDiscpiline(Discipline discpiline) {
        this.discpiline = discpiline;
    }

    public List<Credential> getCredList() {
        return credList;
    }

    public void setCredList(List<Credential> credList) {
        this.credList = credList;
    }

    public Adminusers getAu() {
        return au;
    }

    public void setAu(Adminusers au) {
        this.au = au;
    }

    public String getVacancy() {
        return vacancy;
    }

    public void setVacancy(String vacancy) {
        this.vacancy = vacancy;
    }

    public String getSelectedMenu() {
        return selectedMenu;
    }

    public void setSelectedMenu(String selectedMenu) {
        this.selectedMenu = selectedMenu;
    }
 
    
    public String sendSMS() throws ProcessingException
    {            
        List<Credential> lcredList = new ArrayList<Credential>();
        if(selectedMenu.equalsIgnoreCase("Area Of Interest"))
        {                        
        credList = credrepo.findByDiscipline(discpiline);        
        }else
        {
            credList = credrepo.findAll();
            System.out.println("All CredList:"+credList);
        }
        
        if(rangeTo.equalsIgnoreCase("Active")){
            
         for(Credential cred:credList)
         {
             Customer cc=customerrepo.findByUserId(cred.getCustomer());
             if(cc!=null&&cc.getPaymentStatus()!=null&&cc.getPaymentStatus()==1){                 
               lcredList.add(cred);
             }             
         }
        }else if(rangeTo.equalsIgnoreCase("InActive")){
          for(Credential cred:credList)
         {
             Customer cc=customerrepo.findByUserId(cred.getCustomer());
             if(cc!=null&&cc.getPaymentStatus()!=null&&cc.getPaymentStatus()==0){                 
               lcredList.add(cred);
             }             
         } 
        }else{
            lcredList=credList;
        }
        
        
        if(lcredList!=null&&!lcredList.isEmpty())
        {          
           for(Credential cred:lcredList){
               try{
              String prefix="Dear "+cred.getFirstname()+" "+cred.getSurname()+", ";
            smsrepo.sendSMS(ResponseCode.HEADER, prefix+vacancy, null,cred.getPhone(),new Date());
            }catch(Exception ex){ex.printStackTrace();}
         }
        vacancy=null;        
        }
        else
        {
            FacesMessage message = new FacesMessage("Error", " No credentials found");
        FacesContext.getCurrentInstance().addMessage(null, message); 
            return null;
        }
        auditrepo.audit(au.getUsername(), "Broadcast has been submitted Sent","IPADDRESS");              
        
        FacesMessage message = new FacesMessage("Succesful","Message(s) have been submitted");
        FacesContext.getCurrentInstance().addMessage(null, message);        
        return "";
    }
    
    
    public String sendEmail() throws ProcessingException
    {   
        List<Credential> lcredList = new ArrayList<Credential>();
        System.out.println("Discpiline:"+discpiline);
        if(selectedMenu.equalsIgnoreCase("Area Of Interest")){
        credList = credrepo.findByDiscipline(discpiline);
        }else{
            credList = credrepo.findAll();
        }
        if(rangeTo.equalsIgnoreCase("Active")){
            System.out.println("RangeTo:"+rangeTo);
         for(Credential cred:credList)
         {
             System.out.println("RangeTo:"+rangeTo+" "+cred.getCustomer());
             Customer cc=customerrepo.findByUserId(cred.getCustomer());
             if(cc!=null&&cc.getPaymentStatus()!=null&&cc.getPaymentStatus()==1){                 
               lcredList.add(cred);
             }             
         }
        }else if(rangeTo.equalsIgnoreCase("InActive")){
            
          for(Credential cred:credList)
         {
             System.out.println("RangeTo:"+rangeTo+" "+cred.getCustomer());
             Customer cc=customerrepo.findByUserId(cred.getCustomer());
             if(cc!=null&&cc.getPaymentStatus()!=null&&cc.getPaymentStatus()==0){  
               lcredList.add(cred);
             }             
         } 
        }else{
            lcredList=credList;
        }
        
        if(lcredList!=null&&!lcredList.isEmpty())
        {          
           for(Credential cred:lcredList)
         {
             try
             {
                 String prefix="Dear "+cred.getFirstname()+" "+cred.getSurname()+", ";
            emailrepo.sendEmail(ResponseCode.HEADER, prefix+vacancy, null,cred.getEmail(),new Date());
            }catch(Exception ex){ex.printStackTrace();}
         }
        vacancy=null;        
        }
        else
        {
        FacesMessage message = new FacesMessage("Error", " No credentials found");
        FacesContext.getCurrentInstance().addMessage(null, message); 
            return null;
        }
        auditrepo.audit(au.getUsername(), "Broadcast has been submitted Sent","IPADDRESS");              
        
        FacesMessage message = new FacesMessage("Succesful","Message(s) have been submitted");
        FacesContext.getCurrentInstance().addMessage(null, message);        
        return "";
    }

    public List<Vacancy> getVacancyList() {
        return vacancyList;
    }

    public void setVacancyList(List<Vacancy> vacancyList) {
        this.vacancyList = vacancyList;
    }
    
    public void searchVacancy(){
        System.out.println("Startdate:"+startdate);
        System.out.println("Enddate:"+enddate);        
        vacancyList=vacancyrepo.searchByDate(startdate,enddate);          
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public List<String> getBranchnames() {
        return branchnames;
    }

    public void setBranchnames(List<String> branchnames) {
        this.branchnames = branchnames;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public List<Discipline> getDiscpilineList() {
        return discpilineList;
    }

    public void setDiscpilineList(List<Discipline> discpilineList) {
        this.discpilineList = discpilineList;
    }

    public String getRangeTo() {
        return rangeTo;
    }

    public void setRangeTo(String rangeTo) {
        this.rangeTo = rangeTo;
    }
    
    
     public void onEdit(RowEditEvent event) {  
        Vacancy pr=(Vacancy) event.getObject();        
        System.out.println("Description:"+pr.getVacancyId());
        FacesMessage msg = new FacesMessage("Vacancy Edited","");        
        vvacancy=vacancyrepo.findById(pr.getVacancyId());
        vvacancy.setMessage(pr.getMessage());         
        vacancyrepo.edit(vvacancy);
        FacesContext.getCurrentInstance().addMessage(null, msg); 
    }  
       
    public void onCancel(RowEditEvent event) {  
        FacesMessage msg = new FacesMessage("Vacancy Cancelled");          
        Vacancy pr=(Vacancy) event.getObject();        
        vvacancy=vacancyrepo.findById(pr.getVacancyId());
        vacancyrepo.remove(vvacancy);
        vacancyList.remove(pr);
        FacesContext.getCurrentInstance().addMessage(null, msg); 
    }  

    public Vacancy getVvacancy() {
        return vvacancy;
    }

    public void setVvacancy(Vacancy vvacancy) {
        this.vvacancy = vvacancy;
    }
    
    
    

}