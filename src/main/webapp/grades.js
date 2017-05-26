

    var assignments = {};
    var all_assignments = {};
    var user = {};

    $.ajax({url: "/conf/user", method: "GET"})
                .done (function(result) {
                  if (result.authenticated) {
                    console.log("You are logged in as user " + result.name);
                    let d1 = document.getElementById('message');
                    d1.insertAdjacentHTML('beforeend', " for user " + result.name);
                    user = result;
                  } else {
                    $('#message').html('YOU ARE NOT LOGGED IN');
                    $('#list').html('<a href="/">Go back</a> and log into the system');
                  }
                });


    $.ajax({url: "/conf/submit", method: "GET"})
                .done (function(sub) {
                    assignments = sub;
                    var values = "";
                    $.ajax({url: "/conf/assignments", method: "GET"})
                    .done (function(all) {
                       let all_assignments = all;
                       let count = 0;
                       for (i = 0; i < all_assignments.length; i++) {
                         found = 0;
                         for (j = 0; j < assignments.length; j++) {
                           if ((all_assignments[i].id == assignments[j].assignmentId) &&
                             assignments[j].passed) {
                             values += all_assignments[i].name + '<br>' ;
                             found = 1;
                             count++;
                             break;
                           }
                         }
                         if (!found) values += "INCOMPLETE: " + all_assignments[i].name + "<br>";
                       }
                       values += '<br>Overall score: ' + 100.0*count/all_assignments.length;
                       $('#list').html(values);

                    });
                });


