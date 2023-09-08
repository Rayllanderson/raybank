package com.rayllanderson.raybank.security.service;

import com.rayllanderson.raybank.models.User;
import com.rayllanderson.raybank.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("userDetailsService")
public class UserDetailsServiceImp {

    private final UserRepository repository;

    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));
    }

}
