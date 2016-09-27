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
@Table(name = "quickteller_transactions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "QuicktellerTransactions.findAll", query = "SELECT q FROM QuicktellerTransactions q order by q.qtID desc"),
    @NamedQuery(name = "QuicktellerTransactions.findByQtID", query = "SELECT q FROM QuicktellerTransactions q WHERE q.qtID = :qtID"),
    @NamedQuery(name = "QuicktellerTransactions.findByCustomerId", query = "SELECT q FROM QuicktellerTransactions q WHERE q.customerId = :customerId"),
    @NamedQuery(name = "QuicktellerTransactions.findByAmount", query = "SELECT q FROM QuicktellerTransactions q WHERE q.amount = :amount"),
    @NamedQuery(name = "QuicktellerTransactions.findByRequestStatus", query = "SELECT q FROM QuicktellerTransactions q WHERE q.requestStatus = :requestStatus"),
    @NamedQuery(name = "QuicktellerTransactions.findByTranxDate", query = "SELECT q FROM QuicktellerTransactions q WHERE q.tranxDate = :tranxDate")})
public class QuicktellerTransactions implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "qtID")
    private Integer qtID;
    @Size(max = 45)
    @Column(name = "customerId")
    private String customerId;
    @Lob
    @Size(max = 65535)
    @Column(name = "tx_ref")
    private String txRef;
    @Lob
    @Size(max = 65535)
    @Column(name = "resp_desc")
    private String respDesc;
    @Lob
    @Size(max = 65535)
    @Column(name = "status")
    private String status;
    @Lob
    @Size(max = 65535)
    @Column(name = "resp_code")
    private String respCode;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amount")
    private Double amount;
    @Column(name = "requestStatus")
    private Integer requestStatus;
    @Column(name = "tranxDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tranxDate;

    public QuicktellerTransactions() {
    }

    public QuicktellerTransactions(Integer qtID) {
        this.qtID = qtID;
    }

    public Integer getQtID() {
        return qtID;
    }

    public void setQtID(Integer qtID) {
        this.qtID = qtID;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTxRef() {
        return txRef;
    }

    public void setTxRef(String txRef) {
        this.txRef = txRef;
    }

    public String getRespDesc() {
        return respDesc;
    }

    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(Integer requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Date getTranxDate() {
        return tranxDate;
    }

    public void setTranxDate(Date tranxDate) {
        this.tranxDate = tranxDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (qtID != null ? qtID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QuicktellerTransactions)) {
            return false;
        }
        QuicktellerTransactions other = (QuicktellerTransactions) object;
        if ((this.qtID == null && other.qtID != null) || (this.qtID != null && !this.qtID.equals(other.qtID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.QuicktellerTransactions[ qtID=" + qtID + " ]";
    }
    
}
