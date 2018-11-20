package com.projector.management.server.util.customexceptions;

public class NoAvailableDurationException extends Exception{
    private String errorMessage;

    public NoAvailableDurationException(String fromDate, String toDate) {
        this.errorMessage = "Projectors are not available from "+ fromDate + " to "+toDate;
    }

    @Override
    public String getMessage() {
        return this.errorMessage;
    }
}
