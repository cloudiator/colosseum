var executionwareServices = angular.module('executionwareServices', []);

executionwareServices.factory('Cloud', ['$resource',
    function($resource){
        return $resource('/cloud/:cloudId', {}, {
            get: {method:'GET', params:{cloudId:'1'}},
            query: {method:'GET', isArray:true},
            save: {method:'PUT'},
            delete: {method:'DELETE', params:{}},
            update: {method:'POST', params:{}}
        });
    }]);