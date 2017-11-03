package org.greenhorn.servlets.practice;

import java.io.*; 
import javax.servlet.*; 
import javax.servlet.http.*; 
 
public class WelcomeUser extends HttpServlet { 
 
 public void doPost(HttpServletRequest request, HttpServletResponse response) 
 throws ServletException, IOException 
 { 
	 ServletConfig config = getServletConfig();
	 String initname = config.getInitParameter("userName");
     System.out.println("==========="+initname);
    response.setContentType("text/html"); 
    //response.sendRedirect("http://shaik09.blogspot.in/p/core-java.html");
    PrintWriter pwriter = response.getWriter(); 
 
    String name=request.getParameter("uname"); 
    pwriter.print("Hello "+name+"!");
    pwriter.print("your Init param .....  "+initname+"!");
    pwriter.print(" Welcome to J2EE Practice Sessions ..."); 
 } 
 
} 