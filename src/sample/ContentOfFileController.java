package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class ContentOfFileController implements Initializable {
    String path;
    @FXML
    TextArea textArea;
    String text = "";
    public ContentOfFileController(String path) {
        this.path = path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try (Stream<String> lines = Files.lines(Paths.get(path), Charset.defaultCharset())) {
            lines.forEachOrdered(line -> process(line));
        } catch (IOException e) {
            e.printStackTrace();
        }
        textArea.setText(text);
    }

    private void process(String line) {
        text+= line+"\n";
    }
}
