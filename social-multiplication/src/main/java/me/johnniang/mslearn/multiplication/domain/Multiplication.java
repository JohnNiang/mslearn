package me.johnniang.mslearn.multiplication.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Multiplication.
 *
 * @author johnniang
 */
@Data
@Entity
public final class Multiplication {

    @Id
    @GeneratedValue
    @Column(name = "multiplication_id")
    private Long id;

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
