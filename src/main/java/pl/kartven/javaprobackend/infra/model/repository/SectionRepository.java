package pl.kartven.javaprobackend.infra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import pl.kartven.javaprobackend.infra.model.entity.Section;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    List<Section> findByTopic_Id(@NonNull Long id);
}

