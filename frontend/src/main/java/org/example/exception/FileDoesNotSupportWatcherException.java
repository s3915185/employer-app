package org.example.exception;

import java.io.IOException;


/*
    FILE DOES NOT SUPPORT WATCHER EXCEPTION: RESPONSIBLE FOR THROWING EXCEPTIONS
    WHEN THE FILE CANNOT BE IMPLEMENTED THE WATCHER
 */
public class FileDoesNotSupportWatcherException extends IOException {
    public FileDoesNotSupportWatcherException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
