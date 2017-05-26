package frisch.java.service;

import frisch.java.model.Submit;

import java.util.List;

/**
 * Created by luke on 7/15/16.
 */
public interface SubmitService extends JpaService<Submit, Long> {

    List<Submit> getSubmitByUserId(Long userId);

}
