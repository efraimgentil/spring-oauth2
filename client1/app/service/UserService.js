angular.module('myApp').service("userService" , [ "$userResourceUrl", "$http", function( $userResourceUrl , $http ){

    this.getUsers = function( callback ){
        $http.get($userResourceUrl + "/user/" ).then(
            function success(response){
                callback(response.data);
            }
        )
    }

    this.getUser = function( userId  , callback){
        $http.get($userResourceUrl + "/user/" + userId ).then(
            function success(response){
                callback(response.data);
            }
        )
    }

    this.save = function(user , callback){
        if(user.id){
            $http.put( $userResourceUrl + "/user/" + user.id , user ).success(function(data){
                callback(data);
            });
        }else{
            $http.post( $userResourceUrl + "/user/" , user ).success(function(data){
                callback(data);
            });
        }
    }

}]);