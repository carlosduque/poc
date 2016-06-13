$(document).ready(function () {
	"use strict";
	tracker.map($("#map")[0], -33.431441, -70.488281);
});

var tracker = tracker || {};
tracker.map = function (mapDiv, latitude, longitude) {
	"use strict";

	var createMap = function (mapDiv, coordinates) {
		var mapOptions = {
			center: coordinates,
			mapTypeId: google.maps.MapTypeId.ROADMAP,
			zoom: 15
		};
		return new google.maps.Map(mapDiv, mapOptions);
	};
	
	var addMarker = function (map, coordinates) {
		var markerOptions = {
			clickable: false,
			map: map,
			position: coordinates
		};
		 
		return new google.maps.Marker(markerOptions);
	};

	var initialize = function (mapDiv, latitude, longitude) {
		var coordinates = new google.maps.LatLng(latitude, longitude);
		createMap(mapDiv, coordinates);
	};
	 
	initialize(mapDiv, latitude, longitude);
};

