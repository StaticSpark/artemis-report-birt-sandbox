/**
 * Created by zhangyong on 14-1-17.
 */

function search_controler($scope) {
    $scope.do_search = function () {
        alert("you selected:" + $scope.selected_date);
    }

    $scope.do_switch = function () {
        // alert("switch data and chart...");
        switchLocation = new SwitchLocation();

    }

}