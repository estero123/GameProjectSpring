$(document).ready ( function() {
    var x = document.getElementsByClassName("gameBox");
    if ((typeof window.orientation !== "undefined") || (navigator.userAgent.indexOf('IEMobile') !== -1)) {
        for (var i = 0; i<x.length; i++) {
            x[i].style.backgroundColor = "green";
        }
    } else {
        for (var i = 0; i<x.length; i++) {
            x[i].style.backgroundColor = "red";
        }
//            return ((typeof window.orientation !== "undefined") || (navigator.userAgent.indexOf('IEMobile') !== -1));
    }
});