/*
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
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

package org.jakartaee9.servlet.singlethreadmodel;

import java.net.URL;

import org.jakartaee8.urlclient.AbstractUrlClient;

public class SingleThreadModelClient extends AbstractUrlClient {

    private static int NTHREADS = 2;

    public SingleThreadModelClient(URL base, String testServlet) {
        super(base, testServlet);
    }
  

    /* Run test */

    /*
     * @testName: singleModelTest
     *
     * @assertion_ids: Servlet:SPEC:200; Servlet:SPEC:10;
     *
     * @test_Strategy: This test will use a multi-threaded client to validate that
     * SingleThreadModel servlets are handled properly. To do this, the following
     * jte property needs to be set: ServletClientThreads. This configures the
     * number of threads that the client will use.
     *
     * If a servlet implements this interface, you are guaranteed that no two
     * threads will execute concurrently in the servlet's service method.
     *
     * If the container implementation is one that doesn't pool SingleThreadModel
     * servlets, then the value can be left at the default of 2. However, if the
     * container does pool SingleThreadModel servlets, the ServletClientThreads
     * property should be set to twice the size of the instance pool. For example,
     * if the container's pool size for SingleThreadModel servlets is 10, then set
     * the ServletClientThreads property to 20.
     *
     * Also take note, that each thread will make 3 requests to the test servlet.
     * The outcome of this test for both pooled and non-pooled implementations
     * will be the same, however by configuring the threads, we can validate
     * implementations which pool these type of servlets.
     */
    public void singleModelTest() throws Exception {
        TEST_PROPS.setProperty(REQUEST, "GET " + getContextRoot() + "/STMClient?count=" + NTHREADS + " HTTP/1.1");
        TEST_PROPS.setProperty(STATUS_CODE, OK);
        
        invoke();
    }

}
