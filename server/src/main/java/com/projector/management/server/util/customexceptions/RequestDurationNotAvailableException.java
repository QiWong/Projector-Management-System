package com.projector.management.server.util.customexceptions;

import com.projector.management.server.model.Duration;

/**
 * Customized exception class, indicates that there is no available projector for the requested duration.
 * @author Qi Wang
 */
public class RequestDurationNotAvailableException extends Exception {
    //Error Message of the exception
    private String errorMessage;

    /**
     * Constructor
     * @param duration Duration object
     */
    public RequestDurationNotAvailableException(Duration duration){
        this.errorMessage = "Request Duration "+duration.toString()+" is not available";
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
