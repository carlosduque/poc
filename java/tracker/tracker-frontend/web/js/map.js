$(document).ready(function () {
	//var params = "startDate=YYYYMMDD&endDate=YYYYMMDD";
	var contextPath = '/' + window.location.pathname.split('/')[1];
	var getJsonTrackingHistoryUrl = contextPath  + "/ajax/GetJsonTrackingHistory.action";
	   var markersArray = [];
	   var homeLat = -33.421423;
	   var homeLon = -70.564475;
	   var equilibra = new google.maps.LatLng(homeLat, homeLon);
    $.ajax({
    	type: "get",
            // el módulo que hará la búsqueda
    		url: getJsonTrackingHistoryUrl,
            // los datos para la consulta
            //data: params,
            dataType: 'json',
            // que hacer si esto falla
            error: function (jqXHR, ajaxOptions, thrownError) {
                console.log('jqXHR: ' + jqXHR.status + ' | thrownError: ' + thrownError);
              },
            // que hacer si funciona
            success: function (data) {
                //$("#loc").append(response);
            	var coords = [equilibra];
                $.each(data, function(index, value){
                	var location = new google.maps.LatLng(data[index].latitude, data[index].longitude);
                	var info = data[index].packageHandlerDto.name + ' ' + data[index].packageHandlerDto.lastname + ' (' + data[index].packageHandlerDto.phonenumber + ') - ' + data[index].date;
                	console.log('location: (' + data[index].latitude + ',' + data[index].longitude + ') '+ info);
                	addMarker(location, info);
                	coords.push(location);
                });
              }
        });

   //var markersArray = [];
   //var homeLat = -33.421423;
   //var homeLon = -70.564475;
   //var equilibra = new google.maps.LatLng(homeLat, homeLon);
   var mapOptions = {
     //-33.421423, -70.564475 : Manquehue Sur 1333, Santiago, Chile
     center: equilibra,
     zoom: 12,
     mapTypeId: google.maps.MapTypeId.ROADMAP
   };
   var map = new google.maps.Map(document.getElementById("map"), mapOptions);

   home = new google.maps.Marker({
            position: equilibra,
            draggable:false,
            animation: google.maps.Animation.DROP,
            title: "Equilibra",
            map: map
   });
    
    var shadow = '../images/icons/cycle-64x20-bw.png';
    var motorcycle = '../images/icons/cycle-64x20.png';

    function addMarker(location, info) {
        marker = new google.maps.Marker({
            position: location,
            draggable:false,
            animation: google.maps.Animation.DROP,
            icon: shadow,
            title: info,
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

    //addLatestLocationMarker(new google.maps.LatLng(homeLat + 0.050000, homeLon + 0.020000), "PackageHandler 1");
    //addLatestLocationMarker(new google.maps.LatLng(homeLat - 0.040000, homeLon - 0.020000), "PackageHandler 2");
    //addLatestLocationMarker(new google.maps.LatLng(homeLat + 0.010000, homeLon - 0.050000), "PackageHandler 3");

//    var coords1 = [
//        equilibra,
//        new google.maps.LatLng(homeLat + 0.010000, homeLon + 0.010000),
//        new google.maps.LatLng(homeLat + 0.020000, homeLon + 0.010000),
//        new google.maps.LatLng(homeLat + 0.030000, homeLon + 0.020000),
//        new google.maps.LatLng(homeLat + 0.040000, homeLon + 0.030000),
//        new google.maps.LatLng(homeLat + 0.050000, homeLon + 0.020000)
//    ];


    //var route1 = new google.maps.Polyline({
    //    path: coords1,
    //    strokeColor: "#BB00AA",
    //    strokeOpacity: 1.0,
    //    strokeWeight: 2
    //    });


    //route1.setMap(map);
});
