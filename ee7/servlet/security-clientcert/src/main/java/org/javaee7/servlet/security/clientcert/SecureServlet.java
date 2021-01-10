/** Copyright Payara Services Limited **/
package org.javaee7.servlet.security.clientcert;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Arjan Tijms
 */
@WebServlet(urlPatterns = { "/SecureServlet" })
public class SecureServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().print("principal " + request.getUserPrincipal() + " in role g1:" + request.isUserInRole("g1"));
    }

}
