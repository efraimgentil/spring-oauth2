/**
 * Created by efraimgentil on 20/06/16.
 */
angular.module(moduleName).controller("UserController" , ["$scope" , "$location" , "userService", function( $scope , $location, userService ){
    var self = this;
    $scope.user = { login: "" , name : ""};

    $scope.cancel = function(){
        $location.path("/user")
    }

    $scope.save = function(){
        userService.save( $scope.user , function(){  });
    }

}]);