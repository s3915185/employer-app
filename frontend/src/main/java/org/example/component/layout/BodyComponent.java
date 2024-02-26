package org.example.component.layout;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.example.EmployerNoSalaries;
import org.example.model.alert.ConfirmationAlert;
import org.example.config.BasicConfig;
import org.example.constant.Constant;
import org.example.factory.ObservableResourceFactory;
import org.example.fragment.layout.DetailBodyFragment;
import org.example.fragment.layout.SearchBodyFragment;
import org.example.model.message.CustomSearchViewObject;
import org.example.model.view.DetailViewObject;
import org.example.model.view.SearchViewObject;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.component.View;
import org.jacpfx.api.message.Message;
import org.jacpfx.rcp.component.FXComponent;
import org.jacpfx.rcp.components.managedFragment.ManagedFragment;
import org.jacpfx.rcp.components.managedFragment.ManagedFragmentHandler;

import org.jacpfx.rcp.context.Context;
import org.jacpfx.rcp.util.FXUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@View(id = BasicConfig.BODY_TAB,
        name = "SearchBodyComponent",
        active = true,
        resourceBundleLocation = "bundles.languageBundle",
        initialTargetLayoutId = BasicConfig.TARGET_CONTAINER_BODY_LAYOUT)
public class BodyComponent implements FXComponent {
    @Resource
    private Context context;

    private boolean detailModified = false;
    private Node root;

    @Autowired
    private ObservableResourceFactory observableResourceFactory;
    @Override
    public Node postHandle(Node node, Message<Event, Object> message) throws Exception {
        if (message.messageBodyEquals(FXUtil.MessageUtil.INIT)) {
            this.root = initFragment(SearchBodyFragment.class,null, null);
            return this.root;
        }
        if (message.messageBodyEquals(Constant.MESSAGE_MODIFIED_FALSE)) {
            detailModified = false;
            return this.root;
        }
        if (detailModified) {
            ConfirmationAlert.ConfirmationAlertBuilder confirmationAlertBuilder = ConfirmationAlert.builder();
            ConfirmationAlert confirmationAlert = confirmationAlertBuilder.build();
            ObservableResourceFactory.Language language = observableResourceFactory.getLanguage();
            boolean confirmed = confirmationAlert.showExitConfirmationDialog(language);
            if (confirmed) {detailModified = false;} else {return this.root; }
        }
        if (message.messageBodyEquals(Constant.MESSAGE_MODIFIED)) {
            detailModified = true;
        }
        if(message.messageBodyEquals(Constant.MESSAGE_SHOW_DETAIL_BODY_TAB)) {
            this.root = initFragment(DetailBodyFragment.class,null, null);
        }
        else if(message.messageBodyEquals(Constant.MESSAGE_SHOW_SEARCH_BODY_TAB)) {
            this.root = initFragment(SearchBodyFragment.class,null, null);
        }

        if (message.isMessageBodyTypeOf(EmployerNoSalaries.class)) {
            this.root = initFragment(DetailBodyFragment.class, null, message.getMessageBody());
        }
        else if (message.messageBodyEquals(Constant.MESSAGE_DELETE)) {
            this.root = initFragment(DetailBodyFragment.class, Constant.MESSAGE_DELETE, message.getMessageBody());
        }
        else if (message.messageBodyEquals(Constant.MESSAGE_ADD_NEW)) {
            this.root = initFragment(DetailBodyFragment.class, Constant.MESSAGE_ADD_NEW, null);
        }
        else if (message.isMessageBodyTypeOf(SearchViewObject.class)) {
            this.root = initFragment(SearchBodyFragment.class,null, message.getMessageBody());
        }
        else if (message.isMessageBodyTypeOf(DetailViewObject.class)) {
            this.root = initFragment(DetailBodyFragment.class,null, message.getMessageBody());
        }
        else if (message.isMessageBodyTypeOf(CustomSearchViewObject.class)) {
            CustomSearchViewObject object = (CustomSearchViewObject) message.getMessageBody();
            this.root = initFragment(SearchBodyFragment.class, object.getMethod(), object.getSearchViewObject());
        }
        return this.root;
    }

    @Override
    public Node handle(Message<Event, Object> message) throws Exception {
        return null;
    }

    private Node initFragment(Class<? extends ManagedFragment> fragmentClass, String method, Object object) {
        final VBox container = new VBox();
        VBox.setVgrow(container, Priority.ALWAYS);
        final ManagedFragmentHandler<? extends ManagedFragment> handler = context.getManagedFragmentHandler(fragmentClass);
        final ManagedFragment controller = handler.getController();
        try {
            Method initMethod = fragmentClass.getDeclaredMethod("init", String.class, Object.class);
            initMethod.invoke(controller, method, object);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        container.getChildren().addAll(handler.getFragmentNode());
        return container;
    }
}
