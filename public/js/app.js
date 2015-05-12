/* App Module */

var executionware = angular.module('executionware', [
    'ngRoute',
    'ngResource',

    'executionwareControllers',
    'executionwareServices'
]);

executionware.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
            when('/api/cloud', {
                templateUrl: '/api/cloud',
                controller: 'CloudListCtrl'
            }).
            when('/api/cloud/:cloudId', {
                templateUrl: '/api/cloud/:phoneId',
                controller: 'CloudDetailCtrl'
            }).
            otherwise({
                redirectTo: '/api/cloud'
            });
    }]);
