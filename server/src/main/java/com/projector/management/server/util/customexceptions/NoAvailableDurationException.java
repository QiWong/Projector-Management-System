package com.projector.management.server.util.customexceptions;

/**
 * Customized exception class, indicates that there is no available duration.
 * @author Qi Wang
 */
public class NoAvailableDurationException extends Exception{
    //Error Message of the exception
    private String errorMessage;

    /**
     * Constructor
     * @param fromDate The start date
     * @param toDate Then end date
     */
    public NoAvailableDurationException(String fromDate, String toDate) {
        this.errorMessage = "Projectors are not available from "+ fromDate + " to "+toDate;
    }

    /**
     * Get the error message of the exception
     * @return String error message of the exception
     */
    @Override
    public String getMessage() {
        return this.errorMessage;
    }
}
