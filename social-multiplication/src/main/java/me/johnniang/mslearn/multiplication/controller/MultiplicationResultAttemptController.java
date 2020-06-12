package me.johnniang.mslearn.multiplication.controller;

import me.johnniang.mslearn.multiplication.domain.MultiplicationResultAttempt;
import me.johnniang.mslearn.multiplication.service.MultiplicationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    List<MultiplicationResultAttempt> getStatistics(@RequestParam("alias") String alias) {
        return multiplicationService.getStatsForUser(alias);
    }
}
