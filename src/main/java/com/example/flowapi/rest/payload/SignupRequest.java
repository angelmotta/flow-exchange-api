package com.example.flowapi.rest.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class SignupRequest {

    @NotNull(message = "idp field required")
    @Pattern(regexp = "^(google|facebook)$", message = "Invalid idp value")
    private String idp;

    @NotNull(message = "user_info field required")
    @JsonProperty("user_info")
    @Valid
    private UserInfoSignupRequest userInfo;

    SignupRequest() {};

    public String getIdp() {
        return idp;
    }

    public void setIdp(String idp) {
        this.idp = idp;
    }

    public UserInfoSignupRequest getUserInfo() {
        return userInfo;
    }

    @Override
    public String toString() {
        return "SignupRequest{" +
                ", idp='" + idp + '\'' +
                ", userInfo=" + userInfo +
                '}';
    }
}
