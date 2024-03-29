/************************************************************************
 *
 * Copyright (C) 2010 - 2012
 *
 * [ApplicationLauncher.java]
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
package org.example.main;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.config.BasicConfig;
import org.example.exception.handler.GeneralExceptionHandler;
import org.example.workbench.JacpFXWorkbench;
import org.jacpfx.api.handler.ErrorDialogHandler;
import org.jacpfx.rcp.workbench.FXWorkbench;
import org.jacpfx.spring.launcher.AFXSpringJavaConfigLauncher;

/**
 * The application launcher containing the main method, the workbench definition, the configuration class and the packages to scan
 *
 * @author Andy Moncsek
 */
public class FrontendStarter extends AFXSpringJavaConfigLauncher {


    public FrontendStarter() {
    }

    @Override
    protected Class<? extends FXWorkbench> getWorkbenchClass() {
        return JacpFXWorkbench.class;
    }

    @Override
    protected String[] getBasePackages() {
        return new String[]{"org.example"};
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void postInit(Stage stage) {
        Scene scene = stage.getScene();
        // add style sheet
        scene.getStylesheets().addAll(
                FrontendStarter.class.getResource("/styles/default.css")
                        .toExternalForm()
        );
    }

    @Override
    protected ErrorDialogHandler<Node> getErrorHandler() {
        return new GeneralExceptionHandler();
    }

    @Override
    protected Class<?>[] getConfigClasses() {
        return new Class<?>[]{BasicConfig.class};
    }
}
