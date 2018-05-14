package ru.miet.orgact.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class FindConferenceController {

    @FXML
    TextField finderName;

    @FXML
    TableView<String> tableResult;

    @FXML
    TableColumn<String, String> col;


    @FXML
    public void findConference() {
        ObservableList<String> list = FXCollections.observableArrayList();


        list.addAll("a", "b", "c", "d", "e", "f");

        tableResult.setItems(list);

    }
}
