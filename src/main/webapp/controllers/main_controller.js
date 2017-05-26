var opts = {
  lines: 13, length: 28, width: 14, radius: 42, scale: 1, corners: 1,
  color: '#000', opacity: 0.25, rotate: 0, direction: 1, speed: 1, trail: 60,
  fps: 20, zIndex: 2e9, className: 'spinner', top: '50%', left: '50%',
  shadow: false, hwaccel: false, position: 'absolute'
};

var google = null;
var myresult;
var mydataresult;

angular.module('CodeBotApp', ['ngSanitize'])
  .controller('FrontCtrl', ['$http', '$scope', '$sce', function($http, $scope, $sce) {

    var self = this;
 //   self.user = {
 //       username: "",
 //       password: ""
 //   };
//    self.user.username = "";
//    self.user.password = "";
    var currentA = 1;
      self.jobrequest = {
          className: "",
          text: ""
      };

    self.registered = function() {
    $http.get('/conf/user').then(function(result) {
      mydataresult = result;
      if (result.data.authenticated) {
          $('#unauthed').hide();
          $('#authed').show();
          $('#nonfrisch').hide();
      } else {
          $('#authed').hide();
          $('#unauthed').show();
          $('#nonfrisch').hide();
      }
    })
    }

    self.registered();

    this.assignments = [];
    this.message = 'Ready to process';

    var movies = 'Select an assignment from the menu to the left.' +
        '<p><iframe width="560" height="315" src="https://www.youtube.com/embed/l26i83Cl-Rw" frameborder="0" allowfullscreen></iframe>' +
        '<p><iframe width="560" height="315" src="https://www.youtube.com/embed/8_MIRqAhpnQ" frameborder="0" allowfullscreen></iframe>' +
        '<p><iframe width="560" height="315" src="https://www.youtube.com/embed/YgKcIlqLKTM" frameborder="0" allowfullscreen></iframe>' +
        '<p><iframe width="560" height="315" src="https://www.youtube.com/embed/AjjYo0tjMOI" frameborder="0" allowfullscreen></iframe>' +
        '<p><iframe width="560" height="315" src="https://www.youtube.com/embed/CsHv4oCe9Vc" frameborder="0" allowfullscreen></iframe>' +
        '<p><iframe width="560" height="315" src="https://www.youtube.com/embed/og86bDBYIyc" frameborder="0" allowfullscreen></iframe>' +
        '<p><iframe width="560" height="315" src="https://www.youtube.com/embed/3PL_pCqtjao" frameborder="0" allowfullscreen></iframe>' +
        '<p><iframe width="560" height="315" src="https://www.youtube.com/embed/8UfceBWw6Wg" frameborder="0" allowfullscreen></iframe>' +
        '<p><iframe width="560" height="315" src="https://www.youtube.com/embed/bQX8o7W-yNI" frameborder="0" allowfullscreen></iframe>';
    this.description = movies;

    this.description = $sce.trustAsHtml(this.description);

    $('#jobwindow').hide();
    $http.get('/conf/assignments').then(function(response) {
      self.assignments = response.data;
      currentA = response.data[0].id;
    }, function(errResponse) {
      console.log("error fetching assignments- ", errResponse);
    });


/*
    self.checkuser = function() {
      $.ajax({
        method: 'POST',
        url: '/login',
        data: self.user
      }).done(function(data) {
        myresult = data;
        self.registered()
      });
      console.log(self.user);
    }
*/


    self.submit = function() {
      if (self.jobrequest == null) return;
      var spinner = new Spinner(opts).spin(document.getElementById('progressbar'));
      self.jobrequest.assignmentId = currentA;
      $.ajax( { headers: {
                  "Content-Type": "application/json"
                },
                method: 'POST',
                url: '/conf/submit',
                data: JSON.stringify(self.jobrequest),
                success: function(data) { console.log("success: " + data); }
              })
              .done(function(data) {
                spinner.stop();
                self.message = data;
                console.log(self.message);
                $scope.$apply();
              })
              .error(function(data) {
                spinner.stop();
                console.log(data.responseText);
                self.message = "<b>Testing of your code could not be completed " + 
                	(data.responseText.search("Forbidden") != -1 ? "(make sure that you are logged in):</b><br/>"
                	: "(check your source for errors)</b><br/>")
                    + data.responseText;
                $scope.$apply();
              })
              .always(function() { spinner.stop(); } );
    };

    self.signout = function() {
        $.ajax({
            method: 'DELETE',
            url: "/conf/user",
            success: function(data) {
                console.log("logged out");
                if (google != null) google.disconnect();
                $('#authed').hide();
                $('#unauthed').show();
                $('#nonfrisch').hide();
            }
        });
    }

    self.goback = function() {
      $('#jobwindow').hide();
      this.description = movies;
      this.description = $sce.trustAsHtml(this.description);
    }

    self.switch = function(x) {
      $('#jobwindow').show();
      currentA = x;
      for (var i = 0; i < this.assignments.length; i++)
        if (this.assignments[i].id == x) {
            self.description = this.assignments[i].description;
            self.description = $sce.trustAsHtml(self.description);
            if (this.assignments[i].className != null) {
              self.jobrequest.className = this.assignments[i].className;
            } else {
               self.jobrequest.className = "";
            }
            if (this.assignments[i].initialSource != null) {
                self.jobrequest.text = this.assignments[i].initialSource;
            } else {
                self.jobrequest.text = "";
            }
        }
    };

  //  self.jobrequest.assignmentId = 1;

  }]);




function onSignIn(googleUser) {

    var profile = googleUser.getBasicProfile();
    google = googleUser;
/*    console.log("ID: " + profile.getId()); // Send to server?
    console.log('Full Name: ' + profile.getName());
    console.log('Given Name: ' + profile.getGivenName());
    console.log('Family Name: ' + profile.getFamilyName());
    console.log("Image URL: " + profile.getImageUrl());
    console.log("Email: " + profile.getEmail());
*/
    var user = {"email": profile.getEmail(),
        "firstName": profile.getGivenName(),
        "lastName": profile.getFamilyName(),
        "socialId": profile.getId(),
        "tokenId" : googleUser.getAuthResponse().id_token};

//The ID token you need to pass to the backend:
//    var id_token = googleUser.getAuthResponse().id_token;
//    console.log("ID Token: " + id_token);
    if (profile.getEmail().toLowerCase().search('@frisch.org') != -1) {
        $('#nonfrisch').hide();
        $('#authed').show();
        if (profile.getImageUrl() != null && profile.getImageUrl().length > 1) {
            $('#imgbox').html('<img src="' + profile.getImageUrl() + '" width=20 height=20></img>');
        }
        console.log("Frisch email received");

        $.ajax({
            headers: {
                "Content-Type": "application/json"
            },
            method: 'POST',
            url: '/conf/user',
            data: JSON.stringify(user)
        }).done(function(data) {
            result = data;
        }).error(function(data) {
            console.log("error: ");
            console.log(data);
        });

    } else {
        $('#nonfrisch').show();
        console.log("Non-Frisch emails are forbidden to log in and submit jobs");
        googleUser.disconnect();
    }

}
