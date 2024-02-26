package org.example.exception;

import java.io.IOException;

/*
    FILE NOT IDENTIFIABLE EXCEPTION: RESPONSIBLE FOR THROWING EXCEPTION WHEN THE FILE IS NOT PROCESSABLE,
    WHICH MEAN THAT THE FILE ISN'T SUPPORTED IN THIS APPLICATION
 */
public class FileNotIdentifiableException extends IOException {
    public FileNotIdentifiableException(String errorMessage) {
        super(errorMessage);
    }
}
