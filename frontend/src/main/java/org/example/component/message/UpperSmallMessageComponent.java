package org.example.component.message;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.example.EmployerNoSalaries;
import org.example.config.BasicConfig;
import org.example.fragment.message.DeleteConfirmationFragment;
import org.example.fragment.message.UpperSmallMessageFragment;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.component.View;
import org.jacpfx.api.message.Message;
import org.jacpfx.rcp.component.FXComponent;
import org.jacpfx.rcp.components.managedFragment.ManagedFragment;
import org.jacpfx.rcp.components.managedFragment.ManagedFragmentHandler;
import org.jacpfx.rcp.context.Context;
import org.jacpfx.rcp.util.FXUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@View(id = BasicConfig.UPPER_SMALL_MESSAGE_TAB,
        name = "UpperSmallMessageComponent",
        active = true,
        resourceBundleLocation = "bundles.languageBundle",
        localeID = "en_US",
        initialTargetLayoutId = BasicConfig.TARGET_CONTAINER_UPPER_SMALL_MESSAGE_POPUP)
public class UpperSmallMessageComponent implements FXComponent {
    @Resource
    private Context context;

    private Node root;
    @Override
    public Node postHandle(Node node, Message<Event, Object> message) throws Exception {
        if (message.messageBodyEquals(FXUtil.MessageUtil.INIT)) {}
        if (message.isMessageBodyTypeOf(String.class)) {
            this.root = initFragment(UpperSmallMessageFragment.class, message.getMessageBody().toString());
        }
        return this.root;
    }

    @Override
    public Node handle(Message<Event, Object> message) throws Exception {
        return null;
    }

    private Node initFragment(Class<? extends ManagedFragment> fragmentClass, String message) {
        final VBox container = new VBox();
        VBox.setVgrow(container, Priority.ALWAYS);
        final ManagedFragmentHandler<? extends ManagedFragment> handler = context.getManagedFragmentHandler(fragmentClass);
        final ManagedFragment controller = handler.getController();
        try {
            Method initMethod = fragmentClass.getDeclaredMethod("init", String.class);
            initMethod.invoke(controller, message);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        container.getChildren().addAll(handler.getFragmentNode());
        return container;
    }
}
