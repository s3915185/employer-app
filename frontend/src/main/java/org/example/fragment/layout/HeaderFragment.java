package org.example.fragment.layout;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import org.example.config.BasicConfig;
import org.example.constant.BundleKey;
import org.example.factory.ObservableResourceFactory;
import org.example.model.message.MainMessage;
import org.example.model.enums.MessageView;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.fragment.Fragment;
import org.jacpfx.api.fragment.Scope;
import org.jacpfx.rcp.components.managedFragment.ManagedFragment;
import org.jacpfx.rcp.context.Context;
import org.springframework.beans.factory.annotation.Autowired;

@Fragment(
        id = BasicConfig.HEADER_TAB,
        viewLocation = "/fxml/fragment/header-view.fxml",
        resourceBundleLocation = "bundles/languageBundle",
        scope = Scope.PROTOTYPE
)
public class HeaderFragment extends ManagedFragment {
    @Autowired
    private ObservableResourceFactory observableResourceFactory;
    @FXML
    private Label header;
    @FXML
    private Label managementLabel;
    @FXML
    private Label employerLabel;
    @FXML
    private HBox languageButton;
    @FXML
    private Label enLabel;
    @FXML
    private Label frLabel;
    @Resource
    private Context context;

    public void init(String name, boolean isDefault) {
        managementLabel.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.HEADER_MANAGEMENT));
        employerLabel.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.HEADER_EMPLOYER));
        if (isDefault) {
            header.textProperty().bind(observableResourceFactory.getStringBinding(name));
        }
        else {
            header.setText(name);
        }
        getLanguageAndIndicate(observableResourceFactory.getLanguage());
        languageButton.setOnMouseClicked(event -> {
            PauseTransition pause = new PauseTransition(Duration.millis(100)); // Adjust the duration as needed
            pause.setOnFinished(e -> languageButton.getStyleClass().remove("pressed-button"));
            pause.play();
            ObservableResourceFactory.Language language = observableResourceFactory.switchResourceForLanguage();
            context.send(BasicConfig.PERSPECTIVE_MAIN, getLanguageAndIndicate(language));
        });
    }
    private MainMessage getLanguageAndIndicate(ObservableResourceFactory.Language language) {
        MainMessage mainMessage = new MainMessage();
        if (language.getLocale().equals(ObservableResourceFactory.Language.EN.getLocale())) {
            frLabel.getStyleClass().removeAll("language-label-active");
            frLabel.getStyleClass().add("language-label");
            enLabel.getStyleClass().removeAll("language-label");
            enLabel.getStyleClass().add("language-label-active");
            mainMessage.setMessage("language:" + ObservableResourceFactory.Language.EN.getLocale().toString());
        }
        else if (language.getLocale().equals(ObservableResourceFactory.Language.FR.getLocale())){
            enLabel.getStyleClass().removeAll("language-label-active");
            enLabel.getStyleClass().add("language-label");
            frLabel.getStyleClass().removeAll("language-label");
            frLabel.getStyleClass().add("language-label-active");
            mainMessage.setMessage("language:" + ObservableResourceFactory.Language.FR.getLocale().toString());
        }
        mainMessage.setView(MessageView.UPPER_SMALL_MESSAGE);
        return mainMessage;
    }
}
