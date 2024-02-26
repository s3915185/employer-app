/************************************************************************
 *
 * Copyright (C) 2010 - 2012
 *
 * [JacpFXWorkbench.java]
 * AHCP Project (http://jacp.googlecode.com)
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 *
 *
 ************************************************************************/
package org.example.workbench;

import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.constant.BundleKey;
import org.example.factory.ObservableResourceFactory;
import org.jacpfx.api.annotations.Resource;
import org.jacpfx.api.annotations.workbench.Workbench;
import org.jacpfx.api.componentLayout.WorkbenchLayout;
import org.jacpfx.api.message.Message;
import org.jacpfx.api.util.ToolbarPosition;
import org.jacpfx.controls.optionPane.JACPDialogButton;
import org.jacpfx.controls.optionPane.JACPDialogUtil;
import org.jacpfx.controls.optionPane.JACPOptionPane;
import org.jacpfx.rcp.componentLayout.FXComponentLayout;
import org.jacpfx.rcp.components.menuBar.JACPMenuBar;
import org.jacpfx.rcp.context.Context;
import org.jacpfx.rcp.workbench.FXWorkbench;
import org.example.config.BasicConfig;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A simple JacpFX workbench
 *
 * @author Andy Moncsek
 */
@Workbench(id = "id1", name = "workbench",
        perspectives = {
                BasicConfig.PERSPECTIVE_MAIN
        })
public class JacpFXWorkbench implements FXWorkbench {

    @Resource
    private Context context;
    @Autowired
    private ObservableResourceFactory observableResourceFactory;

    @Override
    public void handleInitialLayout(final Message<Event, Object> action,
                                    final WorkbenchLayout<Node> layout, final Stage stage) {
        layout.setWorkbenchXYSize(1280 , 900);
//        layout.registerToolBar(ToolbarPosition.NORTH);
        layout.setStyle(StageStyle.DECORATED);
        stage.setResizable(false);
        stage.titleProperty().bind(observableResourceFactory.getStringBinding(BundleKey.HEADER));
        Image icon = new Image("images/logo_elca.png");
        stage.getIcons().add(icon);
//        layout.setMenuEnabled(true);
    }

    @Override
    public void postHandle(final FXComponentLayout layout) {

    }


}
