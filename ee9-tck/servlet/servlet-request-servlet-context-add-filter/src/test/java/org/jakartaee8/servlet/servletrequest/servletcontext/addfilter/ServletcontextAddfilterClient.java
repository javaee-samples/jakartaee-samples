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

package org.jakartaee8.servlet.servletrequest.servletcontext.addfilter;

import java.net.URL;

import org.jakartaee8.urlclient.AbstractUrlClient;

public class ServletcontextAddfilterClient extends AbstractUrlClient {

    public ServletcontextAddfilterClient(URL base, String testServlet) {
        super(base, testServlet);
    }

    /* Run test */
    /*
     * @testName: addFilterTest
     *
     * @assertion_ids: Servlet:JAVADOC:668.6;
     *
     * @test_Strategy: Create a ServletContextInitializer, in which, Add a
     * ServletContextListener instance using ServletContext.addListener; in the
     * ServletContextListener call ServletContext.addFilter(String, String) Verify
     * that UnsupportedOperationException is thrown.
     */
    public void addFilterTest() throws Exception {
      TEST_PROPS.setProperty(APITEST, "addFilterTest");
      invoke();
    }

}
