package ru.miet.orgact.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import ru.miet.orgact.Journal;
import ru.miet.orgact.handlers.DoubleNumberListener;
import ru.miet.orgact.handlers.NegativeNumberListener;

public class JournalController {


    private ThirdTabController thirdTabController;
    private MainController main;

    @FXML
    private TextField journalName;
    @FXML
    private TextField journalFactor;

    @FXML
    private TextField journalPages;

    @FXML
    private TextField journalISSN;

    @FXML
    private TextField journalNumber;

    @FXML
    private TextField journalLink;
    @FXML
    private CheckBox journalVak;

    @FXML
    private CheckBox journalRussian;

    @FXML
    private CheckBox journalRecenz;

    @FXML
    private CheckBox journalISI;

    @FXML
    private CheckBox journalScopus;

    @FXML
    private CheckBox journalRinc;


    public void initialize() {
        journalFactor.textProperty().addListener(new DoubleNumberListener(journalFactor));

        journalPages.textProperty().addListener(new NegativeNumberListener(journalPages));

        journalISSN.textProperty().addListener(new NegativeNumberListener(journalISSN));
    }


    public void setJournal(Journal journal) {
        main.setJournalOther();
        journalName.clear();
        journalName.setText(journal.getName());
        journalFactor.clear();
        journalFactor.setText(Double.toString(journal.getImpact_factor()));

        journalLink.clear();
        journalLink.setText(journal.getLink());
        journalISSN.clear();
        journalISSN.setText(journal.getIssn());

        journalVak.setSelected(journal.isVak());
        journalRussian.setSelected(journal.isRussian());
        journalRecenz.setSelected(journal.isRecenz());
        journalRinc.setSelected(journal.isRinc());
        journalScopus.setSelected(journal.isScopus());
        journalISI.setSelected(journal.isIsi());
    }

    public void toSearch() {
        thirdTabController.toSearch("journal");
    }

    public void setController(ThirdTabController contr) {
        thirdTabController = contr;
    }

    public void clear() {
        journalNumber.clear();
        journalPages.clear();
        journalLink.clear();
        journalRecenz.setSelected(false);
        journalName.clear();
        journalISSN.clear();
        journalFactor.clear();
        journalISI.setSelected(false);
        journalRinc.setSelected(false);
        journalRussian.setSelected(false);
        journalScopus.setSelected(false);
        journalVak.setSelected(false);
    }

    public MainController getMain() {
        return main;
    }

    public void setMain(MainController main) {
        this.main = main;
    }
}
