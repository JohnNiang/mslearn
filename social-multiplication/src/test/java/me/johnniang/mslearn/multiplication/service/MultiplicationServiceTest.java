package me.johnniang.mslearn.multiplication.service;

import me.johnniang.mslearn.multiplication.domain.Multiplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class MultiplicationServiceTest {

    @MockBean
    RandomGeneratorService randomGeneratorService;

    @Autowired
    MultiplicationService multiplicationService;

    @Test
    void createRandomMultiplicationTest() {
        // given: return first 50, then 30
        given(randomGeneratorService.generateRandomFactor()).willReturn(50, 30);

        // when:
        Multiplication multiplication = multiplicationService.createRandomMultiplication();

        // then：
        assertEquals(50, multiplication.getFactorA());
        assertEquals(30, multiplication.getFactorB());
        assertEquals(1500, multiplication.getResult());
    }
}