package com.codestart.backend.serviceTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.codestart.backend.dto.UserRegistrationRequest;
import com.codestart.backend.exception.EmailExistsException;
import com.codestart.backend.exception.InvalidRegistrationDataException;
import com.codestart.backend.repository.UserRepository;
import com.codestart.backend.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repo;

    @InjectMocks
    private UserService userService;

    @Test
    void registerUser_shouldThrowExceptionWhenNameIsEmpty() {

        UserRegistrationRequest dto = new UserRegistrationRequest();

        dto.setName("");
        dto.setEmail("test@gmail.com");
        dto.setPassword("mypassword123");

        assertThrows(
            InvalidRegistrationDataException.class,
            () -> userService.registerUser(dto)
        );
    }

    @Test
    void registerUser_shouldThrowExceptionWhenEmailIsEmpty() {

        UserRegistrationRequest dto = new UserRegistrationRequest();

        dto.setName("Test");
        dto.setEmail("");
        dto.setPassword("mypassword123");

        assertThrows(
            InvalidRegistrationDataException.class,
            () -> userService.registerUser(dto)
        );
    }

    @Test
    void registerUser_shouldThrowExceptionWhenPasswordIsLessThan8() {

        UserRegistrationRequest dto = new UserRegistrationRequest();

        dto.setName("Test");
        dto.setEmail("test@gmail.com");
        dto.setPassword("1234567");

        assertThrows(
            InvalidRegistrationDataException.class,
            () -> userService.registerUser(dto)
        );
    }

    @Test
    void registerUser_shouldThrowExceptionWhenEmailExists() {

        UserRegistrationRequest dto = new UserRegistrationRequest();

        dto.setName("Test");
        dto.setEmail("test@gmail.com");
        dto.setPassword("mypassword123");

        when(repo.existsByEmail("test@gmail.com"))
            .thenReturn(true);

        assertThrows(
            EmailExistsException.class,
            () -> userService.registerUser(dto)
        );
    }
}