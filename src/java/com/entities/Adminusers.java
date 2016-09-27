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
import javax.persistence.Lob;
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
@Table(name = "adminusers")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Adminusers.findAll", query = "SELECT a FROM Adminusers a"),
    @NamedQuery(name = "Adminusers.findByUserId", query = "SELECT a FROM Adminusers a WHERE a.userId = :userId"),
    @NamedQuery(name = "Adminusers.findByFirstname", query = "SELECT a FROM Adminusers a WHERE a.firstname = :firstname"),
    @NamedQuery(name = "Adminusers.findByLastname", query = "SELECT a FROM Adminusers a WHERE a.lastname = :lastname"),
    @NamedQuery(name = "Adminusers.findByUsername", query = "SELECT a FROM Adminusers a WHERE a.username = :username"),
    @NamedQuery(name = "Adminusers.findByPhonenumber", query = "SELECT a FROM Adminusers a WHERE a.phonenumber = :phonenumber"),
    @NamedQuery(name = "Adminusers.findByEmail", query = "SELECT a FROM Adminusers a WHERE a.email = :email"),
    @NamedQuery(name = "Adminusers.findBySecret", query = "SELECT a FROM Adminusers a WHERE a.secret = :secret"),
    @NamedQuery(name = "Adminusers.findByStatus", query = "SELECT a FROM Adminusers a WHERE a.status = :status"),
    @NamedQuery(name = "Adminusers.findByTrycount", query = "SELECT a FROM Adminusers a WHERE a.trycount = :trycount"),
    @NamedQuery(name = "Adminusers.findByDatecreated", query = "SELECT a FROM Adminusers a WHERE a.datecreated = :datecreated")})
public class Adminusers implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "userId")
    private Integer userId;
    @Size(max = 45)
    @Column(name = "firstname")
    private String firstname;
    @Size(max = 45)
    @Column(name = "lastname")
    private String lastname;
    @Size(max = 45)
    @Column(name = "username")
    private String username;
    @Lob
    @Size(max = 65535)
    @Column(name = "password")
    private String password;
    @Size(max = 45)
    @Column(name = "phonenumber")
    private String phonenumber;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 45)
    @Column(name = "email")
    private String email;
    @Size(max = 45)
    @Column(name = "secret")
    private String secret;
    @Column(name = "status")
    private Integer status;
    @Column(name = "trycount")
    private Integer trycount;
    @Column(name = "datecreated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datecreated;

    public Adminusers() {
    }

    public Adminusers(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTrycount() {
        return trycount;
    }

    public void setTrycount(Integer trycount) {
        this.trycount = trycount;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Adminusers)) {
            return false;
        }
        Adminusers other = (Adminusers) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Adminusers[ userId=" + userId + " ]";
    }
    
}
