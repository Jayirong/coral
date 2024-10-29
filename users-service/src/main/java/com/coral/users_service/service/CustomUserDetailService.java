package com.coral.users_service.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.coral.users_service.model.User;
import com.coral.users_service.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //a partir de aca buscamos el usuario den la base de datos
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el nombre: " + username));

        //Retorna una instancia de UserDetails
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            Collections.singletonList(new SimpleGrantedAuthority(user.getRol()))
        );
    }
}
