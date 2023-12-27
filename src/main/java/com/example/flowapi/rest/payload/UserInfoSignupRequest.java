package com.example.flowapi.rest.payload;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

public class UserInfoSignupRequest {

    @NotNull(message = "dni field required")
    private String dni;
    @NotNull(message = "name field required")
    private String name;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserInfoSignupRequest{" +
                "dni='" + dni + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
