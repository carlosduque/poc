$(document).ready(function () {
	initialize();
});

var markersArray = [];

function initialize() {
   var homeLat = -33.421423;
   var homeLon = -70.564475;
   var equilibra = new google.maps.LatLng(homeLat, homeLon);
   var mapOptions = {
     //-33.421423, -70.564475 : Manquehue Sur 1333, Santiago, Chile
     center: equilibra,
     zoom: 12,
     mapTypeId: google.maps.MapTypeId.ROADMAP
   };
   var map = new google.maps.Map(document.getElementById("map"), mapOptions);

   google.maps.event.addListener(map, 'click', function(event) {
       addMarker(event.latLng);
    });

    home = new google.maps.Marker({
            position: equilibra,
            draggable:false,
            animation: google.maps.Animation.DROP,
            title: "Equilibra",
            map: map
        });
    var shadow = '../images/icons/cycle-64x20-bw.png';
    var motorcycle = '../images/icons/cycle-64x20.png';

    function addMarker(location) {
        marker = new google.maps.Marker({
            position: location,
            draggable:false,
            animation: google.maps.Animation.DROP,
            icon: shadow,
            map: map
        });
        markersArray.push(marker);
    };

    function addLatestLocationMarker(location, info) {
        marker = new google.maps.Marker({
            position: location,
            draggable:false,
            animation: google.maps.Animation.BOUNCE,
            icon: motorcycle,
            title: info,
            map: map
        });
        markersArray.push(marker);
    };

    addMarker(new google.maps.LatLng(homeLat + 0.010000, homeLon + 0.010000));

    addMarker(new google.maps.LatLng(homeLat + 0.020000, homeLon + 0.010000));
    addMarker(new google.maps.LatLng(homeLat + 0.030000, homeLon + 0.020000));
    addMarker(new google.maps.LatLng(homeLat + 0.040000, homeLon + 0.030000));

    addMarker(new google.maps.LatLng(homeLat - 0.010000, homeLon - 0.010000));
    addMarker(new google.maps.LatLng(homeLat - 0.020000, homeLon - 0.001000));
    addMarker(new google.maps.LatLng(homeLat - 0.030000, homeLon - 0.010000));
    addMarker(new google.maps.LatLng(homeLat - 0.040000, homeLon - 0.010000));

    addMarker(new google.maps.LatLng(homeLat + 0.010000, homeLon - 0.010000));
    addMarker(new google.maps.LatLng(homeLat + 0.015000, homeLon - 0.018000));
    addMarker(new google.maps.LatLng(homeLat + 0.016000, homeLon - 0.019000));


    addLatestLocationMarker(new google.maps.LatLng(homeLat + 0.050000, homeLon + 0.020000), "PackageHandler 1");
    addLatestLocationMarker(new google.maps.LatLng(homeLat - 0.040000, homeLon - 0.020000), "PackageHandler 2");
    addLatestLocationMarker(new google.maps.LatLng(homeLat + 0.010000, homeLon - 0.050000), "PackageHandler 3");

    var coords1 = [
        equilibra,
        new google.maps.LatLng(homeLat + 0.010000, homeLon + 0.010000),
        new google.maps.LatLng(homeLat + 0.020000, homeLon + 0.010000),
        new google.maps.LatLng(homeLat + 0.030000, homeLon + 0.020000),
        new google.maps.LatLng(homeLat + 0.040000, homeLon + 0.030000),
        new google.maps.LatLng(homeLat + 0.050000, homeLon + 0.020000)
    ];

    var coords2 = [
        equilibra,
        new google.maps.LatLng(homeLat - 0.010000, homeLon - 0.010000),
        new google.maps.LatLng(homeLat - 0.020000, homeLon - 0.001000),
        new google.maps.LatLng(homeLat - 0.030000, homeLon - 0.010000),
        new google.maps.LatLng(homeLat - 0.040000, homeLon - 0.010000),
        new google.maps.LatLng(homeLat - 0.040000, homeLon - 0.020000)
    ];

    var coords3 = [
        equilibra,
        new google.maps.LatLng(homeLat + 0.010000, homeLon - 0.010000),
        new google.maps.LatLng(homeLat + 0.015000, homeLon - 0.018000),
        new google.maps.LatLng(homeLat + 0.016000, homeLon - 0.019000),
        new google.maps.LatLng(homeLat + 0.010000, homeLon - 0.050000)
    ];

    var route1 = new google.maps.Polyline({
        path: coords1,
        strokeColor: "#BB00AA",
        strokeOpacity: 1.0,
        strokeWeight: 2
        });

    var route2 = new google.maps.Polyline({
        path: coords2,
        strokeColor: "#FF0099",
        strokeOpacity: 1.0,
        strokeWeight: 2
        });

    var route3 = new google.maps.Polyline({
        path: coords3,
        strokeColor: "#AA00DD",
        strokeOpacity: 1.0,
        strokeWeight: 2
        });

    route1.setMap(map);
    route2.setMap(map);
    route3.setMap(map);

};
