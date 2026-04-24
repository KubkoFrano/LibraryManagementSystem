import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private BookDAO bookDAO = new BookDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"ADMIN".equals(session.getAttribute("role"))) {
            response.sendRedirect("login.html");
            return;
        }

        List<Book> allBooks = bookDAO.findAll();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body><h1>Admin Control Panel</h1>");
        
        out.println("<h3>Add New Book</h3>");
        out.println("<form method='POST' action='AdminServlet'>");
        out.println("Title: <input name='title' required><br>");
        out.println("Author: <input name='author' required><br>");
        out.println("<input type='submit' value='Add to Library'>");
        out.println("</form>");

        out.println("<h3>Library Inventory</h3><table border='1'><tr><th>Title</th><th>Author</th><th>Status</th></tr>");
        for (Book b : allBooks) {
            out.println("<tr><td>" + b.getTitle() + "</td><td>" + b.getAuthor() + "</td><td>" + 
                        (b.isAvailable() ? "Available" : "Rented") + "</td></tr>");
        }
        out.println("</table></body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String title = request.getParameter("title");
        String author = request.getParameter("author");

        Book newBook = new Book();
        newBook.setTitle(title);
        newBook.setAuthor(author);
        newBook.setAvailable(true);

        bookDAO.save(newBook);

        //NotificationServer.broadcast("A new book titled '" + title + "' was just added to the collection!");

        response.sendRedirect("AdminServlet");
    }
}