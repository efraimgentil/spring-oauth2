angular.module(moduleName).controller("DateController" , ["$scope" , "DateService", function( $scope , DateService ){
    var self = this;

    DateService.getCurrentTimeForUser(function(data){
       $scope.userDate = data;
    });

}]);