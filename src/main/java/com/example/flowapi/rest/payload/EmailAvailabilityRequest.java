package com.example.flowapi.rest.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class EmailAvailabilityRequest {
    @NotNull(message = "idp field required")
    @Pattern(regexp = "^(google|facebook)$", message = "Invalid idp value")
    private String idp;

    public String getIdp() {
        return idp;
    }

    public void setIdp(String idp) {
        this.idp = idp;
    }
}
