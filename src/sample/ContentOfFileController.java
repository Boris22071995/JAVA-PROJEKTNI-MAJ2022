package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ContentOfFileController implements Initializable {
    String path;
    @FXML
    Label label;

    public ContentOfFileController(String path) {
        this.path = path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        label.setText("VOLIM SARUUUUUU " + path);
    }
}
