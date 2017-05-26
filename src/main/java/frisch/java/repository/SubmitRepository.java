package frisch.java.repository;

import frisch.java.model.Submit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Created by luke on 6/23/16.
 */
public interface SubmitRepository extends JpaRepository<Submit, Long> {
    public List<Submit> findByAssignmentId(Long assignmentId);
    public List<Submit> findByUserId(Long userId);
}
