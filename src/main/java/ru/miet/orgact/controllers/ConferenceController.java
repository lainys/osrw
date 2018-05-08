package ru.miet.orgact.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ru.miet.orgact.handlers.NegativeNumberListener;
import ru.miet.orgact.handlers.StringListener;

public class ConferenceController {
    @FXML
    private TextField conferencePages;

    @FXML
    private TextField conferenceCity;

    @FXML
    private TextField conferenceCountry;


    public void initialize() {
        conferenceCity.textProperty().addListener(new StringListener(conferenceCity));

        conferenceCountry.textProperty().addListener(new StringListener(conferenceCountry));

        conferencePages.textProperty().addListener(new NegativeNumberListener(conferencePages));
    }
}
