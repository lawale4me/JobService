/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

/**
 *
 * @author Ahmed
 */
public class Email {
    private int emailid;
    private String emailAddress;
    private String subject;
    private String message;
    private int fetchStatus;
    private int status;

    /**
     * @return the emailid
     */
    public int getEmailid() {
        return emailid;
    }

    /**
     * @param emailid the emailid to set
     */
    public void setEmailid(int emailid) {
        this.emailid = emailid;
    }

    /**
     * @return the emailAddress
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * @param emailAddress the emailAddress to set
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the fetchStatus
     */
    public int getFetchStatus() {
        return fetchStatus;
    }

    /**
     * @param fetchStatus the fetchStatus to set
     */
    public void setFetchStatus(int fetchStatus) {
        this.fetchStatus = fetchStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }
    
}
