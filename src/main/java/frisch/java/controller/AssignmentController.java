package frisch.java.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import frisch.java.model.Assignment;
import frisch.java.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.security.Principal;
import java.util.List;

/**
 * Created by luke on 6/3/16.
 */
@RestController
@Api("Assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @ApiOperation(value = "Get all assignments", response = Assignment.class, responseContainer = "List", notes = "Get all assignments that exist")
    @RequestMapping(value = "/conf/assignments", method = RequestMethod.GET)
    @JsonView(JsonViews.Assignment.class)
    public List<Assignment> getAll() {
        return assignmentService.getAll();
    }

    @ApiOperation(value = "Create a new assignment", response = Assignment.class, notes = "Add a new assignment to the list")
    @RequestMapping(value = "/conf/assignment", method = RequestMethod.POST)
    @JsonView(JsonViews.Assignment.class)
    public ResponseEntity create(@ApiParam("The new assignment") @RequestBody Assignment assignment) {
        return new ResponseEntity(assignmentService.add(assignment), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update an assignment", response = Assignment.class, notes = "Update an assignment with new data")
    @RequestMapping(value = "/conf/assignment/{id}", method = RequestMethod.PUT)
    @JsonView(JsonViews.Assignment.class)
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody Assignment assignment) {
        return new ResponseEntity(assignmentService.update(id, assignment), HttpStatus.OK);
    }

    @ApiOperation(value = "Get an assignment", response = Assignment.class, notes = "Get an assignment by assignment ID")
    @RequestMapping(value = "/conf/assignment/{id}", method = RequestMethod.GET)
    public Assignment getOne(@PathVariable("id") Long id) {
        return assignmentService.getOne(id);
    }

    @ApiOperation(value = "Delete an assignment", notes = "Delete one assignment by assignment ID")
    @RequestMapping( value = "/conf/assignment/{id}", method = RequestMethod.DELETE )
    @ResponseStatus( HttpStatus.NO_CONTENT )
    public void delete(@PathVariable("id") Long id ){
        assignmentService.delete(id);
    }

}
