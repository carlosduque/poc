<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Quotes</title>
    </head>
    <body>
        <h1>Quotes Generator</h1>
        <form action="pages/quoteServlet" method="POST">
            <ul>
                <li><input type="submit" name="lang" value="ES" /></li>
                <li><input type="submit" name="lang" value="EN" /></li>
            </ul>
        </form>
        <br />
    </body>
</html>
