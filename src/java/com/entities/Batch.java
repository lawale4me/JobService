/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ahmed
 */
@Entity
@Table(name = "batch")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Batch.findAll", query = "SELECT b FROM Batch b"),
    @NamedQuery(name = "Batch.findByBatchId", query = "SELECT b FROM Batch b WHERE b.batchId = :batchId"),
    @NamedQuery(name = "Batch.findByBatchname", query = "SELECT b FROM Batch b WHERE b.batchname = :batchname"),
    @NamedQuery(name = "Batch.findByBatchdate", query = "SELECT b FROM Batch b WHERE b.batchdate = :batchdate")})
public class Batch implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "batchId")
    private Integer batchId;
    @Size(max = 45)
    @Column(name = "batchname")
    private String batchname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "batchdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date batchdate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "batchId")
    private Collection<Addressbook> addressbookCollection;
    @JoinColumn(name = "batchowner", referencedColumnName = "customerId")
    @ManyToOne(optional = false)
    private Customer batchowner;

    public Batch() {
    }

    public Batch(Integer batchId) {
        this.batchId = batchId;
    }

    public Batch(Integer batchId, Date batchdate) {
        this.batchId = batchId;
        this.batchdate = batchdate;
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public String getBatchname() {
        return batchname;
    }

    public void setBatchname(String batchname) {
        this.batchname = batchname;
    }

    public Date getBatchdate() {
        return batchdate;
    }

    public void setBatchdate(Date batchdate) {
        this.batchdate = batchdate;
    }

    @XmlTransient
    public Collection<Addressbook> getAddressbookCollection() {
        return addressbookCollection;
    }

    public void setAddressbookCollection(Collection<Addressbook> addressbookCollection) {
        this.addressbookCollection = addressbookCollection;
    }

    public Customer getBatchowner() {
        return batchowner;
    }

    public void setBatchowner(Customer batchowner) {
        this.batchowner = batchowner;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (batchId != null ? batchId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Batch)) {
            return false;
        }
        Batch other = (Batch) object;
        if ((this.batchId == null && other.batchId != null) || (this.batchId != null && !this.batchId.equals(other.batchId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Batch[ batchId=" + batchId + " ]";
    }
    
}
