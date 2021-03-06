/*
 * Copyright (c) 2012, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

/*
 * $Id:i$
 */
package org.jakartaee8.servlet.servletrequest.servletcontext30;
import static org.jakartaee8.urlclient.ServletTestUtil.printResult;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import org.jakartaee8.urlclient.StaticLog;

public class DuplicateServletString extends GenericServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        PrintWriter printWriter = response.getWriter();

        printWriter.println("DuplicateServletString is invoked");
        ArrayList result = (ArrayList) getServletContext().getAttribute("arraylist");

        for (Object tmp : result) {
            printWriter.println(tmp.toString());
        }
        getServletContext().removeAttribute("arraylist");

        result = StaticLog.getClear();
        if (result != null) {
            for (Object tmp : result) {
                if (tmp != null) {
                    printWriter.println(tmp.toString());
                }
            }
        }

        printResult(printWriter, true);
    }
}
