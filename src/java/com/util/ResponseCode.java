/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

/**
 *
 * @author Ahmed
 */
public class ResponseCode
{
    public static final int NEW = 20;
    public static final int PROCESSING = 30;
    public static final int PENDING = 40;    
    public static final int OK = 0;
    public static final String sOK = "0";
    public static final int ERROR = 10;    
    public static final int TIMEOUT = 100;
    public static final int TEMP_PIN = 101;    
    public static final int CUSTOMER_NOT_FOUND = 102;
    public static final int SESSION_NOT_AVAILABLE = 103;
    public static final int VALIDATE_FAILED = 104;
    public static final int BILLER_NOT_FOUND = 1011;
    public static String ERROR_RECEIVING_RESPONSE="ERROR RECEIVING RESPONSE";
    public static String ERROR_COMMUNICATION_WITH_HOST="ERROR COMMUNICATION WITH HOST";
    public static final int FAILED=10;
    public static Double EMPTY_BALANCE=0d;
    public static Integer ACTIVE=1;
    public static String REGISTRATION_SUCCESSFUL="Account Registration Was Successful";
    public static String DEST_ACCOUNT_DOES_NOT_EXIST="DEST ACCOUNT DOES NOT EXIST";
    public static String INSUFFICIENT_BALANCE="INSUFFICIENT BALANCE";
    public static String SUCCESSFUL="SUCCESSFUL";
    public static String SFAILED="FAILED";
    public static String BALANCE_SUCCESSFUL="BALANCE SUCCESSFUL";
    public static String INVALID_ACCOUNTNO="INVALID ACCOUNTNO";
    public static String INVALID_EMAIL="INVALID EMAIL";
    public static String Invalid_request="Invalid Request";
    public static String Internal_Error_Occurred="Internal Error Occurred";
    public static String SAME_ACCOUNT_TRANSFER_NOT_POSSIBLE="SAME ACCOUNT TRANSFER NOT POSSIBLE";
    public static String TRANSFER_SUCCESSFUL="TRANSFER SUCCESSFUL";
    public static String INVALID_PIN="INVALID PIN";
    public static String WITHDRAWAL_SUCCESSFUL="WITHDRAWAL SUCCESSFUL";
    public static String DEPOSIT_SUCCESSFUL="DEPOSIT SUCCESSFUL";
    public static String LOAN_APPLICATION_SUCCESSFUL="LOAN APPLICATION SUCCESSFUL";
    public static String LOAN_APPLICATION_SUBMITTED="YOUR LOAN APPLICATION HAS BEEN SUBMITTED";
    public static int METHODNOTALLOWED=415;
    public static String METHOD_NOT_ALLOWED="METHOD NOT ALLOWED";
            
    public static final int UNKNOWN =30;
    public static final int USER_DISABLED=77;
    public static final int USER_EXPIRED=88;
    public static final int USER_NOT_FOUND =99;
    public static String LOAN_APPROVED="YOU LOAN HAS BEEN APPROVED";
    public static String LOAN_CANCELED="YOU LOAN HAS BEEN REJECTED";
    public static String LOAN_AUTHORIZED="YOU LOAN HAS BEEN AUTHORIZED";
    public static String LOAN_DISBURSRED="YOU LOAN HAS BEEN DISBURSRED";
    public static String HEADER="VaultJobs";    
    public static String EMAIL_SIGNATURE="\nThe VAULTJOBS Team";
    
}
