package me.johnniang.mslearn.multiplication.service;

import me.johnniang.mslearn.multiplication.domain.Multiplication;
import me.johnniang.mslearn.multiplication.domain.MultiplicationResultAttempt;
import me.johnniang.mslearn.multiplication.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class MultiplicationServiceTest {

    @Mock
    RandomGeneratorService randomGeneratorService;

    MultiplicationServiceImpl multiplicationServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        multiplicationServiceImpl = new MultiplicationServiceImpl(randomGeneratorService);
    }

    @Test
    void createRandomMultiplicationTest() {
        // given: return first 50, then 30
        given(randomGeneratorService.generateRandomFactor()).willReturn(50, 30);

        // when:
        Multiplication multiplication = multiplicationServiceImpl.createRandomMultiplication();

        // then：
        assertEquals(50, multiplication.getFactorA());
        assertEquals(30, multiplication.getFactorB());
        assertEquals(1500, multiplication.getFactorA() * multiplication.getFactorB());
    }

    @Test
    void checkCorrectAttemptTest() {
        // given
        User user = new User("John");
        Multiplication multiplication = new Multiplication(40, 60);
        MultiplicationResultAttempt multiplicationResultAttempt = new MultiplicationResultAttempt(user, multiplication, 2400);

        // when
        boolean attemptResult = multiplicationServiceImpl.checkAttempt(multiplicationResultAttempt);

        // then
        assertTrue(attemptResult);
    }

    @Test
    void checkWrongAttemptTest() {
        // given
        User user = new User("John");
        Multiplication multiplication = new Multiplication(40, 60);
        MultiplicationResultAttempt multiplicationResultAttempt = new MultiplicationResultAttempt(user, multiplication, 3000);

        // when
        boolean attemptResult = multiplicationServiceImpl.checkAttempt(multiplicationResultAttempt);

        // then
        assertFalse(attemptResult);
    }
}