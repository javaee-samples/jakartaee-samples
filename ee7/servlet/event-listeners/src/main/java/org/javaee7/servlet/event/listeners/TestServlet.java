/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package org.javaee7.servlet.event.listeners;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Arun Gupta
 */
@WebServlet(urlPatterns = "/TestServlet")
public class TestServlet extends HttpServlet {

    private static final long serialVersionUID = -535776072448287787L;
    
    public static StringBuffer eventBuffer = new StringBuffer();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        eventBuffer.setLength(0);
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
            out.println("<head>");
                out.println("<title>Servlet Event Listeners</title>");
            out.println("</head>");
            out.println("<body>");
                out.println("<h1>Servlet Event Listeners</h1>");
                
                out.println("<h2>Setting, updating, and removing ServletContext Attributes</h2>");
                request.getServletContext().setAttribute("attribute1", "attribute-value1");
                request.getServletContext().setAttribute("attribute1", "attribute-updated-value1");
                request.getServletContext().removeAttribute("attribute1");
                out.println("done");
        
                out.println("<h2>Setting, updating, and removing HttpSession Attributes</h2>");
                request.getSession(true).setAttribute("attribute1", "attribute-value1");
                request.getSession().setAttribute("attribute1", "attribute-updated-value1");
                request.getSession().removeAttribute("attribute1");
                out.println("done");
        
                out.println("<h2>Setting, updating, and removing ServletRequest Attributes</h2>");
                request.setAttribute("attribute1", "attribute-value1");
                request.setAttribute("attribute1", "attribute-updated-value1");
                request.removeAttribute("attribute1");
                out.println("done");
        
                out.println("<h2>Invalidating session</h2>");
                request.getSession().invalidate();
                out.println("done");
        
                out.println("<br><br>Generated output:");
                out.println("<pre>");
                out.println(eventBuffer.toString());
                out.println("</pre>");
            out.println("</body>");
        out.println("</html>");
    }

}
