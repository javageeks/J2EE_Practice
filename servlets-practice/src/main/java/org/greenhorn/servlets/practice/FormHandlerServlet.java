package org.greenhorn.servlets.practice;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FormHandlerServlet extends HttpServlet
{
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
  {
    String enteredValue;
    // gets all the selected options from the client browser
    String[] selectedOptions = request.getParameterValues("options");
    // gets the enteredValue fields value
    enteredValue = request.getParameter("enteredValue");
    response.setContentType("text/html");
    PrintWriter printWriter;
    try
    {
      // get a printwriter from the HttpServletResponse objects ref.
      printWriter = response.getWriter();
      // return on the HttpServletResponse objects ref. requested values
      printWriter.println("<p>");
      printWriter.print("You entered: ");
      printWriter.print(enteredValue);
      printWriter.print("</p>");
      printWriter.println("<p>");
      printWriter.print("The following options were selected:");
      printWriter.println("<br/>");

      if (selectedOptions != null)
      {
        for (String option : selectedOptions)
        {
          printWriter.print(option);
          printWriter.println("<br/>");
        }
      }
      else
      {
        printWriter.println("None");
      }
      printWriter.println("</p>");
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}