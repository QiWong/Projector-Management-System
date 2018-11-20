package com.projector.management.server.util.customexceptions;

public class ReservationNotFoundException extends Exception{

    private String errorMessage;

    public ReservationNotFoundException(String errorMessage){
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        return this.errorMessage;
    }
}
