package com.example.test;

import com.example.test.DTO.UserDTO;
import com.example.test.wrapper.Wrapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.test.Service.UserService;
import java.util.List;

@RestController
@RequestMapping(path = "/test")
public class TestController {
    @Autowired
    UserService userService;

    @GetMapping(path = "/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {

        return ResponseEntity.ok(userService.getAllUsers());
    }
    @GetMapping("/user")
    public ResponseEntity<UserDTO> getUserById(@RequestParam int id) {
        UserDTO user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }
    @PostMapping(path = "/saveUser")
    public ResponseEntity<UserDTO> saveUser(@Valid @RequestBody UserDTO userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(userDto));
    }
    @PostMapping(path = "/saveUsers")
    public ResponseEntity<List<UserDTO>> saveUsers(@Valid @RequestBody Wrapper users) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUsers(users.getUsers()));
    }

    @PutMapping(path = "/updateUser")
    public ResponseEntity<UserDTO> updateUser(@RequestParam Integer id, @Valid @RequestBody UserDTO userdto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updateUser(id, userdto));
    }

    @PutMapping(path = "/updateUsers")
    public ResponseEntity<List<UserDTO>> updateUsers(@Valid @NotEmpty(message = "User list cannot be empty") @RequestBody List<@Valid UserDTO> userDTOS) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updateUsers(userDTOS));
    }

    @DeleteMapping(path = "/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestParam int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Deleted User with id: " + id);
    }

    @DeleteMapping(path = "deleteUsers")
    public ResponseEntity<String> deleteUsers(@RequestBody List<Integer> ids) {
        userService.deleteUsers(ids);
        return ResponseEntity.ok("Deleted All Users with ids");
    }
}
