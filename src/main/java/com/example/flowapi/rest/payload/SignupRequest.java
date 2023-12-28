package com.example.flowapi.rest.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class SignupRequest {

    @NotNull(message = "step required")
    @Pattern(regexp = "[1-2]", message = "Invalid step value")
    private String step;
    @NotNull(message = "idp field required")
    @Pattern(regexp = "^(google|facebook)$", message = "Invalid idp value")
    private String idp;

    @JsonProperty("user_info")
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
