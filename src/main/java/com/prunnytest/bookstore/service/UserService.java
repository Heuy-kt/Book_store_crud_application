package com.prunnytest.bookstore.service;

import com.prunnytest.bookstore.requests.UserDto;
import com.prunnytest.bookstore.responses.UserResponseDto;
import com.prunnytest.bookstore.exception.AlreadyExistsException;
import com.prunnytest.bookstore.exception.NotFoundException;
import com.prunnytest.bookstore.model.Book;
import com.prunnytest.bookstore.model.User;

import java.util.List;

public interface UserService{
    UserResponseDto saveUser(UserDto userDto) throws AlreadyExistsException;
    UserResponseDto updateUser(Long id, UserDto userDto) throws NotFoundException;
    List<UserResponseDto> listAllUsers();
    UserResponseDto getUserByName(String name) throws NotFoundException;
    void deleteUser(Long id) throws NotFoundException;
    Book getABook(Long id, User user) throws RuntimeException;
    Book getABook (String title, User user) throws RuntimeException;
}
