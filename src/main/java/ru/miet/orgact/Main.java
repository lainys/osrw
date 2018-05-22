package ru.miet.orgact;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/layouts/main.fxml"));
        primaryStage.setTitle("Организация отчетности");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(false);
        primaryStage.setWidth(700);
        primaryStage.setHeight(700);
        primaryStage.setResizable(false);
        primaryStage.show();

    }
}
