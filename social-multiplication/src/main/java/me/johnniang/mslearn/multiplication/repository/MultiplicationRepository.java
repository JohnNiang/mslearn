package me.johnniang.mslearn.multiplication.repository;

import me.johnniang.mslearn.multiplication.domain.Multiplication;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MultiplicationRepository extends CrudRepository<Multiplication, Long> {

    Optional<Multiplication> findByFactorAAndFactorB(int factorA, int factorB);
}
