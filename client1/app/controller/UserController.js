/**
 * Created by efraimgentil on 20/06/16.
 */
angular.module(moduleName).controller("UserController" , ["$scope" , "$location" , "$routeParams" , "userService", function( $scope , $location , $routeParams, userService ){
    var self = this;
    $scope.user = { login: "" , name : ""};
    if($routeParams.id){
        $scope.userId = $routeParams.id;
        userService.getUser( $scope.userId , function(data){
            $scope.user = data;
        });
    }

    $scope.cancel = function(){
        $location.path("/user")
    }

    $scope.save = function(){
        userService.save( $scope.user , function(data){ $scope.cancel(); });
    }

}]);