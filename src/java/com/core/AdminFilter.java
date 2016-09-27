/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.core;

import java.io.IOException;
import static javax.servlet.DispatcherType.FORWARD;
import static javax.servlet.DispatcherType.REQUEST;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ahmed
 */
@WebFilter(filterName = "AdminFilter", urlPatterns = {"/faces/admin/*"} , dispatcherTypes = { REQUEST, FORWARD })
public class AdminFilter implements Filter {
/**
     * Checks if user is logged in. If not it redirects to the login.xhtml page.
     * @param request
     * @param response
     * @param chain
     * @throws java.io.IOException
     * @throws javax.servlet.ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Get the loginBean from session attribute
       String username = (String)((HttpServletRequest)request).getSession().getAttribute("adminadmin");
         
       String path = ((HttpServletRequest) request).getRequestURI();
        if (path.startsWith("/adminLogon.xhtml")) {
            System.out.println("Path:"+path);
            chain.doFilter(request, response); // Just continue chain.
        } else {
        
       
       
        if (username == null ) {
            String contextPath = ((HttpServletRequest)request).getContextPath();
            ((HttpServletResponse)response).sendRedirect(contextPath + "/faces/adminLogon.xhtml");
        }
        }     
        chain.doFilter(request, response);
             
    }
 
    public void init(FilterConfig config) throws ServletException {
        // Nothing to do here!
    }
 
    public void destroy() {
        // Nothing to do here!
    }  

    
}
