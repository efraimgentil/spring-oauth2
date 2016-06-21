var moduleName = "myApp";
var app = angular.module( moduleName , ["ngResource", "ngRoute", "ngCookies", "oauth"]);
app.constant("$authorizationResourceUrl", "http://localhost:8080/ws");
app.constant("$userResourceUrl", "http://localhost:9080/");

app.config( function( $routeProvider , $locationProvider) {

    $routeProvider
        .when('/' , {
            templateUrl: 'app/view/home.html',
        })
        .when('/user-list', {
            templateUrl: 'app/view/user-list.html',
            controller: "UserListController"
        })
        .when("/new-user" , {
            templateUrl: 'app/view/new-user.html',
        })
        .otherwise({ //Anything that is not mapped will be considered home
            templateUrl: 'app/view/resource-not-found.html'
        });

    $locationProvider.html5Mode({
        enabled: false,
        requireBase: false
    }).hashPrefix('!');
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
                case  -1:
                    alert("Was not possible to establish a connection with the server");
                    break;
                case 403:
                    alert("You don't have access to this resource");
                    break;
                case 404:
                    alert("Resource not found");
                    break;
                case 500:
                    alert("Internal server error");
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
