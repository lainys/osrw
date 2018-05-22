package ru.miet.orgact.controllers.edit;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ru.miet.orgact.Article;
import ru.miet.orgact.Client;
import ru.miet.orgact.controllers.MainController;

public class FindArticleForEdit {

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
        //ArrayList<Article> publications = Client.getListOfPublications();
        ObservableList<Article> list = FXCollections.observableArrayList();
        list.addAll(Client.getArticles());

        //установить в таблицу все значения
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        authorsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.join(",", cellData.getValue().getAuthors())));
        countryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCountry()));
        cityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCity()));
        yearColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getYear().toString()));

        selectEditTable.setItems(list);
    }

    public void setMain(MainController contrl) {
        mainController = contrl;
    }

    public void selectForEdit() {

        mainController.edit(selectEditTable.getSelectionModel().getSelectedItem());
    }
}
