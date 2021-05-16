package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class loginController {

    @FXML
    private TextField userIdField;

    @FXML
    private TextField passwordField;

    @FXML
    void onActionLogin(ActionEvent event) throws IOException {

        //FIXME will set to temporarily open next page
        mainMenuController.returnToMain(event);

    }

}
