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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "credential")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Credential.findAll", query = "SELECT c FROM Credential c"),
    @NamedQuery(name = "Credential.findByDisc", query = "SELECT c FROM Credential c WHERE c.discipline = :discipline"),
    @NamedQuery(name = "Credential.findByDiscId", query = "SELECT c FROM Credential c WHERE c.discipline.disciplineId = :disciplineId"),
    @NamedQuery(name = "Credential.findByCredentialId", query = "SELECT c FROM Credential c WHERE c.credentialId = :credentialId"),
    @NamedQuery(name = "Credential.findByCustomer", query = "SELECT c FROM Credential c WHERE c.customer = :customer"),
    @NamedQuery(name = "Credential.findByFirstname", query = "SELECT c FROM Credential c WHERE c.firstname = :firstname"),
    @NamedQuery(name = "Credential.findBySurname", query = "SELECT c FROM Credential c WHERE c.surname = :surname"),
    @NamedQuery(name = "Credential.findByEmail", query = "SELECT c FROM Credential c WHERE c.email = :email"),
    @NamedQuery(name = "Credential.findByPhone", query = "SELECT c FROM Credential c WHERE c.phone = :phone"),
    @NamedQuery(name = "Credential.findByDob", query = "SELECT c FROM Credential c WHERE c.dob = :dob"),
    @NamedQuery(name = "Credential.findByQualification", query = "SELECT c FROM Credential c WHERE c.qualification = :qualification"),
    @NamedQuery(name = "Credential.findByCvurl", query = "SELECT c FROM Credential c WHERE c.cvurl = :cvurl"),
    @NamedQuery(name = "Credential.findByPaymentStatus", query = "SELECT c FROM Credential c WHERE c.paymentStatus = :paymentStatus")})
public class Credential implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "credentialId")
    private Integer credentialId;
    @Column(name = "customer")
    private Integer customer;
    @Size(max = 45)
    @Column(name = "firstname")
    private String firstname;
    @Size(max = 45)
    @Column(name = "surname")
    private String surname;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 45)
    @Column(name = "email")
    private String email;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 45)
    @Column(name = "phone")
    private String phone;
    @Column(name = "dob")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dob;
    @Size(max = 45)
    @Column(name = "qualification")
    private String qualification;
    @Size(max = 255)
    @Column(name = "cvurl")
    private String cvurl;
    @Column(name = "paymentStatus")
    private Integer paymentStatus;
    @JoinColumn(name = "discipline", referencedColumnName = "disciplineId")
    @ManyToOne
    private Discipline discipline;
    @JoinColumn(name = "state", referencedColumnName = "stateId")
    @ManyToOne
    private States state;

    public Credential() {
    }

    public Credential(Integer credentialId) {
        this.credentialId = credentialId;
    }

    public Integer getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Integer credentialId) {
        this.credentialId = credentialId;
    }

    public Integer getCustomer() {
        return customer;
    }

    public void setCustomer(Integer customer) {
        this.customer = customer;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getCvurl() {
        return cvurl;
    }

    public void setCvurl(String cvurl) {
        this.cvurl = cvurl;
    }

    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public States getState() {
        return state;
    }

    public void setState(States state) {
        this.state = state;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (credentialId != null ? credentialId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Credential)) {
            return false;
        }
        Credential other = (Credential) object;
        if ((this.credentialId == null && other.credentialId != null) || (this.credentialId != null && !this.credentialId.equals(other.credentialId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Credential[ credentialId=" + credentialId + " ]";
    }
    
}
