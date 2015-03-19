/**
 * Created by daniel on 24.11.14.
 */

var executionwareControllers = angular.module('executionwareControllers', []);

executionwareControllers.controller('CloudListCtrl', ['$scope', 'Cloud',
    function ($scope, Cloud) {

        $scope.newCloud = function () {
            Cloud.save($scope.cloud)
        }

        $scope.clouds = Cloud.query();
        $scope.orderProp = 'name';
    }]);
