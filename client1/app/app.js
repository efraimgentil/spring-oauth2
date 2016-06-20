var moduleName = "myApp";
var app = angular.module( moduleName , ["ngResource", "ngRoute", "ngCookies", "oauth"]);
app.constant("$userResourceUrl", "http://localhost:8080/ws");

app.config( function( $routeProvider , $locationProvider) {

    $routeProvider
        .when('/' , {
            templateUrl: 'app/view/home.html',
        })
        .when('/user-list', {
            templateUrl: 'app/view/user-list.html',
        })
        .otherwise({ //Anything that is not mapped will be considered home
            templateUrl: 'app/view/resource-not-found.html'
        });

    /*$locationProvider.html5Mode({
        enabled: true,
        requireBase: false
    }).hashPrefix('!');*/
});
app.factory('oauthHttpInterceptor', function (Storage) {
    return {
        request: function (config) {
            // This is just example logic, you could check the URL (for example)
            var token = Storage.get("token");
            if (token) {
                config.headers.Authorization = 'Bearer ' + token.access_token;
            }
            return config;
        }
    };
});
app.factory('responseObserver', function ($q, $window) {
    return {
        responseError: function (errorResponse) {
            switch (errorResponse.status) {
                case 403:
                    alert("Você não tem acesso a esse recurso");
                    break;
                case 500:
                    alert("Ouve um problema interno");
                    break;
            }
            return $q.reject(errorResponse);
        }
    };
});
app.config(function ($httpProvider) {
    $httpProvider.interceptors.push('oauthHttpInterceptor');
    $httpProvider.interceptors.push('responseObserver');
});
app.service("usuarioService", ["$http", "Storage", "$userResourceUrl", function ($http, Storage, $userResourceUrl) {
    this.getUserPermissions = function (callback) {
        var token = Storage.get("token");
        if (token) {
            $http.get($userResourceUrl + "/user-permissions").then(function success(response) {
                callback(response.data);
            });
        } else {
            callback([]);
        }
    }
    this.isUserAuthenticated = function () {
        var token = Storage.get("token");
        return (token) ? true : false;
    }
}]);


app.controller('mainCtrl', ["$scope", "$resource", "$http", "usuarioService",
    function ($scope, $resource, $http, usuarioService) {
        var self = this;
        $scope.$on('oauth:login', function(event, token) { self.loadPermissions(); });
        $scope.$on('oauth:authorized', function(event, token) { self.loadPermissions(); });

        self.loadPermissions = function(){
            $scope.permissions = {};
            usuarioService.getUserPermissions(function (data) {
                for(var i = 0 ; i < data.length ; i++ ){
                    $scope.permissions[data[i]] = true;
                }
                console.log( $scope.permissions );
            });
            $scope.userAuthenticated = usuarioService.isUserAuthenticated();
        };
        self.loadPermissions();


        $scope.foo = {id: 0, name: "sample foo"};
        $scope.foos = $resource("http://localhost:9080/hey");
        $scope.getFoo = function () {
            $scope.foo = $scope.foos.get();
            console.log($scope.foo);
        }
    }]);