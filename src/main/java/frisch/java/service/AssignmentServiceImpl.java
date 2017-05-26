package frisch.java.service;

import frisch.java.service.AbstractService;
import frisch.java.model.Assignment;
import frisch.java.repository.AssignmentRepository;
import frisch.java.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by luke on 6/3/16.
 */
@Service
@Transactional
public class AssignmentServiceImpl extends AbstractService<Assignment, Long> implements AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Override
    protected JpaRepository<Assignment, Long> getDao() {
        return assignmentRepository;
    }

    @Override
    public Assignment add(Assignment entity) {
        return super.add(entity);
    }

    @Override
    public Assignment update(Long id, Assignment newAssignment) {
        Assignment oldAssignment = getOne(id);
        newAssignment.setId(id);

        return getDao().saveAndFlush(newAssignment);
    }

}
