package ru.miet.orgact.handlers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class ISBNListener implements ChangeListener<String> {
    private TextField field;

    public ISBNListener(TextField text) {
        field = text;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, java.lang.String oldValue, java.lang.String newValue) {
        java.lang.String numberMatcher = "\\d{3}-\\d{1}-\\d{5}-\\d{3}-\\d{1}";
        if (!newValue.isEmpty()) {
            if (!newValue.matches(numberMatcher)) {
                field.setText(oldValue);
            }
        }
    }
}