(function() {
	var app = angular.module("rga-customer", [ 'ngCookies' ]);

	app.config([ '$sceDelegateProvider', function($sceDelegateProvider) {
		$sceDelegateProvider.resourceUrlWhitelist([ 'self', '/**' ])
	} ]);
	
	app
		.controller(
				'customerController',
				[
						'$scope',
						'$http',
						function($scope, $http) {
							$scope.errors = null;
							$scope.loading = true;
							$scope.customers = [];
	
							$scope.getCustomersData = function() {
								var urlCustomers = 'rest/customer/list';
	
								$http.get(urlCustomers).success(
										function(data) {
											$scope.errors = null;
											$scope.customers = data;
										});
							};
							
							$scope.newCustomer = function () {
								$scope.errors = null;
								$scope.customer = null;
							}
							
							$scope.go = function (id) {
								var urlCustomers = 'rest/customer/get/' + id;
								
								$http.get(urlCustomers).success(
										function(data) {
											$scope.errors = null;
											$scope.customer = data;
										});
							}
							
							$scope.remove = function () {
								var urlCustomers = 'rest/customer/remove/' +
									$scope.customer.id;
								
								$http.get(urlCustomers).success(
										function(data) {
											$scope.errors = null;
											$scope.customer = data;
											$scope.getCustomersData();
										});
							}
							
							$scope.add = function () {
								var urlCustomers = 'rest/customer/add';
								
								$http({
									method:'POST',
									url: urlCustomers,
									data: $scope.customer,
									headers : {
								        'Content-Type' : 'application/json'
								    }
								}).success(
									function(data) {
										$scope.errors = null;
										$scope.getCustomersData();
								}).error(
									function (data) {
										$scope.errors = data.errors;
								});
							}
							
							$scope.getCustomersData();
						} ]);
})();