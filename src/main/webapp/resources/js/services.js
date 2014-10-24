
angular.module('classifyApp.services', []).factory('User', function($resource) {
	return $resource('api/users/:id', {
		id : '@id'
	}, {
		update : {
			method : 'PUT'
		}
	});
}).factory('Ad', function($resource) {
	return $resource('api/ads/:id', {
		id : '@id',
		filter: '@filter'
	}, {
		update : {
			method : 'PUT'
		}
	});
}).service('popupService',function($window){
    this.showPopup=function(message){
        return $window.confirm(message);
    }
});