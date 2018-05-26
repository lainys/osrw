package ru.miet.orgact.controllers;

import com.google.gson.Gson;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.textfield.TextFields;
import ru.miet.orgact.*;
import ru.miet.orgact.controllers.edit.FindArticleForEdit;
import ru.miet.orgact.handlers.DeleteAuthorHandler;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;


public class MainController {


    static MainController main;

    private int codeEdit;
    static ThirdTabController ttc;
    Node old;
    private String typeWork;
    @FXML
    private MenuItem saveMenu;

    @FXML
    private GridPane startWindow;

    @FXML
    private BorderPane borderPane;

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
    public void initialize() {
        nextButton.setVisible(false);
        saveMenu.setVisible(false);
        MainController.main = this;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/main_window.fxml"));
            startWindow = loader.load();

            StartWindow contrl = loader.getController();

            contrl.setMainController(this);

            ttc.setMain();

            borderPane.setCenter(startWindow);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

                            if (vboxChilds.size() == 0) {
                                showMessage("Не указано ни одного автора");
                                return false;
                            }
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
                                article.setTopic(Integer.toString(topic.getSelectionModel().getSelectedIndex() + 1));
                            }

                            break;
                        }
                        case "directions": {
                            CheckComboBox directions = (CheckComboBox) (child);
                            if (directions.getCheckModel().getCheckedIndices().size() == 0) {
                                showMessage("Не выбранo направление");
                                return false;
                            } else {
                                //article.setDirections(directions.getCheckModel().getCheckedIndices(), directions.getCheckModel().getItemCount());
                                article.setDirections(directions.getCheckModel().getCheckedIndices());
                            }
                            break;
                        }

                        case "doi": {

                            TextField doi = (TextField) child;
                            if (doi.getText().isEmpty()) {
                                //showMessage("Не заполнено поле \"Издательство\"");
                                //return false;
                                article.setDoi(doi.getText());
                            } else {
                                article.setDoi(doi.getText());
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

            for (int i = 1; i < childs.size(); i += 2) {

                CheckBox name = (CheckBox) childs.get(i);
                if (name.isSelected()) {
                    TextField value = (TextField) childs.get(i + 1);
                    if (value.getText().isEmpty()) {
                        citations.put(name.getText(), 0);
                    } else {
                        citations.put(name.getText(), Integer.parseInt(value.getText()));
                    }
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
            HBox hbox = (HBox) childs.get(1);
            GridPane grid = (GridPane) ((BorderPane) ((ScrollPane) childs.get(3)).getContent()).getCenter();
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


            Book book = new Book();
            for (int i = 0; i < childs.size(); i++) {
                if (childs.get(i).getId() != null) {
                    switch (childs.get(i).getId()) {
                        case "bookName": {
                            TextField bookName = (TextField) childs.get(i);
                            if (bookName.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"Название книги\"");
                                return false;
                            } else {
                                book.setName(bookName.getText());
                            }
                            break;
                        }
                        case "bookPublishingHouse": {
                            TextField bookPublishingHouse = (TextField) childs.get(i);
                            if (bookPublishingHouse.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"Издательство\"");
                                return false;
                            } else {
                                article.setPublishingHouse(bookPublishingHouse.getText());
                            }
                            break;
                        }
                        case "bookISBN": {
                            TextField bookISBN = (TextField) childs.get(i);
                            if (bookISBN.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"ISBN\"");
                                return false;
                            } else {
                                book.setISBN(bookISBN.getText());
                            }
                            break;
                        }
                        case "bookPages": {
                            TextField bookPages = (TextField) childs.get(i);
                            if (bookPages.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"страницы\"");
                                return false;
                            } else {
                                article.setPages(bookPages.getText());
                            }
                            break;
                        }
                        case "bookLink": {
                            TextField bookLink = (TextField) childs.get(i);
                            if (bookLink.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"Ссылка на книгу\"");
                                return false;
                            } else {
                                article.setLink(bookLink.getText());
                            }
                            break;
                        }
                        case "bookTom": {
                            TextField bookTom = (TextField) childs.get(i);
                            if (bookTom.getText().isEmpty()) {
                                //showMessage("Не заполнено поле \"Ссылка на книгу\"");
                                //return false;
                            } else {
                                book.setBookTom(bookTom.getText());
                            }
                            break;
                        }

                    }
                }

            }
            Gson gson = new Gson();
            article.setType("book");
            article.setTypeJson(gson.toJson(book));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean getFieldsFromJournal(GridPane grid) {
        try {
            ObservableList<Node> childs = grid.getChildren();

            Journal journal = new Journal();
            journal.setCode(ttc.getCode());
            for (int i = 0; i < childs.size(); i++) {
                if (childs.get(i).getId() != null) {
                    switch (childs.get(i).getId()) {
                        case "journalName": {
                            TextField journalName = (TextField) childs.get(i);
                            if (journalName.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"Название журнала\"");
                                return false;
                            } else {
                                journal.setName(journalName.getText());
                            }
                            break;
                        }
                        case "journalFactor": {
                            TextField journalFactor = (TextField) childs.get(i);
                            if (journalFactor.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"Импакт-фактор журнала\"");
                                return false;
                            } else {
                                journal.setImpact_factor(Double.parseDouble(journalFactor.getText()));
                            }
                            break;
                        }
                        case "journalPages": {
                            TextField journalPages = (TextField) childs.get(i);
                            if (journalPages.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"Страницы журнала\"");
                                return false;
                            } else {
                                article.setPages(journalPages.getText());
                            }
                            break;
                        }
                        case "journalLink": {
                            TextField journalLink = (TextField) childs.get(i);
                            if (journalLink.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"Ссылка на журнал\"");
                                return false;
                            } else {
                                journal.setLink(journalLink.getText());
                            }
                            break;
                        }

                        case "journalNumber": {
                            TextField journalNumber = (TextField) childs.get(i);
                            if (journalNumber.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"Номер журнала\"");
                                return false;
                            } else {
                                article.setNumber(journalNumber.getText());
                            }
                            break;
                        }
                        case "journalISSN": {
                            TextField journalISSN = (TextField) childs.get(i);
                            if (journalISSN.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"ISSN журнала\"");
                                return false;
                            } else {
                                journal.setIssn(journalISSN.getText());
                            }
                            break;
                        }
                        case "journalVak": {
                            CheckBox journalVak = (CheckBox) childs.get(i);
                            if (journalVak.isSelected()) {
                                journal.setVak(true);
                            } else {
                                journal.setVak(false);
                            }
                            break;
                        }
                        case "journalRussian": {
                            CheckBox journalRussian = (CheckBox) childs.get(i);
                            if (journalRussian.isSelected()) {
                                journal.setRussian(true);
                            } else {
                                journal.setRussian(false);
                            }
                            break;
                        }
                        case "journalRecenz": {
                            CheckBox journalRecenz = (CheckBox) childs.get(i);
                            if (journalRecenz.isSelected()) {
                                journal.setRecenz(true);
                            } else {
                                journal.setRecenz(false);
                            }
                            break;
                        }
                        case "journalRinc": {
                            CheckBox journalRinc = (CheckBox) childs.get(i);
                            if (journalRinc.isSelected()) {
                                journal.setRinc(true);
                            } else {
                                journal.setRinc(false);
                            }
                            break;
                        }
                        case "journalISI": {
                            CheckBox journalISI = (CheckBox) childs.get(i);
                            if (journalISI.isSelected()) {
                                journal.setIsi(true);
                            } else {
                                journal.setIsi(false);
                            }
                            break;
                        }
                        case "journalScopus": {
                            CheckBox journalScopus = (CheckBox) childs.get(i);
                            if (journalScopus.isSelected()) {
                                journal.setScopus(true);
                            } else {
                                journal.setScopus(false);
                            }
                            break;
                        }
                    }
                }

            }
            article.setType("journal");
            Gson gson = new Gson();
            article.setTypeJson(gson.toJson(journal));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean getFieldsFromOther(GridPane grid) {
        try {
            ObservableList<Node> childs = grid.getChildren();

            Other other = new Other();
            for (int i = 0; i < childs.size(); i++) {
                if (childs.get(i).getId() != null) {
                    switch (childs.get(i).getId()) {
                        case "otherName": {
                            TextField otherName = (TextField) childs.get(i);
                            if (otherName.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"Название ресурса\"");
                                return false;
                            } else {
                                other.setName(otherName.getText());
                            }
                            break;
                        }
                        case "otherLink": {
                            TextField otherLink = (TextField) childs.get(i);
                            if (otherLink.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"Ссылка на ресурс\"");
                                return false;
                            } else {
                                article.setLink(otherLink.getText());
                            }
                            break;
                        }

                    }
                }

            }
            article.setType("other");
            Gson gson = new Gson();
            article.setTypeJson(gson.toJson(other));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean getFieldsFromConference(GridPane grid) {
        try {
            ObservableList<Node> childs = grid.getChildren();

            Conference conf = new Conference();
            conf.setCode(ttc.getCode());
            for (int i = 0; i < childs.size(); i++) {
                if (childs.get(i).getId() != null) {
                    switch (childs.get(i).getId()) {
                        case "conferenceName": {
                            TextField conferenceName = (TextField) childs.get(i);
                            if (conferenceName.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"Название публикации\"");
                                return false;
                            } else {
                                conf.setName(conferenceName.getText());
                            }
                            break;
                        }
                        case "conferenceStart": {
                            DatePicker conferenceStart = (DatePicker) childs.get(i);
                            if (conferenceStart.getValue() == null) {
                                showMessage("Не заполнено поле \"Начало конференции\"");
                                return false;
                            } else {
                                conf.setStart(conferenceStart.getValue().toString().replace("-", ". "));
                            }
                            break;
                        }
                        case "conferenceFinish": {
                            DatePicker conferenceFinish = (DatePicker) childs.get(i);
                            if (conferenceFinish.getValue() == null) {
                                showMessage("Не заполнено поле \"Конец конференции\"");
                                return false;
                            } else {
                                conf.setFinish(conferenceFinish.getValue().toString().replace("-", ". "));
                            }
                            break;
                        }
                        case "conferenceCountry": {
                            TextField conferenceCountry = (TextField) childs.get(i);
                            if (conferenceCountry.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"Страна конференции\"");
                                return false;
                            } else {
                                conf.setCountry(conferenceCountry.getText());
                            }
                            break;
                        }
                        case "conferenceCity": {
                            TextField conferenceCity = (TextField) childs.get(i);
                            if (conferenceCity.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"Город конференции\"");
                                return false;
                            } else {
                                conf.setCity(conferenceCity.getText());
                            }
                            break;
                        }
                        case "conferencePages": {
                            TextField conferencePages = (TextField) childs.get(i);
                            if (conferencePages.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"Страницы сборника конференции\"");
                                return false;
                            } else {
                                article.setPages(conferencePages.getText());
                            }
                            break;
                        }
                        case "conferenceLink": {
                            TextField conferenceLink = (TextField) childs.get(i);
                            if (conferenceLink.getText().isEmpty()) {
                                showMessage("Не заполнено поле \"Ссылка на сборник конференции\"");
                                return false;
                            } else {
                                article.setLink(conferenceLink.getText());
                            }
                            break;
                        }

                    }

                }
            }
            article.setType("conference");
            Gson gson = new Gson();
            article.setTypeJson(gson.toJson(conf));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.setTitle("Сообщение");
        alert.showAndWait();
    }

    public boolean showMessageConfirm(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(message);
        alert.setTitle("Успех!");
        alert.showAndWait();
        return alert.getResult().getButtonData().isDefaultButton();
    }

    public void send() {
        article = new Article();
        if (getFieldsFromFirstTab() && getFieldsFromSecondTab() && getFieldsFromThird()) {
            if (!cycleSend(5)) {
                if (showMessageConfirm("Нет доступа к серверу.\nХотите сохранить публикацию в файл?")) {
                    if (saveArticle()) {
                        showMessage("Публикация успешно сохранена!");
                    }
                }
            }
        }
    }

    @FXML
    public void menuSaveArticle() {
        article = new Article();
        if (getFieldsFromFirstTab() && getFieldsFromSecondTab() && getFieldsFromThird()) {
            if (saveArticle()) {
                showMessage("Публикация успешно сохранена!");
            }
        }
    }

    @FXML
    public void menuOpenArticle() {
        // menuCleanArticle();

        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(null);
        //открыть диалог выбора файлы
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            //открыть файл запись всё в строку
            String fileText = "";
            String a;
            while ((a = reader.readLine()) != null) {
                fileText += a;
            }
            //из JSON парсим в объект
            Gson gson = new Gson();
            Article fromJson = gson.fromJson(fileText, Article.class);

            setValue(fromJson);
            article = fromJson;
            if (article.getCode() > 0) {
                codeEdit = article.getCode();
                typeWork = "article_edit";
            } else {
                typeWork = "article_add";
            }
            System.out.println(typeWork);
            borderPane.setCenter(tabPane);
            nextButton.setVisible(true);
            saveMenu.setVisible(true);
        } catch (Exception e) {
            showMessage("Не возможно открыть файл! Попробуйте снова.");
            e.printStackTrace();
        }


    }

    public void setValue(Article article) {
        setFirstTab(article);
        setSecondTab(article);
        setThirdTab(article);
    }

    public boolean setFirstTab(Article article) {
        try {
            ArrayList<String> employers = new ArrayList<>();
            for (Node child : ((GridPane) firstTabPage.getContent()).getChildren()) {
                if (child.getId() != null) {
                    switch (child.getId()) {
                        case "nameField": {
                            TextField nameField = (TextField) child;
                            nameField.setText(article.getName());
                            break;
                        }
                        case "authorsFields": {
                            VBox authorsField = (VBox) child;
                            ObservableList<Node> vboxChilds = authorsField.getChildren();
                            ArrayList<String> authors = article.getAuthors();
                            ArrayList<String> positions = article.getPositions();

                            int len = vboxChilds.size();
                            vboxChilds.clear();
                            for (int i = 0; i < len; i++) {
                                resize_grid(-45, (GridPane) firstTabPage.getContent());
                            }
                            for (int i = 0; i < authors.size(); i++) {
                                addAuthor(authors.get(i), positions.get(i), vboxChilds, authorsField, (GridPane) firstTabPage.getContent(), employers);
                            }

                            break;
                        }

                        case "doi": {
                            TextField doi = (TextField) child;
                            doi.setText(article.getDoi());
                            break;
                        }
                        case "yearField": {
                            TextField yearField = (TextField) child;
                            yearField.setText(article.getYear().toString());
                            break;
                        }
                        case "countryField": {
                            TextField countryField = (TextField) child;
                            countryField.setText(article.getCountry());

                            break;
                        }
                        case "cityField": {
                            TextField cityField = (TextField) child;
                            cityField.setText(article.getCity());
                            break;
                        }
                        case "publishingHouseField": {

                            TextField publishingHouseField = (TextField) child;
                            publishingHouseField.setText(article.getPublishingHouse());
                            break;
                        }
                        case "topic": {
                            ComboBox topic = (ComboBox) (child);
                            if (article.getTopic().length() > 2) {
                                topic.getSelectionModel().select(article.getTopic());
                            } else {

                                topic.getSelectionModel().select(FirstTabController.topics.get(Integer.parseInt(article.getTopic())));
                            }

                            break;
                        }
                        case "directions": {
                            CheckComboBox directions = (CheckComboBox) (child);
                            if (article.getDirections().size() > 0) {
                                for (int i = 0; i < directions.getItems().size(); i++) {
                                    if (article.getDirections().get(i) == 1) {
                                        directions.getCheckModel().check(i);
                                    }
                                }
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

    public void addAuthor(String fio, String position, ObservableList<Node> vbox, VBox authorsFields, GridPane grid, ArrayList<String> list) {

        Font buttonFont = new Font("Times new roman", 14);
        Insets paddingAuthorBox = new Insets(0, 10, 0, 10);


        HBox nextAuthor = new HBox();
        nextAuthor.setSpacing(20);
        nextAuthor.setPadding(paddingAuthorBox);
        nextAuthor.setPrefSize(200, 100);

        TextField textAuthor = new TextField();
        textAuthor.setPrefSize(140, 25);
        textAuthor.setPromptText("Введите ФИО автора");
        textAuthor.setText(fio);
        TextFields.bindAutoCompletion(textAuthor, list);

        ComboBox positionBox = new ComboBox();
        positionBox.getItems().addAll("Студент МИЭТ", "Аспирант МИЭТ", "Сотрудник МИЭТ", "Другое");
        positionBox.setPromptText("Выберите должность");
        positionBox.getSelectionModel().select(position);
        positionBox.setPrefSize(170, 25);

        Button addAuthor = new Button("-");
        addAuthor.setPrefSize(80, 25);
        addAuthor.setFont(buttonFont);
        addAuthor.setOnAction(new DeleteAuthorHandler(authorsFields, grid));

        nextAuthor.getChildren().addAll(textAuthor, positionBox, addAuthor);//, deleteAuthor);

        vbox.add(nextAuthor);
        resize_grid(45, grid);

    }

    public void resize_grid(int k, GridPane grid) {
        for (int i = 0; i < grid.getRowConstraints().size(); i++) {
            if (i == 2) {
                grid.getRowConstraints().get(i).setPrefHeight(grid.getRowConstraints().get(i).getPrefHeight() + k);
            }
        }
        grid.setPrefHeight(grid.getPrefHeight() + k);
        grid.setMaxHeight(grid.getPrefHeight());
        grid.setMinHeight(grid.getPrefHeight());
    }

    public void setSecondTab(Article article) {
        try {
            ObservableList<Node> childs = ((GridPane) secondTabPage.getContent()).getChildren();

            HashMap<String, Integer> citations = article.getCitations();

            for (int i = 1; i < childs.size(); i += 2) {

                CheckBox name = (CheckBox) childs.get(i);

                if (citations.containsKey(name.getText())) {
                    name.setSelected(true);
                    TextField value = (TextField) childs.get(i + 1);
                    value.setText(citations.get(name.getText()).toString());
                    value.setDisable(false);
                }

            }
            article.setCitations(citations);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setThirdTab(Article article) {
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/third_tab.fxml"));
        FXMLLoader loader = new FXMLLoader();
        try {
            //loader.setRoot(thirdTabPage);
            //thirdTabPage = loader.load();
            //нужно как то получить этот контролер
            ThirdTabController controller = ttc;
            ObservableList<Node> childs = thirdTabPage.getChildren();
            HBox hbox = (HBox) childs.get(1);
            for (int i = 0; i < hbox.getChildren().size(); i++) {
                RadioButton radio = (RadioButton) hbox.getChildren().get(i);
                if (radio.getId().compareTo(article.getType()) == 0) {
                    radio.setSelected(true);
                    break;
                }
            }

            GridPane grid = (GridPane) controller.getPlace(article.getType());
            switch (article.getType()) {
                case "book": {
                    setBook(article, grid);
                    controller.selectPlace("book");
                    break;
                }
                case "journal": {
                    setJournal(article, grid);
                    controller.toNotSearch("journal");
                    break;
                }
                case "conference": {
                    setConference(article, grid);
                    controller.toNotSearch("conference");
                    break;
                }
                case "other": {
                    setOther(article, grid);
                    controller.selectPlace("other");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setBook(Article article, GridPane grid) {
        try {
            ObservableList<Node> childs = grid.getChildren();

            Gson gson = new Gson();
            Book book = gson.fromJson(article.getTypeJson(), Book.class);
            for (int i = 0; i < childs.size(); i++) {
                if (childs.get(i).getId() != null) {
                    switch (childs.get(i).getId()) {
                        case "bookName": {
                            TextField bookName = (TextField) childs.get(i);
                            bookName.setText(book.getName());
                            break;
                        }
                        case "bookPublishingHouse": {
                            TextField bookPublishingHouse = (TextField) childs.get(i);
                            bookPublishingHouse.setText(book.getPublishingHouse());
                            break;
                        }
                        case "bookISBN": {
                            TextField bookISBN = (TextField) childs.get(i);
                            bookISBN.setText(book.getISBN());
                            break;
                        }
                        case "bookPages": {
                            TextField bookPages = (TextField) childs.get(i);
                            bookPages.setText(article.getPages());
                            break;
                        }
                        case "bookLink": {
                            TextField bookLink = (TextField) childs.get(i);
                            bookLink.setText(book.getLink());
                            break;
                        }
                        case "bookTom": {
                            TextField bookTom = (TextField) childs.get(i);
                            bookTom.setText(book.getBookTom());
                            break;
                        }

                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setJournal(Article article, GridPane grid) {
        try {
            ObservableList<Node> childs = grid.getChildren();

            Gson gson = new Gson();
            Journal journal = gson.fromJson(article.getTypeJson(), Journal.class);
            ttc.setCode(journal.getCode());
            for (int i = 0; i < childs.size(); i++) {
                if (childs.get(i).getId() != null) {
                    switch (childs.get(i).getId()) {
                        case "journalName": {
                            TextField journalName = (TextField) childs.get(i);
                            journalName.setText(journal.getName());
                            break;
                        }
                        case "journalFactor": {
                            TextField journalFactor = (TextField) childs.get(i);
                            journalFactor.setText(Double.toString(journal.getImpact_factor()));
                            break;
                        }
                        case "journalPages": {
                            TextField journalPages = (TextField) childs.get(i);
                            journalPages.setText(article.getPages());
                            break;
                        }
                        case "journalLink": {
                            TextField journalLink = (TextField) childs.get(i);
                            journalLink.setText(journal.getLink());
                            break;
                        }
                        case "journalISSN": {
                            TextField journalISSN = (TextField) childs.get(i);
                            journalISSN.setText(journal.getIssn());
                            break;
                        }
                        case "journalNumber": {
                            TextField journalNumber = (TextField) childs.get(i);
                            journalNumber.setText(article.getNumber());
                            break;
                        }
                        case "journalVak": {
                            CheckBox journalVak = (CheckBox) childs.get(i);

                            journalVak.setSelected(journal.isVak());
                            break;
                        }
                        case "journalRussian": {
                            CheckBox journalRussian = (CheckBox) childs.get(i);
                            journalRussian.setSelected(journal.isRussian());
                            break;
                        }
                        case "journalRecenz": {
                            CheckBox journalRecenz = (CheckBox) childs.get(i);
                            journalRecenz.setSelected(journal.isRecenz());
                            break;
                        }
                        case "journalISI": {
                            CheckBox journalISI = (CheckBox) childs.get(i);
                            journalISI.setSelected(journal.isIsi());
                            break;
                        }
                        case "journalScopus": {
                            CheckBox journalScopus = (CheckBox) childs.get(i);
                            journalScopus.setSelected(journal.isScopus());
                            break;
                        }
                        case "journalRinc": {
                            CheckBox journalRinc = (CheckBox) childs.get(i);
                            journalRinc.setSelected(journal.isRinc());
                            break;
                        }
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setJournalOther() {
        try {
            GridPane grid = (GridPane) ttc.getPlace("journal");
            ObservableList<Node> childs = grid.getChildren();

            for (int i = 0; i < childs.size(); i++) {
                if (childs.get(i).getId() != null) {
                    switch (childs.get(i).getId()) {

                        case "journalPages": {
                            TextField journalPages = (TextField) childs.get(i);
                            journalPages.setText(article.getPages());
                            break;
                        }

                        case "journalNumber": {
                            TextField journalNumber = (TextField) childs.get(i);
                            journalNumber.setText(article.getNumber());
                            break;
                        }

                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setConferenceOther() {
        try {
            GridPane grid = (GridPane) ttc.getPlace("conference");
            ObservableList<Node> childs = grid.getChildren();

            for (int i = 0; i < childs.size(); i++) {
                if (childs.get(i).getId() != null) {
                    switch (childs.get(i).getId()) {
                        case "conferencePages": {
                            TextField conferencePages = (TextField) childs.get(i);
                            //
                            conferencePages.setText(article.getPages());
                            break;
                        }
                        case "conferenceLink": {
                            TextField conferenceLink = (TextField) childs.get(i);
                            //
                            conferenceLink.setText(article.getLink());
                            break;
                        }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setConference(Article article, GridPane grid) {
        try {
            ObservableList<Node> childs = grid.getChildren();

            Gson gson = new Gson();
            Conference conf = gson.fromJson(article.getTypeJson(), Conference.class);
            ttc.setCode(conf.getCode());
            for (int i = 0; i < childs.size(); i++) {
                if (childs.get(i).getId() != null) {
                    switch (childs.get(i).getId()) {
                        case "conferenceName": {
                            TextField conferenceName = (TextField) childs.get(i);
                            conferenceName.setText(conf.getName());
                            break;
                        }
                        case "conferenceStart": {
                            DatePicker conferenceStart = (DatePicker) childs.get(i);
                            String[] date = conf.getStart().split(". ");
                            System.out.println("start " + date[0] + " " + date[1] + " " + date[2]);
                            int year = Integer.parseInt(date[0]);
                            int month = Integer.parseInt(date[1]);
                            int day = Integer.parseInt(date[2]);
                            conferenceStart.setValue(LocalDate.of(year, month, day));
                            conferenceStart.getEditor().setText(conferenceStart.getValue().toString());

                            break;
                        }
                        case "conferenceFinish": {
                            DatePicker conferenceFinish = (DatePicker) childs.get(i);
                            String[] date = conf.getFinish().split(". ");
                            System.out.println("finish " + date[0] + " " + date[1] + " " + date[2]);
                            int year = Integer.parseInt(date[0]);
                            int month = Integer.parseInt(date[1]);
                            int day = Integer.parseInt(date[2]);
                            conferenceFinish.setValue(LocalDate.of(year, month, day));
                            conferenceFinish.getEditor().setText(conferenceFinish.getValue().toString());
                            break;
                        }
                        case "conferenceCountry": {
                            TextField conferenceCountry = (TextField) childs.get(i);
                            conferenceCountry.setText(conf.getCountry());
                            break;
                        }
                        case "conferenceCity": {
                            TextField conferenceCity = (TextField) childs.get(i);
                            conferenceCity.setText(conf.getCity());
                            break;
                        }
                        case "conferencePages": {
                            TextField conferencePages = (TextField) childs.get(i);
                            //
                            conferencePages.setText(article.getPages());
                            break;
                        }
                        case "conferenceLink": {
                            TextField conferenceLink = (TextField) childs.get(i);
                            //
                            conferenceLink.setText(article.getLink());
                            break;
                        }

                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOther(Article article, GridPane grid) {
        try {
            ObservableList<Node> childs = grid.getChildren();

            Gson gson = new Gson();
            Other other = gson.fromJson(article.getTypeJson(), Other.class);
            for (int i = 0; i < childs.size(); i++) {
                if (childs.get(i).getId() != null) {
                    switch (childs.get(i).getId()) {
                        case "otherName": {
                            TextField otherName = (TextField) childs.get(i);
                            otherName.setText(other.getName());
                            break;
                        }
                        case "otherLink": {
                            TextField otherLink = (TextField) childs.get(i);
                            otherLink.setText(other.getLink());
                            break;
                        }

                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void menuCleanArticle() {
        clearFirstTab();
        clearSecondTab();
        clearThirdTab();
    }

    public void clearFirstTab() {
        ArrayList<String> employers = new ArrayList<>();
        for (Node child : ((GridPane) firstTabPage.getContent()).getChildren()) {
            if (child.getId() != null) {
                switch (child.getId()) {
                    case "nameField": {
                        TextField nameField = (TextField) child;
                        nameField.clear();
                        break;
                    }
                    case "authorsFields": {
                        VBox authorsField = (VBox) child;
                        ObservableList<Node> vboxChilds = authorsField.getChildren();

                        int len = vboxChilds.size();
                        vboxChilds.clear();
                        for (int i = 0; i < len; i++) {
                            resize_grid(-45, (GridPane) firstTabPage.getContent());
                        }

                        addAuthor(null, null, vboxChilds, authorsField, (GridPane) firstTabPage.getContent(), employers);


                        break;
                    }
                    case "yearField": {
                        TextField yearField = (TextField) child;
                        yearField.clear();
                        break;
                    }
                    case "countryField": {
                        TextField countryField = (TextField) child;
                        countryField.clear();

                        break;
                    }
                    case "cityField": {
                        TextField cityField = (TextField) child;
                        cityField.clear();
                        break;
                    }
                    case "publishingHouseField": {

                        TextField publishingHouseField = (TextField) child;
                        publishingHouseField.clear();
                        break;
                    }
                    case "topic": {
                        ComboBox topic = (ComboBox) (child);
                        topic.getSelectionModel().clearSelection();

                        break;
                    }
                    case "directions": {
                        CheckComboBox directions = (CheckComboBox) (child);
                        directions.getCheckModel().clearChecks();

                        break;
                    }
                    case "doi": {
                        TextField doi = (TextField) child;
                        doi.clear();
                        break;
                    }
                }
            }
        }
    }

    public void clearSecondTab() {
        ObservableList<Node> childs = ((GridPane) secondTabPage.getContent()).getChildren();

        for (int i = 1; i < childs.size(); i += 2) {
            CheckBox name = (CheckBox) childs.get(i);
            name.setSelected(false);

            TextField value = (TextField) childs.get(i + 1);
            value.clear();
            value.setDisable(true);
        }
    }

    public void clearThirdTab() {

        ThirdTabController controller = ttc;
        ObservableList<Node> childs = thirdTabPage.getChildren();
        HBox hbox = (HBox) childs.get(1);
        for (int i = 0; i < hbox.getChildren().size(); i++) {
            RadioButton radio = (RadioButton) hbox.getChildren().get(i);
            radio.setSelected(false);
        }

        GridPane grid = (GridPane) controller.getPlace("book");
        clearBook(grid);
        grid = (GridPane) controller.getPlace("journal");
        clearJournal(grid);
        grid = (GridPane) controller.getPlace("conference");
        clearConference(grid);
        grid = (GridPane) controller.getPlace("other");
        clearOther(grid);
        grid = (GridPane) controller.getPlace("findConference");
        clearFindConf(grid);
        grid = (GridPane) controller.getPlace("findJournal");
        clearFindJournal(grid);
        controller.clear();

    }

    public void clearBook(GridPane grid) {
        ObservableList<Node> childs = grid.getChildren();

        for (int i = 0; i < childs.size(); i++) {
            if (childs.get(i).getId() != null) {
                switch (childs.get(i).getId()) {
                    case "bookName": {
                        TextField bookName = (TextField) childs.get(i);
                        bookName.clear();
                        break;
                    }
                    case "bookPublishingHouse": {
                        TextField bookPublishingHouse = (TextField) childs.get(i);
                        bookPublishingHouse.clear();
                        break;
                    }
                    case "bookISBN": {
                        TextField bookISBN = (TextField) childs.get(i);
                        bookISBN.clear();
                        break;
                    }
                    case "bookPages": {
                        TextField bookPages = (TextField) childs.get(i);
                        bookPages.clear();
                        break;
                    }
                    case "bookLink": {
                        TextField bookLink = (TextField) childs.get(i);
                        bookLink.clear();
                        break;
                    }

                }
            }

        }
    }

    public void clearConference(GridPane grid) {
        ObservableList<Node> childs = grid.getChildren();

        for (int i = 0; i < childs.size(); i++) {
            if (childs.get(i).getId() != null) {
                switch (childs.get(i).getId()) {
                    case "conferenceName": {
                        TextField conferenceName = (TextField) childs.get(i);
                        conferenceName.clear();
                        break;
                    }
                    case "conferenceStart": {
                        DatePicker conferenceStart = (DatePicker) childs.get(i);
                        conferenceStart.getEditor().clear();

                        break;
                    }
                    case "conferenceFinish": {
                        DatePicker conferenceFinish = (DatePicker) childs.get(i);
                        conferenceFinish.getEditor().clear();
                        break;
                    }
                    case "conferenceCountry": {
                        TextField conferenceCountry = (TextField) childs.get(i);
                        conferenceCountry.clear();
                        break;
                    }
                    case "conferenceCity": {
                        TextField conferenceCity = (TextField) childs.get(i);
                        conferenceCity.clear();
                        break;
                    }
                    case "conferencePages": {
                        TextField conferencePages = (TextField) childs.get(i);
                        //
                        conferencePages.clear();
                        break;
                    }
                    case "conferenceLink": {
                        TextField conferenceLink = (TextField) childs.get(i);
                        //
                        conferenceLink.clear();
                        break;
                    }

                }

            }
        }
    }

    public void clearJournal(GridPane grid) {
        ObservableList<Node> childs = grid.getChildren();

        for (int i = 0; i < childs.size(); i++) {
            if (childs.get(i).getId() != null) {
                switch (childs.get(i).getId()) {
                    case "journalName": {
                        TextField journalName = (TextField) childs.get(i);
                        journalName.clear();
                        break;
                    }
                    case "journalFactor": {
                        TextField journalFactor = (TextField) childs.get(i);
                        journalFactor.clear();
                        break;
                    }
                    case "journalPages": {
                        TextField journalPages = (TextField) childs.get(i);
                        journalPages.clear();
                        break;
                    }
                    case "journalLink": {
                        TextField journalLink = (TextField) childs.get(i);
                        journalLink.clear();
                        break;
                    }
                    case "journalISSN": {
                        TextField journalISSN = (TextField) childs.get(i);
                        journalISSN.clear();
                        break;
                    }
                    case "journalVak": {
                        CheckBox journalVak = (CheckBox) childs.get(i);

                        journalVak.setSelected(false);
                        break;
                    }
                    case "journalRussian": {
                        CheckBox journalRussian = (CheckBox) childs.get(i);
                        journalRussian.setSelected(false);
                        break;
                    }
                }
            }

        }

    }

    public void clearOther(GridPane grid) {
        ObservableList<Node> childs = grid.getChildren();
        for (int i = 0; i < childs.size(); i++) {
            if (childs.get(i).getId() != null) {
                switch (childs.get(i).getId()) {
                    case "otherName": {
                        TextField otherName = (TextField) childs.get(i);
                        otherName.clear();
                        break;
                    }
                    case "otherLink": {
                        TextField otherLink = (TextField) childs.get(i);
                        otherLink.clear();
                        break;
                    }

                }
            }

        }
    }

    public void clearFindConf(GridPane grid) {
        ObservableList<Node> childs = grid.getChildren();
        for (int i = 0; i < childs.size(); i++) {
            if (childs.get(i).getId() != null) {
                switch (childs.get(i).getId()) {
                    case "finderName": {
                        TextField otherName = (TextField) childs.get(i);
                        otherName.clear();
                        break;
                    }
                    case "listConference": {
                        ListView otherLink = (ListView) childs.get(i);
                        otherLink.getItems().clear();
                        break;
                    }

                }
            }

        }
    }

    public void clearFindJournal(GridPane grid) {
        ObservableList<Node> childs = grid.getChildren();
        for (int i = 0; i < childs.size(); i++) {
            if (childs.get(i).getId() != null) {
                switch (childs.get(i).getId()) {
                    case "finderName": {
                        TextField otherName = (TextField) childs.get(i);
                        otherName.clear();
                        break;
                    }
                    case "listJournals": {
                        ListView otherLink = (ListView) childs.get(i);
                        otherLink.getItems().clear();
                        break;
                    }

                }
            }

        }
    }

    @FXML
    public void menuAbout() {
        if (showMessageConfirm("Вы нажали на кнопку 'О прогорамме'\nНе знаю помогло ли вам это или нет\nНо всё-таки помогло?")) {
            showMessage("Хорошо, спасибо\nЗаходите сюда почаще");
        } else {
            if (showMessageConfirm("Нет? Ну и ладно\nПодумайте ещё раз, может всё-таки да?")) {
                if (showMessageConfirm("Ты молодец!\nНо всё-таки не ведись на поводу у других.\n Нужно стоять на своём\nМожет теперь ты подумаешь сам и решишь?")) {
                    showMessage("А ты хорош!\nТак держать!\nУдачи тебе, счастья, счастья, здоровья");
                } else {
                    showMessage("И всё-таки ты не понял урока\nНо да ладно");
                }
            } else {
                showMessage("Ой ой, не особо то и хотелось\nПока.");
            }
        }
    }

    @FXML
    public void menuHelp() {
    }

    @FXML
    public void toStartWindow() {
        borderPane.setCenter(startWindow);
        nextButton.setVisible(false);
        saveMenu.setVisible(false);
    }

    @FXML
    public void buttonEditClick() {
        typeWork = "article_edit";
        menuCleanArticle();
        menuEditArticle();
    }

    @FXML
    public void buttonAddClick() {
        typeWork = "article_add";
        menuCleanArticle();
        borderPane.setCenter(tabPane);
        nextButton.setVisible(true);
        saveMenu.setVisible(true);
    }

    @FXML
    public void menuEditArticle() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/editArticle/find_article_for_edit.fxml"));
            Parent root = loader.load();
            FindArticleForEdit controller = loader.getController();
            controller.setMain(this);

            borderPane.setCenter(root);
            nextButton.setVisible(false);
            saveMenu.setVisible(false);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void edit(Article article) {
/*        String editArticle = "fdssd";

        Gson gson = new Gson();
        Article fromJson = gson.fromJson(editArticle, Article.class);
*/
        setValue(article);
        codeEdit = article.getCode();
        typeWork = "article_edit";
        borderPane.setCenter(tabPane);
        nextButton.setVisible(true);
        saveMenu.setVisible(true);
    }


    public boolean saveArticle() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранение публикации в файл");
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try (PrintWriter printer = new PrintWriter(new FileWriter(file))) {
                Gson gson = new Gson();

                printer.println(gson.toJson(article, Article.class));
                printer.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            return false;
        }

        return true;
    }

    public boolean cycleSend(int cycle) {
        try {

            for (int i = 0; i < cycle; i++) {

                if (sendToServer()) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }


    public boolean sendToServer() {

        try {
            if (typeWork.compareTo("article_edit") == 0) {
                article.setCode(codeEdit);
            }
            System.out.println(article.toJSON());
            String query = "{";

            query += "\"type\":\"" + typeWork + "\",";

            query += "\"data\":" + article.toJSON() + ",";

            query += "}";

            Client client = new Client();
            client.sendMessage(query);
            String answer = client.getMessage();
            if (typeWork.compareTo("article_edit") == 0) {
                if (answer.compareTo("1") == 0) {
                    showMessage("Успешно изменено!");
                    menuCleanArticle();
                    toStartWindow();
                } else {
                    showMessage("Ошибка редактирования");
                }
            } else {
                if (answer.compareTo("1") == 0) {
                    showMessage("Успешно добавлено!");
                    menuCleanArticle();
                    toStartWindow();
                } else {
                    showMessage("Ошибка добавления");
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @FXML
    public void changeSettingServer() {
        old = borderPane.getCenter();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/client_change.fxml"));

            Node n = loader.load();

            ClientSetting contr = loader.getController();
            contr.setMain(this);

            borderPane.setCenter(n);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void change() {
        borderPane.setCenter(old);
    }

}
