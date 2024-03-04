package com.example.flowapi.rest.payload;

public class EmailAvailabilityResponse {
    private boolean available;

    public EmailAvailabilityResponse(boolean available) {
        this.available = available;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

}
