package me.johnniang.mslearn.multiplication.service;

import me.johnniang.mslearn.multiplication.domain.Multiplication;
import me.johnniang.mslearn.multiplication.domain.MultiplicationResultAttempt;
import me.johnniang.mslearn.multiplication.domain.User;
import me.johnniang.mslearn.multiplication.repository.MultiplicationRepository;
import me.johnniang.mslearn.multiplication.repository.MultiplicationResultAttemptRepository;
import me.johnniang.mslearn.multiplication.repository.UserRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class MultiplicationServiceTest {

    MultiplicationServiceImpl multiplicationServiceImpl;

    @Mock
    RandomGeneratorService randomGeneratorService;

    @Mock
    MultiplicationResultAttemptRepository attemptRepository;

    @Mock
    MultiplicationRepository multiplicationRepository;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        multiplicationServiceImpl = new MultiplicationServiceImpl(randomGeneratorService,
            attemptRepository,
            userRepository,
            multiplicationRepository);
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
        MultiplicationResultAttempt resultAttempt = new MultiplicationResultAttempt(user,
            multiplication,
            2400,
            false);
        MultiplicationResultAttempt verifiedAttempt = new MultiplicationResultAttempt(user,
            multiplication,
            2400,
            true);
        given(userRepository.findByAlias("John")).willReturn(Optional.empty());
        given(multiplicationRepository.findByFactorAAndFactorB(40, 60)).willReturn(Optional.empty());

        // when
        boolean attemptResult = multiplicationServiceImpl.checkAttempt(resultAttempt);

        // then
        assertTrue(attemptResult);
        verify(attemptRepository).save(verifiedAttempt);
        verify(userRepository, times(1)).findByAlias("John");
        verify(multiplicationRepository, times(1)).findByFactorAAndFactorB(40, 60);
    }

    @Test
    void checkWrongAttemptTest() {
        // given
        User user = new User("John");
        Multiplication multiplication = new Multiplication(40, 60);
        MultiplicationResultAttempt resultAttempt = new MultiplicationResultAttempt(user,
            multiplication,
            3000,
            false);
        given(userRepository.findByAlias("John")).willReturn(Optional.empty());
        given(multiplicationRepository.findByFactorAAndFactorB(40, 60)).willReturn(Optional.empty());

        // when
        boolean attemptResult = multiplicationServiceImpl.checkAttempt(resultAttempt);

        // then
        assertFalse(attemptResult);
        verify(attemptRepository).save(resultAttempt);
        verify(userRepository, times(1)).findByAlias("John");
        verify(multiplicationRepository, times(1)).findByFactorAAndFactorB(40, 60);
    }

    @Test
    void retrieveStatsTest() {
        // given
        Multiplication multiplication = new Multiplication(30, 40);
        User user = new User("John");
        MultiplicationResultAttempt attempt1 = new MultiplicationResultAttempt(user, multiplication, 3600, false);
        MultiplicationResultAttempt attempt2 = new MultiplicationResultAttempt(user, multiplication, 1200, false);
        MultiplicationResultAttempt attempt3 = new MultiplicationResultAttempt(user, multiplication, 3000, false);
        List<MultiplicationResultAttempt> latestAttempts = Lists.newArrayList(attempt1, attempt2, attempt3);
        given(attemptRepository.findTop5ByUserAliasOrderByIdDesc("John")).willReturn(latestAttempts);

        // when
        List<MultiplicationResultAttempt> latestAttemptsResult = multiplicationServiceImpl.getStatsForUser("John");

        // then
        assertEquals(latestAttempts, latestAttemptsResult);
    }
}