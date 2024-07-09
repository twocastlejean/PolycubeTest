package kr.co.polycube.backendtest.controller;

import kr.co.polycube.backendtest.repository.LottoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LottoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LottoRepository lottoRepository;

    @BeforeEach
    void setUp() {
        lottoRepository.deleteAll();
    }

    @Test
    public void postLottoTest() throws Exception {
        mockMvc.perform(post("/lottos")
                        /*.contentType(MediaType.APPLICATION_JSON)*/)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numbers").isArray())
                .andExpect(jsonPath("$.numbers").isNotEmpty())
                .andExpect(jsonPath("$.numbers.length()").value(6));
    }
}