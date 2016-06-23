angular.module(moduleName).service("AuthenticationService", ["$http", "Storage", "$authorizationResourceUrl", function ($http, Storage, $authorizationResourceUrl) {
    var self = this;
    self.permissions = null;

    this.getUserInfo = function(callback){
        $http.get($authorizationResourceUrl  +"/user-info" ).then(function success(response){
           callback(response.data);
        });
    }
    this.getUserPermissions = function (callback) {
        if (self.permissions) {
            callback(self.permissions);
        }
        var token = Storage.get("token");
        if (token) {
            $http.get($authorizationResourceUrl + "/user-permissions").then(function success(response) {
                self.permissions = response.data;
                callback(self.permissions);
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
