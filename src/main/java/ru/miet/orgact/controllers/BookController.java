package ru.miet.orgact.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ru.miet.orgact.handlers.NegativeNumberListener;
import ru.miet.orgact.handlers.NumberListener;

public class BookController {
    @FXML
    private TextField bookISBN;

    @FXML
    private TextField bookPages;

    @FXML
    public void initialize() {
        bookPages.textProperty().addListener(new NumberListener(bookPages));
        bookISBN.textProperty().addListener(new NegativeNumberListener(bookISBN));
    }
}
