/*
 * Copyright (c) 2017, 2020 Oracle and/or its affiliates. All rights reserved.
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
package org.jakartaee9.ejblite.tx;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;

import org.jakartaee9.ejblite.tx.beans.BeanBase;
import org.jakartaee9.ejblite.tx.beans.ConcurrencyIF;
import org.jakartaee9.ejblite.tx.beans.ReadSingletonBean;
import org.jakartaee9.ejblite.tx.beans.SingletonBean;
import org.jakartaee9.ejblite.tx.interceptors.Interceptor0;
import org.jakartaee9.ejblite.tx.interceptors.Interceptor3;
import org.jakartaee9.ejblite.tx.interceptors.InterceptorBase;
import org.jakartaee9.ejblite.tx.tests.Asserts;
import org.jakartaee9.ejblite.tx.tests.CdiClient;
import org.jakartaee9.ejblite.tx.tests.SimpleServlet;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gargoylesoftware.htmlunit.TextPage;
import com.gargoylesoftware.htmlunit.WebClient;

@RunWith(Arquillian.class)
public class SimpleServletTest {

    @ArquillianResource
    private URL base;

    WebClient webClient;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        WebArchive war =
            ShrinkWrap.create(WebArchive.class)
                      .addClasses(
                          ConcurrencyIF.class,
                          BeanBase.class,
                          ReadSingletonBean.class,
                          SingletonBean.class,

                          InterceptorBase.class,
                          Interceptor0.class,
                          Interceptor3.class,

                          SimpleServlet.class,
                          CdiClient.class,
                          Asserts.class)

                      .addAsWebInfResource((new File("src/main/webapp" + "/WEB-INF", "beans.xml")))
                      .addAsWebInfResource((new File("src/main/webapp" + "/WEB-INF", "ejb-jar.xml")))
                      ;

        System.out.println(war.toString(true));

        return war;
    }

    @Before
    public void setup() {
        webClient = new WebClient();
        webClient.getOptions().setTimeout(0);
    }

    @Test
    public void lockedSum1() throws Exception {
        System.out.println("lockedSum1");

        TextPage page = webClient.getPage(base + "SimpleServlet?test=" + "lockedSum1");
        assertTrue(page.getContent().startsWith("passed"));

        System.out.println(page.getContent());
    }

    @Test
    public void lockedSum2() throws Exception {
        TextPage page = webClient.getPage(base + "SimpleServlet?test=" + "lockedSum2");
        assertTrue(page.getContent().startsWith("passed"));

        System.out.println(page.getContent());
    }


    @Test
    public void lockedSumFromInterceptors1() throws Exception {
        TextPage page = webClient.getPage(base + "SimpleServlet?test=" + "lockedSumFromInterceptors1");
        assertTrue(page.getContent().startsWith("passed"));

        System.out.println(page.getContent());
    }

    @Test
    public void lockedSumFromInterceptors2() throws Exception {
        TextPage page = webClient.getPage(base + "SimpleServlet?test=" + "lockedSumFromInterceptors2");

        System.out.println(page.getContent());

        assertTrue(page.getContent().startsWith("passed"));
    }

    @Test
    public void lockedLinkedList1() throws Exception {
        TextPage page = webClient.getPage(base + "SimpleServlet?test=" + "lockedLinkedList1");
        assertTrue(page.getContent().startsWith("passed"));

        System.out.println(page.getContent());
    }

    @Test
    public void lockedLinkedList2() throws Exception {
        TextPage page = webClient.getPage(base + "SimpleServlet?test=" + "lockedLinkedList2");
        assertTrue(page.getContent().startsWith("passed"));

        System.out.println(page.getContent());
    }

}