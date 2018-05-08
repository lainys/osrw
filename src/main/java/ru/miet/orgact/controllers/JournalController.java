package ru.miet.orgact.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ru.miet.orgact.handlers.DoubleNumberListener;
import ru.miet.orgact.handlers.NegativeNumberListener;
import ru.miet.orgact.handlers.NumberListener;

public class JournalController {
    @FXML
    private TextField journalFactor;

    @FXML
    private TextField journalPages;


    @FXML
    private TextField journalISSN;


    public void initialize() {
        journalFactor.textProperty().addListener(new DoubleNumberListener(journalFactor));

        journalPages.textProperty().addListener(new NumberListener(journalPages));

        journalISSN.textProperty().addListener(new NegativeNumberListener(journalISSN));
    }

}
