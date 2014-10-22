var controllers = angular.module('classifyApp.controllers', []);

controllers.controller('UserListController',
		function($scope, $state, popupService, $window, User) {
			$scope.users = User.query(); // fetch all users. Issues a GET to
			// /api/users

			$scope.deleteUser = function(user) { // Delete a user. Issues a
				// DELETE to /api/users/:id
				if (popupService.showPopup('Really delete this?')) {
					user.$delete(function() {
						$window.location.href = ''; // redirect to home
					});
				}
			};
		}).controller('UserViewController',
		function($scope, $stateParams, User) {
			$scope.user = User.get({
				id : $stateParams.id
			}); // Get a single user.Issues a GET to /api/users/:id
		}).controller('UserCreateController',
		function($scope, $state, $stateParams, User) {
			$scope.user = new User(); // create new user instance. Properties
			// will be set via ng-model on UI

			$scope.addUser = function() { // create a new user. Issues a POST
				// to /api/users
				$scope.user.$save(function() {
					$state.go('users'); // on success go back to home i.e.
					// users state.
				});
			};
		}).controller('UserEditController',
		function($scope, $state, $stateParams, User) {
			$scope.updateUser = function() { // Update the edited user.
												// Issues a PUT to
												// /api/users/:id
				$scope.user.$update(function() {
					$state.go('users'); // on success go back to home i.e.
					// users state.
				});
			};

			$scope.loadUser = function() { // Issues a GET request to
				// /api/users/:id to get a user to
				// update
				$scope.user = User.get({
					id : $stateParams.id
				});
			};

			$scope.loadUser(); // Load a user which can be edited on UI
		}).controller('AdListController',
		function($scope, $state, popupService, $window, Ad) {
			$scope.ads = Ad.query(); // fetch all ads. Issues a GET to
			// /api/ads

		}).controller('AdViewController',
		function($scope, $stateParams, Ad) {
			$scope.ad = Ad.get({
				id : $stateParams.id
			}); // Get a single ad.Issues a GET to /api/ads/:id
		}).controller('AdCreateController',
		function($scope, $state, $stateParams, Ad) {
			$scope.ad = new Ad(); // create new ad instance. Properties
			// will be set via ng-model on UI

			$scope.addAd = function() { // create a new ad. Issues a POST
				// to /api/ads
				$scope.ad.$save(function() {
					$state.go('ads'); // on success go back to home i.e.
					// ads state.
				});
			};
		}).controller('AdEditController',
		function($scope, $state, $stateParams, Ad) {
			$scope.updateAd = function() { // Update the edited ad.
												// Issues a PUT to
												// /api/ads/:id
				$scope.ad.$update(function() {
					$state.go('ads'); // on success go back to home i.e.
					// adq state.
				});
			};

			$scope.loadAd = function() { // Issues a GET request to
				// /api/ads/:id to get a ad to
				// update
				$scope.ad = Ad.get({
					id : $stateParams.id
				});
			};

			$scope.loadUser(); // Load a user which can be edited on UI
		});
