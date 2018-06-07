package ru.miet.orgact.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ru.miet.orgact.Client;
import ru.miet.orgact.handlers.IPListener;
import ru.miet.orgact.handlers.NumberListener;

public class ClientSetting {


    MainController main;
    @FXML
    TextField port;

    @FXML
    TextField ip;

    public void initialize() {
        ip.textProperty().addListener(new IPListener(ip));
        port.textProperty().addListener(new NumberListener(port));
        setValue();
    }

    public void setValue() {
        ip.setText(Client.getIP());
        port.setText(Client.getPort());
    }

    @FXML
    public void change() {
        Client.setSet(ip.getText(), port.getText());
        main.change();
    }

    @FXML
    public void reset() {
        Client.reset();
        main.showMessage("Установлены сетевые настройки по умолчанию");
        main.change();
    }

    public void setMain(MainController main) {
        this.main = main;
    }
}
