/**
 * @author kjell
 */

package rps.gui.controller;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayerNameController implements Initializable {

    public TextField nameField;
    public Button submitBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void handleSubmit(ActionEvent actionEvent) {
    }
}
