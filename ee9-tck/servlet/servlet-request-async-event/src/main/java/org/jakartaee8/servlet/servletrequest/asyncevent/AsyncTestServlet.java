/*
 * Copyright (c) 2009, 2018 Oracle and/or its affiliates. All rights reserved.
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

package org.jakartaee8.servlet.servletrequest.asyncevent;

import java.io.IOException;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.AsyncEvent;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class AsyncTestServlet extends GenericTCKServlet {

    private static final long serialVersionUID = 1L;

    public void constructorTest1(ServletRequest request, ServletResponse response) throws IOException {
        AsyncContext asyncContext = request.startAsync();
        AsyncEvent asyncEvent = new AsyncEvent(asyncContext);

        response.getWriter().println("Test PASSED" + asyncEvent.toString());
        asyncContext.complete();
    }

    public void constructorTest2(ServletRequest request, ServletResponse response) throws IOException {
        AsyncContext asyncContext = request.startAsync();
        AsyncEvent asyncEvent = new AsyncEvent(asyncContext, request, response);
        response.getWriter().println("Test PASSED " + asyncEvent.toString());
        asyncContext.complete();
    }

    public void constructorTest3(ServletRequest request, ServletResponse response) throws IOException {
        AsyncContext asyncContext = request.startAsync();
        AsyncEvent asyncEvent = new AsyncEvent(asyncContext, new IOException("AsyncEvent"));

        response.getWriter().println("Test PASSED" + asyncEvent.toString());
        asyncContext.complete();
    }

    public void constructorTest4(ServletRequest request, ServletResponse response) throws IOException {
        AsyncContext asyncContext = request.startAsync();
        AsyncEvent asyncEvent = new AsyncEvent(asyncContext, request, response, new IOException("AsyncEvent"));

        response.getWriter().println("Test PASSED" + asyncEvent.toString());
        asyncContext.complete();
    }

    public void getSuppliedRequestTest1(ServletRequest request, ServletResponse response) throws IOException {
        AsyncContext asyncContext = request.startAsync();

        AsyncEvent asyncEvent = new AsyncEvent(asyncContext, request, response);
        if (request != asyncEvent.getSuppliedRequest()) {
            response.getWriter().println("getSuppliedRequest() returned are incorrect. Test FAILED");
        } else {
            response.getWriter().println("Test PASSED");
        }

        asyncContext.complete();
    }

    public void getSuppliedRequestTest2(ServletRequest request, ServletResponse response) throws IOException {
        AsyncContext asyncContext = request.startAsync();

        AsyncEvent asyncEvent = new AsyncEvent(asyncContext, request, response, new IOException("AsyncEvent"));
        if (request != asyncEvent.getSuppliedRequest()) {
            response.getWriter().println("getSuppliedRequest() returned are incorrect. Test FAILED");
        } else {
            response.getWriter().println("Test PASSED");
        }

        asyncContext.complete();
    }

    public void getSuppliedResponseTest1(ServletRequest request, ServletResponse response) throws IOException {
        AsyncContext asyncContext = request.startAsync();

        AsyncEvent asyncEvent = new AsyncEvent(asyncContext, request, response);
        if (response != asyncEvent.getSuppliedResponse()) {
            response.getWriter().println("getSuppliedResponse() returned are incorrect. Test FAILED");
        } else {
            response.getWriter().println("Test PASSED");
        }

        asyncContext.complete();
    }

    public void getSuppliedResponseTest2(ServletRequest request, ServletResponse response) throws IOException {
        AsyncContext asyncContext = request.startAsync();

        AsyncEvent asyncEvent = new AsyncEvent(asyncContext, request, response, new IOException("AsyncEvent"));
        if (response != asyncEvent.getSuppliedResponse()) {
            response.getWriter().println("getSuppliedResponse() returned are incorrect. Test FAILED");
        } else {
            response.getWriter().println("Test PASSED");
        }

        asyncContext.complete();
    }

    public void getThrowableTest(ServletRequest request, ServletResponse response) throws IOException {
        AsyncContext asyncContext = request.startAsync();

        IOException ie = new IOException("AsyncEvent");
        AsyncEvent asyncEvent = new AsyncEvent(asyncContext, request, response, ie);
        if (ie != asyncEvent.getThrowable()) {
            response.getWriter().println("getThrowable() returned are incorrect. Test FAILED");
        } else {
            response.getWriter().println("Test PASSED");
        }

        asyncContext.complete();
    }
}
