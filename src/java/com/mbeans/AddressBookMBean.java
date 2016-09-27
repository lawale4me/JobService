/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbeans;


import com.core.ProcessingException;
import com.dao.AddressBookRepo;
import com.dao.AdminRepo;
import com.dao.BatchRepo;
import com.dao.CustomerRepo;
import com.entities.Addressbook;
import com.entities.Adminusers;
import com.entities.Batch;
import com.entities.Customer;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Ahmed
 */
@ManagedBean
@RequestScoped
public class AddressBookMBean {

    
    public AddressBookMBean() {
    }

    private String name,phonenumber,batchname;    
    private Batch batch;
    Addressbook contact;
    private List<Addressbook> contacts;
    private List<Batch>batchList;
    private UploadedFile file,myfile;
    String username;
    Adminusers user;
    Customer customer;
    @Inject
    AddressBookRepo addressrepo;
    @Inject
    AdminRepo adminrepo;
    @Inject
    CustomerRepo customerrepo;
    @Inject
    BatchRepo batchrepo;
    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        HttpSession httpSession = request.getSession(false);
        username=(String) httpSession.getAttribute("username");
        customer=customerrepo.findByUsername(username);
        contacts = addressrepo.getContacts(customer);
        batchList = batchrepo.getBatchList(customer);
    }



    public String addAction() throws ProcessingException {
        Addressbook ad = new Addressbook();
        ad.setBatchId(batch);
        ad.setName(name);
        ad.setOwnerId(customer);
        ad.setPhonenumber(phonenumber);
        
        addressrepo.create(ad);
        contacts.add(ad);

        name = null;
        phonenumber=null;        
        return null;
    }

    public String addAction2() throws ProcessingException {
        System.out.println("addAction222222222");   
        if (file != null) {
            ArrayList<String> Strs = new ArrayList<String>();
            String line = "";
            BufferedReader br;
            try {
                br = new BufferedReader(new InputStreamReader(file.getInputstream()));
            
            while ((line = br.readLine()) != null) {
                System.out.println("Contact:" + line);
                Strs.add(line);
            }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            Batch b=new Batch();
            b.setBatchdate(new Date());
            b.setBatchname(batchname);
            b.setBatchowner(customer);            
            batchrepo.create(b);
            
            for (String str : Strs) {                
                String[] arr=str.split(",");
                Addressbook ad = new Addressbook();
                ad.setBatchId(b);
                ad.setName(arr.length>1?arr[1]:"");
                ad.setOwnerId(customer);
                ad.setPhonenumber(arr[0]);
                addressrepo.create(ad);
                contacts.add(ad);
            }
            name = "";
            phonenumber="";
            batchname=null;

            FacesMessage message = new FacesMessage("Successful", file.getFileName() + " was uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            FacesMessage message = new FacesMessage("Error", " File not uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

        return null;
    }
    
    public String addAction3() throws ProcessingException {
        
        System.out.println("File"+myfile);
        if (myfile != null) {
            ArrayList<String> Strs = new ArrayList<String>();
            String line = "";
            BufferedReader br;
            try {
                br = new BufferedReader(new InputStreamReader(myfile.getInputstream()));
            
            while ((line = br.readLine()) != null) {
                System.out.println("Contact:" + line);
                Strs.add(line);
            }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            
            
            for (String str : Strs) {                
                String[] arr=str.split(",");
                Addressbook ad = new Addressbook();
                ad.setBatchId(batch);
                ad.setName(arr.length>1?arr[1]:"");
                ad.setOwnerId(customer);
                ad.setPhonenumber(arr[0]);
                addressrepo.create(ad);
                contacts.add(ad);
            }        ;

            FacesMessage message = new FacesMessage("Successful", myfile.getFileName() + " was uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            FacesMessage message = new FacesMessage("Error", " File not uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

        return null;
    }
    

    public void onEdit(RowEditEvent event) {
    }

    public void onCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Contact Deleted");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        Addressbook pr = (Addressbook) event.getObject();
        pr = addressrepo.findByPhonenumber(pr.getPhonenumber());
        addressrepo.remove(pr);
        contacts.remove(pr);
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public Addressbook getContact() {
        return contact;
    }

    public void setContact(Addressbook contact) {
        this.contact = contact;
    }

    public List<Addressbook> getContacts() {
        return contacts;
    }

    public void setContacts(List<Addressbook> contacts) {
        this.contacts = contacts;
    }

    public List<Batch> getBatchList() {
        return batchList;
    }

    public void setBatchList(List<Batch> batchList) {
        this.batchList = batchList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Adminusers getUser() {
        return user;
    }

    public void setUser(Adminusers user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getBatchname() {
        return batchname;
    }

    public void setBatchname(String batchname) {
        this.batchname = batchname;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public UploadedFile getMyfile() {
        return myfile;
    }

    public void setMyfile(UploadedFile myfile) {
        this.myfile = myfile;
    }

  
    
    

}
