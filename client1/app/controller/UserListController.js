/**
 * Created by efraimgentil on 20/06/16.
 */
angular.module(moduleName).controller("UserListController" , ["$scope", "$location" , "userService", function( $scope , $location, userService ){
    var self = this;
    $scope.users = [];
    userService.getUsers( function(data){  $scope.users = data; console.log(data) ;  });

    $scope.refreshing = false;

    $scope.startRefresh = function(){
        $scope.refreshing = true;
        setTimeout( function(){
            userService.getUsers( function(data){
                $scope.users = data;
                console.log(data);
                if($scope.refreshing){
                    $scope.startRefresh();
                }
            });
        } , 1000 );
    }

    $scope.stopRefresh = function(){
        $scope.refreshing = false;
    }

    $scope.newUser = function(){
        $location.path("/user/new")
    }

    $scope.edit = function(userId){
        $location.path("/user/edit/" + userId );
    }
}]);