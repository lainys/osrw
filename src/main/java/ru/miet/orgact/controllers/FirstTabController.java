package ru.miet.orgact.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.validation.ValidationMessage;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.decoration.StyleClassValidationDecoration;
import org.controlsfx.validation.decoration.ValidationDecoration;
import ru.miet.orgact.Client;
import ru.miet.orgact.handlers.AddAuthorHandler;
import ru.miet.orgact.handlers.StringListener;
import ru.miet.orgact.handlers.YearFieldListener;

import java.util.ArrayList;


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
    private TextField authorField;

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
    private CheckComboBox directions;


    @FXML
    public void initialize() {

        ValidationSupport validationSupport = new ValidationSupport();

        validationSupport.setValidationDecorator(new StyleClassValidationDecoration());

        validationSupport.setValidationDecorator(new ValidationDecoration() {
            Control control;
            Border border;

            @Override
            public void removeDecorations(Control control) {
                control.borderProperty().setValue(border);
            }

            @Override
            public void applyValidationDecoration(ValidationMessage validationMessage) {
                Border temp = border;
                control.borderProperty().setValue(temp);
            }

            @Override
            public void applyRequiredDecoration(Control control) {
                this.control = control;
                border = control.getBorder();
            }
        });
        validationSupport.registerValidator(nameField, false, Validator.createEmptyValidator("Необходимо ввести название"));


        yearField.textProperty().addListener(new YearFieldListener(yearField));
        countryField.textProperty().addListener(new StringListener(countryField));
        cityField.textProperty().addListener(new StringListener(cityField));

        ArrayList<String> employees = Client.getEmployees();
        TextFields.bindAutoCompletion(authorField, employees);
        addAuthor.setOnAction(new AddAuthorHandler(authorsFields, grid, employees));

        position.getItems().addAll("Студент МИЭТ", "Аспирант МИЭТ", "Сотрудник МИЭТ", "Другое");
        topic.getItems().addAll("Монографии", "Учебники и учебные пособия", "Учебно-методические пособия", "Статьи в зарубежных журналах", "Статьи в российских журналах", "Материалы докладов зарубежных научно-технических конференций", "Материалы докладов научно-технических конференций в России и СНГ", "Статьи в сборниках научных трудов", "Тезисы докладов");
        directions.getItems().addAll("Естественные и точные науки", "Техника и технологии", "Медицинские науки и общественное здравоохранение", "Социальные науки", "Гуманитарные науки");

    }

}
