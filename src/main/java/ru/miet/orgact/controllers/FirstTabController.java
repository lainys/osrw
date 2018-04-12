package ru.miet.orgact.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class FirstTabController {

    @FXML
    private TextField nameField;

    @FXML
    private VBox authorsFields;

    @FXML
    private TextField yearField;

    @FXML
    private TextField countryField;

    @FXML
    private TextField cityField;

    @FXML
    private TextField publicHouseField;

    @FXML
    private TextField pagesField;

    @FXML
    private Button nextButton;

    @FXML
    public void initialize() {

    }

    @FXML
    public void button_add() {
        int len = authorsFields.getChildren().size();

        TextField text = new TextField();

        text.setId("text");

        authorsFields.getChildren().add(len - 2, text);

        for (int i = 0; i < len + 1; i++) {
            System.out.println(authorsFields.getChildren().get(i).getId());
        }
    }

    @FXML
    public void next_tab() {
        String name = nameField.getText();

        if (!checkNameOfPublication(name)) {
            sendError("Not correct Name");
            return;
        }

        int len = authorsFields.getChildren().size() - 1;
        String[] authors = new String[len];

        for (int i = 0; i < len; i++) {
            authors[i] = ((TextField) authorsFields.getChildren().get(i)).getText();
        }

        if (!checkAuthors(authors)) {
            sendError("Not correct Authors");
            return;
        }

        String year = yearField.getText();

        if (!checkYear(year)) {
            sendError("Not correct Year");
            return;
        }

        String country = countryField.getText();

        if (!checkCountry(country)) {
            sendError("Not correct Country");
            return;
        }

        String city = cityField.getText();

        if (!checkCity(city)) {
            sendError("Not correct City");
            return;
        }

        String publicHouse = publicHouseField.getText();

        if (!checkPublicHouse(publicHouse)) {
            sendError("Not correct Public House");
            return;
        }

        String pages = pagesField.getText();

        if (!checkPages(pages)) {
            sendError("Not correct Pages");
            return;
        }

        sendToServer(name, authors, Integer.parseInt(year), country, city, publicHouse, Integer.parseInt(pages));

    }

    public void sendToServer(String name, String[] authors, int year, String country, String city, String publicHouse, int pages) {
        System.out.println("Send to ... data:" +
                "\n\tname:" + name +
                ",\n\tcount authors:" + authors.length +
                ",\n\tyear:" + Integer.toString(year) +
                ",\n\tcountry:" + country +
                ",\n\tcity:" + city +
                ",\n\tpages:" + Integer.toString(pages));
    }

    public boolean checkNameOfPublication(String name) {
        return true;
    }

    public boolean checkAuthors(String... authors) {
        return true;
    }

    public boolean checkYear(String year) {
        return true;
    }

    public boolean checkCountry(String country) {
        return true;
    }

    public boolean checkCity(String city) {
        return true;
    }

    public boolean checkPublicHouse(String publicHouse) {
        return true;
    }

    public boolean checkPages(String pages) {
        return true;
    }

    public void sendError(String message) {
        System.out.println(message);
    }

}
