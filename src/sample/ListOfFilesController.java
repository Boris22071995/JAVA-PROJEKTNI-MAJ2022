package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.unibl.etf.pj2.projektni.files.FileRead;
import org.unibl.etf.pj2.projektni.files.FileVisit;

import javax.swing.plaf.basic.BasicDesktopIconUI;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
            e.printStackTrace();
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
//                    Runtime runtime = Runtime.getRuntime();
//                    Process process = runtime.exec(path + File.separator + nameOfFile);
                    Desktop desktop =  Desktop.getDesktop();
                    desktop.open(new File(path + File.separator + nameOfFile));
                }catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });

    }
}
