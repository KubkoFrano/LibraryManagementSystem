import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html><html><head><title>Library Login</title></head><body>");
        out.println("<h2>Library System Login</h2>");
        
        out.println("<form action='login' method='post'>");
        out.println("Username: <input type='text' name='username' required><br><br>");
        out.println("Password: <input type='password' name='password' required><br><br>");
        out.println("<input type='submit' value='Login'>");
        out.println("</form></body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        try (Connection conn = DBConfig.getConnection()) {
            String sql = "SELECT id, username, role FROM users WHERE username = ? AND password = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user);
            pstmt.setString(2, pass);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("userId", rs.getLong("id"));
                session.setAttribute("username", rs.getString("username"));
                session.setAttribute("role", rs.getString("role"));

                Cookie userCookie = new Cookie("lastLoggedInUser", user);
                userCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(userCookie);

                if ("ADMIN".equals(rs.getString("role"))) {
                    response.sendRedirect("admin");
                } else {
                    response.sendRedirect("user");
                }
            } else {
                renderError(response, "Invalid username or password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            renderError(response, "Database connection error.");
        }
    }

    private void renderError(HttpServletResponse response, String msg) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h3 style='color:red'>" + msg + "</h3>");
        out.println("<a href='login'>Try Again</a>");
        out.println("</body></html>");
    }
}