package org.javaee7.servlet.programmatic.registration;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author OrelGenya
 */
public class DynamicServlet extends HttpServlet {

    private static final long serialVersionUID = 8310377560908221629L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().print("dynamic GET");
    }

}