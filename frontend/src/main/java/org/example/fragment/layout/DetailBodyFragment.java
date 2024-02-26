package org.example.fragment.layout;

import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import org.example.*;
import org.example.model.alert.ConfirmationAlert;
import org.example.config.BasicConfig;
import org.example.constant.BundleKey;
import org.example.constant.Constant;
import org.example.controller.FileImporterController;
import org.example.factory.ObservableResourceFactory;
import org.example.mapper.EmployerClientMapper;
import org.example.model.message.MainMessage;
import org.example.model.pagination.PaginationObject;
import org.example.model.view.DetailViewObject;
import org.example.model.object.SalaryObject;
import org.example.service.employerService.EmployerClientService;
import org.example.setup.FileImporterSetup;
import org.example.model.enums.PensionFund;
import org.example.util.Utils;
import org.example.model.enums.MessageView;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.fragment.Fragment;
import org.jacpfx.api.fragment.Scope;
import org.jacpfx.rcp.components.managedFragment.ManagedFragment;
import org.jacpfx.rcp.context.Context;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.MinguoChronology;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Fragment(
        id = BasicConfig.DETAIL_TAB,
        viewLocation = "/fxml/fragment/detail-view.fxml",
        resourceBundleLocation = "bundles.languageBundle",
        scope = Scope.PROTOTYPE
)
public class DetailBodyFragment extends ManagedFragment {
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
    private Label rechercherButton;

    @FXML
    private TableView<Salary> detailTableView;

    @FXML
    private TableColumn<Salary, String> numberAVSColumn;

    @FXML
    private TableColumn<Salary, String> nameColumn;

    @FXML
    private TableColumn<Salary, String> firstNameColumn;

    @FXML
    private TableColumn<Salary, String> dateDebutColumn;

    @FXML
    private TableColumn<Salary, String> dateFinishedColumn;

    @FXML
    private TableColumn<Salary, Float> numberAvsAiApgColumn;

    @FXML
    private TableColumn<Salary, Float> numberACColumn;

    @FXML
    private TableColumn<Salary, Float> numberAFColumn;

    @FXML
    private Label returnButton;

    @FXML
    private Label importButton;

    @FXML
    private Label forwardButton;

    @FXML
    private Pagination pagination;

    @FXML
    private Label detailLabel;
    @FXML
    private Text numberLabel;
    @FXML
    private Text nameLabel;
    @FXML
    private Text typeLabel;
    @FXML
    private Text numberIDELabel;
    @FXML
    private Text affiliationLabel;
    @FXML
    private Text radiationLabel;
    @FXML
    private Label salaryDeclarationLabel;

    @FXML
    private TextField importField;

    @Resource
    private Context context;
    @Autowired
    private EmployerClientService employerClientService;
    @Autowired
    private EmployerClientMapper employerClientMapper;
    @Autowired
    private ObservableResourceFactory observableResourceFactory;
    @FXML
    private Label importViewBtn;
    @FXML
    private Label detailViewBtn;

    private List<Salary> salaries;
    private Long selectEmployerId;
    private final int dataRow = 6;
    private int pageLength = 1;
    private EmployerNoSalaries employerNoSalaries;
    private String viewType = Constant.MESSAGE_DETAIL;
    private boolean detailModified = false;
    private boolean isAddView = false;


    public void init(String method, Object object) {
        Utils.applyEaseInAnimation(gridPane);
        localeBinding();
        displayDetailViewFromCallBack(object);
        addNewEmployerHandling(method);
        addFieldConstraint();
        disableEdit();
    }

    private void addNewEmployerHandling(String method) {
        if (method != null && method.equals(Constant.MESSAGE_ADD_NEW)) {
            String number = employerClientService.getLastEmployerNumber().getNumber();
            int value = Integer.parseInt(number);
            value++;
            String numberFormatted = Utils.formatNumber(value, 8);
            numberField.setText(numberFormatted);
            forwardButton.setOnMouseClicked(this::addNewButton);
            isAddView = true;
            detailViewBtn.setVisible(false);
        }
    }

    private void displayDetailViewFromCallBack(Object object) {
        if (object != null) {
            if (object instanceof EmployerNoSalaries) {
                employerNoSalaries = (EmployerNoSalaries) object;
                selectEmployerId = employerNoSalaries.getId();
                setFieldsInfo(employerNoSalaries);
                context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".").concat(BasicConfig.DETAIL_STATEFUL_CALLBACK), employerNoSalaries);
                getAndSetData(selectEmployerId, 0);
                int dataCount = (int) employerClientService.getEmployerSalariesCount(selectEmployerId).getNumber();
                sendPaginationObject(dataCount, Constant.MESSAGE_DETAIL);
                pagination.setCurrentPageIndex(0);
            } else if (object instanceof DetailViewObject) {
                DetailViewObject detailViewObject = (DetailViewObject) object;
                Optional.ofNullable(detailViewObject.getEmployerNoSalaries()).ifPresent(employerNoSalaries -> {
                    this.employerNoSalaries = employerNoSalaries;
                    selectEmployerId = employerNoSalaries.getId();
                    setFieldsInfo(employerNoSalaries);
                });
                Optional.ofNullable(detailViewObject.getSalaries()).ifPresent(salaries -> {
                    ObservableList<Salary> data = FXCollections.observableArrayList(salaries);
                    detailTableView.setItems(data);
                });
                Optional.ofNullable(detailViewObject.getPaginationObject()).ifPresent(number -> {
                    pagination.setPageCount(number.getPageLength().intValue() > 0 ? number.getPageLength().intValue() : 1);
                    pagination.setCurrentPageIndex(number.getPage().intValue());
                    pageLength = number.getPageLength().intValue();
                });
            }
            forwardButton.setOnMouseClicked(this::updateButton);
            isAddView = false;
        }
    }

    private void setKeyEventHandler(Scene newScene) {
        newScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (event.isAltDown()) {
                } else if (event.isControlDown()) {
                } else {
                    if (isAddView) {
                        addNewButton(null);
                    } else {
                        updateButton(null);
                    }
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

    private void sendViewModifiedToTrue() {
        context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".").concat(BasicConfig.BODY_TAB), Constant.MESSAGE_MODIFIED);
    }

    private void getImportViewData() {
        getAndSetDataFileImport(importField.getText(), 0);
    }

    private void getDetailViewData() {
        viewType = Constant.MESSAGE_DETAIL;
        context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".").concat(BasicConfig.DETAIL_STATEFUL_CALLBACK), employerNoSalaries);
        getAndSetData(selectEmployerId, 0);
        int dataCount = (int) employerClientService.getEmployerSalariesCount(selectEmployerId).getNumber();
        sendPaginationObject(dataCount, Constant.MESSAGE_DETAIL);
        pagination.setCurrentPageIndex(0);
        Utils.applyEaseInAnimation(detailTableView);
    }

    private void detailViewBtnClicked() {
        importViewBtn.setStyle("");
        detailViewBtn.setStyle("-fx-text-fill: #59a6c6;");
    }

    private void importViewBtnClicked() {
        detailViewBtn.setStyle("");
        importViewBtn.setStyle("-fx-text-fill: #59a6c6;");
    }

    private void getAndSetData(Long id, int page) {
        salaries = employerClientService.getSalaryByEmployerId(id, dataRow, page * dataRow);
        if (!salaries.isEmpty()) {
            SalaryObject salaryObject = SalaryObject.builder().salaryList(salaries).type(Constant.MESSAGE_DETAIL).build();
            context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".").concat(BasicConfig.DETAIL_STATEFUL_CALLBACK), salaryObject);
        } else {
            context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".").concat(BasicConfig.DETAIL_STATEFUL_CALLBACK), Constant.MESSAGE_NO_SALARIES);
        }
        ObservableList<Salary> data = FXCollections.observableArrayList(salaries);
        detailTableView.setItems(data);
    }

    private void updateRadiationDateRestriction(java.time.LocalDate selectedDate) {
        dateRadiationField.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(empty || item.isBefore(selectedDate));
            }
        });
        if (dateRadiationField.getValue() != null) {
            if (dateRadiationField.getValue().isBefore(selectedDate)) {
                dateRadiationField.setValue(selectedDate);
            }
        }
    }

    private void setFieldsInfo(EmployerNoSalaries employerNoSalaries) {
        context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".").concat(BasicConfig.HEADER), employerNoSalaries);
        numberField.setText(employerNoSalaries.getNumber());
        nameField.setText(employerNoSalaries.getName());
        typeChoiceBox.setValue(PensionFund.getDisplayVersionResourceBundle(employerNoSalaries.getType(), observableResourceFactory.getLanguage().getLocale()));
        numberIDEField.setText(employerNoSalaries.getNumberIDE());
        dateAffiliationField.setValue(Utils.toLocalDate(employerNoSalaries.getDateAffiliation(), "yyyy-MM-dd").get());
        Optional.of(employerNoSalaries.getDateRadiation()).ifPresent(value -> {
            dateRadiationField.setValue(Utils.toLocalDate(employerNoSalaries.getDateRadiation(), "yyyy-MM-dd").orElse(null));
        });
    }

    protected void addFieldConstraint() {
        Label placeholder = new Label("");
        detailTableView.setPlaceholder(placeholder);
        placeholder.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.SEARCH_TABLE_NO_CONTENT));
        Utils.setChoiceBoxForType(typeChoiceBox, observableResourceFactory);
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
        Utils.updateDateRestriction(dateAffiliationField, LocalDate.now(), false);
        if (dateAffiliationField.getValue() != null) {
            Utils.updateDateRestriction(dateRadiationField, dateAffiliationField.getValue(), true);
        }
        numberIDEField.setPromptText(Constant.IDE_FORMAT_PROMPT_TEXT);
        Utils.applyDateInputHandler(dateAffiliationField);
        Utils.applyDateInputHandler(dateRadiationField);
        Utils.addListenerForIDE(numberIDEField);
        numberAVSColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumberAVS()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
        Utils.setupDateColumnSalary(dateDebutColumn, Salary::getDateDebut);
        Utils.setupDateColumnSalary(dateFinishedColumn, Salary::getDateFinished);
        numberAvsAiApgColumn.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getNumberAvsAiApg()).asObject());
        numberACColumn.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getNumberAc()).asObject());
        numberAFColumn.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getNumberAf()).asObject());
        importField.setEditable(false);
        Utils.setNumberFormat(numberAvsAiApgColumn);
        Utils.setNumberFormat(numberACColumn);
        Utils.setNumberFormat(numberAFColumn);
        returnButton.setOnMouseClicked(this::returnButton);
        importButton.setOnMouseClicked(event -> {
            try {
                importButton(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        pagination.currentPageIndexProperty().addListener(((observable, oldValue, newValue) -> {
            if (viewType.equals(Constant.MESSAGE_DETAIL)) {
                getAndSetData(selectEmployerId, newValue.intValue());
                PaginationObject paginationObject = PaginationObject.builder().page(newValue).pageLength(pageLength).type(Constant.MESSAGE_DETAIL).build();
                context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".").concat(BasicConfig.DETAIL_STATEFUL_CALLBACK), paginationObject);
            } else if (viewType.equals(Constant.MESSAGE_IMPORT)) {
                getAndSetDataFileImport(importField.getText(), newValue.intValue());
                PaginationObject paginationObject = PaginationObject.builder().page(newValue).pageLength(pageLength).type(Constant.MESSAGE_IMPORT).build();
                context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".").concat(BasicConfig.DETAIL_STATEFUL_CALLBACK), paginationObject);
            }
        }));
        detailViewBtn.setStyle("-fx-text-fill: #59a6c6;");
        detailViewBtn.setOnMouseClicked(event -> {
            getDetailViewData();
            detailViewBtnClicked();
        });
        importViewBtn.setOnMouseClicked(event -> {
            if (importField.getText().isEmpty()) {
                selectFileModel();
            } else {
                getImportViewData();
            }
        });
//        addListenerToPropertyChoiceBox(typeChoiceBox.getSelectionModel().selectedItemProperty());
//        addListenerToProperty(dateAffiliationField.valueProperty());
        addListenerToProperty(nameField.textProperty());
        addListenerToProperty(numberIDEField.textProperty());
//        addListenerToProperty(dateRadiationField.valueProperty());
        gridPane.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                setKeyEventHandler(newScene);
                context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".")
                        .concat(BasicConfig.TAB_COLUMN), Constant.MESSAGE_DETAIL_BODY_ENABLED);
            }
        });
    }

    private void addListenerToProperty(Property<?> property) {
        property.addListener((observable, oldValue, newValue) -> {
            if (!detailModified) {
                setAndSendModifiedTrueToCallBack();
            }
        });
    }
    private void addListenerToPropertyChoiceBox(ReadOnlyObjectProperty<?> property) {
        property.addListener((observable, oldValue, newValue) -> {
            if (!detailModified) {
                setAndSendModifiedTrueToCallBack();
            }
        });
    }

    private void setAndSendModifiedTrueToCallBack() {
        if (!detailModified) {
            detailModified = true;
            sendViewModifiedToTrue();
        }
    }

    public void addNewButton(MouseEvent event) {
        if (!validatingFieldNull()) {
            return;
        }
        if (!Utils.validateNumberIDE(numberIDEField.getText())) {
            sendMessageToMain(Constant.MESSAGE_ERROR_NUMBER_IDE);
            return;
        }
        ConfirmationAlert.ConfirmationAlertBuilder confirmationAlertBuilder = ConfirmationAlert.builder();
        ConfirmationAlert confirmationAlert = confirmationAlertBuilder.build();
        ObservableResourceFactory.Language language = observableResourceFactory.getLanguage();
        boolean confirmed = confirmationAlert.showAddConfirmationDialog(language);
        if (confirmed) {
            try {
                List<Salary> salariesImported = readSalaryListFromFile();
                Employer toAddEmployer = employerClientService.addEmployer(PensionFund.getDatabaseVersion(typeChoiceBox.getValue(), observableResourceFactory.getLanguage().getLocale()), numberField.getText(), nameField.getText(),
                        numberIDEField.getText(), dateAffiliationField.getValue(), dateRadiationField.getValue(), salariesImported);
                context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".").concat(BasicConfig.SEARCH_STATEFUL_CALLBACK), Constant.MESSAGE_RELOAD_REQUIRED);
                context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".").concat(BasicConfig.BODY_TAB), Constant.MESSAGE_MODIFIED_FALSE);
                context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".").concat(BasicConfig.DETAIL_STATEFUL_CALLBACK), toAddEmployer.getNumber());
                context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".").concat(BasicConfig.TAB_COLUMN), Constant.MESSAGE_OFF_DETAIL_BUTTON);
                returnButton(null);
                sendMessageToMain("added:" + toAddEmployer.getName());
                importField.setText("");
                if (viewType.equals(Constant.MESSAGE_IMPORT)) {
                    detailTableView.getItems().clear();
                }
            } catch (IOException | RuntimeException e) {
                sendMessageToMain(e.getMessage());
            }
        }
    }

    private List<Salary> readSalaryListFromFile() throws IOException {
        if (!importField.getText().isEmpty()) {
            FileImporterController fileImporterController = FileImporterSetup.setupModel(importField.getText());
            return fileImporterController.readFile(importField.getText(), observableResourceFactory.getLanguage().getLocale());
        }
        return Collections.emptyList();
    }

    public void updateButton(MouseEvent event) {
        if (!validatingFieldNull()) {
            return;
        }
        if (!Utils.validateNumberIDE(numberIDEField.getText())) {
            sendMessageToMain(Constant.MESSAGE_ERROR_NUMBER_IDE);
            return;
        }
        ConfirmationAlert.ConfirmationAlertBuilder confirmationAlertBuilder = ConfirmationAlert.builder();
        ConfirmationAlert confirmationAlert = confirmationAlertBuilder.build();
        ObservableResourceFactory.Language language = observableResourceFactory.getLanguage();
        boolean confirmed = confirmationAlert.showUpdateConfirmationDialog(language);
        if (confirmed) {
            try {
                List<Salary> salariesImported = readSalaryListFromFile();
                Employer toAddEmployer = employerClientService.updateEmployer(selectEmployerId, PensionFund.getDatabaseVersion(typeChoiceBox.getValue(), observableResourceFactory.getLanguage().getLocale()), numberField.getText(), nameField.getText(),
                        numberIDEField.getText(), dateAffiliationField.getValue(), dateRadiationField.getValue(), salariesImported);
                employerNoSalaries = employerClientMapper.toEmployerNoSalaries(toAddEmployer);
                selectEmployerId = employerNoSalaries.getId();
                context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".").concat(BasicConfig.SEARCH_STATEFUL_CALLBACK), Constant.MESSAGE_RELOAD_REQUIRED);
                context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".").concat(BasicConfig.DETAIL_STATEFUL_CALLBACK), employerNoSalaries);
                context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".").concat(BasicConfig.BODY_TAB), Constant.MESSAGE_MODIFIED_FALSE);
                sendMessageToMain("updated:" + toAddEmployer.getName());
                importField.setText("");
                if (viewType.equals(Constant.MESSAGE_IMPORT)) {
                    detailTableView.getItems().clear();
                }
            } catch (IOException | RuntimeException e) {
                sendMessageToMain(e.getMessage());
            }

        }
    }

    public void returnButton(MouseEvent event) {
        context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".").concat(BasicConfig.SEARCH_STATEFUL_CALLBACK), Constant.MESSAGE_DISPLAY);
        context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".").concat(BasicConfig.HEADER), Constant.MESSAGE_SHOW_EMPLOYER_LABEL);
    }

    public void importButton(MouseEvent event) throws IOException {
        selectFileModel();
    }

    private void selectFileModel() {

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter(
                "Spreadsheet formats",
                "*.xlsx", "*.xml", "*.csv"
        );
        fileChooser.getExtensionFilters().add(extensionFilter);
        File file = fileChooser.showOpenDialog(null);
        if (file == null) {return;}
        String selectedFile = file.getPath();
        importField.setText(selectedFile);
        getAndSetDataFileImport(selectedFile, 0);
    }

    private void getAndSetDataFileImport(String selectedFile, int page) {
        try {
            FileImporterController fileImporterController = FileImporterSetup.setupModel(selectedFile);
            int dataCount = Math.toIntExact(fileImporterController.readFileLinesIgnoreHeader(selectedFile));
            salaries = fileImporterController.readFile(selectedFile, dataRow, page, observableResourceFactory.getLanguage().getLocale());
            sendPaginationObject(dataCount, Constant.MESSAGE_IMPORT);
            if (!salaries.isEmpty()) {
                SalaryObject salaryObject = SalaryObject.builder().salaryList(salaries).type(Constant.MESSAGE_IMPORT).build();
                ObservableList<Salary> data = FXCollections.observableArrayList(salaries);
                detailTableView.setItems(data);
                Utils.applyEaseInAnimation(detailTableView);
                context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".").concat(BasicConfig.DETAIL_STATEFUL_CALLBACK), salaryObject);
            }
            viewType = Constant.MESSAGE_IMPORT;
            setAndSendModifiedTrueToCallBack();
            importViewBtnClicked();
        } catch (Exception e) {
            importField.setText("");
            sendMessageToMain(e.getMessage());
        }
    }

    private void sendPaginationObject(int dataCount, String type) {
        pageLength = (int) Math.ceil((double) dataCount / dataRow);
        if (dataCount > 0) {
            pagination.setPageCount(pageLength);
        } else {
            pagination.setPageCount(1);
        }
        PaginationObject paginationObject = PaginationObject.builder().page(0).pageLength(pageLength).type(type).build();
        context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".").concat(BasicConfig.DETAIL_STATEFUL_CALLBACK), paginationObject);
    }

    private boolean validatingFieldNull() {
        StringBuilder emptyFields = new StringBuilder();
        Utils.compareToAppend(emptyFields, numberField.getText(), numberLabel);
        Utils.compareToAppend(emptyFields, typeChoiceBox.getValue(), typeLabel);
        Utils.compareToAppend(emptyFields, nameField.getText(), nameLabel);
        Utils.compareToAppend(emptyFields, numberIDEField.getText(), numberIDELabel);
        Utils.compareToAppend(emptyFields, dateAffiliationField.getEditor().getText(), affiliationLabel);
        if (emptyFields.length() > 0) {
            emptyFields.delete(emptyFields.length() - 2, emptyFields.length());
            sendMessageToMain(emptyFields.toString());
            return false;
        }
        return true;
    }

    private void sendMessageToMain(String message) {
        MainMessage mainMessage = new MainMessage();
        mainMessage.setMessage(message);
        mainMessage.setView(MessageView.UPPER_SMALL_MESSAGE);
        context.send(BasicConfig.PERSPECTIVE_MAIN, mainMessage);
    }

    protected void disableEdit() {
        numberField.setEditable(false);
    }

    private void localeBinding() {
        detailLabel.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.DETAIL_DETAIl));
        typeLabel.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.DETAIL_TYPE));
        numberLabel.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.DETAIL_NUMBER));
        affiliationLabel.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.DETAIL_AFFILIATION));
        nameLabel.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.DETAIL_NAME));
        numberIDELabel.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.DETAIL_NUMBERIDE));
        radiationLabel.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.DETAIL_RADIATION));
        salaryDeclarationLabel.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.DETAIL_SALARY_DECLARATION));
        importButton.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.DETAIL_IMPORT));
        numberAVSColumn.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.DETAIL_TABLE_NUMBERAVS));
        nameColumn.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.DETAIL_TABLE_NAME));
        firstNameColumn.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.DETAIL_TABLE_FIRST_NAME));
        dateDebutColumn.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.DETAIL_TABLE_DATE_DEBUT));
        dateFinishedColumn.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.DETAIL_TABLE_DATE_FINISHED));
        numberAvsAiApgColumn.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.DETAIL_TABLE_NUMBERAVSAIAPG));
        numberACColumn.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.DETAIL_TABLE_NUMBERAC));
        numberAFColumn.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.DETAIL_TABLE_NUMBERAF));
        returnButton.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.DETAIL_RETURN));
        forwardButton.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.DETAIL_FORWARD));
        detailViewBtn.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.DETAIL_DETAIL_LABEL));
        importViewBtn.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.DETAIL_IMPORT_LABEL));
    }
}
