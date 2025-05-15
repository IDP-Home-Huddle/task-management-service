package com.mobylab.springbackend.config.security;

import com.mobylab.springbackend.service.RequestService;
import com.mobylab.springbackend.service.dto.entity.user.UserDetailsResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private RequestService requestService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ResponseEntity<UserDetailsResponseDto> response = requestService.sendGetRequest(
                "/userproxy/getByEmail",
                Collections.singletonMap("email", email),
                new ParameterizedTypeReference<>(){});

        if (response.getBody() != null) {
            UserDetailsResponseDto userResponseDto = response.getBody();
            return new User(userResponseDto.getEmail(), userResponseDto.getPassword(), mapRolesToAuthorities(userResponseDto.getRoles()));
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<String> roles){
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
