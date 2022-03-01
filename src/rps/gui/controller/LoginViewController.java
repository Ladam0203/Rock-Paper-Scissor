package rps.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import rps.gui.JavaFXApp;

import java.io.IOException;
import java.util.spi.ToolProvider;

public class LoginViewController {
    @FXML
    private TextField txfName;

    JavaFXApp main;
    public void setMain(JavaFXApp javaFXApp) {
        this.main = javaFXApp;
    }

    public void handleLogin() throws IOException
    {
        openGameView(txfName.getText());
    }

    private void openGameView(String name) throws IOException {
        main.openGameView(name);
    }
}
