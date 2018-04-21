package ru.miet.orgact.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.GridPane;

public class SecondTabController {

    @FXML
    private GridPane grid;

    @FXML
    public void changeEnable(ActionEvent event) {

        int index = grid.getChildren().indexOf(event.getSource()) + 1;

        Spinner spin = (Spinner) grid.getChildren().get(index);

        if (((CheckBox) event.getSource()).isSelected()) {
            spin.setDisable(false);
            spin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000));
        } else {
            spin.setDisable(true);
            spin.decrement((Integer) spin.getValue());
        }


    }


    public void initialize() {

    }
}
