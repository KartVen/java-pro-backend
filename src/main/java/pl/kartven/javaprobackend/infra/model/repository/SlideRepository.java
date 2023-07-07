package pl.kartven.javaprobackend.infra.model.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kartven.javaprobackend.infra.model.entity.Slide;

import java.util.List;

@Repository
public interface SlideRepository extends JpaRepository<Slide, Long> {
    List<Slide> findByTopic_Id(Long id, Pageable pageable);
}
