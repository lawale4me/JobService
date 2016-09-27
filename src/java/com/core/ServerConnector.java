/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core;


import com.util.QTResponse;
import com.util.Util;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Ahmed
 */


public class ServerConnector {

	static final String TAG = "SCANTRANXRETAIL";

	public static String post(String endpoint, Map<String, String> params)throws Exception {
		String result=null;
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
		HttpConnectionParams.setSoTimeout(httpParameters, 10000);
		HttpClient httpclient = new DefaultHttpClient(httpParameters);
	    HttpPost httppost = new HttpPost(endpoint);
	    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String> param = iterator.next();
			nameValuePairs.add(new BasicNameValuePair(param.getKey(),param.getValue()));
		}
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream instream = entity.getContent();
            result= SystemUtil.convertStreamToString(instream);
   
            instream.close();
          }
    
    return result;
	}

	public static String GET(String endpoint, Map<String, String> params, Map<String, String> header)throws Exception {
		String result = null;
		StringBuilder bodyBuilder = new StringBuilder(endpoint).append("?");
		Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
		// constructs the POST body using the parameters
		while (iterator.hasNext()) {
			Entry<String, String> param = iterator.next();
			bodyBuilder.append(param.getKey()).append('=')
					.append(param.getValue());
			if (iterator.hasNext()) {
				bodyBuilder.append('&');
			}
		}
		String body = bodyBuilder.toString();
		//Log.i("SCANTRANX", "URL:"+body);
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
		HttpConnectionParams.setSoTimeout(httpParameters, 10000);
		 HttpClient client =  new DefaultHttpClient(httpParameters);
	        HttpGet request = new HttpGet(body);
                
                for(Entry<String,String> mm:header.entrySet()){
                request.addHeader(mm.getKey(), mm.getValue());
                }
                System.out.println("Raw Request:"+request);
	        HttpResponse response = client.execute(request);
	        HttpEntity entity = response.getEntity();
	        if (entity != null) {
	            InputStream instream = entity.getContent();
	            result= SystemUtil.convertStreamToString(instream);
	            instream.close();
	          }
	    
	    return result;
	}

    public static QTResponse getQtResponse(String txRef) 
    {
        QTResponse qtResponse =new QTResponse();
        String resCode = null,amount=null,resDesc=null,pmtRef=null,tdate=null,message=null;
        JSONObject jo = null;
        boolean gotres=false;
        
        try {
            String secretKey="DSAKJFD9873JKDS32DFDSKJ389FF2";            
            String endpoint="https://paywith.quickteller.com/api/v2/transaction.json";
            HashMap params=new HashMap<String,String>();
            HashMap headers=new HashMap<String,String>();
            params.put("transRef", URLEncoder.encode(txRef));
          //  params.put("amount", "100");
            headers.put("clientid", "smssolutions.com.ng");
            headers.put("Hash", Util.hash(txRef+secretKey));
            
            
            String response = GET(endpoint, params,headers);            
            System.out.println("Raw Respone:"+response);
            
            jo=new JSONObject(response);
            resCode=jo.getString("ResponseCode");
            amount=jo.getString("Amount");
            resDesc=jo.getString("ResponseDescription");
            pmtRef=jo.getString("PaymentReference");
            tdate=jo.getString("TransactionDate");
            gotres=true;
            
        } catch (JSONException ex) {
            ex.printStackTrace();
            try {
                message=jo.getString("Message");
            } catch (JSONException ex1) {
                ex1.printStackTrace();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
                
        if(gotres){
            System.out.println("*********Successful Response**********");            
            System.out.println("ResponseCode:"+resCode);      
            System.out.println("Amount:"+amount);      
            System.out.println("ResDesc:"+resDesc);
            System.out.println("pmtRef:"+pmtRef);
            System.out.println("tdate:"+tdate);
            
            
            qtResponse.setAmount(amount);
            qtResponse.setResponseCode(resCode);
            qtResponse.setDescription(resDesc);
            qtResponse.setHonour(true);
            if(resCode.equals("00")){
            qtResponse.setSuccess(true);
            }
        }
        else
        {
            System.out.println("*********Failed**********");            
            System.out.println("Message:"+message); 
            qtResponse.setSuccess(false);
            qtResponse.setDescription(message);
        }
        
        return qtResponse;
    }

}

