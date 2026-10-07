import java.sql.*;
import java.util.Scanner;

public class JdbcCRUD {
    // Database connection details
    static final String URL = "jdbc:oracle:thin:@localhost:1521:xe"; // Update with your DB details
    static final String USER = "system";  // Replace with your Oracle DB username
    static final String PASSWORD = "Pr@veen2005";  // Replace with your Oracle DB password

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Load the Oracle JDBC Driver
            Class.forName("oracle.jdbc.OracleDriver");

            // Establish Connection
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to Oracle Database!");

            while (true) {
                System.out.println("\n=== CRUD Operations ===");
                System.out.println("1. Insert Record");
                System.out.println("2. Read Records");
                System.out.println("3. Update Record");
                System.out.println("4. Delete Record");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                switch (choice) {
                    case 1:
                        insertRecord(con, scanner);
                        break;
                    case 2:
                        readRecords(con);
                        break;
                    case 3:
                        updateRecord(con, scanner);
                        break;
                    case 4:
                        deleteRecord(con, scanner);
                        break;
                    case 5:
                        System.out.println("Exiting program...");
                        con.close();
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // INSERT operation
    private static void insertRecord(Connection con, Scanner scanner) throws SQLException {
        System.out.print("Enter ID: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        String sql = "INSERT INTO employees (id, name) VALUES (?, ?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.setString(2, name);

        int rowsInserted = stmt.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Record inserted successfully!");
        }
    }

    // READ operation
    private static void readRecords(Connection con) throws SQLException {
        String sql = "SELECT * FROM employees";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        System.out.println("\n=== Employee Records ===");
        while (rs.next()) {
            System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name"));
        }
    }

    // UPDATE operation
    private static void updateRecord(Connection con, Scanner scanner) throws SQLException {
        System.out.print("Enter ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter new Name: ");
        String newName = scanner.nextLine();

        String sql = "UPDATE employees SET name = ? WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, newName);
        stmt.setInt(2, id);

        int rowsUpdated = stmt.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Record updated successfully!");
        } else {
            System.out.println("ID not found!");
        }
    }

    // DELETE operation
    private static void deleteRecord(Connection con, Scanner scanner) throws SQLException {
        System.out.print("Enter ID to delete: ");
        int id = scanner.nextInt();

        String sql = "DELETE FROM employees WHERE id = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, id);

        int rowsDeleted = stmt.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("Record deleted successfully!");
        } else {
            System.out.println("ID not found!");
        }
    }
}

