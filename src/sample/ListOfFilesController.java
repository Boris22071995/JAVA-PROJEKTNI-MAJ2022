package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.unibl.etf.pj2.projektni.exception.LoggingException;
import org.unibl.etf.pj2.projektni.files.FileRead;
import org.unibl.etf.pj2.projektni.files.FileVisit;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class ListOfFilesController implements Initializable {
    @FXML
    TableView<FileRead> tableView;
    @FXML
    private TableColumn<String,String> fileNameForTable;
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        String pattern = "*.txt";
        String path = System.getProperty("user.dir") + File.separator + "rezultati";
        File f = new File(path);
        if (!f.exists()) {
            return;
        }
        Path startingDir = Paths.get(path);
        FileVisit fv = new FileVisit(pattern);
        try {
            Files.walkFileTree(startingDir, fv);
        } catch (IOException e) {
            LoggingException.logger.log(Level.SEVERE, e.fillInStackTrace().toString());
        }
        List<FileRead> listaFajlova = fv.getFileReadList();
        final ObservableList<FileRead> filesModels = FXCollections.observableList(listaFajlova);
        fileNameForTable.setCellValueFactory(new PropertyValueFactory<>("nameOfFile"));
        tableView.setItems(filesModels);
        tableView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                if (tableView.getSelectionModel().getSelectedItem() == null) {
                    return;
                }
                String nameOfFile = tableView.getSelectionModel().getSelectedItem().getNameOfFile();
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("ContentOfFile.fxml"));
                    loader.setController(new ContentOfFileController(path + File.separator + nameOfFile));
                    Parent root = loader.load();
                    Stage primaryStage = new Stage();
                    primaryStage.setTitle( nameOfFile);
                    primaryStage.setScene(new Scene(root, 820, 454));
                    primaryStage.show();
                }catch (IOException e) {
                    LoggingException.logger.log(Level.SEVERE, e.fillInStackTrace().toString());
                }
            }
        });
    }
}
