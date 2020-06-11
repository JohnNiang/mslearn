package me.johnniang.mslearn.multiplication.controller;

import me.johnniang.mslearn.multiplication.service.MultiplicationService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MultiplicationController {

    private final MultiplicationService multiplicationService;

    public MultiplicationController(MultiplicationService multiplicationService) {
        this.multiplicationService = multiplicationService;
    }

}
