package o.quote.controller;

import java.io.IOException;
import java.lang.Integer;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ejb.EJB;

import o.quote.common.dto.QuoteDto;
import o.quote.facade.QuoteSessionFacadeLocal;

public class QuoteServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@EJB
    private QuoteSessionFacadeLocal quoteSession;

    protected void doGet( HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String action = request.getParameter("action");
        String idStr = request.getParameter("id");
        long id = 0;
        if (null != idStr && !idStr.isEmpty())
           id = Integer.parseInt(idStr);
        String text = request.getParameter("text");
        String author = request.getParameter("author");

        QuoteDto dto = new QuoteDto(id, text, author);

        if (("Add").equalsIgnoreCase(action)) {
            quoteSession.create(dto);
        } else if (("Edit").equalsIgnoreCase(action)) {
            quoteSession.update(dto);
        } else if (("Delete").equalsIgnoreCase(action)) {
            quoteSession.delete(id);
        } else if (("Search").equalsIgnoreCase(action)) {
            quoteSession.read(id);
        }
        request.setAttribute("quote", dto);
        request.setAttribute("allQuotes", quoteSession.findAll());
        //response.getWriter().write("<html><body>GET response</body></html>");
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
//        response.sendRedirect("index.jsp");
    }

    protected void doPost( HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        doGet(request, response);
    }
}
