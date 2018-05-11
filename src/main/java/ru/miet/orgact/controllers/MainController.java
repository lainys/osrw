package ru.miet.orgact.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.controlsfx.control.CheckComboBox;
import ru.miet.orgact.Article;
import ru.miet.orgact.Client;

import java.util.ArrayList;
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

    public boolean getFieldsFromFirstTab() {
        try {

            for (Node child : ((GridPane) firstTabPage.getContent()).getChildren()) {
                if (child.getId() != null) {
                    switch (child.getId()) {
                        case "nameField": {
                            TextField nameField = (TextField) child;
                            if (nameField.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"Название публикации\"");
                                return false;
                            } else {
                                article.setName(nameField.getText());
                            }
                            break;
                        }
                        case "authorsFields": {
                            VBox authorsField = (VBox) child;
                            ObservableList<Node> vboxChilds = authorsField.getChildren();
                            ArrayList<String> authors = new ArrayList<>();
                            ArrayList<String> positions = new ArrayList<>();

                            for (int i = 0; i < vboxChilds.size(); i++) {
                                HBox currentAuthor = (HBox) vboxChilds.get(i);
                                TextField name = (TextField) currentAuthor.getChildren().get(0);
                                ComboBox position = (ComboBox) currentAuthor.getChildren().get(1);

                                if (name.getText().isEmpty()) {
                                    showMessage("Не введено имя одного из авторов");
                                    return false;
                                } else {

                                    authors.add(name.getText());
                                }

                                if (position.getSelectionModel().getSelectedIndex() == -1) {
                                    showMessage("Не выбрана должность для одного из авторов");
                                    return false;
                                } else {

                                    positions.add(position.getSelectionModel().getSelectedItem().toString());
                                }
                            }

                            article.setAuthors(authors);
                            article.setPositions(positions);

                            break;
                        }
                        case "yearField": {
                            TextField yearField = (TextField) child;
                            if (yearField.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"Год\"");
                                return false;
                            } else {
                                article.setYear(Integer.parseInt(yearField.getText()));
                            }
                            break;
                        }
                        case "countryField": {
                            TextField countryField = (TextField) child;
                            if (countryField.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"Страна\"");
                                return false;
                            } else {
                                article.setCountry(countryField.getText());
                            }

                            break;
                        }
                        case "cityField": {
                            TextField cityField = (TextField) child;
                            if (cityField.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"Город\"");
                                return false;
                            } else {
                                article.setCity(cityField.getText());
                            }
                            break;
                        }
                        case "publishingHouseField": {

                            TextField publishingHouseField = (TextField) child;
                            if (publishingHouseField.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"Издательство\"");
                                return false;
                            } else {
                                article.setPublishingHouse(publishingHouseField.getText());
                            }
                            break;
                        }
                        case "topic": {
                            ComboBox topic = (ComboBox) (child);
                            if (topic.getSelectionModel().getSelectedIndex() == -1) {
                                showMessage("Не выбран раздел");
                                return false;
                            } else {
                                article.setTopic(topic.getSelectionModel().getSelectedItem().toString());
                            }

                            break;
                        }
                        case "directions": {
                            CheckComboBox directions = (CheckComboBox) (child);
                            if (directions.getCheckModel().getCheckedIndices().size() == 0) {
                                showMessage("Не выбранo направление");
                                return false;
                            } else {
                                article.setDirections(directions.getCheckModel().getCheckedIndices(), directions.getCheckModel().getItemCount());
                            }
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean getFieldsFromSecondTab() {
        try {
            ObservableList<Node> childs = ((GridPane) secondTabPage.getContent()).getChildren();

            HashMap<String, Integer> citations = new HashMap<>();

            for (int i = 0; i < childs.size(); i += 2) {

                CheckBox name = (CheckBox) childs.get(i);
                if (name.isSelected()) {
                    TextField value = (TextField) childs.get(i + 1);

                    citations.put(name.getText(), Integer.parseInt(value.getText()));
                }
            }
            article.setCitations(citations);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean getFieldsFromThird() {
        try {
            ObservableList<Node> childs = thirdTabPage.getChildren();
            HBox hbox = (HBox) childs.get(0);
            GridPane grid = (GridPane) ((BorderPane) ((ScrollPane) childs.get(2)).getContent()).getCenter();
            for (Node n : hbox.getChildren()) {
                RadioButton radio = (RadioButton) n;
                if (radio.isSelected()) {
                    if (n.getId() != null) {
                        switch (n.getId()) {
                            case "book": {
                                if (!getFieldsFromBook(grid)) return false;
                                break;
                            }
                            case "journal": {
                                if (!getFieldsFromJournal(grid)) return false;
                                break;
                            }
                            case "conference": {
                                if (!getFieldsFromConference(grid)) return false;
                                break;
                            }
                            case "other": {
                                if (!getFieldsFromOther(grid)) return false;
                                break;
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean getFieldsFromBook(GridPane grid) {
        try {
            ObservableList<Node> childs = grid.getChildren();


            String json = "\"book\": {";
            for (int i = 0; i < childs.size(); i++) {
                if (childs.get(i).getId() != null) {
                    switch (childs.get(i).getId()) {
                        case "bookName": {
                            TextField bookName = (TextField) childs.get(i);
                            if (bookName.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"Название книги\"");
                                return false;
                            } else {
                                json += "\"name\":\"" + bookName.getText() + "\",";
                            }
                            break;
                        }
                        case "bookPublishingHouse": {
                            TextField bookPublishingHouse = (TextField) childs.get(i);
                            if (bookPublishingHouse.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"Издательство\"");
                                return false;
                            } else {
                                json += "\"publishingHouse\":\"" + bookPublishingHouse.getText() + "\",";
                            }
                            break;
                        }
                        case "bookISBN": {
                            TextField bookPublishingHouse = (TextField) childs.get(i);
                            if (bookPublishingHouse.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"ISBN\"");
                                return false;
                            } else {
                                json += "\"publishingHouse\":\"" + bookPublishingHouse.getText() + "\",";
                            }
                            break;
                        }
                        case "bookPages": {
                            TextField bookPages = (TextField) childs.get(i);
                            if (bookPages.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"страницы\"");
                                return false;
                            } else {
                                json += "\"pages\":\"" + bookPages.getText() + "\",";
                            }
                            break;
                        }
                        case "bookLink": {
                            TextField bookLink = (TextField) childs.get(i);
                            if (bookLink.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"Ссылка на книгу\"");
                                return false;
                            } else {
                                json += "\"link\":\"" + bookLink.getText() + "\",";
                            }
                            break;
                        }

                    }
                }

            }
            json += "}";
            article.setType("book");
            article.setTypeJson(json);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean getFieldsFromJournal(GridPane grid) {
        try {
            ObservableList<Node> childs = grid.getChildren();

            String json = "\"journal\": {";
            for (int i = 0; i < childs.size(); i++) {
                if (childs.get(i).getId() != null) {
                    switch (childs.get(i).getId()) {
                        case "journalName": {
                            TextField journalName = (TextField) childs.get(i);
                            if (journalName.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"Название журнала\"");
                                return false;
                            } else {
                                json += "\"name\":\"" + journalName.getText() + "\",";
                            }
                            break;
                        }
                        case "journalFactor": {
                            TextField journalFactor = (TextField) childs.get(i);
                            if (journalFactor.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"Импакт-фактор журнала\"");
                                return false;
                            } else {
                                json += "\"impactFactor\":\"" + journalFactor.getText() + "\",";
                            }
                            break;
                        }
                        case "journalPages": {
                            TextField journalPages = (TextField) childs.get(i);
                            if (journalPages.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"Страницы журнала\"");
                                return false;
                            } else {
                                json += "\"pages\":\"" + journalPages.getText() + "\",";
                            }
                            break;
                        }
                        case "journalLink": {
                            TextField journalLink = (TextField) childs.get(i);
                            if (journalLink.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"Ссылка на журнал\"");
                                return false;
                            } else {
                                json += "\"link\":\"" + journalLink.getText() + "\",";
                            }
                            break;
                        }
                        case "journalISSN": {
                            TextField journalISSN = (TextField) childs.get(i);
                            if (journalISSN.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"ISSN журнала\"");
                                return false;
                            } else {
                                json += "\"issn\":\"" + journalISSN.getText() + "\",";
                            }
                            break;
                        }
                        case "journalVak": {
                            CheckBox journalVak = (CheckBox) childs.get(i);
                            if (journalVak.isSelected()) {
                                json += "\"vak\":\"1\",";
                            } else {

                                json += "\"vak\":\"0\",";
                            }
                            break;
                        }
                        case "journalRussian": {
                            CheckBox journalRussian = (CheckBox) childs.get(i);
                            if (journalRussian.isSelected()) {
                                json += "\"russian\":\"1\",";
                            } else {

                                json += "\"russian\":\"0\",";
                            }
                            break;
                        }
                    }
                }

            }
            json += "}";
            article.setType("journal");
            article.setTypeJson(json);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean getFieldsFromOther(GridPane grid) {
        try {
            ObservableList<Node> childs = grid.getChildren();

            String json = "\"other\": {";
            for (int i = 0; i < childs.size(); i++) {
                if (childs.get(i).getId() != null) {
                    switch (childs.get(i).getId()) {
                        case "otherName": {
                            TextField otherName = (TextField) childs.get(i);
                            if (otherName.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"Название ресурса\"");
                                return false;
                            } else {
                                json += "\"name\":\"" + otherName.getText() + "\",";
                            }
                            break;
                        }
                        case "otherLink": {
                            TextField otherLink = (TextField) childs.get(i);
                            if (otherLink.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"Ссылка на ресурс\"");
                                return false;
                            } else {
                                json += "\"link\":\"" + otherLink.getText() + "\",";
                            }
                            break;
                        }

                    }
                }

            }
            json += "}";
            article.setType("other");
            article.setTypeJson(json);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean getFieldsFromConference(GridPane grid) {
        try {
            ObservableList<Node> childs = grid.getChildren();

            String json = "\"conference\": {";
            for (int i = 0; i < childs.size(); i++) {
                if (childs.get(i).getId() != null) {
                    switch (childs.get(i).getId()) {
                        case "conferenceName": {
                            TextField conferenceName = (TextField) childs.get(i);
                            if (conferenceName.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"Название публикации\"");
                                return false;
                            } else {
                                json += "\"name\":\"" + conferenceName.getText() + "\",";
                            }
                            break;
                        }
                        case "conferenceStart": {
                            DatePicker conferenceStart = (DatePicker) childs.get(i);
                            if (conferenceStart.getValue() == null) {
                                showMessage("Не заполнено поле \"Начало конференции\"");
                                return false;
                            } else {
                                json += "\"start\":\"" + conferenceStart.getValue().toString() + "\",";
                            }
                            break;
                        }
                        case "conferenceFinish": {
                            DatePicker conferenceFinish = (DatePicker) childs.get(i);
                            if (conferenceFinish.getValue() == null) {
                                showMessage("Не заполнено поле \"Конец конференции\"");
                                return false;
                            } else {
                                json += "\"start\":\"" + conferenceFinish.getValue().toString() + "\",";
                            }
                            break;
                        }
                        case "conferenceCountry": {
                            TextField conferenceCountry = (TextField) childs.get(i);
                            if (conferenceCountry.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"Страна конференции\"");
                                return false;
                            } else {
                                json += "\"country\":\"" + conferenceCountry.getText() + "\",";
                            }
                            break;
                        }
                        case "conferenceCity": {
                            TextField conferenceCity = (TextField) childs.get(i);
                            if (conferenceCity.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"Город конференции\"");
                                return false;
                            } else {
                                json += "\"city\":\"" + conferenceCity.getText() + "\",";
                            }
                            break;
                        }
                        case "conferencePages": {
                            TextField conferencePages = (TextField) childs.get(i);
                            if (conferencePages.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"Страницы сборника конференции\"");
                                return false;
                            } else {
                                json += "\"pages\":\"" + conferencePages.getText() + "\",";
                            }
                            break;
                        }
                        case "conferenceLink": {
                            TextField conferenceLink = (TextField) childs.get(i);
                            if (conferenceLink.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"Ссылка на сборник конференции\"");
                                return false;
                            } else {
                                json += "\"link\":\"" + conferenceLink.getText() + "\",";
                            }
                            break;
                        }

                    }

                }
            }
            json += "}";
            article.setType("conference");
            article.setTypeJson(json);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.setTitle("Успех!");
        alert.showAndWait();
    }

    public void send() {
        article = new Article();
        if (getFieldsFromFirstTab() && getFieldsFromSecondTab() && getFieldsFromThird()) {

            System.out.println(article.toJSON());
            String query = "{";

            query += "\"type\":\"article_add\",";

            query += "\"data\":\"" + article.toJSON() + "\",";

            query += "}";

            Client client = new Client();
            client.sendMessage(query);
            String answer = client.getMessage();
            showMessage(answer);
        }

    }
}
