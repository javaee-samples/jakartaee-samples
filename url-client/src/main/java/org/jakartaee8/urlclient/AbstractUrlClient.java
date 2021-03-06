/*
 * Copyright (c) 2007, 2018 Oracle and/or its affiliates. All rights reserved.
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

package org.jakartaee8.urlclient;

import static org.jakartaee8.urlclient.Data.FAILED;
import static org.jakartaee8.urlclient.Data.PASSED;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

import org.jakartaee8.urlclient.http.HttpRequest;

/**
 * Base client for Servlet tests.
 */

public abstract class AbstractUrlClient extends BaseUrlClient {

    protected static final String APITEST = "apitest";

    protected static final String DONOTUSEServletName = "NoServletName";

    private InetAddress[] internetAddresses;
    private String servletName;

    public AbstractUrlClient(URL base) {
        this(base, null);
    }

    public AbstractUrlClient(URL base, String testServlet) {
        setHostname(base.getHost());
        setPort(base.getPort());
        setContextRoot(base.getPath().endsWith("/")? base.getPath().substring(0, base.getPath().length() -1) : base.getPath());
        setServletName(testServlet);
    }


    protected AbstractUrlClient() {
        // Construct a default context root based on the class name of
        // the concrete subclass of this class.
        String className = this.getClass().getName();
        String prefix = "com.sun.ts.tests.";
        if (className.startsWith(prefix)) {
            className = className.substring(prefix.length());
        }

        String suffix = ".URLClient";
        if (className.endsWith(suffix)) {
            className = className.substring(0, className.length() - suffix.length());
        }

        className = className.replace('.', '_');
        className = "/" + className + "_web";
        setContextRoot(className);
    }

    @Override
    protected void setTestProperties(WebTestCase testCase) {
        setStandardProperties(TEST_PROPS.getProperty(STANDARD), testCase);
        setApiTestProperties(TEST_PROPS.getProperty(APITEST), testCase);
        super.setTestProperties(testCase);
    }

    /**
     * Sets the request, testname, and a search string for test passed. A search is also added for test failure. If found,
     * the test will fail.
     *
     * @param testValue - a logical test identifier
     * @param testCase - the current test case
     */
    private void setApiTestProperties(String testValue, WebTestCase testCase) {
        if (testValue == null) {
            return;
        }

        // An API test consists of a request with a request parameter of
        // testname, a search string of Test PASSED, and a logical test name.

        // set the testname
        _testName = testValue;

        // Set the request
        StringBuffer requestLine = new StringBuffer(50);
        if (servletName != null && TEST_PROPS.getProperty(DONOTUSEServletName) == null) {
            requestLine.append(GET).append(contextRoot).append(SL)
                       .append(servletName).append("?testname=").append(testValue)
                       .append(HTTP11);
        } else {
            requestLine.append(GET).append(contextRoot).append(SL)
                       .append(testValue)
                       .append(HTTP10);
        }

        System.out.println("\n\nREQUEST LINE: " + requestLine.toString());

        testCase.setRequest(new HttpRequest(requestLine.toString(), hostname, port));

        if (TEST_PROPS.getProperty(SEARCH_STRING) == null || TEST_PROPS.getProperty(SEARCH_STRING).equals("")) {
            testCase.setResponseSearchString(PASSED);
            testCase.setUnexpectedResponseSearchString(FAILED);
        }

    }

    /**
     * Consists of a test name, a request, and a goldenfile.
     *
     * @param testValue - a logical test identifier
     * @param testCase - the current test case
     */
    private void setStandardProperties(String testValue, WebTestCase testCase) {
        if (testValue == null) {
            return;
        }

        // A standard test sets consists of a testname
        // a request, and a goldenfile. The URI is not used
        // in this case since the JSP's are assumed to be located
        // at the top of the contextRoot
        StringBuffer sb = new StringBuffer(50);

        // set the testname
        _testName = testValue;

        // set the request

        if (servletName != null) {
            sb.append(GET).append(contextRoot).append(SL);
            sb.append(servletName).append("?testname=").append(testValue);
            sb.append(HTTP11);
        } else {
            sb.append(GET).append(contextRoot).append(SL);
            sb.append(testValue).append(HTTP10);
        }

        System.out.println("REQUEST LINE: " + sb.toString());
        HttpRequest req = new HttpRequest(sb.toString(), hostname, port);
        testCase.setRequest(req);

        // set the goldenfile
        sb = new StringBuffer(50);
        sb.append(_tsHome).append(GOLDENFILEDIR);
        sb.append(_generalURI).append(SL);
        sb.append(testValue).append(GF_SUFFIX);

        testCase.setGoldenFilePath(sb.toString());
    }

    /**
     * Sets the name of the servlet to use when building a request for a single servlet API test.
     *
     * @param servlet - the name of the servlet
     */
    public void setServletName(String servlet) {
        servletName = servlet;
    }

    public String getServletName() {
        return servletName;
    }

    protected String getLocalInterfaceInfo(boolean returnAddresses) {
        String result = null;
        initInetAddress();

        if (internetAddresses.length != 0) {
            StringBuffer sb = new StringBuffer(32);
            if (!returnAddresses) {
                // localhost might not show up if aliased
                sb.append("localhost,");
            } else {
                // add 127.0.0.1
                sb.append("127.0.0.1,");
            }

            for (int i = 0; i < internetAddresses.length; i++) {
                if (returnAddresses) {
                    String ip = internetAddresses[i].getHostAddress();
                    if (!ip.equals("127.0.0.1")) {
                        if (ip.contains("%")) {
                            int scope_id = ip.indexOf("%");
                            ip = ip.substring(0, scope_id);
                        }
                        sb.append(ip);
                    }
                } else {
                    String host = internetAddresses[i].getCanonicalHostName();
                    if (!host.equals("localhost")) {
                        sb.append(host);
                    }
                }
                if (i + 1 != internetAddresses.length) {
                    sb.append(",");
                }
            }
            result = sb.toString();
        }

        return result;
    }

    private void initInetAddress() {
        if (internetAddresses == null) {
            try {
                internetAddresses = InetAddress.getAllByName(InetAddress.getLocalHost().getCanonicalHostName());
            } catch (UnknownHostException uhe) {
            }
        }
    }
}
