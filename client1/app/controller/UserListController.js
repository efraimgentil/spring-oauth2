/**
 * Created by efraimgentil on 20/06/16.
 */
angular.module(moduleName).controller("UserListController" , ["$scope" , "userService", function( $scope , userService ){
    $scope.users = [];
    userService.getUsers( function(data){  $scope.users = data; console.log(data) ;  });
}]);