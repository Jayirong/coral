package com.coral.users_service.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coral.users_service.model.User;
import com.coral.users_service.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    //Endpoint para nuevo usuario (Post)
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User newUser = userService.createUser(user);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    //Get por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    //Actualizar usuario (Put)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        try {
            User updatedUser = userService.updateUser(id, userDetails);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //Eliminar un usuario (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>("Usuario eliminado con éxito", HttpStatus.OK);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    //obtener un usuario por username (pa lo del auth)
    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.findByUsername(username);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
    }

}
