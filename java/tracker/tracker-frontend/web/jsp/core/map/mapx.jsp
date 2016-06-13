<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html>
<html>
  <head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <style type="text/css">
      html { height: 100% }
      body { height: 100%; margin: 0; padding: 0 }
      #map_canvas { height: 100% }
    </style>
    <script type="text/javascript"
      src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDjJYTB4-BJRCpTPGhq6cJTm0LjIEjrkJ0&sensor=false">
    </script>

    <script type="text/javascript">
      function initialize() {
        var santiagoCity = new google.maps.LatLng(-33.431441,-70.488281);
        var mapOptions = {
          //center: new google.maps.LatLng(-34.397, 150.644),
          center: santiagoCity,
          zoom: 8,
          mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        var map = new google.maps.Map(document.getElementById("map_canvas"),
            mapOptions);
      }
    </script>
  </head>
  <body onload="initialize()">
    <div id="map_canvas" style="width:100%; height:100%"></div>
  </body>
  <div>
  [
{"track":{"id":"1","date":"20130220144560","longitude":"-33.455000","latitude":"-70.655000","packagehandler":{"id":"1","name":"Anakin","lastname":"Skywalker","phonenumber":"11119999"}}},
{"track":{"id":"1","date":"20130220150060","longitude":"-33.455000","latitude":"-70.680000","packagehandler":{"id":"1","name":"Anakin","lastname":"Skywalker","phonenumber":"11119999"}}},
{"track":{"id":"1","date":"20130220151060","longitude":"-33.465000","latitude":"-70.710000","packagehandler":{"id":"1","name":"Anakin","lastname":"Skywalker","phonenumber":"11119999"}}},
{"track":{"id":"1","date":"20130220152560","longitude":"-33.467500","latitude":"-70.750000","packagehandler":{"id":"1","name":"Anakin","lastname":"Skywalker","phonenumber":"11119999"}}},
{"track":{"id":"1","date":"20130220155060","longitude":"-33.470000","latitude":"-70.775000","packagehandler":{"id":"1","name":"Anakin","lastname":"Skywalker","phonenumber":"11119999"}}},
{"track":{"id":"1","date":"20130220160560","longitude":"-33.480000","latitude":"-70.775000","packagehandler":{"id":"1","name":"Anakin","lastname":"Skywalker","phonenumber":"11119999"}}},
{"track":{"id":"1","date":"20130220170060","longitude":"-33.485000","latitude":"-70.785000","packagehandler":{"id":"1","name":"Anakin","lastname":"Skywalker","phonenumber":"11119999"}}},

{"track":{"id":"2","date":"20130220100060","longitude":"-33.456000","latitude":"-70.665000","packagehandler":{"id":"2","name":"Bruce","lastname":"Wayne","phonenumber":"22228888"}}},
{"track":{"id":"2","date":"20130220102560","longitude":"-33.457000","latitude":"-70.690000","packagehandler":{"id":"2","name":"Bruce","lastname":"Wayne","phonenumber":"22228888"}}},
{"track":{"id":"2","date":"20130220105060","longitude":"-33.468000","latitude":"-70.720000","packagehandler":{"id":"2","name":"Bruce","lastname":"Wayne","phonenumber":"22228888"}}},
{"track":{"id":"2","date":"20130220110560","longitude":"-33.468500","latitude":"-70.760000","packagehandler":{"id":"2","name":"Bruce","lastname":"Wayne","phonenumber":"22228888"}}},
{"track":{"id":"2","date":"20130220121560","longitude":"-33.473000","latitude":"-70.785000","packagehandler":{"id":"2","name":"Bruce","lastname":"Wayne","phonenumber":"22228888"}}},
{"track":{"id":"2","date":"20130220134560","longitude":"-33.483000","latitude":"-70.795000","packagehandler":{"id":"2","name":"Bruce","lastname":"Wayne","phonenumber":"22228888"}}},
{"track":{"id":"2","date":"20130220140060","longitude":"-33.488000","latitude":"-70.799000","packagehandler":{"id":"2","name":"Bruce","lastname":"Wayne","phonenumber":"22228888"}}},

{"track":{"id":"3","date":"20130220110060","longitude":"-33.456000","latitude":"-70.655000","packagehandler":{"id":"3","name":"Erick","lastname":"Lenscherr","phonenumber":"33337777"}}},
{"track":{"id":"3","date":"20130220112560","longitude":"-33.455000","latitude":"-70.649000","packagehandler":{"id":"3","name":"Erick","lastname":"Lenscherr","phonenumber":"33337777"}}},
{"track":{"id":"3","date":"20130220115060","longitude":"-33.464000","latitude":"-70.649500","packagehandler":{"id":"3","name":"Erick","lastname":"Lenscherr","phonenumber":"33337777"}}},
{"track":{"id":"3","date":"20130220120560","longitude":"-33.463500","latitude":"-70.638000","packagehandler":{"id":"3","name":"Erick","lastname":"Lenscherr","phonenumber":"33337777"}}},
{"track":{"id":"3","date":"20130220131560","longitude":"-33.472000","latitude":"-70.635800","packagehandler":{"id":"3","name":"Erick","lastname":"Lenscherr","phonenumber":"33337777"}}},
{"track":{"id":"3","date":"20130220144560","longitude":"-33.481000","latitude":"-70.605000","packagehandler":{"id":"3","name":"Erick","lastname":"Lenscherr","phonenumber":"33337777"}}},
{"track":{"id":"3","date":"20130220160060","longitude":"-33.480000","latitude":"-70.599000","packagehandler":{"id":"3","name":"Erick","lastname":"Lenscherr","phonenumber":"33337777"}}},
]
</div>
</html>

