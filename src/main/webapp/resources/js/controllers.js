var controllers = angular.module('classifyApp.controllers', []);

controllers.controller('UserListController',
		function($scope, $state, popupService, User) {
			$scope.users = User.query(); // fetch all users.
			$scope.deleteUser = function(user) { // Delete a user.
				if (popupService.showPopup('Really delete this?')) {
					user.$delete(function() {
						$scope.users = User.query();
					});
				}
			};
		}).controller('UserViewController',
		function($scope, $stateParams, User) {
			$scope.user = User.get({
				id : $stateParams.id
			}); // Get a single user.
		}).controller('UserCreateController',
		function($scope, $state, $stateParams, User) {
			$scope.user = new User(); // create new user instance. Properties
			// will be set via ng-model on UI
			$scope.addUser = function() { // create a new user.
				$scope.user.$save(function() {
					$state.go('users'); // on success go back to users state.
				});
			};
		}).controller('UserEditController',
		function($scope, $state, $stateParams, User, $rootScope) {
			$scope.updateUser = function() { // Update the edited user.
				$scope.user.$update(function() {
					if ($rootScope.userAuth.id == $scope.user.id) {
						$rootScope.userAuth = $scope.user;
					}
					$state.go('viewUser', {
						id : $scope.user.id
					}); // on success go back to viewUser state.
				});
			};

			$scope.loadUser = function() {
				$scope.user = User.get({
					id : $stateParams.id
				});
			};

			$scope.loadUser(); // Load a user which can be edited on UI
		}).controller('AdListController', function($scope, $state, Ad) {
	$scope.ads = Ad.query(); // fetch all ads.

}).controller('AdViewController', function($scope, $stateParams, Ad) {
	$scope.ad = Ad.get({
		id : $stateParams.id
	}); // Get a single ad.
}).controller('AdCreateController',
		function($scope, $state, $stateParams, Ad, User, $rootScope) {
			$scope.ad = new Ad(); // create new ad instance. Properties
			// will be set via ng-model on UI
			$scope.ad.guy = $rootScope.userAuth;
			$scope.addAd = function() { // create a new ad.
				$scope.ad.$save(function() {
					$state.go('ads'); // on success go back to ads state.
				});
			};
		}).controller('AdEditController',
		function($scope, $state, $stateParams, Ad) {
			$scope.updateAd = function() { // Update the edited ad.
				$scope.ad.$update(function() {
					$state.go('myAds'); // on success go back to myAds state.
				});
			};

			$scope.loadAd = function() {
				$scope.ad = Ad.get({
					id : $stateParams.id
				});
			};

			$scope.loadAd(); // Load a user which can be edited on UI
		}).controller('MyAdsController',
		function($scope, $state, popupService, Ad) {
			$scope.ads = Ad.query(); // fetch all ads.
			$scope.matchGuy = function(query) {
				return function(ad) {
					return (ad.guy.id == query);
				}
			};

			$scope.deleteAdd = function(ad) { // Delete an ad.
				if (popupService.showPopup('Really delete this?')) {
					ad.$delete(function() {
						$scope.ads = Ad.query();
					});
				}
			};

		});
