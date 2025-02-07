
import java.sql.*;

public class DatabaseHandler {

    private static DatabaseHandler handler = null;

    private Statement stmt = null;
    // Removed the unused field pstatement
    // private PreparedStatement pstatement = null;

    // Singleton pattern to get the single instance of DatabaseHandler
    public static DatabaseHandler getInstance() {
        if (handler == null) {
            handler = new DatabaseHandler();
        }
        return handler;
    }

    // Get database connection
    public Connection getDBConnection() {
        Connection connection = null;
        String dburl = "jdbc:mysql://localhost:3306/userdb";
        String userName = "root";
        String password = "password";

        try {
            connection = DriverManager.getConnection(dburl, userName, password);
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getLocalizedMessage());
            e.printStackTrace();
        }

        return connection;
    }

    // Execute the query with statement
    public ResultSet execQuery(String query) {
        ResultSet result = null;
        try (Connection conn = getDBConnection()) {
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println("Exception at execQuery:dataHandler: " + ex.getLocalizedMessage());
            ex.printStackTrace();
        }
        return result;
    }

    // Validate the login credentials with parameterized query
    public boolean validateLogin(String username, String password) {
        System.out.println("Attempting to log in with username: " + username + " and password: " + password);

        String query = "SELECT * FROM useracc WHERE username = ? AND password = ?";

        try (Connection conn = getDBConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet result = pstmt.executeQuery();
            return result.next();  // Returns true if a record is found

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
