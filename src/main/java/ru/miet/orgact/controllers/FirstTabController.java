package ru.miet.orgact.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ru.miet.orgact.handlers.AddAuthorHandler;
import ru.miet.orgact.handlers.StringListener;
import ru.miet.orgact.handlers.YearFieldListener;

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
    private Button addAuthor;

    @FXML
    private GridPane grid;

    @FXML
    private ComboBox topic;

    @FXML
    private ComboBox position;

    @FXML
    private ComboBox directions;


    @FXML
    public void initialize() {

        yearField.textProperty().addListener(new YearFieldListener(yearField));
        countryField.textProperty().addListener(new StringListener(countryField));
        cityField.textProperty().addListener(new StringListener(cityField));

        addAuthor.setOnAction(new AddAuthorHandler(authorsFields, grid));


        position.getItems().addAll("Студент МИЭТ", "Аспирант МИЭТ", "Сотрудник МИЭТ", "Другое");
        topic.getItems().addAll("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve");

    }

}
