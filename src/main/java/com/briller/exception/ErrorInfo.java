package com.briller.exception;

public class ErrorInfo {

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String details;
    private String message;

    public ErrorInfo(String message, String details) {
        this.details = details;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorInfo{" +
                "message=" + message +
                ", details='" + details + '\'' +
                '}';
    }
}
