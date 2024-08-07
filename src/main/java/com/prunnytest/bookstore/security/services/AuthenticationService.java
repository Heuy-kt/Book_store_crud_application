package com.prunnytest.bookstore.security.services;

import com.prunnytest.bookstore.responses.AuthenticationResponseDto;
import com.prunnytest.bookstore.requests.UserDto;

import com.prunnytest.bookstore.requests.UserDtoLogin;
import com.prunnytest.bookstore.exception.AlreadyExistsException;
import com.prunnytest.bookstore.exception.NotAccessibleException;
import com.prunnytest.bookstore.model.User;
import com.prunnytest.bookstore.model.enums.Roles;
import com.prunnytest.bookstore.repository.UserRepository;
import com.prunnytest.bookstore.security.jwt.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class AuthenticationService  {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponseDto register(UserDto userDto) {
        if(userRepository.findByUsername(userDto.userName()).isPresent() || userRepository.findByEmail(userDto.email()).isPresent()){
            throw new AlreadyExistsException("EMAIL OR USERNAME ALREADY EXISTS");
        }
        User user = User.builder()
                .firstName(userDto.firstName())
                .lastName(userDto.lastName())
                .username(userDto.userName())
                .email(userDto.email())
                .password(passwordEncoder.encode(userDto.password()))
                .role(userDto.role())
                .build();
        userRepository.save(user);
        String token = jwtService.generateToken(user);

        return new AuthenticationResponseDto(token);
    }

    public AuthenticationResponseDto authenticate(UserDtoLogin userRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequest.username(),
                        userRequest.password()
                )
        );
        User user = userRepository.findByUsername(userRequest.username())
                .orElseThrow(() -> new NotAccessibleException(format("Having errors, or not permitted", userRequest.username())));

        String token = jwtService.generateToken(user);
        return new AuthenticationResponseDto(token);
    }
}
