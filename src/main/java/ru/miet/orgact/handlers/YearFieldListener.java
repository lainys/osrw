package ru.miet.orgact.handlers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

import java.util.Calendar;

public class YearFieldListener implements ChangeListener<String> {

    private TextField field;

    public YearFieldListener(TextField text) {
        field = text;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        String numberMatcher = "^\\d{1,4}$";
        if (!newValue.isEmpty()) {
            if (!newValue.matches(numberMatcher)) {
                field.setText(oldValue);
            } else {
                int year = Integer.parseInt(newValue);
                if (year > Calendar.getInstance().get(Calendar.YEAR)) {
                    year = 2018;
                }
                field.setText(String.valueOf(year));
            }
        }
    }
}
