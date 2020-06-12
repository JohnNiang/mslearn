package me.johnniang.mslearn.multiplication.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@RequiredArgsConstructor
@Entity
public final class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private final String alias;

    protected User() {
        alias = null;
    }
}
