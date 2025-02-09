
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseHandler {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/userdb"; // Ensure this is correct
    private static final String DB_USER = "root"; // Your MySQL username
    private static final String DB_PASSWORD = "password"; // Your MySQL password
    private static final Logger LOGGER = Logger.getLogger(DatabaseHandler.class.getName());

    public static boolean validateLogin(String username, String password) {
        String sql = "SELECT * FROM users WHERE Username = ? AND Password = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Returns true if a match is found
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database connection error", e);
            return false;
        }
    }
}
