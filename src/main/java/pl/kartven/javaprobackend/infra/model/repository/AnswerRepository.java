package pl.kartven.javaprobackend.infra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kartven.javaprobackend.infra.model.entity.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
