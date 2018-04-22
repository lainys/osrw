package ru.miet.orgact.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


public class ThirdTabController {

    @FXML
    private AnchorPane placePanel;

    @FXML
    private SplitPane split;

    public void initialize() {
    }

    @FXML
    public void selectPlace(ActionEvent event) {
        Node source = getLayout(((RadioButton) event.getSource()).getId());
        placePanel.getChildren().clear();
        placePanel.getChildren().add(source);
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

}
