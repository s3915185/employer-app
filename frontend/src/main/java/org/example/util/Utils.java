package org.example.util;

import com.sun.org.apache.xerces.internal.util.Status;
import javafx.animation.FadeTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.apache.commons.lang3.StringUtils;
import org.example.EmployerNoSalaries;
import org.example.Salary;
import org.example.constant.BundleKey;
import org.example.constant.Constant;
import org.example.factory.ObservableResourceFactory;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.formatDate;

public class Utils {
    public static <T> List<T> iteratorToList(Iterator<T> iterator) {
        List<T> list = new ArrayList<>();

        while (iterator.hasNext()) {
            T element = iterator.next();
            list.add(element);
        }
        return list;
    }
    public static Optional<LocalDate> toLocalDate(String date, String pattern) {
        try {
            if (date == null) {
                return Optional.empty();
            }
            if (date.equals("")) {
                return Optional.empty();
            }
            final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
            return Optional.of(LocalDate.parse(date, dtf));
        } catch (Exception e) {
            throw new RuntimeException(Constant.MESSAGE_DATE_FORMAT_ERROR);
        }
    }

    public static String formatNumber(int number, int length) {
        return StringUtils.leftPad(String.valueOf(number), length, '0');
    }

    public static String getFileExtension(String fileDirectory) {
        if (fileDirectory == null || fileDirectory.lastIndexOf(".") == -1) {throw new RuntimeException(String.valueOf(Status.NOT_SUPPORTED));
        }
        return fileDirectory.substring(fileDirectory.lastIndexOf("."));
    }
    public static void setupDateColumnEmployer(TableColumn<EmployerNoSalaries, String> column, Function<EmployerNoSalaries, String> dateExtractor) {
        column.setCellValueFactory(cellData -> {
            String date = dateExtractor.apply(cellData.getValue());
            return new SimpleObjectProperty<>(date != null ? formatDate(date, "dd.MM.yyyy") : "");
        });
    }
    public static void setupDateColumnSalary(TableColumn<Salary, String> column, Function<Salary, String> dateExtractor) {
        column.setCellValueFactory(cellData -> {
            String date = dateExtractor.apply(cellData.getValue());
            return new SimpleObjectProperty<>(date != null ? formatDate(date, "dd.MM.yyyy") : "");
        });
    }
    public static StringConverter<LocalDate> getStringConverter(ObservableResourceFactory.Language language) {
        return new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", language.getLocale());
            @Override
            public String toString(LocalDate object) {
                if (object != null) {
                    return dateFormatter.format(object);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
    }

    public static TextFormatter<Integer> getIntegerTextFormatter() {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change; // Accept the change
            }
            return null; // Reject the change
        };
        StringConverter<Integer> converter = new IntegerStringConverter();

        // Create a TextFormatter with the converter and filter
        TextFormatter<Integer> textFormatter = new TextFormatter<>(converter, 0, filter);
        return textFormatter;
    }

    public static void setNumberFormat(TableColumn<Salary, Float> column) {
        column.setCellFactory(new Callback<TableColumn<Salary, Float>, TableCell<Salary, Float>>() {
            @Override
            public TableCell<Salary, Float> call(TableColumn<Salary, Float> param) {
                return new TableCell<Salary, Float>() {
                    @Override
                    protected void updateItem(Float item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            String formattedValue = String.format(Locale.US, "%.2f", item);
                            setText(formattedValue);
                        }
                    }
                };
            }
        });
    }

    public static String convertFrenchDateToStandardFormat(String frenchDate) {
        try {
            SimpleDateFormat frenchFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.FRENCH);
            SimpleDateFormat standardFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date date = frenchFormat.parse(frenchDate);
            return standardFormat.format(date);
        } catch (ParseException e) {
            throw new RuntimeException(Constant.MESSAGE_DATE_FORMAT_ERROR);
        }
    }


    public static String formatDateString(String inputDate) {
        if (!inputDate.contains("/")) { throw new RuntimeException(Constant.MESSAGE_DATE_FORMAT_ERROR); }
        // Split the input date string by '/'
        String[] dateComponents = inputDate.split("/");
        if (dateComponents.length != 3) { throw new RuntimeException(Constant.MESSAGE_DATE_FORMAT_ERROR); }

        // Ensure day and month have leading zeros
        String day = addLeadingZero(dateComponents[0]);
        String month = addLeadingZero(dateComponents[1]);
        String year = dateComponents[2];
        // Assemble the formatted date string
        return year + "-" + day + "-" + month;
    }
    private static String addLeadingZero(String value) {
        int intValue = Integer.parseInt(value);
        return (intValue < 10) ? "0" + value : value;
    }

    public static void applyEaseInAnimation(Node node) {
        node.setOpacity(0);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.3), node);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    public static boolean isValidFormat(String input) {
        // Define the pattern for the desired format
        String pattern = "756\\.\\d{4}\\.\\d{4}\\.\\d{2}";

        // Create a Pattern object
        Pattern regex = Pattern.compile(pattern);

        // Create a Matcher object
        Matcher matcher = regex.matcher(input);
        // Check if the input string matches the pattern
        return matcher.matches();
    }

    public static void validateDateFormat(String dateString, String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDateTime.parse(dateString, formatter);
        } catch (Exception e) {
            throw new RuntimeException(Constant.MESSAGE_DATE_FORMAT_ERROR);
        }
    }

    public static String formatText(String text, String oldText) {
        String upperCaseText = text.toUpperCase();
        String upperCasePrefix1 = "CHE";
        String upperCasePrefix2 = "ADM";

        if (upperCaseText.startsWith(upperCasePrefix1) || upperCaseText.startsWith(upperCasePrefix2)) {
            if (upperCaseText.length() == 3) {
                upperCaseText += '-';
            }
            if (upperCaseText.length() == 7 || upperCaseText.length() == 11) {
                upperCaseText += '.';
            }
        }
        return upperCaseText;
    }

    public static void addListenerForIDE(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 15) {
                textField.setText(oldValue);
            } else {
                if (oldValue.length() < newValue.length()) {
                    newValue = Utils.formatText(newValue, oldValue);
                }
                textField.setText(newValue);
            }
        });
    }
    public static void addDateRestrictionForAffiliation(DatePicker dateAffiliation, DatePicker dateRadiation) {
        dateAffiliation.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {updateDateRestriction(dateAffiliation, newValue, true);}
            else { dateAffiliation.setDayCellFactory(picker -> new DateCell());}
            LocalDate today = LocalDate.now();
            if (newValue != null && newValue.isAfter(today)) {
                dateAffiliation.setValue(today);
            }
        });
    }

    public static void updateDateRestriction(DatePicker datePicker, java.time.LocalDate selectedDate, boolean disableBeforeDate) {
        if (disableBeforeDate) {
            datePicker.setDayCellFactory(picker -> new DateCell() {
                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    setDisable(empty || item.isBefore(selectedDate));
                }
            });
            if (datePicker.getValue() != null) {
                if (datePicker.getValue().isBefore(selectedDate)) {
                    datePicker.setValue(selectedDate);
                }
            }
        } else {
            datePicker.setDayCellFactory(picker -> new DateCell() {
                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    setDisable(empty || item.isAfter(selectedDate));
                }
            });
            if (datePicker.getValue() != null) {
                if (datePicker.getValue().isAfter(selectedDate)) {
                    datePicker.setValue(selectedDate);
                }
            }
        }
    }

    public static String formatDateInput(String text) {
        text = text.replaceAll("[^\\d.]", "");
        if (text.length() == 2 || text.length() == 5) {
            text += '.';
        }
        return text;
    }

    public static void addValidationListener(TextField textField) {
        if (textField.getText() == null || textField.getText().trim().isEmpty()) {
            textField.setStyle("-fx-border-color: red;");
        }

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.trim().isEmpty()) {
                textField.setStyle("-fx-border-color: red;");
            } else {
                textField.setStyle(""); // reset border color
            }
        });
    }

    public static void addValidationListener(DatePicker datePicker) {
        if (datePicker.getEditor().getText() == null || datePicker.getEditor().getText().trim().isEmpty()) {
            datePicker.setStyle("-fx-border-color: red;");
        }

        datePicker.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.trim().isEmpty()) {
                datePicker.setStyle("-fx-border-color: red;");
            } else {
                datePicker.setStyle(""); // reset border color
            }
        });
    }

    public static void addValidationListener(ChoiceBox<?> choiceBox) {
        if (choiceBox.getValue() == null || choiceBox.getValue().toString().trim().isEmpty()) {
            choiceBox.setStyle("-fx-border-color: red;");
        }

        choiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.toString().trim().isEmpty()) {
                choiceBox.setStyle("-fx-border-color: red;");
            } else {
                choiceBox.setStyle(""); // reset border color
            }
        });
    }
    public static boolean isNumeric(String str) {
        // Check if the given string is numeric
        return str.matches("\\d+");
    }

    public static void compareToAppend(StringBuilder emptyFields, String firstField, Text label) {
        if (firstField == null || firstField.trim().isEmpty()) {
            emptyFields.append(label.getText()).append(", ");
        }
    }
    public static void updatePensionFundChoiceBox(ChoiceBox<String> typeChoiceBox, ObservableResourceFactory observableResourceFactory) {
        int selectedIndex = typeChoiceBox.getSelectionModel().getSelectedIndex();
        typeChoiceBox.getItems().clear();;
        typeChoiceBox.getItems().addAll(FXCollections.observableArrayList(observableResourceFactory
                .getStringBinding(BundleKey.PENSION_FUND_CHOICES).get().split(",")));
        if (selectedIndex >= 0) {
            typeChoiceBox.setValue(typeChoiceBox.getItems().get(selectedIndex));
        }
    }
    public static void setDefaultPensionFundChoiceBox(ChoiceBox<String> typeChoiceBox, ObservableResourceFactory observableResourceFactory) {
        typeChoiceBox.itemsProperty()
                .bind(Bindings.createObjectBinding(() -> {
                    return FXCollections.observableArrayList(observableResourceFactory
                            .getStringBinding(BundleKey.PENSION_FUND_CHOICES).get().split(","));
                }));
    }

    public static void setChoiceBoxForType(ChoiceBox<String> typeChoiceBox, ObservableResourceFactory observableResourceFactory) {
        Utils.setDefaultPensionFundChoiceBox(typeChoiceBox, observableResourceFactory);
        observableResourceFactory.addLanguageChangeListener(new ObservableResourceFactory.LanguageChangeListener() {
            @Override
            public void onLanguageChange(ObservableResourceFactory.Language newLanguage) {
                Utils.updatePensionFundChoiceBox(typeChoiceBox, observableResourceFactory);
            }
        });
    }
    public static String format(double num) {
        DecimalFormatSymbols decimalSymbols = DecimalFormatSymbols.getInstance();
        decimalSymbols.setDecimalSeparator('.');
        return new DecimalFormat("0.00", decimalSymbols).format(num);
    }

    public static void applyDateInputHandler(DatePicker datePicker) {
        datePicker.getEditor().textProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue.length() > 10){
                datePicker.getEditor().setText(oldValue);
            }
            else {
                if (oldValue.length() < newValue.length()) {
                    newValue = Utils.formatDateInput(newValue);
                }
                datePicker.getEditor().setText(newValue);
            }
        }));
    }

    public static boolean validateNumberIDE(String text) {
        if (text.startsWith("CHE-") || text.startsWith("ADM-")) {
            if (text.length() == 4) {
                return false;
            }
            text = text.split("-")[1];
            String[] parts = text.split("\\.");
            if (parts.length == 3 && Utils.isNumeric(parts[1]) && Utils.isNumeric(parts[2]) && Utils.isNumeric(parts[0]) &&
                    parts[0].length() == 3 && parts[1].length() == 3 && parts[2].length() == 3) {
                return true;
            }
        }
        return false;
    }

}
