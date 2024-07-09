package kr.co.polycube.backendtest.exception;

import kr.co.polycube.backendtest.domain.User;
import kr.co.polycube.backendtest.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    
    @Test
    public void Status404NotFoundTest_UserNotFound() throws Exception {
        //given
        long invalidId = 10L;

        //when.then
        mockMvc.perform(get("/users/" + invalidId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.reason").exists())
                .andExpect(jsonPath("$.reason").value("no user found with id: " + invalidId));
    }

    @Test
    public void Status400BadRequestTest_InvalidRequest() throws Exception {
        //given
        User user = new User();
        userRepository.save(user);
        long userId = user.getId();

        //when.then
        mockMvc.perform(patch("/users/" + userId)
                        .param("name", ""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.reason").exists())
                .andExpect(jsonPath("$.reason").value("Name cannot be empty"));
    }
    
    @Test
    public void Status404NotFoundTest_NoEndpoint() throws Exception {
        mockMvc.perform(get("/fakepath"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.reason").exists());
    }
}