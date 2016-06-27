var moduleName = "myApp";
var app = angular.module( moduleName , ["ngResource", "ngRoute", "ngCookies", "oauth"]);
app.constant("$authorizationResourceUrl", "http://localhost:8080/ws");
app.constant("$userResourceUrl", "http://localhost:9080/");
app.constant("$dateResourceUrl", "http://127.0.0.1:5000/");

app.config( function( $routeProvider , $locationProvider) {

    $routeProvider
        .when('/' , {
            templateUrl: 'app/view/home.html',
        })
        .when('/user', {
            templateUrl: 'app/view/user/user-list.html',
            controller: "UserListController"
        })
        .when("/user/new" , {
            templateUrl: 'app/view/user/user-form.html',
            controller: "UserController"
        })
        .when("/user/edit/:id" , {
            templateUrl: 'app/view/user/user-form.html',
            controller: "UserController"
        })
      .when("/date" , {
          templateUrl: 'app/view/date.html',
          controller: "DateController"
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
                    console.log(errorResponse);
                    alert("Was not possible to establish a connection with the server");
                    break;
                case 401:
                    alert("You don't have access to this resource");
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
