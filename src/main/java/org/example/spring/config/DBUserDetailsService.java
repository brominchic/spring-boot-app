package org.example.spring.config;

import org.example.spring.model.entity.UserEntity;
import org.example.spring.repositories.RoleRepository;
import org.example.spring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class DBUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity user = userRepository.findByLogin(username).orElseThrow();
        return new DBUserDetails(user, roleRepository.findById(user.getRole()).orElseThrow().getName());
    }

}
