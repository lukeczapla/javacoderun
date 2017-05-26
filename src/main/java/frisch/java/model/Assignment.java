package frisch.java.model;

import com.fasterxml.jackson.annotation.JsonView;
import frisch.java.controller.JsonViews;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by luke on 6/2/16.
 */

/**
 * Assignment types:
 * 1) Fixed reference output - compares output of code to fixed reference
 * 2) Validation program - compares output of code to output code program
 * 3) External model program runs the code and result uses fixed reference output
 */

@Entity
public class Assignment implements IDable<Long> {

    @Id
    @GeneratedValue
    @JsonView(value = {JsonViews.Assignment.class})
    private Long id;

    @JsonView(value = {JsonViews.Assignment.class})
    @Column(nullable = false)
    private String name;

    @JsonView(value = {JsonViews.Assignment.class})
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @JsonView(value = {JsonViews.Assignment.class})
    @Column(nullable=false, columnDefinition = "TEXT")
    private String sourcefile;

    @JsonView(value = {JsonViews.Assignment.class})
    @Column(columnDefinition = "TEXT")
    private String initialSource;

    @JsonView(value = {JsonViews.Assignment.class})
    @Column(columnDefinition = "TEXT")
    private String referenceOutput;

    @JsonView(value = {JsonViews.Assignment.class})
    @Column(nullable=false)
    private Long type;

    @JsonView({JsonViews.Assignment.class})
    @Column(columnDefinition = "TEXT")
    private String template;

    @JsonView(value = {JsonViews.Assignment.class})
    @Column
    String className;

    @JsonView(value = {JsonViews.Assignment.class})
    @Column
    String input;

    @JsonView(value = {JsonViews.Assignment.class})
    @Column
    Date dueDate;

//    @Lob @Basic(fetch = FetchType.LAZY)
//    byte[] image;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }


    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getSourcefile() { return sourcefile; }
    public Long getType() { return type; }
    public String getInitialSource() { return initialSource; }
    public String getTemplate() { return template; }
    public String getReferenceOutput() { return referenceOutput; }
    public String getClassName() { return className; }
    public String getInput() { return input; }
    public Date getDueDate() { return dueDate; }

    public void setName(String s) { this.name = s; }
    public void setDescription(String s) { this.description = s; }
    public void setSourcefile(String s) { this.sourcefile = s; }
    public void setType(Long l) { this.type = l; }
    public void setClassName(String s) { this.className = s; }
    public void setInitialSource(String s) { this.initialSource = s; }
    public void setReferenceOutput(String s) { this.referenceOutput = s; }
    public void setTemplate(String s) { this.template = s; }
    public void setInput(String s) { this.input = s; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }

}