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
	<h2><s:text name="packagehandler.register" /></h2>
	<hr />
	<s:form action="Save">
      <s:textfield name="pkgHandler.name" key="packagehandler.name" />
      <s:textfield name="pkgHandler.lastname" key="packagehandler.lastname" />
      <s:textfield name="pkgHandler.phonenumber" key="packagehandler.phonenumber" />
      <s:submit/>
	</s:form>
  </body>
	
</html>
