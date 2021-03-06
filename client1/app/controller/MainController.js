/**
 * Created by efraimgentil on 20/06/16.
 */
angular.module(moduleName).controller('MainController', ["$scope", "$resource", "$http", "AuthenticationService", "$rootScope",
    function ($scope, $resource, $http, AuthenticationService , $rootScope) {
        var self = this;

        $scope.userInfo = { login: "" , name: ""};

        $scope.$on('oauth:login', function (event, token) {
            self.loadPermissions();
        });
        $scope.$on('oauth:authorized', function (event, token) {
            self.loadPermissions();
        });

        $scope.permissions = {};
        self.loadPermissions = function () {
            $scope.permissions = {};
            AuthenticationService.getUserPermissions(function (data) {
                for (var i = 0; i < data.length; i++) {
                    $scope.permissions[data[i]] = true;
                }
                console.log($scope.permissions);
            });
            AuthenticationService.getUserInfo(function(data){
                $scope.userInfo = data;
            });
            $scope.userAuthenticated = AuthenticationService.isUserAuthenticated();
        }

        $scope.logout = function(){
            $rootScope.$broadcast("oauth:loggedOut");
        }
    }
]);