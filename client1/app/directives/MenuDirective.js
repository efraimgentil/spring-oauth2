angular.module( moduleName ).directive( "menuDirective", [ "$location" , "$rootScope" ,  function($location , $rootScope){
  return {
      restrict: 'E'
      , scope : {
           url : "@"
          ,iconClass : "@"
          ,name : "@"
          ,showIf : "@"
      }
      , transclude: true
      , replace: true
      , link: function( scope , element , attrs ){
          scope.active = scope.url.replace("#","") == $location.path();
          $rootScope.$on('$routeChangeSuccess', function(e, current, pre) {
              scope.active = scope.url.replace("#!","") == $location.path();
          });
      }
      ,templateUrl: 'app/view/directives/menu-directive.html'
  };
}]);