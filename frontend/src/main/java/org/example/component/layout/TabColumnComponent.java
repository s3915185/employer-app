package org.example.component.layout;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import org.example.config.BasicConfig;
import org.example.constant.BundleKey;
import org.example.constant.Constant;
import org.example.factory.ObservableResourceFactory;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.component.DeclarativeView;
import org.jacpfx.api.annotations.lifecycle.PostConstruct;
import org.jacpfx.api.message.Message;
import org.jacpfx.rcp.component.FXComponent;
import org.jacpfx.rcp.componentLayout.FXComponentLayout;
import org.jacpfx.rcp.context.Context;
import org.jacpfx.rcp.util.FXUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ResourceBundle;
import java.util.logging.Logger;

@DeclarativeView(id = BasicConfig.TAB_COLUMN,
        name = "TabColumnComponent",
        viewLocation = "/fxml/component/tabColumn-view.fxml",
        active = true,
        resourceBundleLocation = "bundles.languageBundle",
        initialTargetLayoutId = BasicConfig.TARGET_CONTAINER_TAB_COLUMN)
public class TabColumnComponent implements FXComponent {

    private Logger log = Logger.getLogger(TabColumnComponent.class.getName());
    @FXML
    private StackPane searchButton;

    @FXML
    private StackPane detailsButton;

    @FXML
    private StackPane importButton;

    @FXML
    private Label searchLabel;
    @FXML
    private Label detailLabel;
    @FXML
    private Label importLabel;

    @Resource
    private Context context;

    private Node root;

    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @Override
    public Node postHandle(Node node, Message<Event, Object> message) throws Exception {
        return null;
    }

    @Override
    public Node handle(Message<Event, Object> message) throws Exception {
        if (message.messageBodyEquals(FXUtil.MessageUtil.INIT)) {
            detailsButton.setVisible(false);
        }
        if (!message.messageBodyEquals(FXUtil.MessageUtil.INIT)) {
            if (message.messageBodyEquals(Constant.MESSAGE_SEARCH_BODY_ENABLED)) {
                detailsButton.getStyleClass().removeAll("button-clicked");
                searchButton.getStyleClass().add("button-clicked");
            } else if (message.messageBodyEquals(Constant.MESSAGE_DETAIL_BODY_ENABLED)) {
                searchButton.getStyleClass().removeAll("button-clicked");
                detailsButton.getStyleClass().add("button-clicked");
            }
            if (message.messageBodyEquals(Constant.MESSAGE_ON_DETAIL_BUTTON)) {
                detailsButton.setVisible(true);
            }
            else if (message.messageBodyEquals(Constant.MESSAGE_OFF_DETAIL_BUTTON)) {
                detailsButton.setVisible(false);
            }
        }
        return this.root;
    }


    @PostConstruct
    /**
     * The @PostConstruct annotation labels methods executed when the component switch from inactive to active state
     * @param arg0
     * @param resourceBundle
     */
    public void onPostConstructComponent(final FXComponentLayout arg0,
                                         final ResourceBundle resourceBundle) {
        searchLabel.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.TAB_SEARCH));
        detailLabel.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.TAB_DETAIL));
        importLabel.textProperty().bind(observableResourceFactory.getStringBinding(BundleKey.TAB_IMPORT));
//        detailsButton.setVisible(false);
        importButton.setVisible(false);
        this.log.info("run on start of TabColumnComponent ");
        searchButton.setOnMouseClicked(event -> {
//            context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".").concat(BasicConfig.BODY_TAB), "showSearchBodyTab");
            context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".").concat(BasicConfig.SEARCH_STATEFUL_CALLBACK), Constant.MESSAGE_DISPLAY);
            context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".").concat(BasicConfig.HEADER), Constant.MESSAGE_SHOW_EMPLOYER_LABEL);
        });
        detailsButton.setOnMouseClicked(event -> {
            context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".").concat(BasicConfig.DETAIL_STATEFUL_CALLBACK), Constant.MESSAGE_DISPLAY);
            context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".").concat(BasicConfig.HEADER), Constant.MESSAGE_SHOW_DETAIL_LABEL);
//            context.send(BasicConfig.PERSPECTIVE_MAIN.concat(".").concat(BasicConfig.BODY_TAB), "showDetailBodyTab");
        });
    }
}
