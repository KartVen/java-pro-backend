package pl.kartven.javaprobackend.lecture.link;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepo extends JpaRepository<Link, Long> {
}
