package pl.kartven.javaprobackend.infra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kartven.javaprobackend.infra.model.entity.Topic;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findByUser_Id(Long id);
}
