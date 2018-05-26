package ru.miet.orgact.controllers.edit;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import ru.miet.orgact.Article;
import ru.miet.orgact.Client;
import ru.miet.orgact.controllers.MainController;

public class FindArticleForEdit {

    @FXML
    private TextField cityField;

    @FXML
    private TextField nameField;
    @FXML
    private TextField yearField;
    @FXML
    private TextField authorField;

    private MainController mainController;
    @FXML
    private TableColumn<Article, String> nameColumn;
    @FXML
    private TableColumn<Article, String> authorsColumn;
    @FXML
    private TableColumn<Article, String> countryColumn;
    @FXML
    private TableColumn<Article, String> cityColumn;
    @FXML
    private TableColumn<Article, String> yearColumn;

    @FXML
    private TableView<Article> selectEditTable;

    @FXML
    public void initialize() {
       /* //ArrayList<Article> publications = Client.getListOfPublications();
        ObservableList<Article> list = FXCollections.observableArrayList();
        list.addAll(Client.getArticles());

        //установить в таблицу все значения
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        authorsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.join(",", cellData.getValue().getAuthors())));
        countryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCountry()));
        cityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCity()));
        yearColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getYear().toString()));

        selectEditTable.setItems(list);
        */
    }

    public void setMain(MainController contrl) {
        mainController = contrl;
    }

    public void selectForEdit() {

        mainController.edit(selectEditTable.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void search() {
        try {
            ObservableList<Article> list = FXCollections.observableArrayList();
            list.addAll(Client.getArticles());

            //установить в таблицу все значения
            nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
            authorsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.join(",", cellData.getValue().getAuthors())));
            countryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCountry()));
            cityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCity()));
            yearColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getYear().toString()));

            String name = nameField.getText().toLowerCase();
            String author = authorField.getText().toLowerCase();
            String year = yearField.getText().toLowerCase();
            String city = cityField.getText().toLowerCase();

            ObservableList<Article> searchList = FXCollections.observableArrayList();


            for (Article c : list) {
                if (c.getName().toLowerCase().contains(name) && c.getYear().toString().toLowerCase().contains(year) && c.getCity().toLowerCase().contains(city) && String.join(" ", c.getAuthors()).toLowerCase().contains(author)) {
                    searchList.add(c);
                }
            }

            selectEditTable.getItems().clear();
            selectEditTable.setItems(searchList);
        } catch (Exception e) {
            mainController.showMessage("Нет соединения с сервером! Попробуйте позже.");
        }
    }
}
