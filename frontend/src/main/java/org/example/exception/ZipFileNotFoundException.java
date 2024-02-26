package org.example.exception;

public class ZipFileNotFoundException extends Exception {
    public ZipFileNotFoundException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    public ZipFileNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
