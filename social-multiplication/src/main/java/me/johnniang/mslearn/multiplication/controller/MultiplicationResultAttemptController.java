package me.johnniang.mslearn.multiplication.controller;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import me.johnniang.mslearn.multiplication.domain.MultiplicationResultAttempt;
import me.johnniang.mslearn.multiplication.service.MultiplicationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/results")
public class MultiplicationResultAttemptController {

    private final MultiplicationService multiplicationService;

    public MultiplicationResultAttemptController(MultiplicationService multiplicationService) {
        this.multiplicationService = multiplicationService;
    }

    @PostMapping
    MultiplicationResultAttempt postResult(@RequestBody MultiplicationResultAttempt resultAttempt) {
        boolean correct = multiplicationService.checkAttempt(resultAttempt);
        return new MultiplicationResultAttempt(resultAttempt.getUser(),
            resultAttempt.getMultiplication(),
            resultAttempt.getResultAttempt(),
            correct);
    }

    @Data
    @RequiredArgsConstructor
    @NoArgsConstructor(force = true)
    public static final class ResultResponse {
        private final boolean correct;
    }
}
