var userInfoModule = angular.module('userInfoModule', []);
			userInfoModule.controller('userInfoCtrl', ['$scope', function($scope) {
				$scope.userInfo = {	}
				$scope.getFormData = function() {
					console.log($scope.userInfo)
					$("#myaddress").css("display", "block")

				};								
			}])