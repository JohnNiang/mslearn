package me.johnniang.mslearn.multiplication.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public final class User {

    private final String alias;

    protected User() {
        alias = null;
    }
}
