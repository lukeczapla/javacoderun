package frisch.java.service;

import frisch.java.model.Submit;
import frisch.java.repository.SubmitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by luke on 7/15/16.
 */
@Service
@Transactional
public class SubmitServiceImpl extends AbstractService<Submit, Long> implements SubmitService {

    @Autowired
    private SubmitRepository submitRepository;

    @Override
    public List<Submit> getSubmitByUserId(Long userId) {
        return submitRepository.findByUserId(userId);
    }


    @Override
    protected JpaRepository<Submit, Long> getDao() {
        return submitRepository;
    }

    @Override
    public Submit add(Submit entity) {
        return super.add(entity);
    }

    @Override
    public Submit update(Long id, Submit newSubmit) {
        Submit oldSubmit = getOne(id);
        newSubmit.setId(id);

        return getDao().saveAndFlush(newSubmit);
    }

}
