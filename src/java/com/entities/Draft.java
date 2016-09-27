/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ahmed
 */
@Entity
@Table(name = "draft")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Draft.findAll", query = "SELECT d FROM Draft d"),
    @NamedQuery(name = "Draft.findByDraftId", query = "SELECT d FROM Draft d WHERE d.draftId = :draftId")})
public class Draft implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "draftId")
    private Integer draftId;
    @Lob
    @Size(max = 65535)
    @Column(name = "draft")
    private String draft;
    @JoinColumn(name = "customer", referencedColumnName = "customerId")
    @ManyToOne
    private Customer customer;

    public Draft() {
    }

    public Draft(Integer draftId) {
        this.draftId = draftId;
    }

    public Integer getDraftId() {
        return draftId;
    }

    public void setDraftId(Integer draftId) {
        this.draftId = draftId;
    }

    public String getDraft() {
        return draft;
    }

    public void setDraft(String draft) {
        this.draft = draft;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (draftId != null ? draftId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Draft)) {
            return false;
        }
        Draft other = (Draft) object;
        if ((this.draftId == null && other.draftId != null) || (this.draftId != null && !this.draftId.equals(other.draftId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Draft[ draftId=" + draftId + " ]";
    }
    
}
