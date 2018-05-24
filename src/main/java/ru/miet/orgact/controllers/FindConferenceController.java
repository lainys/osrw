package ru.miet.orgact.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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

    ThirdTabController thirdTab;
    MainController main;

    @FXML
    public void findConference() {
        try {
            String name = finderName.getText();

            ObservableList<Conference> list = FXCollections.observableArrayList();


            ArrayList<Conference> con = Client.getConferences();
            for (Conference c : con) {
                if (c.getName().toLowerCase().contains(name)) {
                    list.add(c);
                }
            }

            listConference.setItems(list);
        } catch (Exception e) {
            showMessage("Нет доступа к серверу! Попробуйте позже.");
        }

    }


    public void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.setTitle("Сообщение");
        alert.showAndWait();
    }

    @FXML
    public void notFindConference() {
        thirdTab.notFound("conference");

    }

    @FXML
    public void selectConference() {
        Conference conf = (Conference) listConference.getSelectionModel().getSelectedItem();
        thirdTab.selectConference(conf);
    }

    public void setThirdController(ThirdTabController contrl) {
        thirdTab = contrl;
    }
}
