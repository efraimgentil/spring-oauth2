angular.module('myApp').service("DateService" , [ "$dateResourceUrl", "$http", function( $dateResourceUrl , $http ){

    this.getCurrentTimeForUser = function( callback ){
        $http.get( $dateResourceUrl + "api/time" ).success(function(data){
            callback(data);
        });
    }

}]);