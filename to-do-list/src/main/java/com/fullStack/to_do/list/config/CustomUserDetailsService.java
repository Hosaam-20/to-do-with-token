package com.fullStack.to_do.list.config;


import com.fullStack.to_do.list.model.entity.Role;
import com.fullStack.to_do.list.model.entity.UserEntity;
import com.fullStack.to_do.list.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String input) throws UsernameNotFoundException {
        UserEntity user;

        if (input.contains("@")) {
            // لو المدخل فيه @ → تعامل معه كإيميل
            user = userRepository.findByEmail(input)
                    .orElseThrow(() -> new UsernameNotFoundException("Email not found"));
        } else {
            // غير كذا → اعتبره username
            user = userRepository.findByUsername(input)
                    .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        }

        return new CustomUserDetails(
                user.getUserId(),
                user.getUsername(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles())
        );
    }


    private Collection<GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
    }
}
