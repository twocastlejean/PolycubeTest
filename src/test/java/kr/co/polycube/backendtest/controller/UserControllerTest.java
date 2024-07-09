package kr.co.polycube.backendtest.controller;

import kr.co.polycube.backendtest.domain.User;
import kr.co.polycube.backendtest.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void createUserTest() throws Exception {
        mockMvc.perform(post("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void getUserTest() throws Exception {
        //given
        User user = new User();
        User savedUser = userRepository.save(user);

        //then
        mockMvc.perform(get("/users/" + savedUser.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedUser.getId()))
                .andExpect(jsonPath("$.name").isEmpty());
    }

    @Test
    public void modifyUserTest() throws Exception {
        //given
        User user = new User();
        User savedUser = userRepository.save(user);

        //when
        String name = "polycube";

        //then
        mockMvc.perform(patch("/users/" + savedUser.getId())
                        .param("name", name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedUser.getId()))
                .andExpect(jsonPath("$.name").value(name));
    }

    //id에 해당하는 user가 없는 경우, user 수정 시 param이 비어있는 경우에 대한 예외는 GlobalExceptionHandlerTest에 있다.
}