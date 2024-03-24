package ru.mountcode.desktop.mountpacker.application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {

    private static Application instance;
    private Stage stage;

    public Application() {
        instance = this;
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/assets/application.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 260);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setTitle("MountPacker v1.0.0");
        stage.setScene(scene);
        stage.show();

    }

    public static Application getApplication() {
        return instance;
    }

    public Stage getStage() {
        return stage;
    }

    public void alert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.CANCEL);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.show();
    }
}
