package frisch.java.service;

import frisch.java.model.Assignment;
import frisch.java.model.Submit;
import frisch.java.util.InputProcessor;
import frisch.java.util.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Created by luke on 6/4/16.
 */
public class Submission {

//    private static String append_to_stream = "\nend of input from JavaCodeRun\nExtra\nExtra";
    private static Logger log = LoggerFactory.getLogger(Submission.class);

    private String responseText;
    private String standardText;

    public String getResponseText() { return responseText; }
    public String getStandardText() { return standardText; }

    public void setResponseText(String responseText) { this.responseText = responseText; }
    public void setStandardText(String standardText) { this.standardText = standardText; }

    public boolean execute(Submit job, Assignment assignment) throws FileNotFoundException, IOException {

        log.debug(""+job.getAssignmentId());
        responseText = "";
        standardText = "";

        int jobid = (int)(90000 * Math.random())+10000;

        String[] r = job.getClassName().split("\\.");
        String classname = r[r.length-1];

        String path = "job" + jobid;
        String source = "job" + jobid + "/" + classname + ".java";
        String compiled = job.getClassName();
        log.debug(path + " " + source);

        new File(path).mkdir();
        PrintWriter o = new PrintWriter(source);
        o.println(job.getText());
        o.flush();
        o.close();

        Process compile = new ProcessBuilder("javac", "-nowarn", "-d", path, source).start();
        Scanner error = new Scanner(compile.getErrorStream());
        if (error.hasNext()) {
            while (error.hasNextLine()) {
                responseText += error.nextLine() + "\n";
            }
            SystemUtils.deleteFolder(path);
            return false;
        }

        InputStream is = compile.getInputStream();
        Scanner scan = new Scanner(is);
        while (scan.hasNextLine()) log.debug(scan.nextLine());

        Process execute;
        BufferedWriter out;
        String validationInput = InputProcessor.processInput(assignment.getInput());

        if (assignment.getType().longValue() != 3) {

            execute = new ProcessBuilder("java", "-cp", path, "-Djava.security.manager",
                    "-Djava.security.policy=SecurityPolicy", compiled).start();
            is = execute.getInputStream();

            scan = new Scanner(is);

            out = new BufferedWriter(new OutputStreamWriter(execute.getOutputStream()));

            out.write(validationInput + "\n");
            out.flush();

            // This code makes sure the code completes in under 60 seconds or time out occurs.
        /*
        boolean done = false;
        int count = 0;

        while ((done == false) && (count < 60)) {
            try {
                int value = execute.exitValue();
                done = true;
            } catch (IllegalThreadStateException e) {
                done = false;
                count++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        if (done == false) {
            responseText = "Running your code timed out, you may have had your code read the wrong input\n" +
                    "or forgotten to read some input that was expected but not received.";
            SystemUtils.deleteFolder(path);
            return false;
        }
        */

            try {
                if (!execute.waitFor(1, TimeUnit.MINUTES)) {
                    responseText = "Running your code timed out, you may have had your code read the wrong input\n" +
                            "or it was waiting for input that was not part of the assignment.\n" +
                            "Also, make sure you weren't using more than one input stream (Scanner).";
                    execute.destroy();
                    SystemUtils.deleteFolder(path);
                    return false;
                }
            } catch (InterruptedException e) {

            }

            // here we process the output.
            error = new Scanner(execute.getErrorStream());
            if (error.hasNext()) {
                while (error.hasNextLine()) {
                    responseText += error.nextLine() + "\n";
                }
                SystemUtils.deleteFolder(path);
                return false;
            }

            while (scan.hasNextLine()) {
                responseText += scan.nextLine() + "\n";
            }
        }

        // changes to the algorithm
        if (assignment.getType().longValue() == 2) standardText = assignment.getReferenceOutput();
        else {
            String refsource = "job" + jobid + "/model.java";
            o = new PrintWriter(refsource);
            o.println(assignment.getSourcefile());
//            System.out.println(assignment.getSourcefile());
            o.flush();
            o.close();
            compile = new ProcessBuilder("javac", "-nowarn", "-cp", path, "-d", path, refsource).start();
            error = new Scanner(compile.getErrorStream());
            if (error.hasNext()) {
                while (error.hasNextLine()) {
                    responseText += error.nextLine() + "\n";
                }
                SystemUtils.deleteFolder(path);
                return false;
            }
            execute = new ProcessBuilder("java", "-cp", path, "-Djava.security.manager",
                    "-Djava.security.policy=SecurityPolicy", "model").start();
            is = execute.getInputStream();
            scan = new Scanner(is);
            out = new BufferedWriter(new OutputStreamWriter(execute.getOutputStream()));
            out.write(validationInput+"\n");
            out.flush();
            error = new Scanner(execute.getErrorStream());
            if (error.hasNext()) {
                while (error.hasNextLine()) {
                    responseText += error.nextLine() + "\n";
                }
                SystemUtils.deleteFolder(path);
                return false;
            }
            while (scan.hasNextLine()) {
                if (assignment.getType().longValue() == 1) standardText += scan.nextLine() + "\n";
                else if (assignment.getType().longValue() == 3) responseText += scan.nextLine() + "\n";
            }

            if (assignment.getType().longValue() == 3) standardText = assignment.getReferenceOutput();

        }
        SystemUtils.deleteFolder(path);
        return true;

    }

}
