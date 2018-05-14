package ru.miet.orgact.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import ru.miet.orgact.Journal;
import ru.miet.orgact.handlers.DoubleNumberListener;
import ru.miet.orgact.handlers.NegativeNumberListener;
import ru.miet.orgact.handlers.NumberListener;

public class JournalController {

    @FXML
    private TextField journalName;
    @FXML
    private TextField journalFactor;

    @FXML
    private TextField journalPages;

    @FXML
    private TextField journalISSN;

    @FXML
    private CheckBox journalVak;

    @FXML
    private CheckBox journalRussian;


    public void initialize() {
        journalFactor.textProperty().addListener(new DoubleNumberListener(journalFactor));

        journalPages.textProperty().addListener(new NumberListener(journalPages));

        journalISSN.textProperty().addListener(new NegativeNumberListener(journalISSN));
    }


    public void setJournal(Journal journal) {
        journalName.setText(journal.getName());
        journalFactor.setText(Double.toString(journal.getImpact_factor()));
        journalPages.setText("0");
        journalISSN.setText(journal.getIssn());
        journalVak.setSelected(journal.isVak());
        journalRussian.setSelected(journal.isRussian());
    }
}
