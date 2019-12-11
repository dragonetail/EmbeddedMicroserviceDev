package com.github.dragonetail.service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.github.dragonetail.web.dto.AuthPocResultDTO;

@Service
public class AuthPocService {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public AuthPocResultDTO byId(long id) {
        return new AuthPocResultDTO(id, randomAlphabetic(4));
    }

}
