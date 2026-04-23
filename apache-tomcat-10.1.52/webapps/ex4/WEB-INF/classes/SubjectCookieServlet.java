import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;
import java.io.PrintWriter;

import java.io.*;
import java.nio.file.*;

@WebServlet("/subject-cookie")
public class SubjectCookieServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String savedSubject = "none";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("subject")) {
                    savedSubject = c.getValue();
                    break;
                }
            }
        }

        String filePath = getServletContext().getRealPath("/subject.html");
        String htmlContent = Files.readString(Path.of(filePath));

        htmlContent = htmlContent.replace("{{SAVED_SUBJECT}}", savedSubject);

        response.setContentType("text/html");
        response.getWriter().print(htmlContent);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String selectedSubject = request.getParameter("subject");

        Cookie subjectCookie = new Cookie("subject", selectedSubject);
        
        subjectCookie.setPath(request.getContextPath());
        subjectCookie.setMaxAge(60 * 60 * 24);
        
        response.addCookie(subjectCookie);

        response.sendRedirect("subject-cookie");
    }
}