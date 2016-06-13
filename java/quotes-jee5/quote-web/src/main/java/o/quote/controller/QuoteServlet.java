package o.quote.controller;

import java.io.IOException;
import java.lang.Integer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ejb.EJB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import o.quote.common.dto.QuoteDto;
import o.quote.facade.QuoteSessionFacadeLocal;

public class QuoteServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(QuoteServlet.class);

    @EJB
    private QuoteSessionFacadeLocal quoteSession;

    protected void doGet( HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String action = request.getParameter("action");

        String idStr = request.getParameter("id");
        long id = 0;
        if (null != idStr && !idStr.isEmpty()) {
           id = Integer.parseInt(idStr);
        }

        String text = request.getParameter("quote");
        String author = request.getParameter("author");
        QuoteDto dto = new QuoteDto(id, text, author);

        String message = request.getParameter("message");

        if (("Add").equalsIgnoreCase(action)) {
            LOGGER.debug("action: " + action + ", dto: " + dto);
            quoteSession.create(dto);
        } else if (("Edit").equalsIgnoreCase(action)) {
            LOGGER.debug("action: " + action + ", dto: " + dto);
            quoteSession.update(dto);
        } else if (("Delete").equalsIgnoreCase(action)) {
            LOGGER.debug("action: " + action + ", id: " + id);
            quoteSession.delete(id);
        } else if (("Search").equalsIgnoreCase(action)) {
            LOGGER.debug("action: " + action + ", id: " + id);
            dto = quoteSession.read(id);
        } else if (("Send").equalsIgnoreCase(action)) {
            LOGGER.debug("action: " + action + ", message: " + message);
            quoteSession.process(message);
        }
        request.setAttribute("quote", dto);
        request.setAttribute("allQuotes", quoteSession.findAll());

        RequestDispatcher view = request.getRequestDispatcher("home.jsp");
        view.forward(request, response);
    }

    protected void doPost( HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        doGet(request, response);
    }
}
