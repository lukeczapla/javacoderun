package frisch.java.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import frisch.java.model.Assignment;
import frisch.java.model.Submit;
import frisch.java.model.User;
import frisch.java.repository.UserRepository;
import frisch.java.service.AssignmentService;
import frisch.java.service.Submission;
import frisch.java.service.SubmitService;
import frisch.java.util.ResultProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.List;

/**
 * Created by luke on 6/4/16.
 */
@RestController
@Api("Assignment submission")
public class SubmitController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private SubmitService submitService;

    @ApiOperation("Get all submission by the current user")
    @RequestMapping(value = "/conf/submit", method = RequestMethod.GET)
    @JsonView(JsonViews.Submit.class)
    public List<Submit> getPassed(Principal prince) {
        if (prince == null) return null;
        User user = userRepository.findByEmail(prince.getName());
        return getByUser(user.getId());
    }

    @ApiOperation("Get all submission records for a given user")
    @RequestMapping(value = "/conf/user/submissions/{id}", method=RequestMethod.GET)
    @JsonView(JsonViews.Submit.class)
    public List<Submit> getByUser(@PathVariable("id") Long userId) {
        return submitService.getSubmitByUserId(userId);
    }


    @ApiOperation("Submit code for an assignment")
    @RequestMapping(value = "/conf/submit", method = RequestMethod.POST)
    @JsonView(JsonViews.Submit.class)
    public ResponseEntity submitCode(@ApiParam("Code submitted") @RequestBody Submit code
            , Principal prince) {

        if (code.getAssignmentId() == null)
            return new ResponseEntity("No assignment listed", HttpStatus.NOT_ACCEPTABLE);
        Assignment assignment = assignmentService.getOne(code.getAssignmentId());
        Submission sub = new Submission();
//        System.out.println(code.getClassName() + "\n" + code.getText());
        boolean success;

        try {
            success = sub.execute(code, assignment);
        } catch (IOException e) {
            return new ResponseEntity("The program caused an IO error", HttpStatus.NOT_ACCEPTABLE);
        }
        if (success) {
            User user = userRepository.findByEmail(prince.getName());
//            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            int score = ResultProcessor.compare(sub.getResponseText(), sub.getStandardText());
            String message = "<b>Code accepted and scored as " + score
                    + " out of 100</b><br/>" + sub.getResponseText();
            code.setUser(user);
            code.setScore(new Long(score));
            code.setDate(new Date());
            if (score >= 90) code.setPassed(true);
            else code.setPassed(false);
            if (score >= 70) submitService.add(code);
            return new ResponseEntity(message, HttpStatus.OK);
        }
        else return new ResponseEntity(sub.getResponseText(), HttpStatus.NOT_ACCEPTABLE);
    }

}
