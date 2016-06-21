angular.module('myApp').service("userService" , [ "$userResourceUrl", "$http", function( $userResourceUrl , $http ){

    this.getUsers = function( callback ){
        $http.get($userResourceUrl + "/user/" ).then(
            function success(response){
                callback(response.data);
            }
        )
    }

}]);