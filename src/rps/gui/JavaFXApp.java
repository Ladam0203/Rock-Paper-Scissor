package rps.gui;

// Java imports
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rps.gui.controller.GameViewController;
import rps.gui.controller.LoginViewController;

import java.io.IOException;

/**
 * JavaFX implementation of the RPS game
 *
 * @author smsj
 */
public class JavaFXApp extends Application {
    Stage stage;

    public static void launch() {
        Application.launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/rps/gui/view/RootView.fxml"));
        stage.setScene(new Scene(root));
        stage.show();

        openLoginView();
    }

    public void openLoginView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/rps/gui/view/LoginView.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();

        LoginViewController loginViewController = loader.getController();
        loginViewController.setMain(this);
    }

    public void openGameView(String name) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/rps/gui/view/GameView.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();

        GameViewController gameViewController = loader.getController();
        gameViewController.setPlayerName(name);
    }
}
