package ru.miet.orgact.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.BorderPane;
import ru.miet.orgact.Conference;
import ru.miet.orgact.Journal;

import java.io.IOException;


public class ThirdTabController {


    ConferenceController conferenceController;
    JournalController journalController;

    boolean findC, findJ;
    private int code;
    @FXML
    private BorderPane placePanel;

    @FXML
    private Parent book;

    @FXML
    private Parent journal;

    @FXML
    private Parent conference;

    @FXML
    private Parent other;


    @FXML
    private Parent findConference;


    @FXML
    private Parent findJournal;

    public void initialize() {
        MainController.ttc = this;
        findC = findJ = false;
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/findConference.fxml"));
            findConference = loader.load();
            FindConferenceController controllerC = loader.getController();
            controllerC.setThirdController(this);
            //findConference.setVisible(false);

            loader = new FXMLLoader(getClass().getResource("/layouts/findJournal.fxml"));
            findJournal = loader.load();
            FindJournalController controllerJ = loader.getController();
            controllerJ.setThirdController(this);
            //findJournal.setVisible(false);

            loader = new FXMLLoader(getClass().getResource("/layouts/place/conference.fxml"));
            conference = loader.load();
            conferenceController = loader.getController();
            conferenceController.setController(this);
            //conference.setVisible(false);

            loader = new FXMLLoader(getClass().getResource("/layouts/place/journal.fxml"));
            journal = loader.load();
            journalController = loader.getController();
            journalController.setController(this);
            //journal.setVisible(false);

            other = FXMLLoader.load(getClass().getResource("/layouts/place/other.fxml"));
            //other.setVisible(false);

            book = FXMLLoader.load(getClass().getResource("/layouts/place/book.fxml"));
            //book.setVisible(false);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void selectPlace(ActionEvent event) {
        getLayout(((RadioButton) event.getSource()).getId());

    }

    public void getLayout(String name) {
        switch (name) {
            case "conference": {
                if (findC) {
                    placePanel.setCenter(conference);
                } else {
                    placePanel.setCenter(findConference);
                }
                break;
            }
            case "journal": {

                if (findJ) {
                    placePanel.setCenter(journal);
                } else {
                    placePanel.setCenter(findJournal);
                }

                break;
            }

            case "book": {
                placePanel.setCenter(book);
                break;
            }
            case "other": {
                placePanel.setCenter(other);
                break;
            }
        }

    }

    public void selectPlace(String place) {
        getLayout(place);
        /*try {
            Node source = FXMLLoader.load(getClass().getResource("/layouts/place/" + place + ".fxml"));

            placePanel.setCenter(source);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void selectConference(Conference conf) {
        code = conf.getCode();
        conferenceController.setConference(conf);
        findC = true;
        getLayout("conference");

        /*try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/place/conference.fxml"));
            Parent source = loader.load();
            ConferenceController controller = loader.getController();
            controller.setConference(conf);
            placePanel.setCenter(source);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public void selectJournal(Journal journal) {
        code = journal.getCode();
        journalController.setJournal(journal);
        findJ = true;
        getLayout("journal");
        /*
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/place/journal.fxml"));
            Parent source = loader.load();
            JournalController controller = loader.getController();
            controller.setJournal(journal);
            placePanel.setCenter(source);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public void toSearch(String place) {
        switch (place) {
            case "conference": {
                code = -1;
                findC = false;
                break;
            }
            case "journal": {
                code = -1;
                findJ = false;
                break;
            }
        }

        selectPlace(place);
    }

    public void clear() {
        placePanel.setCenter(null);

        code = -1;
    }

    public void notFound(String place) {
        code = -1;
        if (place.equals("conference")) {
            conferenceController.clear();
        } else if (place.endsWith("journal")) {
            journalController.clear();
        }
        toNotSearch(place);
    }

    public void toNotSearch(String place) {
        switch (place) {
            case "conference": {
                findC = true;
                break;
            }
            case "journal": {
                findJ = true;
                break;
            }
        }

        selectPlace(place);
    }

    public Parent getPlace(String place) {
        switch (place) {
            case "book": {
                return book;
            }
            case "journal": {
                return journal;
            }
            case "conference": {
                return conference;
            }
            case "other": {
                return other;
            }
            case "findConference": {
                return findConference;
            }

            case "findJournal": {
                return findJournal;
            }
            default: {
                return null;
            }
        }
    }

    public void setMain() {
        conferenceController.setMain(MainController.main);
        journalController.setMain(MainController.main);
    }

}


