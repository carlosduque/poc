angular.module('basicDemo', [])
 .controller('DemoController', ['$scope', function($scope) {
    $scope.data = {
       color: null
    };
    $scope.options = [
       {id: 'primary', name: 'Primary'},
       {id: 'success', name: 'Success'},
       {id: 'warning', name: 'Warning'},
       {id: 'danger', name: 'Danger'},
       {id: 'info', name: 'Info'}
     ];
}]);