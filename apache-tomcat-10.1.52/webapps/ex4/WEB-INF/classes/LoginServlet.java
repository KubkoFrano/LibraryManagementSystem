import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if ("admin".equals(user) && "admin123".equals(pass)) {
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);

            out.println("<html><body>");
            out.println("session id: " + session.getId() + "<br>");
            out.println("username:" + user);
            out.println("</body></html>");
        } else {
            out.println("<html><body>");
            out.println("login failed");
            out.println("<br><a href='login.html'>try again</a>");
            out.println("</body></html>");
        }
    }
}