/**
 * Created by efraimgentil on 20/06/16.
 */

angular.module(moduleName).controller('MainController', ["$scope", "$resource", "$http", "AuthenticationService",
    function ($scope, $resource, $http, AuthenticationService) {
        var self = this;
        $scope.$on('oauth:login', function (event, token) {
            self.loadPermissions();
        });
        $scope.$on('oauth:authorized', function (event, token) {
            self.loadPermissions();
        });

        self.loadPermissions = function () {
            $scope.permissions = {};
            AuthenticationService.getUserPermissions(function (data) {
                for (var i = 0; i < data.length; i++) {
                    $scope.permissions[data[i]] = true;
                }
                console.log($scope.permissions);
            });
            $scope.userAuthenticated = AuthenticationService.isUserAuthenticated();
        };
    }
]);