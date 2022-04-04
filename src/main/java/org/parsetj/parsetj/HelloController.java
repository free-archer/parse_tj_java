package org.parsetj.parsetj;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
        System.out.println("Welcome");
    }

    @FXML
    void initialize() {
        System.out.println("initialize");

    }
}