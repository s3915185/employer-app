package org.example.component.message;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.example.EmployerNoSalaries;
import org.example.config.BasicConfig;
import org.example.fragment.message.DeleteConfirmationFragment;
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
import java.util.logging.Logger;

@View(id = BasicConfig.DELETE_MESSAGE_TAB,
        name = "DeleteConfirmationComponent",
        active = true,
        resourceBundleLocation = "bundles.languageBundle",
        localeID = "en_US",
        initialTargetLayoutId = BasicConfig.TARGET_CONTAINER_DELETE_POPUP)
public class DeleleConfirmationComponent implements FXComponent {
    @Resource
    private Context context;

    private Node root;

    private Logger log = Logger.getLogger(DeleleConfirmationComponent.class.getName());

    @Override
    public Node postHandle(Node node, Message<Event, Object> message) throws Exception {
        if (message.messageBodyEquals(FXUtil.MessageUtil.INIT)) {
            this.root = initFragment(DeleteConfirmationFragment.class, null);
        }
        if (message.isMessageBodyTypeOf(EmployerNoSalaries.class)) {
            EmployerNoSalaries employerNoSalaries = (EmployerNoSalaries) message.getMessageBody();
            this.root = initFragment(DeleteConfirmationFragment.class, (EmployerNoSalaries) message.getMessageBody());
        }
        return this.root;
    }

    @Override
    public Node handle(Message<Event, Object> message) throws Exception {
        return null;
    }

    private Node initFragment(Class<? extends ManagedFragment> fragmentClass, EmployerNoSalaries employer) {
        final VBox container = new VBox();
        VBox.setVgrow(container, Priority.ALWAYS);
        final ManagedFragmentHandler<? extends ManagedFragment> handler = context.getManagedFragmentHandler(fragmentClass);
        final ManagedFragment controller = handler.getController();
        try {
            Method initMethod = fragmentClass.getDeclaredMethod("init", EmployerNoSalaries.class);
            initMethod.invoke(controller, employer);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        container.getChildren().addAll(handler.getFragmentNode());
        return container;
    }
}
