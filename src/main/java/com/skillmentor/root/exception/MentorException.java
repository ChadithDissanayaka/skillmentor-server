package com.skillmentor.root.exception;

public class MentorException extends Exception {

    public MentorException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public MentorException(String message) {
        super(message);
    }
}