package org.example.model.alert;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import lombok.*;
import org.example.constant.BundleKey;
import org.example.factory.ObservableResourceFactory;

import java.util.Optional;
import java.util.ResourceBundle;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ConfirmationAlert {
    private String title;
    private String headerText;
    private String contentText;
    private String confirmButtonText;
    private String cancelButtonText;
    private String confirmButtonColor;
    private String cancelButtonColor;
    private Alert.AlertType type;

    private boolean showConfirmationDialog(String titleKey, String headerKey, String contentKey,
                                           String confirmButtonKey, String cancelButtonKey,
                                           ObservableResourceFactory.Language language, boolean firstButtonIsDefault,
                                           boolean isConfirmButtonRed) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("bundles/languageBundle", language.getLocale());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(resourceBundle.getString(titleKey));
        alert.setHeaderText(resourceBundle.getString(headerKey));
        alert.setContentText(resourceBundle.getString(contentKey));

        ButtonType confirmButton = new ButtonType(resourceBundle.getString(confirmButtonKey), ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType(resourceBundle.getString(cancelButtonKey), ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(confirmButton, cancelButton);
        alert.getDialogPane().lookupButton(confirmButton).setStyle( isConfirmButtonRed ? "-fx-base: #d95b66;" : "-fx-base: #cccccc;");
        alert.getDialogPane().lookupButton(cancelButton).setStyle("-fx-base: #cccccc;");
        if (!firstButtonIsDefault) {
            ((Button) alert.getDialogPane().lookupButton(confirmButton)).setDefaultButton(false);
            ((Button) alert.getDialogPane().lookupButton(cancelButton)).setDefaultButton(true);
        }

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == confirmButton;
    }
    public boolean showDeleteConfirmationDialog(ObservableResourceFactory.Language language) {
        return showConfirmationDialog(BundleKey.CONFIRMATION_DELETE_TITLE, BundleKey.CONFIRMATION_DELETE_HEADER,
                BundleKey.CONFIRMATION_DELETE_CONTENT, BundleKey.CONFIRMATION_DELETE_CONFIRM_BUTTON,
                BundleKey.CONFIRMATION_DELETE_CANCEL_BUTTON, language, false, true);
    }
    public boolean showAddConfirmationDialog(ObservableResourceFactory.Language language) {
        return showConfirmationDialog(BundleKey.CONFIRMATION_ADD_TITLE, BundleKey.CONFIRMATION_ADD_HEADER,
                BundleKey.CONFIRMATION_ADD_CONTENT, BundleKey.CONFIRMATION_ADD_CONFIRM_BUTTON,
                BundleKey.CONFIRMATION_ADD_CANCEL_BUTTON, language, true, false);
    }
    public boolean showUpdateConfirmationDialog(ObservableResourceFactory.Language language) {
        return showConfirmationDialog(BundleKey.CONFIRMATION_UPDATE_TITLE, BundleKey.CONFIRMATION_UPDATE_HEADER,
                BundleKey.CONFIRMATION_UPDATE_CONTENT, BundleKey.CONFIRMATION_UPDATE_CONFIRM_BUTTON,
                BundleKey.CONFIRMATION_UPDATE_CANCEL_BUTTON, language, true, false);
    }
    public boolean showExitConfirmationDialog(ObservableResourceFactory.Language language) {
        return showConfirmationDialog(BundleKey.CONFIRMATION_EXIT_TITLE, BundleKey.CONFIRMATION_EXIT_HEADER,
                BundleKey.CONFIRMATION_EXIT_CONTENT, BundleKey.CONFIRMATION_EXIT_CONFIRM_BUTTON,
                BundleKey.CONFIRMATION_EXIT_CANCEL_BUTTON, language, true, false);
    }
}

