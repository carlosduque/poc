$(document).ready(function () {
	initialize();
});

function initialize() {
    var santiagoCity = new google.maps.LatLng(-33.431441,-70.488281);
    var mapOptions = {
      center: santiagoCity,
      zoom: 12,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    var map = new google.maps.Map(document.getElementById("map"),
        mapOptions);
}

function generateRandomPoints() {
	//setup 10 random points 
	var bounds = map.getBounds(); 
	var southWest = bounds.getSouthWest(); 
	var northEast = bounds.getNorthEast(); 
	var lngSpan = northEast.lng() – southWest.lng(); 
	var latSpan = northEast.lat() – southWest.lat(); 
	var markers = []; 
	
	for (var i = 0; i<10; i++) { 
	  var point = new GLatLng(southWest.lat() + latSpan * Math.random(), southWest.lng() + lngSpan * Math.random()); 
	  marker = new GMarker(point); 
	  map.addOverlay(marker); 
	  markers[i] = marker; 
	}
}