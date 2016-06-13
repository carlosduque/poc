<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
  <head>
    <meta charset="UTF-8" />
	<title><s:text name="application.name" /></title>
    <link href="<s:url value='/css/default/layout.css' />" type="text/css" rel="stylesheet" />
    <link href="<s:url value='/css/default/colors.css' />" type="text/css" rel="stylesheet" />
    <link href="<s:url value='/css/default/fonts.css' />" type="text/css" rel="stylesheet" />
    <link href="<s:url value='/css/default/forms.css' />" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="<s:url value='https://maps.googleapis.com/maps/api/js?key=AIzaSyDjJYTB4-BJRCpTPGhq6cJTm0LjIEjrkJ0&sensor=false' />"></script>
    <script type="text/javascript" src="<s:url value='/js/jquery-1.9.1.js' />"></script>
    <script type="text/javascript" src="<s:url value='/js/map.js' />"></script>
  </head>

  <body>
    <h1><s:text name="application.menu.main" /></h1>
    <hr />
    <ul>
      <li><a href="<s:url namespace='admin' action='Add'/>"><s:text name="packagehandler.add" /></a></li>
      <li><a href="<s:url namespace='admin' action='List'/>"><s:text name="packagehandler.list" /></a></li>
      <!-- li><a href="<s:url action='map/ReportMyCurrentLocation'/>">Report current location [not really for navigation purposes]</a></li-->
      <li><a href="<s:url namespace='map' action='Show'/>"><s:text name="map.show" /></a></li>
    </ul>
    <hr />
    <h3><s:text name="json.note.title" /></h3>
    <s:text name="json.note.message" /><a href="<s:url action='ajax/GetJsonTrackingHistory'/>">ajax/GetJsonTrackingHistory</a>
  </body>
	
</html>
