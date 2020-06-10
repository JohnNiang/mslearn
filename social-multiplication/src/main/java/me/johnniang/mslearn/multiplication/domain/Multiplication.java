package me.johnniang.mslearn.multiplication.domain;

import lombok.Data;

/**
 * Multiplication.
 *
 * @author johnniang
 */
@Data
public final class Multiplication {

    private int factorA;

    private int factorB;

    private int result;

    public Multiplication(int factorA, int factorB) {
        this.factorA = factorA;
        this.factorB = factorB;
        this.result = this.factorA * this.factorB;
    }
}
