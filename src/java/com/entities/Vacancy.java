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
@Table(name = "vacancy")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vacancy.findAll", query = "SELECT v FROM Vacancy v"),
    @NamedQuery(name = "Vacancy.findByVacancyId", query = "SELECT v FROM Vacancy v WHERE v.vacancyId = :vacancyId"),
    @NamedQuery(name = "Vacancy.findByTarget", query = "SELECT v FROM Vacancy v WHERE v.target = :target"),
    @NamedQuery(name = "Vacancy.findByVacancyDate", query = "SELECT v FROM Vacancy v WHERE v.vacancyDate = :vacancyDate"),
    @NamedQuery(name = "Vacancy.findByStatus", query = "SELECT v FROM Vacancy v WHERE v.status = :status")})
public class Vacancy implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "vacancyId")
    private Integer vacancyId;
    @Lob
    @Size(max = 65535)
    @Column(name = "message")
    private String message;
    @Column(name = "target")
    private Integer target;
    @Column(name = "vacancyDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vacancyDate;
    @Column(name = "status")
    private Integer status;

    public Vacancy() {
    }

    public Vacancy(Integer vacancyId) {
        this.vacancyId = vacancyId;
    }

    public Integer getVacancyId() {
        return vacancyId;
    }

    public void setVacancyId(Integer vacancyId) {
        this.vacancyId = vacancyId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }

    public Date getVacancyDate() {
        return vacancyDate;
    }

    public void setVacancyDate(Date vacancyDate) {
        this.vacancyDate = vacancyDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vacancyId != null ? vacancyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vacancy)) {
            return false;
        }
        Vacancy other = (Vacancy) object;
        if ((this.vacancyId == null && other.vacancyId != null) || (this.vacancyId != null && !this.vacancyId.equals(other.vacancyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Vacancy[ vacancyId=" + vacancyId + " ]";
    }
    
}
