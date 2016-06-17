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
      console.log(token);
      if ( token ) {
        config.headers.Authorization = 'Bearer ' + token.access_token;
      }
      return config;
    }
  };
});
app.config(function ($httpProvider) {
  $httpProvider.interceptors.push('oauthHttpInterceptor');
});
 
app.controller('mainCtrl', function($scope,$resource,$http) {
  
    $scope.foo = {id:0 , name:"sample foo"};
    $scope.foos = $resource( "http://localhost:9080/hey");
    $scope.getFoo = function(){
        $scope.foo = $scope.foos.get();
    } 
});