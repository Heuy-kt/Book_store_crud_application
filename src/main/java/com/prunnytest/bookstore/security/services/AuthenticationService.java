package com.prunnytest.bookstore.security.services;

import com.prunnytest.bookstore.dtos.AuthenticationResponseDto;
import com.prunnytest.bookstore.dtos.UserDto;

import com.prunnytest.bookstore.exception.NotFoundException;
import com.prunnytest.bookstore.model.User;
import com.prunnytest.bookstore.model.enums.Roles;
import com.prunnytest.bookstore.repository.UserRepository;
import com.prunnytest.bookstore.security.jwt.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class AuthenticationService  {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponseDto register(UserDto userDto) {
        User user = User.builder()
                .firstName(userDto.firstName())
                .lastName(userDto.lastName())
                .username(userDto.userName())
                .email(userDto.email())
                .password(userDto.password())
                .role(Roles.USER)
                .build();
        userRepository.save(user);
        String token = jwtService.generateToken(user);

        return new AuthenticationResponseDto(token);
    }

    public AuthenticationResponseDto authenticate(UserDto userRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequest.userName(),
                        userRequest.password()
                )
        );
        User user = userRepository.findByUsername(userRequest.userName())
                .orElseThrow(() -> new NotFoundException(format("%s has errors", userRequest.userName())));

        String token = jwtService.generateToken(user);
        return new AuthenticationResponseDto(token);
    }
}
