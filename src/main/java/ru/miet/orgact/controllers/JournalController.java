package ru.miet.orgact.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import ru.miet.orgact.Journal;
import ru.miet.orgact.handlers.DoubleNumberListener;
import ru.miet.orgact.handlers.NegativeNumberListener;
import ru.miet.orgact.handlers.NumberListener;

public class JournalController {


    ThirdTabController thirdTabController;

    @FXML
    private TextField journalName;
    @FXML
    private TextField journalFactor;

    @FXML
    private TextField journalPages;

    @FXML
    private TextField journalISSN;

    @FXML
    private TextField journalLink;
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
        journalName.clear();
        journalName.setText(journal.getName());
        journalFactor.clear();
        journalFactor.setText(Double.toString(journal.getImpact_factor()));
        journalPages.clear();
        journalPages.setText("0");
        journalLink.clear();
        journalLink.setText(journal.getLink());
        journalISSN.clear();
        journalISSN.setText(journal.getIssn());

        journalVak.setSelected(journal.isVak());
        journalRussian.setSelected(journal.isRussian());
    }

    public void toSearch() {
        thirdTabController.toSearch("journal");
    }

    public void setController(ThirdTabController contr) {
        thirdTabController = contr;
    }
}
