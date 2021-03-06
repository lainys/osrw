package ru.miet.orgact.handlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.controlsfx.control.textfield.TextFields;

import java.util.ArrayList;

public class AddAuthorHandler implements EventHandler<ActionEvent> {

    final double spacingAuthorBox = 20;

    Insets paddingAuthorBox;
    Font buttonFont;
    public static ArrayList<String> list = new ArrayList<>();

    private VBox authorsFields;
    private GridPane grid;

    public AddAuthorHandler(VBox parent, GridPane grid) {
        this.authorsFields = parent;
        this.grid = grid;

        buttonFont = new Font("Times new roman", 14);
        paddingAuthorBox = new Insets(0, 10, 0, 10);
    }

    @Override
    public void handle(ActionEvent event) {
        button_add();

    }

    public void button_add() {
        int len = authorsFields.getChildren().size();
        if (len <= 21) {
            authorsFields.getChildren().add(len, getNextAuthorBox());
            resize_grid(45);
        }
    }


    public void resize_grid(int k) {
        for (int i = 0; i < grid.getRowConstraints().size(); i++) {
            if (i == 2) {
                grid.getRowConstraints().get(i).setPrefHeight(grid.getRowConstraints().get(i).getPrefHeight() + k);
            }
        }
        grid.setPrefHeight(grid.getPrefHeight() + k);
        grid.setMaxHeight(grid.getPrefHeight());
        grid.setMinHeight(grid.getPrefHeight());
    }

    public HBox getNextAuthorBox() {
        HBox nextAuthor = new HBox();
        nextAuthor.setSpacing(spacingAuthorBox);
        nextAuthor.setPadding(paddingAuthorBox);
        nextAuthor.setPrefSize(200, 100);

        TextField textAuthor = new TextField();
        textAuthor.setPrefSize(140, 25);
        textAuthor.setPromptText("Введите ФИО автора");
        TextFields.bindAutoCompletion(textAuthor, list);


        ComboBox positionBox = new ComboBox();
        positionBox.getItems().addAll("Студент МИЭТ", "Аспирант МИЭТ", "Сотрудник МИЭТ", "Другое");
        positionBox.setPromptText("Выберите должность");
        positionBox.setPrefSize(170, 25);

        Button addAuthor = new Button("-");
        addAuthor.setPrefSize(80, 25);
        addAuthor.setFont(buttonFont);
        addAuthor.setOnAction(new DeleteAuthorHandler(authorsFields, grid));

        nextAuthor.getChildren().addAll(textAuthor, positionBox, addAuthor);//, deleteAuthor);
        return nextAuthor;
    }
}
