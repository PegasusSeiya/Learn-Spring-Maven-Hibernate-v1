package fr.todooz.web.servlet;
import fr.todooz.ejb.WelcomeEJB;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class HelloWorldServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @EJB
    private WelcomeEJB welcomeEJB;

    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException,
            IOException {
    	 response.getWriter().write(welcomeEJB.hello("EJBs"));
        //request.getRequestDispatcher( "index.html" ).forward( request, response );
    }
}
