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
import javax.persistence.Lob;
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
@Table(name = "outmessages")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Outmessages.findAll", query = "SELECT o FROM Outmessages o"),
    @NamedQuery(name = "Outmessages.findByMsgId", query = "SELECT o FROM Outmessages o WHERE o.msgId = :msgId"),
    @NamedQuery(name = "Outmessages.findByMessageType", query = "SELECT o FROM Outmessages o WHERE o.messageType = :messageType"),
    @NamedQuery(name = "Outmessages.findByStatus", query = "SELECT o FROM Outmessages o WHERE o.status = :status"),
    @NamedQuery(name = "Outmessages.findBySendDate", query = "SELECT o FROM Outmessages o WHERE o.sendDate = :sendDate"),
    @NamedQuery(name = "Outmessages.findByHeader", query = "SELECT o FROM Outmessages o WHERE o.header = :header"),
    @NamedQuery(name = "Outmessages.findByMsgDate", query = "SELECT o FROM Outmessages o WHERE o.msgDate = :msgDate"),
    @NamedQuery(name = "Outmessages.findByDestAddress", query = "SELECT o FROM Outmessages o WHERE o.destAddress = :destAddress order by o.msgId desc"),
    @NamedQuery(name = "Outmessages.findByRetryCount", query = "SELECT o FROM Outmessages o WHERE o.retryCount = :retryCount")})
public class Outmessages implements Serializable {
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
    @Size(max = 45)
    @Column(name = "destAddress")
    private String destAddress;
    @Column(name = "retryCount")
    private Integer retryCount;
    @JoinColumn(name = "senderId", referencedColumnName = "customerId")
    @ManyToOne
    private Customer senderId;

    public Outmessages() {
    }

    public Outmessages(Integer msgId) {
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

    public Customer getSenderId() {
        return senderId;
    }

    public void setSenderId(Customer senderId) {
        this.senderId = senderId;
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
        if (!(object instanceof Outmessages)) {
            return false;
        }
        Outmessages other = (Outmessages) object;
        if ((this.msgId == null && other.msgId != null) || (this.msgId != null && !this.msgId.equals(other.msgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Outmessages[ msgId=" + msgId + " ]";
    }
    
}
