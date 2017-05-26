var myresult;
var mydataresult;
angular.module('CodeBotApp', ['ngSanitize'])
  .controller('FrontCtrl', ['$http', '$scope', function($http, $scope) {

    var self = this;
    var currentA = 1;

    self.registered = function() {
    $http.get('/conf/user').then(function(result) {
      mydataresult = result;
      if (result.data.authenticated) {
        $('#unauthed').hide();
        $('#authed').show();
        self.user.username = result.data.details.name
      } else {
        $('#authed').hide();
        $('#unauthed').show();
      }
    })
    }

    self.registered();

    this.assignments = [];
    this.message = 'Ready to process';
    this.description = '';

    $http.get('/conf/assignments').then(function(response) {
      self.assignments = response.data;
      currentA = response.data[0].id;
    }, function(errResponse) {
      console.log("error fetching assignments- ", errResponse);
    });


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


    self.submit = function() {
      self.jobrequest.assignmentId = currentA;
      $.ajax( { headers: {
                  "Content-Type": "application/json"
                },
                method: 'POST',
                url: '/conf/submit',
                data: JSON.stringify(self.jobrequest),
                success: function(data) { console.log(data); }
              })
              .done(function(data) {
                self.message = data;
                console.log(self.message);
                $scope.$apply();
              })
              .error(function(data) {
                self.message = data.responseText;
                console.log(self.message);
                $scope.$apply();
              });
    };

    self.signout = function() {
      self.user.username = "";
      self.user.password = "";
      $http.get('/logout').then(function() {
        $('#authed').hide();
        $('#unauthed').show();
      })
    }

    self.switch = function(x) {
      currentA = x;
      for (var i = 0; i < this.assignments.length; i++)
        if (this.assignments[i].id == x)
          self.description = this.assignments[i].description;
    };

  //  self.jobrequest.assignmentId = 1;

  }])
  .service('Authenication', function() {

  })
