/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.entities.Vacancy;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

/**
 *
 * @author Ahmed
 */
@Stateless
public class VacancyRepo extends AbstractDAO<Vacancy> {
    
    private static final Logger logger = Logger.getLogger(Vacancy.class.getName());

    public VacancyRepo() {
        super(Vacancy.class);
    }        
    
    
     public Vacancy findState(Integer stateId) {    
         EntityManager em = getEntityManager();
        List<Vacancy> cred = null;
        try {
            cred = (List<Vacancy>) em.createNamedQuery("Vacancy.findByStateId").setParameter("stateId", stateId).getResultList();
            if (cred != null&&!cred.isEmpty()) {
                return cred.get(0);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "exception caught", e);
        }
        return null;    
    }

    public List<Vacancy> getPending() {
         EntityManager em = getEntityManager();
        List<Vacancy> vacancyList = null;
        try {
            vacancyList = (List<Vacancy>) em.createNamedQuery("Vacancy.findByStatus").setParameter("status", 0).getResultList();
            if (vacancyList != null&&!vacancyList.isEmpty()) {
                return vacancyList;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "exception caught", e);
        }
        return null;
    }

    public List<Vacancy> searchByDate(Date sDate, Date eDate) {
         EntityManager em = getEntityManager();
        List<Vacancy> outmsgs = null;
        try {
            outmsgs = (List<Vacancy>) em.createQuery("SELECT i FROM Vacancy i WHERE i.vacancyDate BETWEEN :startdate AND :enddate order by i.vacancyId desc", Vacancy.class).setParameter("startdate", sDate).setParameter("enddate", eDate).getResultList();
            if (outmsgs != null) {
                return outmsgs;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outmsgs;
    }

    public Vacancy findById(Integer vacancyId) {
        EntityManager em = getEntityManager();
        List<Vacancy> vacancyList = null;
        try {
            vacancyList = (List<Vacancy>) em.createNamedQuery("Vacancy.findByVacancyId").setParameter("vacancyId", vacancyId).getResultList();
            if (vacancyList != null&&!vacancyList.isEmpty()) {
                return vacancyList.get(0);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "exception caught", e);
        }
        return null;
    }
   
}
