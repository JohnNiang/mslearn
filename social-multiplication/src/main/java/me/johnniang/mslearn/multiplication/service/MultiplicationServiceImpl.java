package me.johnniang.mslearn.multiplication.service;

import me.johnniang.mslearn.multiplication.domain.Multiplication;
import me.johnniang.mslearn.multiplication.domain.MultiplicationResultAttempt;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class MultiplicationServiceImpl implements MultiplicationService {

    private final RandomGeneratorService randomGeneratorService;

    public MultiplicationServiceImpl(RandomGeneratorService randomGeneratorService) {
        this.randomGeneratorService = randomGeneratorService;
    }

    @Override
    public Multiplication createRandomMultiplication() {
        int factorA = randomGeneratorService.generateRandomFactor();
        int factorB = randomGeneratorService.generateRandomFactor();
        return new Multiplication(factorA, factorB);
    }

    @Override
    public boolean checkAttempt(MultiplicationResultAttempt resultAttempt) {
        int factorA = resultAttempt.getMultiplication().getFactorA();
        int factorB = resultAttempt.getMultiplication().getFactorB();
        boolean correct = factorA * factorB == resultAttempt.getResultAttempt();

        Assert.isTrue(!resultAttempt.isCorrect(), "You can't send an attempt marked as correct");

        MultiplicationResultAttempt checkedAttempt = new MultiplicationResultAttempt(resultAttempt.getUser(),
            resultAttempt.getMultiplication(),
            resultAttempt.getResultAttempt(),
            correct);

        // TODO persist the checked attempt

        return correct;
    }
}
