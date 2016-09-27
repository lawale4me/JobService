/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.core.ProcessingException;
import com.entities.Email;
import com.entities.QuicktellerTransactions;
import com.entities.Settings;
import com.util.QTStatus;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

/**
 *
 * @author Ahmed
 */
@Stateless
public class SettingsRepo extends AbstractDAO<Settings> {

    public SettingsRepo() {
        super(Settings.class);
    }
    
    public Settings findByName(String name) {
        EntityManager em = getEntityManager();
        List<Settings> outmsgs = new ArrayList();
        try {
            outmsgs =  em.createNamedQuery("Settings.findByName").setParameter("name", name).getResultList();
            if (outmsgs != null && !outmsgs.isEmpty()) {
                return outmsgs.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
