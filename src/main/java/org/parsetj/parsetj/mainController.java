package org.parsetj.parsetj;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class mainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button chooseLogFile;

    @FXML
    private Button chooseSQLightFile;

    @FXML
    private TextField logFile;

    @FXML
    private TextField sqllightFile;

    @FXML
    private Button doParse;

    @FXML
    protected void initialize() {
        System.out.println("initialize");

    }

    @FXML
    protected void chooseLogFile(ActionEvent event) {
        System.out.println("ActionEvent");
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        File file = chooser.showOpenDialog(new Stage());

        System.out.println(file.getPath());

        logFile.setText(file.getPath());
    }

    @FXML
    protected void chooseSQLightFile(ActionEvent event) {
        System.out.println("ActionEvent");
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        File file = chooser.showSaveDialog(new Stage());

        System.out.println(file.getPath());

        sqllightFile.setText(file.getPath());
    }


    @FXML
    void doParse(ActionEvent event) {
        Parse parse = new Parse(logFile.getText(), sqllightFile.getText());

        parse.doParse();
    }



}
