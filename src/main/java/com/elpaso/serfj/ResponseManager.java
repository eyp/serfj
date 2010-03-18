package com.elpaso.serfj;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class is responsible for presenting the response. It can be a page, or a serialized object.
 * 
 * @author: Eduardo Yáñez
 * Date: 16-may-2009
 */
public class ResponseManager {

    private ServletContext context;

    public ResponseManager(ServletContext context) {
        this.context = context;
    }
    
    public void renderPage(HttpServletRequest request, HttpServletResponse response, String page)
            throws IOException, ServletException {
        try {
            RequestDispatcher dispatcher = context.getRequestDispatcher(page);
            dispatcher.forward(request, response);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } catch (ServletException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void renderAction(String action) {
        // TODO Implementar renderAction
    }

    public void writeObject(Class serializer, Object object) {
        // TODO Implementar writeObject
    }
}
