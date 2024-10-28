package com.coral.users_service.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coral.users_service.model.User;
import com.coral.users_service.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //aqui comenzamos por crear un nuevo usuario
    public User createUser(User user) {
        //primero verificamos si ya existe
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalStateException("El nombre de usuario ya exise");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalStateException("El correo electronico ya esta registrado");
        }

        //antes de guardar el usuario encriptamos la contra
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    //Obtener usuario x ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    //Actualizar un usuario
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalStateException("Usario no encontrado"));

        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());

        //solo actualizar la password
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }

        return userRepository.save(user);
    }

    //Eliminar usuario x ID
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalStateException("El usuario no existe");
        }
        userRepository.deleteById(id);
    }

    //obtener usuario x username (pa probar el tema de la auth)
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
