var executionwareServices = angular.module('executionwareServices', []);

executionwareServices.factory('Cloud', ['$resource',
    function($resource){
        return $resource('/api/cloud/:cloudId', {}, {
            get: {method:'GET', params:{cloudId:'1'}},
            query: {method:'GET', isArray:true},
            save: {method:'POST'},
            delete: {method:'DELETE', params:{}},
            update: {method:'PUT', params:{}}
        });
    }]);