package kr.co.polycube.backendtest.config;

import kr.co.polycube.backendtest.domain.User;
import kr.co.polycube.backendtest.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SpecialCharacterFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
    }

    @Test
    public void testValidUrl() throws Exception {
        //then
        mockMvc.perform(post("/users")).andExpect(status().isOk());

        //----------------

        //given
        User user = new User();
        userRepository.save(user);

        //then
        mockMvc.perform(get("/users/" + user.getId())).andExpect(status().isOk());
    }

    @Test
    public void testInvalidUrl() throws Exception {
        //given
        User user = new User();
        userRepository.save(user);

        //then
        mockMvc.perform(patch("/users/" + user.getId() + "?name=test!!"))
                .andExpect(status().isBadRequest());
    }
}