/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.util;

/**
 *
 * @author Ahmed
 */
public class ResponseDetails {
    private int errorCode;
    private String errorDescription;

    public ResponseDetails() {
    }

    public ResponseDetails(int errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }



}
