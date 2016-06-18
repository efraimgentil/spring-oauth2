var app = angular.module('myApp', ["ngResource","ngRoute","ngCookies" ,"oauth"]);
app.config(function($locationProvider) {
  $locationProvider.html5Mode({
      enabled: true,
      requireBase: false
    }).hashPrefix('!');
});
app.factory('oauthHttpInterceptor', function (Storage) {
  return {
    request: function (config) {
      // This is just example logic, you could check the URL (for example)
      var token = Storage.get("token");
      if ( token ) {
        config.headers.Authorization = 'Bearer ' + token.access_token;
      }
      return config;
    }
  };
});
app.factory('responseObserver', function ($q , $window) {
    return {
        responseError: function (errorResponse) {
            switch (errorResponse.status) {
                case 403:
                    alert("Você não tem acesso a esse recurso");
                    break;
                case 500:
                    alert("Ouve um problema interno");
                    break;
            }
            return $q.reject(errorResponse);
        }
    };
});
app.config(function ($httpProvider) {
    $httpProvider.interceptors.push('oauthHttpInterceptor');
    $httpProvider.interceptors.push('responseObserver');
});
app.service("usuarioService" , [ "$http" , "Storage"] , function($http , Storage){
    this.getUserPermissions = function(){
        var token = Storage.get("token");
        if(token){

        }else{
            return [];
        }
    }


});


var http;
app.controller('mainCtrl', function($scope,$resource, $http) {
    http =  $http;

    $scope.foo = {id:0 , name:"sample foo"};
    $scope.foos = $resource( "http://localhost:9080/hey");
    $scope.getFoo = function(){
        $scope.foo = $scope.foos.get();
        console.log( $scope.foo );
    } 
});