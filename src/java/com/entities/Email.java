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
@Table(name = "email")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Email.findAll", query = "SELECT e FROM Email e"),
    @NamedQuery(name = "Email.findByMsgId", query = "SELECT e FROM Email e WHERE e.msgId = :msgId"),
    @NamedQuery(name = "Email.findByMessageType", query = "SELECT e FROM Email e WHERE e.messageType = :messageType"),
    @NamedQuery(name = "Email.findBySenderId", query = "SELECT e FROM Email e WHERE e.senderId = :senderId"),
    @NamedQuery(name = "Email.findByStatus", query = "SELECT e FROM Email e WHERE e.status = :status"),
    @NamedQuery(name = "Email.findBySendDate", query = "SELECT e FROM Email e WHERE e.sendDate = :sendDate"),
    @NamedQuery(name = "Email.findByHeader", query = "SELECT e FROM Email e WHERE e.header = :header"),
    @NamedQuery(name = "Email.findByMsgDate", query = "SELECT e FROM Email e WHERE e.msgDate = :msgDate"),
    @NamedQuery(name = "Email.findByDestAddress", query = "SELECT e FROM Email e WHERE e.destAddress = :destAddress"),
    @NamedQuery(name = "Email.findByRetryCount", query = "SELECT e FROM Email e WHERE e.retryCount = :retryCount")})
public class Email implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "msgId")
    private Integer msgId;
    @Lob
    @Size(max = 65535)
    @Column(name = "message")
    private String message;
    @Column(name = "messageType")
    private Integer messageType;
    @Column(name = "senderId")
    private Integer senderId;
    @Column(name = "status")
    private Integer status;
    @Column(name = "sendDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendDate;
    @Size(max = 45)
    @Column(name = "header")
    private String header;
    @Column(name = "msgDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date msgDate;
    @Size(max = 55)
    @Column(name = "destAddress")
    private String destAddress;
    @Column(name = "retryCount")
    private Integer retryCount;

    public Email() {
    }

    public Email(Integer msgId) {
        this.msgId = msgId;
    }

    public Integer getMsgId() {
        return msgId;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Date getMsgDate() {
        return msgDate;
    }

    public void setMsgDate(Date msgDate) {
        this.msgDate = msgDate;
    }

    public String getDestAddress() {
        return destAddress;
    }

    public void setDestAddress(String destAddress) {
        this.destAddress = destAddress;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (msgId != null ? msgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Email)) {
            return false;
        }
        Email other = (Email) object;
        if ((this.msgId == null && other.msgId != null) || (this.msgId != null && !this.msgId.equals(other.msgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Email[ msgId=" + msgId + " ]";
    }
    
}
