package com.codestart.backend.controllertest;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.codestart.backend.controller.UserController;
import com.codestart.backend.service.UserService;


@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    void registerUser_shouldReturn201() throws Exception {

        doNothing().when(userService).registerUser(
            org.mockito.ArgumentMatchers.any()
        );

        String request = """
            {
                "name": "Test",
                "email": "test@gmail.com",
                "password": "mypassword123"
            }
            """;

        mockMvc.perform(
                post("/api/users/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request)
            )
            .andExpect(status().isCreated());
    }

    
}