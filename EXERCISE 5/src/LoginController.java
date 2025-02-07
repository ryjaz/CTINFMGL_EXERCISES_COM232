
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    Label usernamelabel;

    @FXML
    Label passwordlabel;

    @FXML
    TextField usernametextfield;

    @FXML
    TextField passwordtextfield;

    @FXML
    Button loginbutton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void loginbuttonHandler(ActionEvent event) throws IOException {

        String uname = usernametextfield.getText();
        String pword = passwordtextfield.getText();

        // Create instance of DatabaseHandler to call the validateLogin method
        DatabaseHandler dbHandler = DatabaseHandler.getInstance();

        if (dbHandler.validateLogin(uname, pword)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
            System.out.println("Login successful");

            root = loader.load();

            // Load stage and scene
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } else {
            System.out.println("Login unsuccessful");
        }
    
}
