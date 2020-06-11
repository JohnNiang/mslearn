package me.johnniang.mslearn.multiplication.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public final class MultiplicationResultAttempt {

    private final User user;

    private final Multiplication multiplication;

    private final int resultAttempt;

    /**
     * Only for json deserialization
     */
    MultiplicationResultAttempt() {
        user = null;
        multiplication = null;
        resultAttempt = -1;
    }
}
