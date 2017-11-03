package org.greenhorn.servlets.practice;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;      
public class Validation extends HttpServlet 
{    
   public void doPost(HttpServletRequest request, 
     HttpServletResponse response) 
       throws ServletException, IOException 
   {        
       response.setContentType("text/html");      
       PrintWriter pwriter = response.getWriter();
      
      
       String name=request.getParameter("uname");      
       String pass=request.getParameter("upass");                
       if(name.equals("j2ee") && 
          pass.equals("practice"))
       {          
          RequestDispatcher dis=request.getRequestDispatcher("welcome");          
          dis.forward(request, response);    
       }     
       else
       {      
          pwriter.print("User name or password is incorrect!");          
          RequestDispatcher dis=request.getRequestDispatcher("index.html");          
          dis.include(request, response);                                
       }      
   }    
}  