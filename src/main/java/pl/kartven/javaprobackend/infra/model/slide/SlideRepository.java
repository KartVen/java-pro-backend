package pl.kartven.javaprobackend.infra.model.slide;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlideRepository extends JpaRepository<Slide, Long> {
    List<Slide> findByTopic_Id(Long id);
}
