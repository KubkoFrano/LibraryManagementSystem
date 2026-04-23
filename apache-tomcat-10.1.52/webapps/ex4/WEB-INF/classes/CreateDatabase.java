import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreateDatabase {
    public static void main(String[] args) {
        // Database credentials
        String url = "jdbc:mysql://localhost:3306/testdb";
        String user = "root";
        String password = "admin"; // Ensure this matches your MySQL password

        try {
            // Load the driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // try-with-resources automatically closes connection and statement
            try (Connection conn = DriverManager.getConnection(url, user, password);
                 Statement stmt = conn.createStatement()) {
                
                System.out.println("Connected to database...");

                stmt.executeUpdate("DROP TABLE IF EXISTS enrollment");
                stmt.executeUpdate("DROP TABLE IF EXISTS student");

                // 2. Create STUDENT table (per task requirements)
                String createTableSQL = "CREATE TABLE student (" +
                                        "student_id INT PRIMARY KEY, " +
                                        "student_name VARCHAR(100))";
                stmt.executeUpdate(createTableSQL);
                System.out.println("Table 'student' created.");

                // 3. Insert at least 5 records
                String insertSQL = "INSERT INTO student (student_id, student_name) VALUES " +
                                   "(1, 'Leopold'), " +
                                   "(2, 'Bonifac'), " +
                                   "(3, 'Horacio'), " +
                                   "(4, 'Alice'), " +
                                   "(5, 'Bob')";
                
                int rowsAffected = stmt.executeUpdate(insertSQL);
                System.out.println(rowsAffected + " records inserted successfully.");

            }
        } catch (Exception e) {
            System.err.println("Error occurred!");
            e.printStackTrace();
        }
    }
}