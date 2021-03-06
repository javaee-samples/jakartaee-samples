package org.jakartaee8.servlet.servletrequest.servletrequest;

import static org.jboss.shrinkwrap.api.ShrinkWrap.create;

import java.io.File;
import java.net.URL;
import java.util.Properties;

import org.jakartaee8.servlet.common.client.RequestClient;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Arjan Tijms
 */
@RunWith(Arquillian.class)
public class ServletRequestTest1 {

    @ArquillianResource
    private URL base;

    private RequestClient requestClient;
    Properties testProperties = new Properties();

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        JavaArchive[] archiveWithServlet =
                Maven.resolver()
                     .loadPomFromFile("pom.xml")
                     .resolve("org.jakartaee8:servlet-common-server")
                     .withTransitivity()
                     .as(JavaArchive.class);

        return create(WebArchive.class)
                    .addAsWebInfResource(new File("src/main/webapp/WEB-INF/web.xml"))
                    .addAsLibraries(archiveWithServlet);
    }

    @Before
    public void setup() {
        requestClient = new RequestClient(base, "TestServlet");
    }

    @After
    public void teardown() {
        testProperties.clear();
    }

    /* Run test */

    /*
     * @testName: getLocalPortTest
     *
     * @assertion_ids: Servlet:JAVADOC:635
     *
     * @test_Strategy: Test servlet API SDervletRequest.getLocalPort()
     */
    @Test
    @RunAsClient
    public void getLocalPortTest() throws Exception {
        requestClient.getLocalPortTest();
    }

    /*
     * @testName: getLocalNameTest
     *
     * @assertion_ids: Servlet:JAVADOC:632
     *
     * @test_Strategy: Test servlet API SDervletRequest.getLocalName()
     */
    @Test
    @RunAsClient
    public void getLocalNameTest() throws Exception {
        requestClient.getLocalNameTest();
    }
}


