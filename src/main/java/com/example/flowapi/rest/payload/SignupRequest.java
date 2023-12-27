package com.example.flowapi.rest.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class SignupRequest {

    @NotNull(message = "step required")
    @Pattern(regexp = "[1-3]", message = "Invalid step value")
    private String step;
    @NotNull(message = "idp required")
    private String idp;

    private UserInfoSignupRequest userInfo;

    SignupRequest() {};
    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

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
                "step='" + step + '\'' +
                ", idp='" + idp + '\'' +
                ", userInfo=" + userInfo +
                '}';
    }
}
