package com.projector.management.server.util.customexceptions;

import com.projector.management.server.model.Duration;

public class RequestDurationNotAvailableException extends Exception {
    private String errorMessage;

    public RequestDurationNotAvailableException(Duration duration){
        this.errorMessage = "Request Duration "+duration.toString()+" is not available";
    }

    @Override
    public String getMessage() {
        return this.errorMessage;
    }
}
