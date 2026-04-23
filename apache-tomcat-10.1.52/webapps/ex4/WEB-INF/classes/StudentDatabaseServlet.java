import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/display-students")
public class StudentDatabaseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String url = "jdbc:mysql://localhost:3306/testdb";
        String user = "root";
        String password = "admin"; 

        out.println("<html><head><title>Student List</title></head><body>");
        out.println("<h2>Registered Students</h2>");
        
        out.println("<table border='1' cellpadding='10' cellspacing='0'>");
        out.println("<tr style='background-color: #eee;'><th>Student ID</th><th>Student Name</th></tr>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(url, user, password);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT student_id, student_name FROM student")) {

                boolean hasRecords = false;
                while (rs.next()) {
                    hasRecords = true;
                    out.println("<tr>");
                    out.println("<td>" + rs.getInt("student_id") + "</td>");
                    out.println("<td>" + rs.getString("student_name") + "</td>");
                    out.println("</tr>");
                }

                if (!hasRecords) {
                    out.println("<tr><td colspan='2'>No records found in database.</td></tr>");
                }
            }
        } catch (ClassNotFoundException e) {
            out.println("<p style='color:red;'>Driver Error: " + e.getMessage() + "</p>");
        } catch (SQLException e) {
            out.println("<p style='color:red;'>Database Error: " + e.getMessage() + "</p>");
        }

        out.println("</table>");
        out.println("</body></html>");
    }
}