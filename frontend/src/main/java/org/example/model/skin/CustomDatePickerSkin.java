package org.example.model.skin;

import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.time.LocalDate;
import java.util.Locale;

public class CustomDatePickerSkin extends SkinBase<DatePicker> {
    private HBox content;

    public CustomDatePickerSkin(DatePicker datePicker, Locale locale) {
        super(datePicker);

        content = new HBox(10);
        content.getStyleClass().add("date-picker");

        TextField textField = new TextField();
        textField.setEditable(false);

        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                textField.setText(newValue.format(java.time.format.DateTimeFormatter.ofPattern("dd MMMM yyyy", locale)));
            } else {
                textField.clear();
            }
        });

        content.getChildren().addAll(datePicker, textField);

        getChildren().add(content);
    }

    @Override
    protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
        super.layoutChildren(contentX, contentY, contentWidth, contentHeight);

        double pickerWidth = contentWidth - content.getChildren().get(1).prefWidth(-1);
        content.getChildren().get(0).resizeRelocate(contentX, contentY, pickerWidth, contentHeight);
        content.getChildren().get(1).resizeRelocate(contentX + pickerWidth, contentY, content.getChildren().get(1).prefWidth(-1), contentHeight);
    }

    @Override
    protected double computeMinWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return super.computeMinWidth(height, topInset, rightInset, bottomInset, leftInset) + content.getChildren().get(1).minWidth(-1);
    }

    @Override
    protected double computePrefWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return super.computePrefWidth(height, topInset, rightInset, bottomInset, leftInset) + content.getChildren().get(1).prefWidth(-1);
    }

    @Override
    protected double computeMaxWidth(double height, double topInset, double rightInset, double bottomInset, double leftInset) {
        return super.computeMaxWidth(height, topInset, rightInset, bottomInset, leftInset) + content.getChildren().get(1).maxWidth(-1);
    }

    private static class CustomDateCell extends DateCell {
        private Locale locale;

        public CustomDateCell(Locale locale) {
            this.locale = locale;
        }

        @Override
        public void updateItem(LocalDate item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
            } else {
                setText(item.format(java.time.format.DateTimeFormatter.ofPattern("d", locale)));
            }
        }
    }
}