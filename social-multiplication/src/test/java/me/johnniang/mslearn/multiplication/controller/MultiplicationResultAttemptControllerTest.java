package me.johnniang.mslearn.multiplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.johnniang.mslearn.multiplication.controller.MultiplicationResultAttemptController.ResultResponse;
import me.johnniang.mslearn.multiplication.domain.Multiplication;
import me.johnniang.mslearn.multiplication.domain.MultiplicationResultAttempt;
import me.johnniang.mslearn.multiplication.domain.User;
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

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MultiplicationResultAttemptController.class)
class MultiplicationResultAttemptControllerTest {

    @MockBean
    MultiplicationService multiplicationService;

    @Autowired
    MockMvc mvc;

    JacksonTester<MultiplicationResultAttempt> jsonResult;
    JacksonTester<MultiplicationResultAttempt> jsonResponse;

    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    void postResultReturnCorrect() throws Exception {
        genericParameterizedTest(true);
    }

    @Test
    void postResultReturnWrong() throws Exception {
        genericParameterizedTest(false);
    }

    void genericParameterizedTest(final boolean correct) throws Exception {
        // given
        given(multiplicationService.checkAttempt(any(MultiplicationResultAttempt.class)))
            .willReturn(correct);
        User user = new User("John");
        Multiplication multiplication = new Multiplication(60, 80);
        MultiplicationResultAttempt resultAttempt = new MultiplicationResultAttempt(user, multiplication, 4800, false);

        // when
        MockHttpServletResponse response = mvc.perform(
            post("/results")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonResult.write(resultAttempt).getJson()))
            .andExpect(status().is2xxSuccessful())
            .andDo(print())
            .andReturn()
            .getResponse();

        // then
        Assertions.assertEquals(jsonResponse.write(
            new MultiplicationResultAttempt(resultAttempt.getUser(),
                resultAttempt.getMultiplication(),
                resultAttempt.getResultAttempt(),
                correct)
        ).getJson(), response.getContentAsString());
    }
}
