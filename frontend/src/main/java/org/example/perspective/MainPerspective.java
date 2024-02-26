package org.example.perspective;

import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.*;
import javafx.util.Duration;
import org.example.EmployerNoSalaries;
import org.example.constant.Constant;
import org.example.model.message.MainMessage;
import org.example.service.employerService.EmployerClientService;
import org.example.model.enums.MessageView;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.lifecycle.OnHide;
import org.jacpfx.api.annotations.lifecycle.OnShow;
import org.jacpfx.api.annotations.lifecycle.PostConstruct;
import org.jacpfx.api.annotations.lifecycle.PreDestroy;
import org.jacpfx.api.annotations.perspective.Perspective;
import org.jacpfx.api.message.Message;

import org.example.config.BasicConfig;
import org.jacpfx.rcp.componentLayout.FXComponentLayout;
import org.jacpfx.rcp.componentLayout.PerspectiveLayout;

import org.jacpfx.rcp.context.Context;
import org.jacpfx.rcp.perspective.FXPerspective;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ResourceBundle;
import java.util.logging.Logger;

@Perspective(id = BasicConfig.PERSPECTIVE_MAIN, name = "Main Perspective",
        viewLocation = "/fxml/main/default-layout.fxml",
        resourceBundleLocation = "bundles.languageBundle",
        components = {
        BasicConfig.HEADER, BasicConfig.TAB_COLUMN, BasicConfig.BODY_TAB, BasicConfig.DELETE_MESSAGE_TAB, BasicConfig.UPPER_SMALL_MESSAGE_TAB,
        BasicConfig.SEARCH_STATEFUL_CALLBACK, BasicConfig.DETAIL_STATEFUL_CALLBACK}
)
public class MainPerspective implements FXPerspective {
    private Logger log = Logger.getLogger(MainPerspective.class.getName());

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private VBox mainVBoxLayout;

    @FXML
    private VBox popUpVBox;
    @FXML
    private HBox popUpSmallHBox;

    @FXML
    private GridPane topLayout;

    @FXML
    private StackPane leftLayout;

    @FXML
    private GridPane bodyLayout;


    @Resource
    public Context context;

    @Autowired
    private EmployerClientService employerClientService;

    @OnShow
    /**
     * This method will be executed when the perspective gets the focus and switches to foreground
     * @param layout, the component layout contains references to the toolbar and the menu
     */
    public void onShow(final FXComponentLayout layout) {
        log.info("on show of MainPerspective");
    }

    @OnHide
    /**
     * will be executed when an active perspective looses the focus and moved to the background.
     * @param layout, the component layout contains references to the toolbar and the menu
     */
    public void onHide(final FXComponentLayout layout) {
        log.info("on hide of MainPerspective");
    }

    @Override
    public void handlePerspective(Message<Event, Object> message, PerspectiveLayout perspectiveLayout) {
        if (message.isMessageBodyTypeOf(EmployerNoSalaries.class)) {
            EmployerNoSalaries employerNoSalaries = (EmployerNoSalaries) message.getMessageBody();
            context.send(BasicConfig.DELETE_MESSAGE_TAB, employerNoSalaries);
            popUpVBox.setVisible(true);
        }
        if (message.isMessageBodyTypeOf(MainMessage.class)) {
            MainMessage mainMessage = (MainMessage) message.getMessageBody();
            if (mainMessage.getView().equals(MessageView.UPPER_SMALL_MESSAGE)) {
                context.send(BasicConfig.UPPER_SMALL_MESSAGE_TAB, mainMessage.getMessage());
            }
            popUpSmallHBox.setVisible(true);
            upperSmallMessageAnimation();
        }
        if (message.messageBodyEquals("offDeleteConfirmation")) {
            popUpVBox.setVisible(false);
        }
        if (message.messageBodyEquals(Constant.MESSAGE_OFF_UPPER_SMALL_MESSAGE)) {
            popUpSmallHBox.setVisible(false);
        }
    }


    @PostConstruct
    /**
     * @PostConstruct annotated method will be executed when component is activated.
     * @param perspectiveLayout , the perspective layout let you register target layouts
     * @param layout, the component layout contains references to the toolbar and the menu
     * @param resourceBundle
     */
    public void onStartPerspective(final PerspectiveLayout perspectiveLayout, final FXComponentLayout layout,
                                   final ResourceBundle resourceBundle) {
        popUpVBox.setVisible(false);
        popUpSmallHBox.setVisible(false);
        perspectiveLayout.registerTargetLayoutComponent(BasicConfig.TARGET_CONTAINER_HEADER, topLayout);
        perspectiveLayout.registerTargetLayoutComponent(BasicConfig.TARGET_CONTAINER_TAB_COLUMN, leftLayout);
        perspectiveLayout.registerTargetLayoutComponent(BasicConfig.TARGET_CONTAINER_BODY_LAYOUT, bodyLayout);
        perspectiveLayout.registerTargetLayoutComponent(BasicConfig.TARGET_CONTAINER_DELETE_POPUP, popUpVBox);
        perspectiveLayout.registerTargetLayoutComponent(BasicConfig.TARGET_CONTAINER_UPPER_SMALL_MESSAGE_POPUP, popUpSmallHBox);
        log.info("on PostConstruct of MainPerspective");
    }

    @PreDestroy
    /**
     * @PreDestroy annotated method will be executed when component is deactivated.
     * @param layout, the component layout contains references to the toolbar and the menu
     */
    public void onTearDownPerspective(final FXComponentLayout layout) {
        log.info("on PreDestroy of MainPerspective");
    }

    private void upperSmallMessageAnimation() {
        popUpSmallHBox.setTranslateY(-popUpSmallHBox.getHeight() - 70);
        TranslateTransition downTransition = new TranslateTransition(Duration.seconds(0.2), popUpSmallHBox);
        downTransition.setToY(0); // Move to the center
        PauseTransition stayTransition = new PauseTransition(Duration.seconds(4));
        TranslateTransition upTransition = new TranslateTransition(Duration.seconds(0.2), popUpSmallHBox);
        upTransition.setToY(-popUpSmallHBox.getHeight()- 70); // Move to the top
        PauseTransition finalPause = new PauseTransition(Duration.seconds(0.1));
        finalPause.setOnFinished(event -> {
            popUpSmallHBox.setVisible(false);
            popUpSmallHBox.setTranslateY(0);
        });
        downTransition.setOnFinished(event -> stayTransition.play());
        stayTransition.setOnFinished(event -> upTransition.play());
        upTransition.setOnFinished(event -> finalPause.play());
        downTransition.play();
    }
}
