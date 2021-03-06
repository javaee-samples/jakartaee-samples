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

package org.jakartaee8.servlet.servletrequest.requestdispatcher;

import java.net.URL;

import org.jakartaee8.urlclient.AbstractUrlClient;

public class RequestDispatcherClient extends AbstractUrlClient {

    public RequestDispatcherClient(URL base, String testServlet) {
        super(base, testServlet);
    }

    /* Run test */

    /*
     * @testName: forwardTest
     *
     * @assertion_ids: Servlet:SPEC:80; Servlet:JAVADOC:230; Servlet:JAVADOC:272; Servlet:JAVADOC:274;
     *
     * @test_Strategy: Create a servlet, get its RequestDispatcher and use it to forward to a servlet
     */

    public void forwardTest() throws Exception {
        TEST_PROPS.setProperty(APITEST, "forwardTest");
        invoke();
    }

    /*
     * @testName: forward_1Test
     *
     * @assertion_ids: Servlet:SPEC:77; Servlet:SPEC:80; Servlet:JAVADOC:230; Servlet:JAVADOC:277;
     *
     * @test_Strategy: A negative test for RequestDispatcher.forward() method. Create a servlet, print a string to the
     * buffer, flush the buffer to commit the string, get its RequestDispatcher and use it to forward to a servlet.
     */

    public void forward_1Test() throws Exception {
        TEST_PROPS.setProperty(APITEST, "forward_1Test");
        invoke();
    }

    /*
     * @testName: includeTest
     *
     * @assertion_ids: Servlet:JAVADOC:230; Servlet:JAVADOC:278;
     *
     * @test_Strategy: Create a servlet, get its RequestDispatcher and use it to include a servlet
     */

    public void includeTest() throws Exception {
        TEST_PROPS.setProperty(APITEST, "includeTest");
        invoke();
    }

    /*
     * @testName: include_1Test
     *
     * @assertion_ids: Servlet:JAVADOC:230; Servlet:JAVADOC:278;
     *
     * @test_Strategy: A negative test for RequestDispatcher.include() test. Create a servlet, set its Content-Type to be
     * 'text/html', get its RequestDispatcher and use it to include a servlet. The included servlet tries to change the
     * Content-Type to be text/html. Test at the client side for correct Content-Type.
     */

    public void include_1Test() throws Exception {
        TEST_PROPS.setProperty(APITEST, "include_1Test");
        TEST_PROPS.setProperty(EXPECTED_HEADERS, "Content-Type: text/sgml");
        invoke();
    }

    /*
     * @testName: include_2Test
     *
     * @assertion_ids: Servlet:SPEC:82; Servlet:SPEC:80; Servlet:JAVADOC:230; Servlet:JAVADOC:279;
     *
     * @test_Strategy: A negative test for RequestDispatcher.include() method. Create a servlet with service() method throws
     * ServletException. Use RequestDispatcher to include to this servlet. Verify that include() method throws
     * ServletException.
     */

    public void include_2Test() throws Exception {
        TEST_PROPS.setProperty(APITEST, "include_2Test");
        invoke();
    }

    /*
     * @testName: include_3Test
     *
     * @assertion_ids: Servlet:SPEC:82; Servlet:SPEC:80; Servlet:JAVADOC:230; Servlet:JAVADOC:280;
     *
     * @test_Strategy: A negative test for RequestDispatcher.include() method. Create a servlet with service() method throws
     * IOException. Use RequestDispatcher to include to this servlet. Verify that include() method throws IOException.
     */

    public void include_3Test() throws Exception {
        TEST_PROPS.setProperty(APITEST, "include_3Test");
        invoke();
    }

}
