package com.example.test.wrapper;

import com.example.test.DTO.UserDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Wrapper {
    @Valid
    @NotEmpty(message = "User list cannot be empty")
    private List<UserDTO> users;

    @Valid
    private UserDTO user;
}
