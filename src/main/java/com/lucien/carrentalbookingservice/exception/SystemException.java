package com.lucien.carrentalbookingservice.exception;

public class SystemException extends RuntimeException {

    public SystemException() {
        super();
    }
    public SystemException(String message) {
        super(message, null, true, false);
    }


}
