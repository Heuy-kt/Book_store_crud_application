package com.prunnytest.bookstore.service.Impl;

import com.prunnytest.bookstore.dtos.UserDto;
import com.prunnytest.bookstore.dtos.UserResponseDto;
import com.prunnytest.bookstore.exception.AlreadyExistsException;
import com.prunnytest.bookstore.exception.NotAccessibleException;
import com.prunnytest.bookstore.exception.NotFoundException;
import com.prunnytest.bookstore.model.Book;
import com.prunnytest.bookstore.model.User;
import com.prunnytest.bookstore.model.enums.Roles;
import com.prunnytest.bookstore.repository.BookRepository;
import com.prunnytest.bookstore.repository.UserRepository;
import com.prunnytest.bookstore.service.UserService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    ModelMapper modelMapper = new ModelMapper();

    @Override
    public UserResponseDto saveUser(UserDto userDto) throws AlreadyExistsException{
        if(userRepository.findByUsername(userDto.userName()).isPresent()){
            throw new AlreadyExistsException("USERNAME ALREADY EXISTS");
        }
        var user = User.builder()
                .firstName(userDto.firstName())
                .lastName(userDto.lastName())
                .username(userDto.userName())
                .email(userDto.email())
                .password(userDto.password())
                .role(Roles.USER)
                .build();
        userRepository.save(user);
        return UserResponseDto
                .builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    @Override
    public UserResponseDto updateUser(Long id, UserDto userDto) throws NotFoundException {
        var user = userRepository.findByUsername(userDto.userName())
                .orElseThrow(()->
                        new NotFoundException(format("$s not found, and cant be updated", userDto.userName()))
                );
        mergeUser(user, userDto);
        userRepository.save(user);
        log.info("Author successfully updated");

        return modelMapper.map(user, UserResponseDto.class);

    }

    @Override
    public List<UserResponseDto> listAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(this::convertToUserResponseDto)
                .collect(Collectors.toList());
    }

    private UserResponseDto convertToUserResponseDto(User user) {
        return new UserResponseDto(
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail()
        );
    }

    @Override
    public UserResponseDto getUserByName(String name) throws NotFoundException {
        return null;
    }

    @Override
    public void deleteUser(Long id) throws NotFoundException {
        userRepository.deleteById(id);

    }

    @Override
    public Book getABook(Long id, User user) throws RuntimeException{
        var book = bookRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Cant find book with this ID")
        );
        return book;
    }

    @Override
    public Book getABook(String title, User user) throws RuntimeException{
        var book = bookRepository.findBookByTitle(title).orElseThrow(() ->
                new NotFoundException("Cant find book with that title"));
        return book;
    }

    private void mergeUser(User user, UserDto userDto) {
        if(StringUtils.isNotBlank(userDto.firstName()))
            user.setFirstName(userDto.firstName());

        if(StringUtils.isNotBlank(userDto.lastName()))
            user.setLastName(userDto.lastName());

        if(StringUtils.isNotBlank(userDto.email()))
            user.setEmail(userDto.email());

        if(StringUtils.isNotBlank(userDto.password()))
            user.setEmail(userDto.email());


    }
}
