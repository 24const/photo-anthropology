package com.nsu.photo_anthropology.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class ErrorServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html");
            PrintWriter writer = response.getWriter();

            String title = "Error Handling";
            String docType = "<!DOCTYPE html>";

            writer.println(docType + "<html>" +
                    "<head>" +
                    "<title>" + title + "</title>" +
                    "</head>" +
                    "<body>");

            writer.println("<h1>Error information</h1>");
            writer.println("Code: " + 500);
            writer.println("Exception Type : Server Error </br></br>");
            writer.println("</body>");
            writer.println("</html>");
        } catch (Exception e) {
            Logger logger = Logger.getLogger(DeleteGroupServlet.class.getName());
            logger.info(e.getMessage());
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            doGet(request, response);
        } catch (NumberFormatException e) {
            Logger logger = Logger.getLogger(ChangeGroupServlet.class.getName());
            logger.info(e.getMessage());

        }
    }
}
