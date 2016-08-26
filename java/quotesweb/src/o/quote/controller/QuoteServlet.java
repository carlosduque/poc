package o.quote.controller;

import java.io.IOException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import o.quote.business.QuoteGenerator;

public class QuoteServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

    protected void doGet( HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String lang = request.getParameter("lang");

        QuoteGenerator model = new QuoteGenerator(lang);
        String quote = model.generateQuote();
        request.setAttribute("quote", quote);
        System.out.println("quote: " + quote);

        //*********************************
        String jndiName = "";
        Context ctx = null;
        try {
            ctx = new InitialContext();
            jndiName = "java:global/var/propertiesKey";
            System.out.println(jndiName + "   " + ctx.lookup(jndiName));
        } catch (NamingException e) {
            System.out.println("naming exception with: " + jndiName + ", msg: " + e.getMessage());
        }
        //*********************************

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/view.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost( HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        doGet(request, response);
    }
}
