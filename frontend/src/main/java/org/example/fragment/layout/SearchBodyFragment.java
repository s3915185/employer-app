package org.example.fragment.layout;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.example.*;
import org.example.model.alert.ConfirmationAlert;
import org.example.config.BasicConfig;
import org.example.constant.BundleKey;
import org.example.constant.Constant;
import org.example.factory.ObservableResourceFactory;
import org.example.mapper.EmployerClientMapper;
import org.example.model.message.MainMessage;
import org.example.model.object.EmployerNoSalariesObject;
import org.example.model.pagination.PaginationObject;
import org.example.model.view.SearchViewObject;
import org.example.service.employerService.EmployerClientService;
import org.example.model.enums.PensionFund;
import org.example.util.Utils;
import org.example.model.enums.MessageView;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.fragment.Fragment;

import org.jacpfx.api.fragment.Scope;
import org.jacpfx.rcp.components.managedFragment.ManagedFragment;
import org.jacpfx.rcp.context.Context;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.MinguoChronology;
import java.util.*;
import java.util.logging.Logger;

@Fragment(
        id = BasicConfig.SEARCH_TAB,
        viewLocation = "/fxml/fragment/search-view.fxml",
        resourceBundleLocation = "bundles.languageBundle",
        scope = Scope.PROTOTYPE
)
public class SearchBodyFragment extends ManagedFragment {
    @FXML
    private GridPane gridPane;
    @FXML
    private VBox mainLayout;
    @FXML
    private ChoiceBox<String> typeChoiceBox;
    @FXML
    private TextField numberField;
    @FXML
    private DatePicker dateAffiliationField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField numberIDEField;
    @FXML
    private DatePicker dateRadiationField;
    @FXML
    private Label searchButton;
    @FXML
    private Label resetSearchButton;
    @FXML
    private Label addButton;
    @FXML
    private TableView<EmployerNoSalaries> searchTableView;
    @FXML
    private TableColumn<EmployerNoSalaries, String> typeColumn;
    @FXML
    private TableColumn<EmployerNoSalaries, String> numberColumn;
    @FXML
    private TableColumn<EmployerNoSalaries, String> numberIDEColumn;
    @FXML
    private TableColumn<EmployerNoSalaries, String> nameColumn;
    @FXML
    private TableColumn<EmployerNoSalaries, String> dateAffiliationColumn;
    @FXML
    private TableColumn<EmployerNoSalaries, String> dateRadiationColumn;
    @FXML
    private TableView<EmployerNoSalaries> fixedColumnTableView;
    @FXML
    private TableColumn<EmployerNoSalaries, Void> functionColumn;

    @FXML
    private Pagination pagination;

    @FXML
    private Label filterLabel;
    @FXML
    private Text typeLabel;
    @FXML
    private Text numberLabel;
    @FXML
    private Text affiliationLabel;
    @FXML
    private Text nameLabel;
    @FXML
    private Text numberIDELabel;
    @FXML
    private Text radiationLabel;
    @Resource
    private Context context;
    @Autowired
    private EmployerClientService employerClientService;
    @Autowired
    private EmployerClientMapper employerClientMapper;
    private final int dataRow = 7;
    private int dataCount = 10;
    private int pageLength = 1;
    private List<EmployerNoSalaries> employerNoSalaries = new ArrayList<>();
    ObservableList<EmployerNoSalaries> data;
    @Autowired
    private ObservableResourceFactory observableResourceFactory;
    private Logger log = Logger.getLogger(SearchBodyFragment.class.getName());

    public void init(String method, Object object) {
        Utils.applyEaseInAnimation(gridPane);
        localeBinding();
        tableInit();
        inputConstraint();
        displaySearchViewFromCallBack(object);
        inputOnChangeHandler();
        buttonEventHandler();
    }

    private void displaySearchViewFromCallBack(Object object) {
        if (object != null) {
            SearchViewObject searchViewObject = (SearchViewObject) object;
            displayViewAfterCallBack(searchViewObject);
            if (searchViewObject.getMethod().equals(Constant.MESSAGE_RELOAD)) {
                reload(0, observableResourceFactory.getLanguage().getLocale());
                context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".")
                        .concat(BasicConfig.SEARCH_STATEFUL_CALLBACK), Constant.MESSAGE_NO_RELOAD_REQUIRED);
            }
        }
    }

    private void buttonEventHandler() {
        gridPane.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                setKeyEventHandler(newScene);
                context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".")
                        .concat(BasicConfig.TAB_COLUMN), Constant.MESSAGE_SEARCH_BODY_ENABLED);
            }
        });
        searchButton.setOnMouseClicked(this::searchButtonClicked);
        resetSearchButton.setOnMouseClicked(this::resetSearchButtonClicked);
        addButton.setOnMouseClicked(this::addButtonClicked);
        pagination.currentPageIndexProperty().addListener(((observable, oldValue, newValue) -> {
            sendPaginationToCallBack(newValue.intValue(), pageLength);
        }));
    }

    private void setKeyEventHandler(Scene newScene) {
        newScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (event.isAltDown()) {
                    resetSearchButtonClicked(null);
                } else if (event.isControlDown()) {
                    addButtonClicked(null);
                } else {
                    searchButtonClicked(null);
                }
                event.consume();
            } else if (event.getCode() == KeyCode.RIGHT) {
                handleRightArrowKey();
                event.consume();
            } else if (event.getCode() == KeyCode.LEFT) {
                handleLeftArrowKey();
                event.consume();
            }
        });
    }

    private void handleRightArrowKey() {
        int currentIndex = pagination.getCurrentPageIndex();
        int nextPageIndex = (currentIndex + 1) % pagination.getPageCount(); // Circular navigation
        pagination.setCurrentPageIndex(nextPageIndex);
    }

    private void handleLeftArrowKey() {
        int currentIndex = pagination.getCurrentPageIndex();
        int previousPageIndex = (currentIndex - 1 + pagination.getPageCount()) % pagination.getPageCount(); // Circular navigation
        pagination.setCurrentPageIndex(previousPageIndex);
    }

    public void searchButtonClicked(MouseEvent mouseEvent) {
        reload(0, observableResourceFactory.getLanguage().getLocale());
    }

    private void reload(int page, Locale locale) {
        dataCount = (int) employerClientService
                .getEmployersByCriteriaCount(PensionFund.getDatabaseVersion(typeChoiceBox.getValue(), locale),
                        numberField.getText(),
                        nameField.getText(),
                        numberIDEField.getText(),
                        dateAffiliationField.getValue(),
                        dateRadiationField.getValue()
                ).getNumber();
        pageLength = (int) Math.ceil((double) dataCount / dataRow);
        if (dataCount > 0) {
            pagination.setPageCount(pageLength);
        } else {
            pagination.setPageCount(1);
        }
        pagination.setCurrentPageIndex(0);
        sendPaginationToCallBack(page, pageLength);
    }

    private void sendPaginationToCallBack(int page, int pageLength) {
        getAndSetData(page);
        PaginationObject paginationObject = PaginationObject.builder().page(page).pageLength(pageLength).build();
        context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".")
                .concat(BasicConfig.SEARCH_STATEFUL_CALLBACK), paginationObject);
    }

    public void resetSearchButtonClicked(MouseEvent mouseEvent) {
        typeChoiceBox.getSelectionModel().selectFirst();
        numberField.setText("");
        dateAffiliationField.setValue(null);
        nameField.setText("");
        numberIDEField.setText("");
        dateRadiationField.setValue(null);
        pagination.setPageCount(1);
        pagination.setCurrentPageIndex(0);
        if (data != null) {
            data.clear();
            context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".")
                    .concat(BasicConfig.SEARCH_STATEFUL_CALLBACK), Constant.MESSAGE_NO_DATA);
        }
        context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".").concat(BasicConfig.TAB_COLUMN), Constant.MESSAGE_OFF_DETAIL_BUTTON);
        Utils.applyEaseInAnimation(searchTableView);
        Utils.applyEaseInAnimation(fixedColumnTableView);
    }

    public void addButtonClicked(MouseEvent mouseEvent) {
        context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".").concat(BasicConfig.TAB_COLUMN), Constant.MESSAGE_ON_DETAIL_BUTTON);
        context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".").concat(BasicConfig.HEADER), Constant.MESSAGE_SHOW_DETAIL_LABEL);
        context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".")
                .concat(BasicConfig.BODY_TAB), Constant.MESSAGE_ADD_NEW);
    }

    protected void tableInit() {
        fixedColumnTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        typeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(PensionFund
                        .getDisplayVersionResourceBundle(cellData.getValue().getType(),
                                observableResourceFactory.getLanguage().getLocale())));

        numberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumber()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        numberIDEColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumberIDE()));
        Utils.setupDateColumnEmployer(dateAffiliationColumn, EmployerNoSalaries::getDateAffiliation);
        Utils.setupDateColumnEmployer(dateRadiationColumn, EmployerNoSalaries::getDateRadiation);
        functionColumn.setCellFactory(param -> createButtonCell());
    }

    private void sendMessageToMain(String message) {
        MainMessage mainMessage = new MainMessage();
        mainMessage.setMessage(message);
        mainMessage.setView(MessageView.UPPER_SMALL_MESSAGE);
        context.send(BasicConfig.PERSPECTIVE_MAIN, mainMessage);
    }

    private void getAndSetData(int page) {
        employerNoSalaries = employerClientService
                .getEmployersByCriteria(PensionFund.getDatabaseVersion(typeChoiceBox.getValue(), observableResourceFactory.getLanguage().getLocale()),
                        numberField.getText(),
                        nameField.getText(),
                        numberIDEField.getText(),
                        dateAffiliationField.getValue(),
                        dateRadiationField.getValue(),
                        dataRow, pagination.getCurrentPageIndex() * dataRow);
        displayEmployerList(employerNoSalaries);
    }

    private void displayEmployerList(List<EmployerNoSalaries> employerNoSalaries) {
        data = FXCollections.observableArrayList(employerNoSalaries);
        storeDataToCallBack();
        searchTableView.getItems().clear();
        fixedColumnTableView.getItems().clear();
        searchTableView.setItems(data);
        fixedColumnTableView.setItems(data);
        Utils.applyEaseInAnimation(searchTableView);
        Utils.applyEaseInAnimation(fixedColumnTableView);
    }

    private void displayViewAfterCallBack(SearchViewObject searchViewObject) {
        Optional.ofNullable(searchViewObject.getEmployerSearchCriteria()).ifPresent(searchCriteria -> {
            Optional.of(searchCriteria.getType()).ifPresent(typeChoiceBox::setValue);
            Optional.of(searchCriteria.getNumber()).ifPresent(numberField::setText);
            Optional.of(searchCriteria.getName()).ifPresent(nameField::setText);
            Optional.of(searchCriteria.getNumberIDE()).ifPresent(numberIDEField::setText);
            Optional.of(searchCriteria.getDateAffiliation())
                    .filter(value -> !value.isEmpty())
                    .ifPresent(value -> dateAffiliationField.setValue(LocalDate.parse(value)));
            Optional.of(searchCriteria.getDateRadiation())
                    .filter(value -> !value.isEmpty())
                    .ifPresent(value -> dateRadiationField.setValue(LocalDate.parse(value)));
        });
        Optional.ofNullable(searchViewObject.getEmployerNoSalariesList()).flatMap(Optional::of).ifPresent(lis -> {
            employerNoSalaries.addAll(lis);
            data = FXCollections.observableArrayList(employerNoSalaries);
            searchTableView.setItems(data);
            fixedColumnTableView.setItems(data);
        });
        Optional.ofNullable(searchViewObject.getPaginationObject()).ifPresent(number -> {
            pagination.setPageCount(number.getPageLength().intValue());
            pagination.setCurrentPageIndex(number.getPage().intValue());
            pageLength = number.getPageLength().intValue();
        });
    }

    private TableCell<EmployerNoSalaries, Void> createButtonCell() {
        return new TableCell<EmployerNoSalaries, Void>() {
            private final VBox vbox = new VBox();
            private final HBox hbox = new HBox();
            private final Button detailsTableButton = new Button("");
            private final Button deleteTableButton = new Button("");

            {
                hbox.getChildren().addAll(detailsTableButton, deleteTableButton);
                hbox.setSpacing(5);
                hbox.setAlignment(Pos.CENTER);
                vbox.getChildren().add(hbox);
                vbox.setAlignment(Pos.CENTER);
                detailsTableButton.setCursor(Cursor.HAND);
                deleteTableButton.setCursor(Cursor.HAND);
                detailsTableButton.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.SEARCH_TABLE_DETAIL_BUTTON));
                deleteTableButton.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.SEARCH_TABLE_DELETE_BUTTON));
                detailsTableButton.setOnAction(event -> {
                    EmployerNoSalaries employer = getTableView().getItems().get(getIndex());
//                    EmployerNoSalaries anotherEmployer = employerClientMapper.toEmployerNoSalariesDatabaseDefined(employer, observableResourceFactory.getLanguage().getLocale());
                    context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".").concat(BasicConfig.TAB_COLUMN), Constant.MESSAGE_ON_DETAIL_BUTTON);
                    context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".").concat(BasicConfig.BODY_TAB), employer);
                });
                deleteTableButton.setOnAction(event -> {
                    EmployerNoSalaries employer = getTableView().getItems().get(getIndex());
                    ConfirmationAlert.ConfirmationAlertBuilder confirmationAlertBuilder = ConfirmationAlert.builder();
                    ConfirmationAlert confirmationAlert = confirmationAlertBuilder.build();
                    ObservableResourceFactory.Language language = observableResourceFactory.getLanguage();
                    boolean confirmed = confirmationAlert.showDeleteConfirmationDialog(language);
                    if (confirmed) {
                        DeleteEmployerResponse employerResponse = employerClientService.deleteEmployerById(employer.getId());
                        if (employerResponse.getSuccess()) {
                            data.remove(employer);
                            reload(pagination.getCurrentPageIndex(), observableResourceFactory.getLanguage().getLocale());
//                             getAndSetData(pagination.getCurrentPageIndex());
                            context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".").concat(BasicConfig.DETAIL_STATEFUL_CALLBACK), employer.getNumber());
                            sendMessageToMain("deleted:" + employer.getName());
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : hbox);
            }
        };
    }

    private void inputConstraint() {
        ObservableResourceFactory.localeProperty().addListener((observable, oldValue, newValue) -> {
            dateAffiliationField.setChronology(MinguoChronology.INSTANCE);
            dateAffiliationField.setChronology(Chronology.ofLocale(newValue));
            dateRadiationField.setChronology(MinguoChronology.INSTANCE);
            dateRadiationField.setChronology(Chronology.ofLocale(newValue));
        });
        dateAffiliationField.setPromptText(Constant.DATE_FORMAT_PROMPT_TEXT);
        dateRadiationField.setPromptText(Constant.DATE_FORMAT_PROMPT_TEXT);
        dateAffiliationField.setConverter(Utils.getStringConverter(observableResourceFactory.getLanguage()));
        dateRadiationField.setConverter(Utils.getStringConverter(observableResourceFactory.getLanguage()));
        dateAffiliationField.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Utils.updateDateRestriction(dateRadiationField, newValue, true);
            } else {
                dateRadiationField.setDayCellFactory(picker -> new DateCell());
            }
            LocalDate today = LocalDate.now();
            if (newValue != null && newValue.isAfter(today)) {
                dateAffiliationField.setValue(today);
            }
        });
        Utils.updateDateRestriction(dateAffiliationField, LocalDate.now(), false);
        Utils.applyDateInputHandler(dateAffiliationField);
        Utils.applyDateInputHandler(dateRadiationField);
        Utils.setChoiceBoxForType(typeChoiceBox, observableResourceFactory);
        typeChoiceBox.getSelectionModel().selectFirst();
        TextFormatter<Integer> textFormatter = Utils.getIntegerTextFormatter();
        numberField.setTextFormatter(textFormatter);
        numberField.setText("");
        Utils.addListenerForIDE(numberIDEField);
        numberIDEField.setPromptText(Constant.IDE_FORMAT_PROMPT_TEXT);
        observableResourceFactory.addLanguageChangeListener(new ObservableResourceFactory.LanguageChangeListener() {
            @Override
            public void onLanguageChange(ObservableResourceFactory.Language newLanguage) {
                reload(pagination.getCurrentPageIndex(), newLanguage.getLocale());
            }
        });
    }

    private void inputOnChangeHandler() {
        addListenerAndStoreCriteria(typeChoiceBox.valueProperty());
        addListenerAndStoreCriteria(numberField.textProperty());
        addListenerAndStoreCriteria(nameField.textProperty());
        addListenerAndStoreCriteria(numberIDEField.textProperty());
        addListenerAndStoreCriteria(dateAffiliationField.valueProperty());
        addListenerAndStoreCriteria(dateRadiationField.valueProperty());
    }

    private void storeCriteriaToCallBack() {
        EmployerSearchCriteria.Builder builder = EmployerSearchCriteria.newBuilder();
        Optional.ofNullable(typeChoiceBox.getValue()).ifPresent(builder::setType);
        Optional.ofNullable(numberField.getText()).ifPresent(builder::setNumber);
        Optional.ofNullable(nameField.getText()).ifPresent(builder::setName);
        Optional.ofNullable(numberIDEField.getText()).ifPresent(builder::setNumberIDE);
        Optional.ofNullable(dateAffiliationField.getValue()).ifPresent(value -> builder.setDateAffiliation(value.toString()));
        Optional.ofNullable(dateRadiationField.getValue()).ifPresent(value -> builder.setDateRadiation(value.toString()));
        EmployerSearchCriteria employerSearchCriteria = builder.build();
        context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".").concat(BasicConfig.SEARCH_STATEFUL_CALLBACK), employerSearchCriteria);
    }

    private void storeDataToCallBack() {
        Optional.ofNullable(employerNoSalaries).ifPresent(lis -> {
            EmployerNoSalariesObject object = EmployerNoSalariesObject.builder().employerNoSalaries(lis).build();
            context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".").concat(BasicConfig.SEARCH_STATEFUL_CALLBACK), object);
        });
    }

    private void addListenerAndStoreCriteria(ObservableValue<?> property) {
        property.addListener((observable, oldValue, newValue) -> {
            storeCriteriaToCallBack();
        });
    }

    private void localeBinding() {
        filterLabel.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.SEARCH_FILTER));
        typeLabel.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.SEARCH_TYPE));
        numberLabel.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.SEARCH_NUMBER));
        affiliationLabel.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.SEARCH_AFFILIATION));
        nameLabel.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.SEARCH_NAME));
        numberIDELabel.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.SEARCH_NUMBERIDE));
        radiationLabel.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.SEARCH_RADIATION));
        searchButton.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.SEARCH_SEARCH));
        resetSearchButton.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.SEARCH_RESET_SEARCH));
        addButton.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.SEARCH_ADD));
        typeColumn.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.SEARCH_TABLE_TYPE));
        numberColumn.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.SEARCH_TABLE_NUMBER));
        numberIDEColumn.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.SEARCH_TABLE_NUMBERIDE));
        nameColumn.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.SEARCH_TABLE_NAME));
        dateAffiliationColumn.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.SEARCH_TABLE_AFFILIATION));
        dateRadiationColumn.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.SEARCH_TABLE_RADIATION));


        Label placeholder = new Label("");
        searchTableView.setPlaceholder(placeholder);
        placeholder.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.SEARCH_TABLE_NO_CONTENT));
        fixedColumnTableView.setPlaceholder(new Label(""));
    }
}
