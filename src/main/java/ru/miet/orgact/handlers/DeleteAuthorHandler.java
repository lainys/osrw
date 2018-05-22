package ru.miet.orgact.handlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class DeleteAuthorHandler implements EventHandler<ActionEvent> {


    private VBox authorsFields;
    private GridPane grid;

    public DeleteAuthorHandler(VBox parent, GridPane grid) {
        this.authorsFields = parent;
        this.grid = grid;

    }


    @Override
    public void handle(ActionEvent event) {
        authorsFields.getChildren().remove(((Node) event.getSource()).getParent());
        resize_grid(-45);
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
}
