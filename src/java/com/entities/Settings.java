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
@Table(name = "settings")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Settings.findAll", query = "SELECT s FROM Settings s"),
    @NamedQuery(name = "Settings.findBySettingsId", query = "SELECT s FROM Settings s WHERE s.settingsId = :settingsId"),
    @NamedQuery(name = "Settings.findByName", query = "SELECT s FROM Settings s WHERE s.name = :name"),
    @NamedQuery(name = "Settings.findByValue", query = "SELECT s FROM Settings s WHERE s.value = :value")})
public class Settings implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "settingsId")
    private Integer settingsId;
    @Size(max = 45)
    @Column(name = "name")
    private String name;
    @Size(max = 45)
    @Column(name = "value")
    private String value;

    public Settings() {
    }

    public Settings(Integer settingsId) {
        this.settingsId = settingsId;
    }

    public Integer getSettingsId() {
        return settingsId;
    }

    public void setSettingsId(Integer settingsId) {
        this.settingsId = settingsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (settingsId != null ? settingsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Settings)) {
            return false;
        }
        Settings other = (Settings) object;
        if ((this.settingsId == null && other.settingsId != null) || (this.settingsId != null && !this.settingsId.equals(other.settingsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Settings[ settingsId=" + settingsId + " ]";
    }
    
}
