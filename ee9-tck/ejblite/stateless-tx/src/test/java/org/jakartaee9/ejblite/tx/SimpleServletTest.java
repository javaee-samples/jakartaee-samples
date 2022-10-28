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

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
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
                      .addPackage(CdiClient.class.getPackage())
                      .addAsResource("META-INF/persistence.xml")
                      .addAsResource("META-INF/create.sql")
                      .addAsResource("META-INF/drop.sql")
                      .addAsWebInfResource((new File("src/main/webapp" + "/WEB-INF", "beans.xml")))

                      ;

        if (System.getProperty("glassfish.suspend") == null ) {
            war.addAsResource("META-INF/persistence.xml");
        } else {
            war.addAsResource("META-INF/persistence2.xml", "META-INF/persistence.xml")
               .addAsWebInfResource((new File("src/main/webapp" + "/WEB-INF", "web.xml")))
               .addAsLibraries(Maven.resolver()
                       .loadPomFromFile("pom.xml")
                       .resolve(
                           "com.h2database:h2"
                           )
                       .withTransitivity()
                       .asFile());
        }

        System.out.println(war.toString(true));

        return war;
    }

    @Before
    public void setup() {
        webClient = new WebClient();
        webClient.getOptions().setTimeout(0);
    }

    @Test
    public void mandatory() throws Exception {
        TextPage page = webClient.getPage(base + "SimpleServlet?test=" + "mandatory");
        assertTrue(page.getContent().startsWith("passed"));

        System.out.println(page.getContent());
    }

    @Test
    public void required() throws Exception {
        TextPage page = webClient.getPage(base + "SimpleServlet?test=" + "required");
        assertTrue(page.getContent().startsWith("passed"));

        System.out.println(page.getContent());
    }


    @Test
    public void requiredNoExistingTransaction() throws Exception {
        TextPage page = webClient.getPage(base + "SimpleServlet?test=" + "requiredNoExistingTransaction");
        assertTrue(page.getContent().startsWith("passed"));

        System.out.println(page.getContent());
    }

    @Test
    public void supports() throws Exception {
        TextPage page = webClient.getPage(base + "SimpleServlet?test=" + "supports");

        System.out.println(page.getContent());

        assertTrue(page.getContent().startsWith("passed"));
    }

    @Test
    public void requiresNew() throws Exception {
        TextPage page = webClient.getPage(base + "SimpleServlet?test=" + "requiresNew");
        assertTrue(page.getContent().startsWith("passed"));

        System.out.println(page.getContent());
    }

}