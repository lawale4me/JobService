/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Ahmed
 */
public class SystemUtil {
    
    public static String convertStreamToString(InputStream is) {
      /*
       * To convert the InputStream to String we use the BufferedReader.readLine()
       * method. We iterate until the BufferedReader return null which means
       * there's no more data to read. Each line will appended to a StringBuilder
       * and returned as String.
       */
      BufferedReader reader = new BufferedReader(new InputStreamReader(is));
      StringBuilder sb = new StringBuilder();

      String line = null;
      try {
          while ((line = reader.readLine()) != null) {
              sb.append(line);
          }
      } catch (IOException e) {
          e.printStackTrace();
      } finally {
          try {
              is.close();
          } catch (IOException e) {
              e.printStackTrace();
          }
      }
      return sb.toString();
  }
    
}
