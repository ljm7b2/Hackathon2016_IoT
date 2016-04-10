angular.module('starter.controllers', [])

.controller('DashCtrl', function($scope, $http, $interval) {
    
    $scope.messageAsleep = "Your Child is Asleep.";
    $scope.messageAwake = "Your Child is Awake.";
    
    var imgAsleep = "img/asleep.jpg";
    var imgAwake = "img/awake.jpg";
    
    
    var success = 1;
    
    if(success){
        $scope.messageUser = $scope.messageAsleep;
        $scope.imageUser = imgAsleep;
    }else{
        $scope.messageUser = $scope.messageAwake;
        $scope.imageUser = imgAwake;
    }
    
    var link = "http://ec2-52-91-251-221.compute-1.amazonaws.com:8080/CodeTroopersIoT/GetSleepStatus";
    
    var getStatus = function(){
        $http.get(link).then(function (response){

            if(response.data.status == "SLEEPING"){
                $scope.messageUser = $scope.messageAsleep;
                $scope.imageUser = imgAsleep;
            }else{
                $scope.messageUser = $scope.messageAwake;
                $scope.imageUser = imgAwake;
            }        
        });
    }
    $interval(getStatus, 1000);
    
    
    
    
})

.controller('ChatsCtrl', function($scope, Chats) {
  // With the new view caching in Ionic, Controllers are only called
  // when they are recreated or on app start, instead of every page change.
  // To listen for when this page is active (for example, to refresh data),
  // listen for the $ionicView.enter event:
  //
  //$scope.$on('$ionicView.enter', function(e) {
  //});

  $scope.chats = Chats.all();
  $scope.remove = function(chat) {
    Chats.remove(chat);
  };
})

.controller('ChatDetailCtrl', function($scope, $stateParams, Chats) {
  $scope.chat = Chats.get($stateParams.chatId);
})

.controller('AccountCtrl', function($scope) {
  $scope.settings = {
    enableFriends: true
  };
});
