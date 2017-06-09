package frisch.java.repository;

import frisch.java.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by luke on 6/3/16.
 */
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    public Assignment findByName(String name);
    public List<Assignment> findByLanguage(String languageName);
}
