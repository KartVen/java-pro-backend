package pl.kartven.javaprobackend.infra.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kartven.javaprobackend.infra.model.entity.ExternalLink;

import java.util.List;

@Repository
public interface ExternalLinkRepository extends JpaRepository<ExternalLink, Long> {
    List<ExternalLink> findByTopic_Id(Long id);
}
