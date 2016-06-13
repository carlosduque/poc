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
	<h2><s:text name="packagehandler.list" /></h2>
	<hr />
    <div>
      <table>
        <s:iterator value="pkgHandlers" status="pkgHandlersStatus">
          <s:if test="#pkgHandlersStatus.count > 0">
            <s:if test="#pkgHandlersStatus.first == true">
              <tr><th><s:text name="packagehandler.name" /></th><th><s:text name="packagehandler.lastname" /></th><th><s:text name="packagehandler.phonenumber" /></th></tr>
            </s:if>
            <tr><td><s:property value="name"/></td><td><s:property value="lastname"/></td><td><s:property value="phonenumber"/></td></tr>
          </s:if>
        </s:iterator>
      </table>
    </div>
  </body>
	
</html>
