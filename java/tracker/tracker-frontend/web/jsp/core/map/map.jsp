<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
 
<!DOCTYPE html>
<html>
  <head>
    <title><s:text name="map.title" /></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no"/>
    <meta charset="UTF-8" />
    <link href="<s:url value='/css/default/layout.css' />" type="text/css" rel="stylesheet" />
    <link href="<s:url value='/css/default/colors.css' />" type="text/css" rel="stylesheet" />
    <link href="<s:url value='/css/default/fonts.css' />" type="text/css" rel="stylesheet" />
    <link href="<s:url value='/css/default/forms.css' />" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="<s:url value='https://maps.googleapis.com/maps/api/js?key=AIzaSyDjJYTB4-BJRCpTPGhq6cJTm0LjIEjrkJ0&sensor=false' />"></script>
    <script type="text/javascript" src="<s:url value='/js/jquery-1.9.1.js' />"></script>
    <script type="text/javascript" src="<s:url value='/js/map.js' />"></script>
  </head>
  <body>
    <s:url action="/" var="ctxPath" />
    <div id="map"></div>
    <div id="loc"></div>
  </body>
</html>

