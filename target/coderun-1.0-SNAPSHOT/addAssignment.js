

function submit() {
//  console.log($('#name').val() + " " + $('#code').val() + " " + $('#typesel').val());
  run();
}


function run() {
  var data = {
    "name": $('#name').val(),
    "description": $('#description').val(),
    "sourcefile": $('#code').val(),
    "type": $('#typesel').val()
  }

  var req = {
    headers: {
      "Accept": "application/json",
      "Content-Type": "application/json"
    },
    url: "http://localhost:8080/conf/assignment",
    method: "POST",
    data: JSON.stringify(data),
    dataType: "json",
    success: function() { console.log("success"); }
  }

//console.log(data)
//console.log(req)

  $.ajax(req)
  	.done(function(response) {
	  	$('#name').val("")
      $('#description').val("")
      $('#code').val("");
      $('#typesel').val(1);
	  })
    .error(function(response) {
      console.log(response);
    })
}


function deleteA() {
  if (confirm("Are you sure you want to delete asssignment " + $('#values').val() + "?")) {
    $.ajax({url: "http://localhost:8080/conf/assignment/" + $('#values').val(),
            method: "DELETE"})
      .done(function() {
        location.reload();
      });
  }
}


$.ajax({url: "http://localhost:8080/conf/assignments",
        method: "GET"})
  .done(function(response) {

     var alist = response;

     for (var i = 0; i < alist.length; i++) {
       $('#values').append('<option value=' + alist[i].id + '>' + alist[i].name + '</option>');
     }

  });
