package com.projector.management.server.util.customexceptions;

/**
 * Customized exception class, indicates that there is no reservation found based on the request
 * @author Qi Wang
 */
public class ReservationNotFoundException extends Exception{
    //Error Message of the exception
    private String errorMessage;

    /**
     * Constructor
     * @param errorMessage String respresents the error message
     */
    public ReservationNotFoundException(String errorMessage){
        this.errorMessage = errorMessage;
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
