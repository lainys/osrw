package ru.miet.orgact.handlers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class FIOListener implements ChangeListener<String> {
    private TextField field;

    public FIOListener(TextField text) {
        field = text;
    }

    @Override
    public void changed(ObservableValue<? extends java.lang.String> observable, java.lang.String oldValue, java.lang.String newValue) {
        java.lang.String numberMatcher = "[a-zа-я]*\\s+[a-zа-я]{1}.[a-zа-я]{1}.";
        if (!newValue.isEmpty()) {
            if (!newValue.matches(numberMatcher)) {
                field.setText(oldValue);
            }
        }
    }
}

