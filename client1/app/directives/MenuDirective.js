angular.module( moduleName ).directive( "menuDirective", [ function(){
  return {
      restrict: 'E'
      , scope : {
           url : "@"
          ,iconClass : "@"
          ,name : "@"
      }
      , transclude: true
      , replace: true
      , link: function( scope , element , attrs ){

      }
      ,templateUrl: 'app/view/directives/menu-directive.html'
  };
}]);