package ru.miet.orgact.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import ru.miet.orgact.Conference;
import ru.miet.orgact.handlers.NegativeNumberListener;
import ru.miet.orgact.handlers.StringListener;

import java.time.LocalDate;

public class ConferenceController {

    ThirdTabController thirdTabController;

    @FXML
    private TextField conferenceName;

    @FXML
    private DatePicker conferenceStart;

    @FXML
    private DatePicker conferenceFinish;

    @FXML
    private TextField conferencePages;

    @FXML
    private TextField conferenceCity;

    @FXML
    private TextField conferenceCountry;

    @FXML
    private TextField conferenceLink;


    public void initialize() {
        conferenceCity.textProperty().addListener(new StringListener(conferenceCity));

        conferenceCountry.textProperty().addListener(new StringListener(conferenceCountry));

        conferencePages.textProperty().addListener(new NegativeNumberListener(conferencePages));
    }

    public void setConference(Conference conf) {
        conferenceName.clear();
        conferenceName.setText(conf.getName());
        conferenceCountry.clear();
        conferenceCountry.setText(conf.getCountry());
        conferenceCity.clear();
        conferenceCity.setText(conf.getCity());
        conferencePages.clear();
        conferencePages.setText("0");
        conferenceLink.clear();
        conferenceLink.setText("");
        System.out.println(conf.getStart());
        String[] date = conf.getStart().split(". ");
        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[2]);
        conferenceStart.setValue(LocalDate.of(year, month, day));
        date = conf.getFinish().split(". ");
        year = Integer.parseInt(date[0]);
        month = Integer.parseInt(date[1]);
        day = Integer.parseInt(date[2]);
        conferenceFinish.setValue(LocalDate.of(year, month, day));

    }

    public void toSearch() {
        thirdTabController.toSearch("conference");
    }

    public void setController(ThirdTabController contr) {
        thirdTabController = contr;
    }

}
