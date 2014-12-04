/* App Module */

var executionware = angular.module('executionware', [
    'ngRoute',
    'ngResource',

    'executionwareControllers',
    'executionwareServices'
]);

executionware.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/cloud', {
                templateUrl: 'cloud',
                controller: 'CloudListCtrl'
            }).
            when('/cloud/:cloudId', {
                templateUrl: 'cloud/:phoneId',
                controller: 'CloudDetailCtrl'
            }).
            otherwise({
                redirectTo: '/cloud'
            });
    }]);