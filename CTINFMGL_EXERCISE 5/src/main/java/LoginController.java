
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());

    @FXML
    private TextField usernametextfield;

    @FXML
    private PasswordField passwordtextfield;

    @FXML
    public void loginbuttonHandler(ActionEvent event) throws IOException {
        String uname = usernametextfield.getText().trim();
        String pword = passwordtextfield.getText().trim();

        if (uname.isEmpty() || pword.isEmpty()) {
            showAlert("Login Failed", "Username and password cannot be empty.", Alert.AlertType.ERROR);
            return;
        }

        try {
            boolean isValid = DatabaseHandler.validateLogin(uname, pword);

            if (isValid) {
                showAlert("Login Successful", "Welcome, " + uname + "!", Alert.AlertType.INFORMATION);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                showAlert("Login Failed", "Invalid username or password.", Alert.AlertType.ERROR);
            }
        } catch (IOException e) { // Removed SQLException
            LOGGER.log(Level.SEVERE, "Login process error", e);
            showAlert("Error", "An error occurred: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
