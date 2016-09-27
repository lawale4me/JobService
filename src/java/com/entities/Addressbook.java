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
@Table(name = "addressbook")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Addressbook.findAll", query = "SELECT a FROM Addressbook a"),
    @NamedQuery(name = "Addressbook.findByAddressbookId", query = "SELECT a FROM Addressbook a WHERE a.addressbookId = :addressbookId"),
    @NamedQuery(name = "Addressbook.findByPhonenumber", query = "SELECT a FROM Addressbook a WHERE a.phonenumber = :phonenumber"),
    @NamedQuery(name = "Addressbook.findByName", query = "SELECT a FROM Addressbook a WHERE a.name = :name")})
public class Addressbook implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "addressbookId")
    private Integer addressbookId;
    @Size(max = 45)
    @Column(name = "phonenumber")
    private String phonenumber;
    @Size(max = 45)
    @Column(name = "name")
    private String name;
    @JoinColumn(name = "batchId", referencedColumnName = "batchId")
    @ManyToOne(optional = false)
    private Batch batchId;
    @JoinColumn(name = "ownerId", referencedColumnName = "customerId")
    @ManyToOne
    private Customer ownerId;

    public Addressbook() {
    }

    public Addressbook(Integer addressbookId) {
        this.addressbookId = addressbookId;
    }

    public Integer getAddressbookId() {
        return addressbookId;
    }

    public void setAddressbookId(Integer addressbookId) {
        this.addressbookId = addressbookId;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Batch getBatchId() {
        return batchId;
    }

    public void setBatchId(Batch batchId) {
        this.batchId = batchId;
    }

    public Customer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Customer ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (addressbookId != null ? addressbookId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Addressbook)) {
            return false;
        }
        Addressbook other = (Addressbook) object;
        if ((this.addressbookId == null && other.addressbookId != null) || (this.addressbookId != null && !this.addressbookId.equals(other.addressbookId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Addressbook[ addressbookId=" + addressbookId + " ]";
    }
    
}
