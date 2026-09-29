package com.codestart.backend.serviceTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.codestart.backend.dto.UserLoginRequest;
import com.codestart.backend.dto.UserRegistrationRequest;
import com.codestart.backend.entity.User;
import com.codestart.backend.exception.EmailExistsException;
import com.codestart.backend.exception.InvalidLoginDataException;
import com.codestart.backend.exception.InvalidRegistrationDataException;
import com.codestart.backend.repository.UserRepository;
import com.codestart.backend.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repo;

    @InjectMocks
    private UserService userService;
    
    @Mock
	private PasswordEncoder encoder;
    
    // Registartion Test

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
    
    //Login Test
    @Test
    void LoginrUser_shouldThrowExceptionWhenEmailIsEmpty() {

        UserLoginRequest dto = new UserLoginRequest();

        dto.setEmail("");
        dto.setPassword("mypassword123");

        assertThrows(
            InvalidLoginDataException.class,
            () -> userService.loginUser(dto)
        );
    }

    
    @Test
    void loginUser_shouldThrowExceptionWhenPasswordIsEmpty() {

        UserLoginRequest dto = new UserLoginRequest();

        dto.setEmail("test@gmail.com");
        dto.setPassword("");

        assertThrows(
            InvalidLoginDataException.class,
            () -> userService.loginUser(dto)
        );
    }

    @Test
    void loginUser_shouldThrowExceptionWhenPasswordDoesnotMatches() {

        UserLoginRequest dto = new UserLoginRequest();

        
        dto.setEmail("test@gmail.com");
        dto.setPassword("123456");

        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("encodedPassword");

        when(repo.findByEmail("test@gmail.com"))
            .thenReturn(Optional.of(user));

        when(encoder.matches("123456", "encodedPassword"))
            .thenReturn(false);

        

        assertThrows(
                InvalidLoginDataException.class,
                () -> userService.loginUser(dto)
            );
    }
    
    
}