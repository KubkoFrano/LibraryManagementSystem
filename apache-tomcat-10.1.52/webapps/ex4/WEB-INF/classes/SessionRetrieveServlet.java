import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/session-retrieve")
public class SessionRetrieveServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);

        out.println("<html><body>");

        if (session != null && session.getAttribute("user_name") != null) {
            String name = (String) session.getAttribute("user_name");
            String sessionId = session.getId();
            out.println("<p>session id: " + sessionId + "</p>");
            out.println("<p>username: " + name + "</p>");
        } else {
            out.println("<p>no session</p>");
        }
        
        out.println("<br><br><a href='session-store'>return</a>");
        out.println("</body></html>");
    }
}