package org.example.fragment.message;

import com.sun.org.apache.xerces.internal.util.Status;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.example.config.BasicConfig;
import org.example.constant.BundleKey;
import org.example.constant.Constant;
import org.example.factory.ObservableResourceFactory;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.fragment.Fragment;
import org.jacpfx.api.fragment.Scope;
import org.jacpfx.rcp.components.managedFragment.ManagedFragment;
import org.jacpfx.rcp.context.Context;
import org.springframework.beans.factory.annotation.Autowired;

@Fragment(
        id = BasicConfig.UPPER_SMALL_MESSAGE_FRAGMENT,
        viewLocation = "/fxml/fragment/upper-small-message.fxml",
        resourceBundleLocation = "bundles.languageBundle",
        scope = Scope.PROTOTYPE
)
public class UpperSmallMessageFragment extends ManagedFragment {
    @FXML
    private Label message;

    @FXML
    private Label preMessage;

    @FXML
    private ImageView removeMessageButton;
    @Resource
    private Context context;

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    public void init(String message) {
        preMessage.setText("These fields must not be missing:");
        removeMessageButton.setOnMouseClicked(event -> {
            context.send(BasicConfig.PERSPECTIVE_MAIN, Constant.MESSAGE_OFF_UPPER_SMALL_MESSAGE);
        });
        if (message.split(":")[0].contains("added")) {
            this.message.setText(message.split(":")[1]);
            preMessage.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.UPPER_SMALL_PREMESSAGE_ADD_SUCCESS_EMPLOYER));
        }
        else if (message.split(":")[0].contains("updated")) {
            this.message.setText(message.split(":")[1]);
            preMessage.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.UPPER_SMALL_PREMESSAGE_UPDATE_SUCCESS_EMPLOYER));
        }
        else if (message.split(":")[0].contains("deleted")) {
            this.message.setText(message.split(":")[1]);
            preMessage.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.UPPER_SMALL_PREMESSAGE_DELETE_SUCCESS_EMPLOYER));
        }
        else if (message.split(":")[0].contains("language")) {
            this.message.setText("");
            preMessage.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.UPPER_SMALL_LANGUAGE_CHANGE));
        }
        else if (message.equals(Constant.MESSAGE_ERROR_NUMBER_IDE)) {
            this.message.setText("");
            preMessage.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.UPPER_SMALL_NUMBERIDE_ERROR));
        }
        else if (message.equals("INVALID_ARGUMENT")) {
            this.message.setText("");
            preMessage.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.UPPER_SMALL_INVALID_ARGUMENT));
        }
        else if (message.equals("ALREADY_EXISTS")) {
            this.message.setText("");
            preMessage.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.UPPER_SMALL_ALREADY_EXISTS));
        }
        else if (message.equals("ABORTED")) {
            this.message.setText("");
            preMessage.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.UPPER_SMALL_ABORTED));
        }
        else if (message.equals(String.valueOf(Status.NOT_SUPPORTED))) {
            this.message.setText("");
            preMessage.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.UPPER_SMALL_NOT_SUPPORTED));
        }
        else if (message.equals(Status.NOT_ALLOWED.toString())) {
            this.message.setText("");
            preMessage.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.UPPER_SMALL_NOT_ALLOWED));
        }
        else if (message.equals(Status.NOT_RECOGNIZED.toString())) {
            this.message.setText("");
            preMessage.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.UPPER_SMALL_NOT_RECOGNIZED));
        }
        else if (message.equals(Constant.MESSAGE_DATE_FORMAT_ERROR)) {
            this.message.setText("");
            preMessage.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.UPPER_SMALL_DATE_FORMAT_ERROR));
        }
        else if (message.equals(Constant.MESSAGE_NUMBER_FORMAT_ERROR)) {
            this.message.setText("");
            preMessage.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.UPPER_SMALL_NUMBER_FORMAT_ERROR));
        }
        else {
            this.message.setText(message);
            preMessage.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.UPPER_SMALL_PREMESSAGE));
        }
    }
}
