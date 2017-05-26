

function processany() {
    text = "";
    uid = $('#userid').val()
    aid = $('#assignmentid').val()
    $.ajax({url: "/conf/user/submissions/"+uid, method: "GET"})
        .done (function(sub) {
            assignments = sub;
            for (i = 0; i < assignments.length; i++) {
              if (assignments[i].assignmentId == aid) {
                 text = text + assignments[i].id + " " + assignments[i].className +
                    " " + assignments[i].score + "<p>"
                    + assignments[i].text.replace(/\n/g, '&nbsp;<br />') + "<p>";
              }
            }
            $('#results').html(text);
        });
}


function process() {
    text = "";
    uid = $('#userid').val()
    aid = $('#assignmentid').val()
    $.ajax({url: "/conf/user/submissions/"+uid, method: "GET"})
        .done (function(sub) {
            assignments = sub;
            for (i = 0; i < assignments.length; i++) {
              if ((assignments[i].assignmentId == aid) && (assignments[i].score > 90)) {
                 text = text + assignments[i].id + " " + assignments[i].className +
                    " " + assignments[i].score + "<p>"
                    + assignments[i].text.replace(/\n/g, '&nbsp;<br />') + "<p>";
              }
            }
            $('#results').html(text);
        });

}


function find() {
      $.ajax({url: "/conf/userlist", method: "GET"})
        .done (function(u) {

            for (i = 0; i < u.length; i++) {
                if (u[i].email == $('#useremail').val())
                  $('#userid').val(u[i].id);
            }

        });
}


function processhighest() {
    text = "No results";
    uid = $('#userid').val();
    aid = $('#assignmentid').val();
    highest = 0;
    $.ajax({url: "/conf/user/submissions/"+uid, method: "GET"})
        .done (function(sub) {
            assignments = sub;
            for (i = 0; i < assignments.length; i++) {
              if ((assignments[i].assignmentId == aid) && (assignments[i].score > 90) && (assignments[i].score >= highest)) {
                 highest = assignments[i].score;
                 text = assignments[i].id + " " + assignments[i].className +
                    " " + assignments[i].score + "<p>"
                    + assignments[i].text.replace(/\n/g, '&nbsp;<br />') + "<p>";
              }
            }
            $('#results').html(text);
        });

}


function lista() {
    $.ajax({url: "/conf/assignments", method: "GET"})
        .done (function(all) {
                         rtext = "";
                         for (i = 0; i < all.length; i++) {
                           rtext = rtext + all[i].id + " " + all[i].name + "<p/>";
                         }
                         $('#assignmentlist').html(rtext);
        });
}



function listu() {
    $.ajax({url: "/conf/userlist", method: "GET"})
        .done (function(all) {
                         rtext = "";
                         for (i = 0; i < all.length; i++) {
                           rtext = rtext + all[i].id + " " + all[i].email + "<p/>";
                         }
                         $('#userlist').html(rtext);
        });
}
