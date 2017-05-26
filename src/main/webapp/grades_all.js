
    assignments = {};
    all_assignments = {};
    user = {};

    $.ajax({url: "/conf/user", method: "GET"})
                .done (function(result) {
                  if (result.authenticated) {
                    console.log("You are logged in as user " + result.name);
                    d1 = document.getElementById('message');
                    d1.insertAdjacentHTML('beforeend', " for admin " + result.name);
                    user = result;
                  } else {
                    $('#message').html("YOU ARE NOT LOGGED IN");
                  }
                });


  $.ajax({url: "/conf/userlist", method: "GET"})
    .done (function(u) {
    user = u;
    var values = "";
    var count = 0;
    for (k = 0; k < user.length; k++) {
      $.ajax({url: "/conf/user/submissions/"+user[k].id, method: "GET", async: false})
                .done (function(sub) {
                    assignments = sub;

                    $.ajax({url: "/conf/assignments", method: "GET", async: false}).done (function(all) {
                       var all_assignments = all;
                       count = 0;
                       for (i = 0; i < all_assignments.length; i++) {
                         found = 0;
                         for (j = 0; j < assignments.length; j++) {
                           if ((all_assignments[i].id == assignments[j].assignmentId) &&
                             assignments[j].passed) {
                             found = 1;
                             count++;
                             break;
                           }
                         }
                       }
                       values += user[k].id + ' ' + user[k].email + ' overall score: ' + 100.0*count/all_assignments.length + '<br/>';
                     });

                });
    }
    $('#list').html(values);
    });

