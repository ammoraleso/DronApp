package com.DronApp.Exception;

public class FileException extends Exception {

    public FileException() {
    }

    public FileException(String message) {
        super(message);
    }

    public FileException(String message, Throwable e) {
        super(message, e);
    }

}
