package com.example.flowapi.rest.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

public class UserInfoSignupRequest {

    @NotNull(message = "dni field required in user_info object")
    private String dni;
    @NotNull(message = "name field required in user_info object")
    private String name;
    @NotNull(message = "lastname_main field required in user_info object")
    @JsonProperty("lastname_main")
    private String lastName_main;

    @NotNull(message = "lastname_secondary field required in user_info object")
    @JsonProperty("lastname_secondary")
    private String lastName_secondary;

    @NotNull(message = "address field required in UserInfo object")
    private String address;

    public String getLastName_main() {
        return lastName_main;
    }

    public void setLastName_main(String lastName_main) {
        this.lastName_main = lastName_main;
    }

    public String getLastName_secondary() {
        return lastName_secondary;
    }

    public void setLastName_secondary(String lastName_secondary) {
        this.lastName_secondary = lastName_secondary;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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
