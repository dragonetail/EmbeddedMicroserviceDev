package com.baeldung.service;

import com.baeldung.model.User;
import com.baeldung.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 */
@Slf4j
@Service
public class OAuthUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public OAuthUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user != null) {
            user.getRoles().size();
            user.getAuthorities().size();
            return user;
        } else {
            throw new UsernameNotFoundException("User not found!");
        }
    }

}