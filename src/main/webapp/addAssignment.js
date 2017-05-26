
var alist;
var currentid = null;
var editmode = false;
//tinymce.init({ selector:'textarea.htmltype' });

function submit() {
//  console.log($('#name').val() + " " + $('#code').val() + " " + $('#typesel').val());
  if (editmode) {
    execedit();
  } else {
    execadd();
  }
}


function execadd() {
  var data = {
      "name": $('#name').val(),
      "description": $('#description').val(),
      "sourcefile": $('#code').val(),
      "type": $('#typesel').val(),
      "className": $('#classname').val(),
      "initialSource": $('#initialsource').val(),
      "input": $('#input').val(),
      "template": $('#template').val(),
      "referenceOutput": $('#refoutput').val()
  }

  var req = {
    headers: {
      "Accept": "application/json",
      "Content-Type": "application/json"
    },
    url: "/conf/assignment",
    method: "POST",
    data: JSON.stringify(data),
    dataType: "json",
    success: function() { console.log("success"); }
  }

  $.ajax(req)
  	.done(function(response) {
        $('#name').val("");
        $('#description').val("");
        $('#code').val("");
        $('#typesel').val(1);
        $('#classname').val("");
        $('#input').val("");
        $('#initialsource').val("");
        $('#template').val("");
        $('#refoutput').val("");
	  })
    .error(function(response) {
      console.log(response);
    })
}


function execedit() {
  var data = {
      "name": $('#name').val(),
      "description": $('#description').val(),
      "sourcefile": $('#code').val(),
      "type": $('#typesel').val(),
      "className": $('#classname').val(),
      "input": $('#input').val(),
      "initialSource": $('#initialsource').val(),
      "template": $('#template').val(),
      "referenceOutput": $('#refoutput').val()
  }

  var req = {
    headers: {
      "Accept": "application/json",
      "Content-Type": "application/json"
    },
    url: "/conf/assignment/"+currentid,
    method: "PUT",
    data: JSON.stringify(data),
    dataType: "json",
    success: function() { console.log("success"); }
  }

  $.ajax(req)
  	.done(function(response) {

	})
    .error(function(response) {
      console.log(response);
    })
}


function deleteA() {
  if (confirm("Are you sure you want to delete asssignment " + $('#values').val() + "?")) {
    $.ajax({url: "/conf/assignment/" + $('#values').val(),
            method: "DELETE"})
      .done(function() {
        location.reload();
      });
  }
}


function editA() {
    if (editmode) {
        $('#editbutton').text("Edit");
        $('#titletext').text("Add an assignment:");
        currentid = null;
    } else {
        $('#editbutton').text("Done Editing");
        currentid = $('#values').val();
        $('#titletext').text("Editing assignment ID#" + currentid);
        $.ajax({url: "/conf/assignment/"+currentid,
                method: "GET"})
                .done (function(response) {
                    $('#name').val(response.name);
                    $('#description').val(response.description);
                    $('#code').val(response.sourcefile);
                    $('#typesel').val(response.type);
                    $('#input').val(response.input);
                    $('#refoutput').val(response.referenceOutput);
                    $('#initialsource').val(response.initialSource);
                    $('#template').val(response.template);
                    $('#classname').val(response.className);
                });
        console.log(currentid);
    }
    editmode = !editmode;
}


$.ajax({url: "/conf/assignments",
        method: "GET"})
  .done(function(response) {

     alist = response;

     for (var i = 0; i < alist.length; i++) {
       $('#values').append('<option value=' + alist[i].id + '>' + alist[i].name + '</option>');
     }

  });
