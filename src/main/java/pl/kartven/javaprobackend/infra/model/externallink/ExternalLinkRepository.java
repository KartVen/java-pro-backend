package pl.kartven.javaprobackend.infra.model.externallink;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExternalLinkRepository extends JpaRepository<ExternalLink, Long> {
}
