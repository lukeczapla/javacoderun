# javacoderun

This project is a system for automated code testing for education in Java programming.
The system relies on REST controllers that manage accounts, roles, assignments, and student submissions.
Administration functions are currently simple, and future iterations will improve a lot of the panels.
Google is used for authentication, and the system does not use passwords.  
A bitap algorithm is used for matching program output to expected results in one assignment mode, 
and it helps to do incomplete matching and assign a score to the output using a Levenshtein distance.
