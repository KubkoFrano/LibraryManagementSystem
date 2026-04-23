import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/request-info")
public class RequestInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
        response.setContentType("text/html");
        java.io.PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<p>client IP: " + request.getRemoteAddr() + "</p>");
        out.println("<p>browser information: " + request.getHeader("User-Agent") + "</p>");
        out.println("<p>request method: " + request.getMethod() + "</p>");
        out.println("<p>server name: " + request.getServerName() + "</p>");
        out.println("<p>server port: " + request.getServerPort() + "</p>");
        out.println("</body></html>");
    }
}