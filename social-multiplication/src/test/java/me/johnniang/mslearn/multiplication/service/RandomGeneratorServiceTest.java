package me.johnniang.mslearn.multiplication.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class RandomGeneratorServiceTest {

    RandomGeneratorServiceImpl randomGeneratorServiceImpl;

    @BeforeEach
    void setUp() {
        randomGeneratorServiceImpl = new RandomGeneratorServiceImpl();
    }

    @Test
    void generateRandomFactorIsBetweenExpectedLimits() {
        // generate a bulk of random factors
        List<Integer> randomFactors = IntStream.range(0, 1000)
            .map(i -> randomGeneratorServiceImpl.generateRandomFactor())
            .boxed()
            .collect(Collectors.toList());

        // then all of them should be between 11 and 99
        randomFactors.forEach(factor -> assertTrue(factor >= 11 && factor <= 99));
    }
}