package ru.miet.orgact.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import ru.miet.orgact.Client;
import ru.miet.orgact.Conference;

import java.util.ArrayList;

public class FindConferenceController {

    @FXML
    TextField finderName;

    @FXML
    ListView listConference;

    @FXML
    public void findConference() {
        String name = finderName.getText();

        ObservableList<Conference> list = FXCollections.observableArrayList();


        ArrayList<Conference> con = Client.getConferences();
        for (Conference c : con) {
            if (c.getName().toLowerCase().contains(name)) {
                list.add(c);
            }
        }

        listConference.setItems(list);

    }

    @FXML
    public void notFindConference() {
        //сделать чтобы открылось пустая форма с полями конференции

    }

    @FXML
    public void selectConference() {
        Conference conf = (Conference) listConference.getSelectionModel().getSelectedItem();
        //сделать чтобы открылось заполненная форма с полями конференции
    }
}
