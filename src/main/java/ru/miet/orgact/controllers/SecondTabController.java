package ru.miet.orgact.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import ru.miet.orgact.handlers.NumberListener;

public class SecondTabController {

    @FXML
    private GridPane grid;

    @FXML
    public void changeEnable(ActionEvent event) {

        int index = grid.getChildren().indexOf(event.getSource()) + 1;

        TextField text = (TextField) grid.getChildren().get(index);

        if (((CheckBox) event.getSource()).isSelected()) {
            text.setDisable(false);
            text.setText("0");
        } else {
            text.setDisable(true);
            text.setText("0");
        }

    }

    public void initTextField() {
        ObservableList<Node> list = grid.getChildren();

        for (int i = 1; i < list.size() - 1; i += 2) {
            TextField text = (TextField) list.get(i + 1);
            text.textProperty().addListener(new NumberListener(text));
        }
    }

    @FXML
    public void initialize() {
        initTextField();
    }

}
