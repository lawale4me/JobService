/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ahmed
 */
@Entity
@Table(name = "auditreport")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Auditreport.findAll", query = "SELECT a FROM Auditreport a"),
    @NamedQuery(name = "Auditreport.findById", query = "SELECT a FROM Auditreport a WHERE a.id = :id"),
    @NamedQuery(name = "Auditreport.findByAction", query = "SELECT a FROM Auditreport a WHERE a.action = :action"),
    @NamedQuery(name = "Auditreport.findByActionDate", query = "SELECT a FROM Auditreport a WHERE a.actionDate = :actionDate"),
    @NamedQuery(name = "Auditreport.findByIpAddress", query = "SELECT a FROM Auditreport a WHERE a.ipAddress = :ipAddress"),
    @NamedQuery(name = "Auditreport.findByUserName", query = "SELECT a FROM Auditreport a WHERE a.userName = :userName")})
public class Auditreport implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 255)
    @Column(name = "action")
    private String action;
    @Column(name = "actionDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actionDate;
    @Size(max = 255)
    @Column(name = "ipAddress")
    private String ipAddress;
    @Size(max = 255)
    @Column(name = "userName")
    private String userName;

    public Auditreport() {
    }

    public Auditreport(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Auditreport)) {
            return false;
        }
        Auditreport other = (Auditreport) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Auditreport[ id=" + id + " ]";
    }
    
}
