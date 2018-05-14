package ru.miet.orgact.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.BorderPane;
import ru.miet.orgact.Conference;
import ru.miet.orgact.Journal;

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
            Parent root;
            switch (name) {
                case "conference": {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/findConference.fxml"));
                    root = loader.load();
                    FindConferenceController controller = loader.getController();
                    controller.setThirdController(this);
                    break;
                }
                case "journal": {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/findJournal.fxml"));
                    root = loader.load();
                    FindJournalController controller = loader.getController();
                    controller.setThirdController(this);
                    break;
                }

                default: {
                    root = FXMLLoader.load(getClass().getResource("/layouts/place/" + name + ".fxml"));
                }
            }
            return root;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void selectPlace(String place) {
        try {
            Node source = FXMLLoader.load(getClass().getResource("/layouts/place/" + place + ".fxml"));

            placePanel.setCenter(source);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectConference(Conference conf) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/place/conference.fxml"));
            Parent source = loader.load();
            ConferenceController controller = loader.getController();
            controller.setConference(conf);
            placePanel.setCenter(source);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectJournal(Journal journal) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/place/journal.fxml"));
            Parent source = loader.load();
            JournalController controller = loader.getController();
            controller.setJournal(journal);
            placePanel.setCenter(source);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


