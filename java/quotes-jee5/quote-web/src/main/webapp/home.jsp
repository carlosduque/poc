<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Quotes</title>
    </head>
    <body>
        <h1>Quotes</h1>

        <form action="/quotes/home.do" method="POST">
            <table>
                <tr>
                    <td>Id:</td>
                    <td><input type="text" name="id" value="${quote.id}" /></td>
                </tr>
                <tr>
                    <td>Quote:</td>
                    <td><input type="text" name="quote" value="${quote.quote}" /></td>
                </tr>
                <tr>
                    <td>Author:</td>
                    <td><input type="text" name="author" value="${quote.author}" /></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" name="action" value="Add" />
                        <input type="submit" name="action" value="Edit" />
                        <input type="submit" name="action" value="Delete" />
                        <input type="submit" name="action" value="Search" />
                    </td>
                </tr>
            </table>
        </form>
        <br />
        <h3>Insert Quote as a message</h3>
        <p>Use this format '<code>id|author|some quote</code>' for the message and press the button to register a new Quote.</p>
        <p>Changes will <strong>not</strong> be reflected immediately since this is an <em>asynchronous</em> process.</p>
        <form action="/quotes/home.do" method="POST">
           <table>
                <tr>
                    <td>message:</td>
                    <td><input type="text" name="message" value="" /></td>
                    <td><input type="submit" name="action" value="Send" /></td>
                </tr>
           </table>
        </form>
        <br />
        <hr />
        <br />
        <table border="1">
            <th>Id</th>
            <th>Quote</th>
            <th>Author</th>
            <c:forEach items="${allQuotes}" var="q">
                <tr><td>${q.id}</td><td>${q.quote}</td><td>${q.author}</td></tr>
            </c:forEach>
        </table>
    </body>
</html>
