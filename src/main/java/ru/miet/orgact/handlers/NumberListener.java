package ru.miet.orgact.handlers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class NumberListener implements ChangeListener<String> {

    private TextField field;

    public NumberListener(TextField text) {
        field = text;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        String numberMatcher = "^\\d{0,6}$";
        if (!newValue.isEmpty()) {
            if (!newValue.matches(numberMatcher)) {
                field.setText(oldValue);
            }
        }
    }
}
