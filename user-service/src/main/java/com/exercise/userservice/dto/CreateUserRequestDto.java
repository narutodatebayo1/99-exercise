package com.exercise.userservice.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateUserRequestDto {

    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
