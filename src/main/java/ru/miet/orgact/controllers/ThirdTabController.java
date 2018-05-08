package ru.miet.orgact.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.BorderPane;

import java.io.IOException;


public class ThirdTabController {

    @FXML
    private BorderPane placePanel;


    public void initialize() {

    }

    @FXML
    public void selectPlace(ActionEvent event) {
        Node source = getLayout(((RadioButton) event.getSource()).getId());

        placePanel.setCenter(source);
    }

    public Node getLayout(String name) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/layouts/place/" + name + ".fxml"));
            return root;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @FXML
    public void nextTab(ActionEvent event) {
        checkField(event);
        send();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Успешно отправлено!");
        alert.setTitle("Успех!");
        alert.showAndWait();
    }


    public void send() {

    }

    public void checkField(ActionEvent event) {

    }

}
