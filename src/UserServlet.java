import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private BookDAO bookDAO = new BookDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        
        if (session == null || !"MEMBER".equals(session.getAttribute("role"))) {
            response.sendRedirect("login");
            return;
        }

        List<Book> availableBooks = bookDAO.findAvailable();
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Member Area</title></head><body>");
        out.println("<h1>Welcome, " + session.getAttribute("username") + "</h1>");
        
        out.println("<div id='notif' style='background:#e1f5fe; border:1px solid #01579b; padding:10px; margin:10px 0; display:none;'></div>");

        out.println("<h3>Available Books</h3><ul>");
        for (Book b : availableBooks) {
            out.println("<li>" + b.getTitle() + " by " + b.getAuthor() + 
                        " <a href='BorrowServlet?bookId=" + b.getId() + "'>Rent Now</a></li>");
        }
        out.println("</ul>");

        out.println("<script>");
        out.println("var ws = new WebSocket('ws://' + window.location.host + request.getContextPath() + '/notifications');");
        out.println("ws.onmessage = function(event) { ");
        out.println("   var div = document.getElementById('notif');");
        out.println("   div.innerHTML = '<b>Update:</b> ' + event.data; div.style.display = 'block';");
        out.println("};");
        out.println("</script></body></html>");
    }
}