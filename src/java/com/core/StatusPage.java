/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core;

import com.dao.QTRepo;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ahmed
 */
public class StatusPage extends HttpServlet {

    
    @Inject
    QTRepo qtrepo;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String resCode=request.getParameter("resp_code");
        String customerId=request.getParameter("customerId");
        String tx_ref=request.getParameter("tx_ref");
        String resp_desc=request.getParameter("resp_desc");        
        String amount=request.getParameter("amount");
        
        System.out.println("customerId:"+customerId);
        System.out.println("tx_ref:"+tx_ref);
        System.out.println("resp_desc:"+resp_desc);        
        System.out.println("amount:"+amount);
        System.out.println("Response Code:"+resCode);
        
      //  Double amt=amount==null?0d:Double.parseDouble(amount);
//        String signature=request.getParameter("signature");
//        String Recharge_pin=request.getParameter("Recharge_pin");
//        if(resCode.equals("90000")||resCode.equals("00")){
            try {
                qtrepo.log(500d, tx_ref, customerId, resCode, resp_desc, null);
            } catch (ProcessingException ex) {
                Logger.getLogger(StatusPage.class.getName()).log(Level.SEVERE, null, ex);
            }
//        }
        
        out.println("--------------------------------------------------------- <br/>");
        out.println("\nCustomer ID:   "+customerId);
        out.println("<br/> Trancation Ref:    "+tx_ref);
        out.println("<br/> Response Description:  "+resp_desc);        
        out.println("<br/>---------------------------------------------------------");
           
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
