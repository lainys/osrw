package ru.miet.orgact.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ru.miet.orgact.Article;
import ru.miet.orgact.Checker;

import java.util.HashMap;


public class MainController {

    @FXML
    ScrollPane firstTabPage;
    @FXML
    ScrollPane secondTabPage;
    @FXML
    VBox thirdTabPage;
    @FXML
    Button nextButton;
    @FXML
    TabPane tabPane;
    private Article article;

    @FXML
    public void nextTab() {

        int currentTab = tabPane.getSelectionModel().getSelectedIndex();

        if (tabPane.getTabs().size() == currentTab + 1) {
            send();
        } else {
            if (tabPane.getTabs().size() == currentTab + 2) {
                nextButton.setText("Отправить");
            } else {
                nextButton.setText("Далее");

            }
            tabPane.getSelectionModel().selectNext();
        }

    }

    @FXML
    public void changeSelection() {
        int currentTab = tabPane.getSelectionModel().getSelectedIndex();
        if (nextButton != null) {
            if (tabPane.getTabs().size() == currentTab + 1) {
                nextButton.setText("Отправить");
            } else {
                nextButton.setText("Далее");
            }
        }

    }

    public void getFieldsFromFirstTab() {
        try {

            for (Node child : ((GridPane) firstTabPage.getContent()).getChildren()) {
                if (child.getId() != null) {
                    switch (child.getId()) {
                        case "nameField": {
                            TextField nameField = (TextField) child;
                            if (nameField.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"Название публикации\"");
                            } else {
                                article.setName(nameField.getText());
                            }
                            break;
                        }
                        case "authorsFields": {
                            break;
                        }
                        case "yearField": {

                            TextField yearField = (TextField) child;
                            if (Checker.checkNumber(yearField.getText())) {
                                showMessage("Год введен неверно");
                            } else {
                                article.setYear(Integer.parseInt(yearField.getText()));
                            }
                            break;
                        }
                        case "countryField": {

                            TextField countryField = (TextField) child;
                            if (Checker.checkStringWithoutNumber(countryField.getText())) {
                                showMessage("Поле страна введено неверно");
                            } else {
                                article.setCountry(countryField.getText());
                            }

                            break;
                        }
                        case "cityField": {
                            TextField cityField = (TextField) child;
                            if (Checker.checkStringWithoutNumber(cityField.getText())) {
                                showMessage("Поле город введено неверно");
                            } else {
                                article.setCity(cityField.getText());
                            }
                            break;
                        }
                        case "publicHouseField": {

                            TextField publicHouseField = (TextField) child;
                            if (Checker.checkStringWithoutNumber(publicHouseField.getText())) {
                                showMessage("Поле издательство введено неверно");
                            } else {
                                article.setPublicHouse(publicHouseField.getText());
                            }
                            break;
                        }
                        case "topic": {
                            ComboBox topic = (ComboBox) (child);
                            if (topic.getSelectionModel().getSelectedIndex() == -1) {
                                showMessage("Не выбран раздел");
                            } else {
                                article.setTopic(topic.getSelectionModel().getSelectedItem().toString());
                            }

                            break;
                        }
                        case "directions": {
                            ComboBox directions = (ComboBox) (child);
                            if (directions.getSelectionModel().getSelectedIndex() == -1) {
                                showMessage("Не выбранo направление");
                            } else {
                                article.setTopic(directions.getSelectionModel().getSelectedItem().toString());
                            }
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getFieldsFromSecondTab() {
        try {
            ObservableList<Node> childs = ((GridPane) secondTabPage.getContent()).getChildren();

            HashMap<String, Integer> citations = new HashMap<>();

            for (int i = 0; i < childs.size(); i += 2) {

                CheckBox name = (CheckBox) childs.get(i);
                if (name.isSelected()) {
                    Spinner value = (Spinner) childs.get(i + 1);

                    citations.put(name.getText(), Integer.parseInt(value.getValue().toString()));
                }
            }
            article.setCitations(citations);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showMessage(String s) {

        System.out.println(s);
    }

    public void send() {
        article = new Article();
        getFieldsFromFirstTab();
        getFieldsFromSecondTab();
        System.out.println(article.toJSON());

    }
}
