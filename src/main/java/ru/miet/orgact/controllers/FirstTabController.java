package ru.miet.orgact.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ru.miet.orgact.handlers.AddAuthorHandler;

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


        addAuthor.setOnAction(new AddAuthorHandler(authorsFields, grid));


        position.getItems().addAll("Студент МИЭТ", "Аспирант МИЭТ", "Сотрудник МИЭТ", "Другое");
        topic.getItems().addAll("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve");

    }

    public void fillArticle() {


    }

    public void showMessage(String message) {
        System.out.println(message);
    }

}
