package me.johnniang.mslearn.multiplication.service;

import me.johnniang.mslearn.multiplication.domain.Multiplication;
import me.johnniang.mslearn.multiplication.domain.MultiplicationResultAttempt;

public interface MultiplicationService {

    /**
     * Creates a Multiplication object with two randomly-generated factors.
     * between 11 and 99
     *
     * @return a Multiplication object with random factors
     */
    Multiplication createRandomMultiplication();

    /**
     * @return true if the attempt matches the result of the multiplication, false otherwise.
     */
    boolean checkAttempt(MultiplicationResultAttempt resultAttempt);
}
