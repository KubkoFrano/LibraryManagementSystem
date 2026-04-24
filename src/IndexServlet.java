import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("") 
public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("role") != null) {
            String role = (String) session.getAttribute("role");
            
            if ("ADMIN".equals(role)) {
                response.sendRedirect("admin");
            } else if ("MEMBER".equals(role)) {
                response.sendRedirect("user");
            }
        } else {
            response.sendRedirect("login");
        }
    }
}