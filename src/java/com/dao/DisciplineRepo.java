/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dao;

import com.dto.DisciplineDTO;
import com.entities.Batch;
import com.entities.Credential;
import com.entities.Credential_;
import com.entities.Customer;
import com.entities.Customer_;
import com.entities.Discipline;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Ahmed
 */
@Stateless
public class DisciplineRepo extends AbstractDAO<Discipline> {
    
    private static final Logger logger = Logger.getLogger(AddressBookRepo.class.getName());

    public DisciplineRepo() {
        super(Discipline.class);
    }        

    public Discipline findDiscpiline(Integer disciplineId) {    
         EntityManager em = getEntityManager();
        List<Discipline> cred = null;
        try {
            cred = (List<Discipline>) em.createNamedQuery("Discipline.findByDisciplineId").setParameter("disciplineId", disciplineId).getResultList();
            if (cred != null&&!cred.isEmpty()) {
                return cred.get(0);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "exception caught", e);
        }
        return null;
    
    }

    public List<DisciplineDTO> findActiveAndInactive(List<Discipline> discList) {         
        List<DisciplineDTO> dDto=new ArrayList<DisciplineDTO>();
        for(Discipline d:discList){                
            int inactive=findDisciplinePaymentStatuscount(0,d);
            int active=findDisciplinePaymentStatuscount(1,d);
            DisciplineDTO dto=new DisciplineDTO();
            dto.setInactive(inactive);
            dto.setActive(active);
            dto.setName(d.getName());  
            dDto.add(dto);
        }
        return dDto;
    }
    
    public Integer findDisciplinePaymentStatuscount(Integer status,Discipline discId) {            
        EntityManager em = getEntityManager();
        Integer count=0;
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Credential> crit = cb.createQuery(Credential.class);
            Root<Credential> aRoot = crit.from(Credential.class);
            Root<Customer> bRoot = crit.from(Customer.class);
            aRoot.alias("a");
            bRoot.alias("b");
            crit.select(aRoot);
            Predicate fieldEquals = cb.equal(aRoot.get(Credential_.customer), bRoot.get(Customer_.customerId));
            Predicate fieldEqualsDisc = cb.equal(aRoot.get(Credential_.discipline), discId);
            Predicate fieldEqualsStatus = cb.equal(bRoot.get(Customer_.paymentStatus), status);
            Predicate p=cb.and(fieldEquals,fieldEqualsDisc,fieldEqualsStatus);
            crit.where(p);
            javax.persistence.Query q = em.createQuery(crit);
            List list=q.getResultList();
            return list.size();
                                              
        } catch (Exception e) {
            logger.log(Level.SEVERE, "exception caught", e);
        }
        return count;     
    }
   
}
