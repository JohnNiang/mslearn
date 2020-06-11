package me.johnniang.mslearn.multiplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.johnniang.mslearn.multiplication.domain.Multiplication;
import me.johnniang.mslearn.multiplication.service.MultiplicationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MultiplicationController.class)
class MultiplicationControllerTest {

    @MockBean
    MultiplicationService multiplicationService;

    @Autowired
    MockMvc mvc;

    JacksonTester<Multiplication> json;

    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    void getRandomMultiplicationTest() throws Exception {
        // given
        given(multiplicationService.createRandomMultiplication())
            .willReturn(new Multiplication(50, 40));

        // when
        MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.get("/multiplications/random")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is2xxSuccessful())
            .andDo(print())
            .andReturn()
            .getResponse();

        // then
        Assertions.assertEquals(
            json.write(new Multiplication(50, 40)).getJson(),
            response.getContentAsString());
    }

}