package me.johnniang.mslearn.multiplication.domain;

import lombok.Data;

/**
 * Multiplication.
 *
 * @author johnniang
 */
@Data
public final class Multiplication {

    private final int factorA;

    private final int factorB;

    Multiplication() {
        this(0, 0);
    }

    public Multiplication(int factorA, int factorB) {
        this.factorA = factorA;
        this.factorB = factorB;
    }
}
