<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
  <head>
    <title><s:text name="packagehandler.manager" /></title>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <meta charset="UTF-8" />
    <link href="<s:url value='/css/default.css' />" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="<s:url value='/js/jquery-1.9.1.js' />"></script>
    <script type="text/javascript" src="<s:url value='/js/main.js' />"></script>
  </head>

  <body>
	<h2><s:text name="packagehandler.registered.successfully" /></h2>
	<hr />
	<ul>
	    <li><s:label name="pkgHandler.id" key="packagehandler.id" /></li>
		<li><s:label name="pkgHandler.name" key="packagehandler.name" /></li>
		<li><s:label name="pkgHandler.lastname" key="packagehandler.lastname" /></li>
		<li><s:label name="pkgHandler.phonenumber" key="packagehandler.phonenumber" /></li>
    </ul>
  </body>
	
</html>
