package ru.miet.orgact.controllers;

import javafx.fxml.FXML;

public class StartWindow {


    MainController mainController;

    @FXML
    public void buttonEditClick() {
        mainController.buttonEditClick();
    }

    @FXML
    public void buttonAddClick() {
        mainController.buttonAddClick();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

}
