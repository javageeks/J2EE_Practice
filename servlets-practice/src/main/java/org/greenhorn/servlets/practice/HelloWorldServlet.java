package org.greenhorn.servlets.practice;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class HelloWorldServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1031422249396784970L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		try {
	           
            String user=request.getParameter("user");
            out.println("<h2> Welcome "+user+"</h2>");
        } finally {            
            out.close();
        }
		/*out.print("Hello World from Servlet");
		out.flush();
		out.close();*/
	}
}
