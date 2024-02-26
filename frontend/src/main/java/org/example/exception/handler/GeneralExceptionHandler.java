package org.example.exception.handler;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import org.jacpfx.api.handler.ErrorDialogHandler;
import org.jacpfx.rcp.components.errorDialog.DefaultErrorDialog;
import org.jacpfx.rcp.context.Context;

import java.io.PrintWriter;
import java.io.StringWriter;


public class GeneralExceptionHandler implements ErrorDialogHandler<Node>, Thread.UncaughtExceptionHandler {
    public static final ObjectProperty<Context> CONTEXT_PROPERTY = new SimpleObjectProperty<>();

    public GeneralExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(this);                    // control all uncaught exceptions
    }
    @Override
    public void handleExceptionInDialog(Throwable e) {
//        CONTEXT_PROPERTY.get().send(BasicConfig.PERSPECTIVE_MAIN, e);      // show exception in ErrorHandlingFragment
        printErrorDetailToConsole(e);
    }

    @Override
    public Node createExceptionDialog(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        return new DefaultErrorDialog("Something Else",sw.toString());
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        handleExceptionInDialog(e);
    }

    private void printErrorDetailToConsole(Throwable throwable) {
        System.out.println();
        throwable.printStackTrace();
        System.out.println();
    }
}
