package me.johnniang.mslearn.multiplication.service;

import me.johnniang.mslearn.multiplication.domain.Multiplication;
import me.johnniang.mslearn.multiplication.domain.MultiplicationResultAttempt;
import me.johnniang.mslearn.multiplication.domain.User;
import me.johnniang.mslearn.multiplication.repository.MultiplicationRepository;
import me.johnniang.mslearn.multiplication.repository.MultiplicationResultAttemptRepository;
import me.johnniang.mslearn.multiplication.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class MultiplicationServiceImpl implements MultiplicationService {

    private final RandomGeneratorService randomGeneratorService;

    private final MultiplicationResultAttemptRepository attemptRepository;

    private final UserRepository userRepository;

    private final MultiplicationRepository multiplicationRepository;

    public MultiplicationServiceImpl(RandomGeneratorService randomGeneratorService,
                                     MultiplicationResultAttemptRepository attemptRepository,
                                     UserRepository userRepository,
                                     MultiplicationRepository multiplicationRepository) {
        this.randomGeneratorService = randomGeneratorService;
        this.attemptRepository = attemptRepository;
        this.userRepository = userRepository;
        this.multiplicationRepository = multiplicationRepository;
    }


    @Override
    public Multiplication createRandomMultiplication() {
        int factorA = randomGeneratorService.generateRandomFactor();
        int factorB = randomGeneratorService.generateRandomFactor();
        return new Multiplication(factorA, factorB);
    }

    @Transactional
    @Override
    public boolean checkAttempt(MultiplicationResultAttempt resultAttempt) {
        // check if the user already exists for that alias
        Optional<User> user = userRepository.findByAlias(resultAttempt.getUser().getAlias());
        Optional<Multiplication> multiplication = multiplicationRepository.findByFactorAAndFactorB(resultAttempt.getMultiplication().getFactorA(),
            resultAttempt.getMultiplication().getFactorB());

        int factorA = resultAttempt.getMultiplication().getFactorA();
        int factorB = resultAttempt.getMultiplication().getFactorB();
        boolean correct = factorA * factorB == resultAttempt.getResultAttempt();

        Assert.isTrue(!resultAttempt.isCorrect(), "You can't send an attempt marked as correct");

        MultiplicationResultAttempt checkedAttempt = new MultiplicationResultAttempt(user.orElse(resultAttempt.getUser()),
            multiplication.orElse(resultAttempt.getMultiplication()),
            resultAttempt.getResultAttempt(),
            correct);

        // persist the checked attempt
        attemptRepository.save(checkedAttempt);

        return correct;
    }

    @Override
    public List<MultiplicationResultAttempt> getStatsForUser(String userAlias) {
        return attemptRepository.findTop5ByUserAliasOrderByIdDesc(userAlias);
    }
}
