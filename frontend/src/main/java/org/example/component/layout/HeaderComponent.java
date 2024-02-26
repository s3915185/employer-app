package org.example.component.layout;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.example.EmployerNoSalaries;
import org.example.config.BasicConfig;
import org.example.constant.Constant;
import org.example.fragment.layout.HeaderFragment;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.component.View;
import org.jacpfx.api.message.Message;
import org.jacpfx.rcp.component.FXComponent;
import org.jacpfx.rcp.components.managedFragment.ManagedFragmentHandler;
import org.jacpfx.rcp.context.Context;
import org.jacpfx.rcp.util.FXUtil;

@View(id = BasicConfig.HEADER,
        name = "HeaderComponent",
        active = true,
        resourceBundleLocation = "bundles.languageBundle",
        initialTargetLayoutId = BasicConfig.TARGET_CONTAINER_HEADER)
public class HeaderComponent implements FXComponent {
    @Resource
    private Context context;

    private Node root;
    @Override
    public Node postHandle(Node node, Message<Event, Object> message) throws Exception {
        if (message.messageBodyEquals(FXUtil.MessageUtil.INIT)) {
            this.root = initHeaderFragment(Constant.SEARCH_HEADER, true);
        }
        if(message.messageBodyEquals(Constant.MESSAGE_SHOW_EMPLOYER_LABEL)) {
            this.root = initHeaderFragment(Constant.SEARCH_HEADER, true);
        }
        else if (message.messageBodyEquals(Constant.MESSAGE_SHOW_DETAIL_LABEL)) {
            this.root = initHeaderFragment(Constant.DETAIL_HEADER, true);
        }
        else if (message.isMessageBodyTypeOf(EmployerNoSalaries.class)) {
            EmployerNoSalaries employer = (EmployerNoSalaries) message.getMessageBody();
            this.root = initHeaderFragment(employer.getName(), false);
        }
        return this.root;
    }

    @Override
    public Node handle(Message<Event, Object> message) throws Exception {
        return null;
    }

    private Node initHeaderFragment(String name, boolean isDefault) {
        final VBox container = new VBox();
        VBox.setVgrow(container, Priority.ALWAYS);
        final ManagedFragmentHandler<HeaderFragment> handler = context.getManagedFragmentHandler(HeaderFragment.class);
        final HeaderFragment controller = handler.getController();
        controller.init(name, isDefault);
        container.getChildren().addAll(handler.getFragmentNode());
        return container;
    }
}
