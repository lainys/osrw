package ru.miet.orgact.handlers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class NegativeNumberListener implements ChangeListener<String> {

    private TextField field;

    public NegativeNumberListener(TextField text) {
        field = text;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        String numberMatcher = "^[\\d-]+$";
        if (!newValue.isEmpty()) {
            if (!newValue.matches(numberMatcher)) {
                field.setText(oldValue);
            }
        }
    }
}
