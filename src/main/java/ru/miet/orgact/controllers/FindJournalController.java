package ru.miet.orgact.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import ru.miet.orgact.Client;
import ru.miet.orgact.Journal;

import java.util.ArrayList;

public class FindJournalController {

    @FXML
    TextField finderName;

    @FXML
    ListView listJournals;

    ThirdTabController thirdTab;
    MainController main;

    @FXML
    public void findJournal() {
        try {
            String name = finderName.getText();

            ObservableList<Journal> list = FXCollections.observableArrayList();


            ArrayList<Journal> con = Client.getJournals();
            for (Journal c : con) {
                if (c.getName().toLowerCase().contains(name)) {
                    list.add(c);
                }
            }

            listJournals.setItems(list);
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
    public void notFindJournal() {

        thirdTab.notFound("journal");
    }

    @FXML
    public void selectJournal() {
        Journal journal = (Journal) listJournals.getSelectionModel().getSelectedItem();
        thirdTab.selectJournal(journal);
    }

    public void setThirdController(ThirdTabController contrl) {
        thirdTab = contrl;
    }

}
