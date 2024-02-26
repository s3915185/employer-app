package org.example.exception;


/*
    FILE EXTENSION NOT FOUND EXCEPTON: RESPONSIBLE FOR THROWING EXCEPTION
    WHEN THE PREDEFINED FILE DOES NOT HAVE ANY EXTENSIONS
 */
public class FileExtensionNotFoundException extends RuntimeException{
    public FileExtensionNotFoundException(String errorMessage) {
        super(errorMessage);
    }
    public FileExtensionNotFoundException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
