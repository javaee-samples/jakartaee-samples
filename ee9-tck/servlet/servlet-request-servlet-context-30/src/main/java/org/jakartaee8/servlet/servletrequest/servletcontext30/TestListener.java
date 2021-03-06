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

/*
 * $Id$
 */
package org.jakartaee8.servlet.servletrequest.servletcontext30;

import static jakarta.servlet.DispatcherType.ERROR;
import static jakarta.servlet.DispatcherType.FORWARD;
import static jakarta.servlet.DispatcherType.INCLUDE;
import static jakarta.servlet.DispatcherType.REQUEST;

import java.util.EnumSet;
import java.util.EventListener;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterRegistration;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;

public class TestListener implements ServletContextListener {

    /**
     * Receives notification that the web application initialization process is starting.
     *
     * @param sce The servlet context event
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        final String addServletName1 = "AddServletString";
        final String addServletName2 = "AddServletClass";
        final String addServletName3 = "CreateServlet";
        final String AddServletNotFound = "AddServletNotFound";
        final String addServletName5 = "DuplicateServletClass";
        final String addServletName6 = "DuplicateServletString";

        final String addFilterName1 = "AddFilterString";
        final String addFilterName2 = "AddFilterClass";
        final String addFilterName3 = "CreateFilter";
        final String AddFilterNotFound = "AddFilterNotFound";


        /*
         * Add Servlet AddServletString
         */
        ServletRegistration servletRegistrationByString = context.addServlet(addServletName1, "org.jakartaee8.servlet.servletrequest.servletcontext30.AddServletString");
        servletRegistrationByString.addMapping("/addServletString");
        servletRegistrationByString.setInitParameter("FILTER", addFilterName1);
        servletRegistrationByString.setInitParameter(addFilterName1, FORWARD.toString());

        FilterRegistration filterRegistrationByString = context.addFilter(addFilterName1, "org.jakartaee8.servlet.servletrequest.servletcontext30.AddFilterString");
        filterRegistrationByString.addMappingForServletNames(EnumSet.of(FORWARD), true, addServletName1);
        filterRegistrationByString.setInitParameter("SERVLET", addServletName1);
        filterRegistrationByString.setInitParameter(addServletName1, FORWARD.toString());


        /*
         * Add Servlet AddServletClass
         */
        ServletRegistration srClass = context.addServlet(addServletName2, AddServletClass.class);
        srClass.addMapping("/addServletClass", "/SecondaddServletClass", "/ThirdAddServletClass", "/AddServletClass/*");
        srClass.setInitParameter("FILTER", addFilterName2);
        srClass.setInitParameter(addFilterName2, REQUEST.toString());

        FilterRegistration frClass = context.addFilter(addFilterName2, AddFilterClass.class);
        frClass.addMappingForServletNames(EnumSet.of(REQUEST), true, addServletName2);
        frClass.setInitParameter("SERVLET", addServletName2);
        frClass.setInitParameter(addServletName2, REQUEST.toString());


        /*
         * Add Servlet CreateServlet
         */
        ServletRegistration srServlet = null;
        FilterRegistration frFilter = null;
        try {
            Servlet servlet3 = context.createServlet(org.jakartaee8.servlet.servletrequest.servletcontext30.CreateServlet.class);
            srServlet = context.addServlet(addServletName3, servlet3);
            srServlet.addMapping("/createServlet", "/SecondCreateServlet", "/ThirdCreateServlet");
            srServlet.setInitParameter("FILTER", addFilterName3);
            srServlet.setInitParameter(addFilterName3, REQUEST.toString());

            Filter filter3 = context.createFilter(org.jakartaee8.servlet.servletrequest.servletcontext30.CreateFilter.class);
            frFilter = context.addFilter(addFilterName3, filter3);
            frFilter.addMappingForServletNames(EnumSet.of(REQUEST), true, addServletName3);
            frFilter.setInitParameter("SERVLET", addServletName3);
            frFilter.setInitParameter(addServletName3, REQUEST.toString());
        } catch (ServletException ex) {
            System.out.println("Error creating Servlet");
        }


        /*
         * Add Servlet AddServletNotFound
         */

        // Test org.jakartaee8.servlet.servletrequest.servletcontext30.Servletcontext30Client.testAddServletNotFound()

        // 1. create a Servlet by calling ServletContext.addServlet(String, Class),
        ServletRegistration servletRegistrationNotFound = context.addServlet(AddServletNotFound, AddServletNotFound.class);

        // 2. mapping the new Servlet programmatically to multiple URLs, one of them is used by another Servlet already.
        servletRegistrationNotFound.addMapping("/addServletNotFound", "/TestServlet");
        servletRegistrationNotFound.setInitParameter("FILTER", AddFilterNotFound);
        servletRegistrationNotFound.setInitParameter(AddFilterNotFound, "ALL");

        // 3. create a Filter by ServletContext.addFilter(String, Class)
        FilterRegistration filterRegistrationNotFound = context.addFilter(AddFilterNotFound, AddFilterNotFound.class);

        // 4. map the filter to the new Servlet programmatically for all DispatcherType
        filterRegistrationNotFound.addMappingForServletNames(
            EnumSet.of(REQUEST, INCLUDE, FORWARD, ERROR),
            true,
            AddServletNotFound);

        filterRegistrationNotFound.setInitParameter("SERVLET", AddServletNotFound);
        filterRegistrationNotFound.setInitParameter(AddServletNotFound, "ALL");


        /*
         * Add ServletContextAttributeListener
         */
        context.addListener(org.jakartaee8.servlet.servletrequest.servletcontext30.AddSCAttributeListenerClass.class);

        context.addListener("org.jakartaee8.servlet.servletrequest.servletcontext30.AddSCAttributeListenerString");
        try {
            EventListener sclistener = context.createListener(org.jakartaee8.servlet.servletrequest.servletcontext30.CreateSCAttributeListener.class);
            context.addListener(sclistener);
        } catch (ServletException ex) {
            System.out.println("Error creating Listener CreateSCAttributeListener: " + ex.getMessage());
        }


        /*
         * Add ServletRequestListener
         */
        context.addListener(org.jakartaee8.servlet.servletrequest.servletcontext30.AddSRListenerClass.class);
        context.addListener("org.jakartaee8.servlet.servletrequest.servletcontext30.AddSRListenerString");
        try {
            EventListener srlistener = context.createListener(org.jakartaee8.servlet.servletrequest.servletcontext30.CreateSRListener.class);
            context.addListener(srlistener);
        } catch (ServletException ex) {
            System.out.println("Error creating Listener CreateSRAttributeListener: " + ex.getMessage());
        }


        /*
         * Add ServletRequestAttributesListener
         */
        context.addListener(org.jakartaee8.servlet.servletrequest.servletcontext30.AddSRAttributeListenerClass.class);
        context.addListener("org.jakartaee8.servlet.servletrequest.servletcontext30.AddSRAttributeListenerString");
        try {
            EventListener sralistener = context.createListener(org.jakartaee8.servlet.servletrequest.servletcontext30.CreateSRAttributeListener.class);
            context.addListener(sralistener);
        } catch (ServletException ex) {
            System.out.println("Error creating Listener CreateSRAttributeListener: " + ex.getMessage());
        }


        /*
         * Negative tests for - createServlet - createFilter - createListener
         */

        // Tests for
        // org.jakartaee8.servlet.servletrequest.servletcontext30.Servletcontext30Test.negativeCreateTests()
        // org.jakartaee8.servlet.servletrequest.servletcontext30.TestServlet.negativeCreateTests(ServletRequest, ServletResponse)

        Boolean servlet_test = false;
        Boolean duplicatec_servlet_test = false;
        Boolean duplicates_servlet_test = false;
        Boolean filter_test = false;
        Boolean duplicatec_filter_test = false;
        Boolean duplicates_filter_test = false;
        Boolean listener_test = false;
        Boolean scc_listener_test = false;
        Boolean scs_listener_test = false;
        Boolean csc_listener_test = false;
        String SERVLET_TEST = "SERVLET_TEST";
        String DUPLICATEC_SERVLET_TEST = "DUPLICATEC_SERVLET_TEST";
        String DUPLICATES_SERVLET_TEST = "DUPLICATES_SERVLET_TEST";
        String FILTER_TEST = "FILTER_TEST";
        String DUPLICATEC_FILTER_TEST = "DUPLICATEC_FILTER_TEST";
        String DUPLICATES_FILTER_TEST = "DUPLICATES_FILTER_TEST";
        String LISTENER_TEST = "LISTENER_TEST";
        String SCC_LISTENER_TEST = "SCC_LISTENER_TEST";
        String SCS_LISTENER_TEST = "SCS_LISTENER_TEST";
        String CSC_LISTENER_TEST = "CSC_LISTENER_TEST";

        // 5. Call ServletContext.createServlet(Servlet) which fails (SERVLET_TEST)
        try {
            Servlet badservlet = context.createServlet(BadServlet.class);
        } catch (ServletException ex) {
            servlet_test = true;
        }
        context.setInitParameter(SERVLET_TEST, servlet_test.toString());

        // 6. Call ServletContext.createFilter(Filter) which should fail; (FILTER_TEST)
        try {
            Filter badfilter = context.createFilter(BadFilter.class);
        } catch (ServletException ex) {
            filter_test = true;
        }
        context.setInitParameter(FILTER_TEST, filter_test.toString());

        //  7. Call ServletContext.createListener(EventListener) which fails (LISTENER_TEST)
        try {
            context.createListener(BadListener.class);
        } catch (ServletException ex) {
            listener_test = true;
        }
        context.setInitParameter(LISTENER_TEST, listener_test.toString());

        // 8. Call ServletContext.addListener(ServletContextListener.class) which fails (SCC_LISTENER_TEST)
        try {
            context.addListener(AddSCListenerClass.class);
        } catch (IllegalArgumentException ilex) {
            scc_listener_test = true;
            System.out.println("Expected exception thrown adding Listener AddSCListenerClass: " + ilex.getMessage());
        } catch (Exception ex) {
            System.out.println("Error adding Listener AddSCListenerClass: " + ex.getMessage());
        }
        context.setInitParameter(SCC_LISTENER_TEST, scc_listener_test.toString());

        // 9. Call ServletContext.addListener(ServletContextListenerClassName) which fails (SCS_LISTENER_TEST)
        try {
            context.addListener("org.jakartaee8.servlet.servletrequest.servletcontext30.AddSCListenerString");
        } catch (IllegalArgumentException ilex) {
            scs_listener_test = true;
            System.out.println("Expected exception thrown adding Listener AddSCListenerString: " + ilex.getMessage());
        } catch (Exception ex) {
            System.out.println("Error adding Listener AddSCListenerString: " + ex.getMessage());
        }
        context.setInitParameter(SCS_LISTENER_TEST, scs_listener_test.toString());

        //  10. Call ServletContext.createListener(ServletContextListener.class) which fails (CSC_LISTENER_TEST)
        try {
            EventListener csclistener = context.createListener(CreateSCListener.class);
            context.addListener(csclistener);
        } catch (IllegalArgumentException ilex) {
            csc_listener_test = true;
            System.out.println("Expected exception thrown adding Listener CreateSCListener: " + ilex.getMessage());
        } catch (Exception ex) {
            System.out.println("Error adding Listener AddSCListenerClass: " + ex.getMessage());
        }
        context.setInitParameter(CSC_LISTENER_TEST, csc_listener_test.toString());


        /*
         * Add Servlet DuplicateServletClass
         */
        ServletRegistration srdClass = context.addServlet(addServletName5, DuplicateServletClass.class);
        if (srdClass == null) {
            duplicatec_servlet_test = true;
        }
        context.setInitParameter(DUPLICATEC_SERVLET_TEST, duplicatec_servlet_test.toString());

        ServletRegistration srdString = context.addServlet(addServletName6, "org.jakartaee8.servlet.servletrequest.servletcontext30.DuplicateServletString");
        if (srdString == null) {
            duplicates_servlet_test = true;
        }
        context.setInitParameter(DUPLICATES_SERVLET_TEST, duplicates_servlet_test.toString());


        // Set test values for test org.jakartaee8.servlet.servletrequest.servletcontext30.TestServlet.duplicateFilterTest1

        String addFilterName6 = "DuplicateFilterString";

        FilterRegistration filterRegistrationDuplicateByClass = context.addFilter(addFilterName6, DuplicateFilterClass.class);
        if (filterRegistrationDuplicateByClass == null) {
            duplicatec_filter_test = true;
        }
        context.setInitParameter(DUPLICATEC_FILTER_TEST, duplicatec_filter_test.toString());

        FilterRegistration filterRegistrationDuplicateByString = context.addFilter(addFilterName6, "org.jakartaee8.servlet.servletrequest.servletcontext30.DuplicateFilterString");
        if (filterRegistrationDuplicateByString == null) {
            duplicates_filter_test = true;
        }
        context.setInitParameter(DUPLICATES_FILTER_TEST, duplicates_filter_test.toString());
    }

    /**
     * Receives notification that the servlet context is about to be shut down.
     *
     * @param sce The servlet context event
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Do nothing
    }
}
