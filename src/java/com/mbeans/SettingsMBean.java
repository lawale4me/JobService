/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mbeans;


import com.core.ProcessingException;
import com.dao.SettingsRepo;
import com.entities.Settings;
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

/**
 *
 * @author Ahmed
 */
@ManagedBean
@RequestScoped
public class SettingsMBean {

    /**
     * Creates a new instance of ProductMBean
     */
    public SettingsMBean() {
    }

    
    private String value;
    private String name;
    Settings settings;
    private List<Settings> settingsList ;
    
    @Inject
    SettingsRepo settingsrepo;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();  
        HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();  
        HttpSession httpSession = request.getSession(false);          
        settingsList=settingsrepo.findAll();
    }
                   

     
    
    
       
 
    public String addAction() {
        Settings setting = new Settings();        
        setting.setName(name);
        setting.setValue(value);        
        try {        
            settingsrepo.create(setting);
        } catch (ProcessingException ex) {
            Logger.getLogger(SettingsMBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        settingsList.add(setting);
 
        name = "";
        value = "";              
        return null;
    }
    public void onEdit(RowEditEvent event) {  
        Settings setting=(Settings) event.getObject();
        System.out.println("Description:"+setting.getValue());
        FacesMessage msg = new FacesMessage("Setting Edited",setting.getName());  
         setting=settingsrepo.findByName(setting.getName());         
         setting.setValue(value);
        settingsrepo.edit(setting);
        settingsList=settingsrepo.findAll();
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }  
       
    public void onCancel(RowEditEvent event) {  
//        FacesMessage msg = new FacesMessage("Settings Cancelled");   
//        FacesContext.getCurrentInstance().addMessage(null, msg); 
//        Settings setting=(Settings) event.getObject();
//        setting=settingsrepo.findByName(setting.getName());
//        settingsrepo.remove(setting);
//        settingsList.remove(setting);
    }  

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public List<Settings> getSettingsList() {
        return settingsList;
    }

    public void setSettingsList(List<Settings> settingsList) {
        this.settingsList = settingsList;
    }
    
    
    
    
}
