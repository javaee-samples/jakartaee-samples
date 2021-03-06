package org.jakartaee8.servlet.servletrequest.genericservlet;

import static org.jboss.shrinkwrap.api.ShrinkWrap.create;

import java.io.File;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Arjan Tijms
 */
@RunWith(Arquillian.class)
public class GenericServletTest {

    @ArquillianResource
    private URL base;

    private GenericServletClient genericFilterClient;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return create(WebArchive.class)
                    .addClasses(
                            DestroyTestServlet.class,
                            GenericTCKServlet.class,
                            Init_ServletConfigServletExceptionTestServlet.class,
                            Init_ServletConfigTestServlet.class,
                            InitServletExceptionTestServlet.class,
                            InitTestServlet.class,
                            ServiceTestServlet.class,
                            ServletErrorPage.class,
                            TestServlet.class,

                            ServletTestUtil.class
                    )
                    .addAsWebInfResource(new File("src/main/webapp/WEB-INF/web.xml"));
    }

    @Before
    public void setup() {
        genericFilterClient = new GenericServletClient(base, "TestServlet");
    }

    @After
    public void teardown() {
        genericFilterClient = null;
    }

    /* Run test */


    /*
     * @testName: destroyTest
     *
     * @assertion_ids: Servlet:JAVADOC:119
     *
     * @test_Strategy: Create a GenericServlet and take out of service using destroy method
     *
     */
    @Test
    @RunAsClient
    public void destroyTest() throws Exception {
        genericFilterClient.destroyTest();
    }

    /*
     * @testName: getServletConfigTest
     *
     * @assertion_ids: Servlet:JAVADOC:124
     *
     * @test_Strategy: Create a GenericServlet and check for its ServletConfig object existence
     *
     */
    @Test
    @RunAsClient
    public void getServletConfigTest() throws Exception {
        genericFilterClient.getServletConfigTest();
    }

    /*
     * @testName: getServletContextTest
     *
     * @assertion_ids: Servlet:JAVADOC:125
     *
     * @test_Strategy: Create a GenericServlet and check for its ServletContext object existence
     *
     */
    @Test
    @RunAsClient
    public void getServletContextTest() throws Exception {
        genericFilterClient.getServletConfigTest();
    }

    /*
     * @testName: getServletInfoTest
     *
     * @assertion_ids: Servlet:JAVADOC:126
     *
     * @test_Strategy: Create a GenericServlet and check for its ServletInfo object values
     *
     */
    @Test
    @RunAsClient
    public void getServletInfoTest() throws Exception {
        genericFilterClient.getServletInfoTest();
    }

    /*
     * @testName: getInitParameterTest
     *
     * @assertion_ids: Servlet:JAVADOC:120
     *
     * @test_Strategy: Servlet tries to access a parameter that exists
     */
    @Test
    @RunAsClient
    public void getInitParameterTest() throws Exception {
        genericFilterClient.getInitParameterTest();
    }

    /*
     * @testName: getInitParameterNamesTest
     *
     * @assertion_ids: Servlet:JAVADOC:122
     *
     * @test_Strategy: Servlet tries to get all parameter names
     */
    @Test
    @RunAsClient
    public void getInitParameterNamesTest() throws Exception {
        genericFilterClient.getInitParameterNamesTest();
    }

    /*
     * @testName: getServletNameTest
     *
     * @assertion_ids: Servlet:JAVADOC:136
     *
     * @test_Strategy: Servlet gets name of servlet
     */
    @Test
    @RunAsClient
    public void getServletNameTest() throws Exception {
        genericFilterClient.getServletNameTest();
    }

    /*
     * @testName: initServletExceptionTest
     *
     * @assertion_ids: Servlet:JAVADOC:130
     *
     * @test_Strategy: Servlet throws a ServletException
     */
    @Test
    @RunAsClient
    public void initServletExceptionTest() throws Exception {
        genericFilterClient.initServletExceptionTest();
    }

    /*
     * @testName: initTest
     *
     * @assertion_ids: Servlet:JAVADOC:129
     *
     * @test_Strategy: Servlet has init method that puts a value into the context. Servlet when called reads value from
     * context
     */
    @Test
    @RunAsClient
    public void initTest() throws Exception {
        genericFilterClient.initTest();
    }

    /*
     * @testName: init_ServletConfigServletExceptionTest
     *
     * @assertion_ids: Servlet:JAVADOC:128
     *
     * @test_Strategy: Servlet throws a ServletException
     */
    @Test
    @RunAsClient
    public void init_ServletConfigServletExceptionTest() throws Exception {
        genericFilterClient.init_ServletConfigServletExceptionTest();
    }

    /*
     * @testName: init_ServletConfigTest
     *
     * @assertion_ids: Servlet:JAVADOC:127
     *
     * @test_Strategy: Servlet has init method that puts a value into the context. Servlet when called reads value from
     * context
     */
    @Test
    @RunAsClient
    public void init_ServletConfigTest() throws Exception {
        genericFilterClient.init_ServletConfigTest();
    }

    /*
     * @testName: serviceTest
     *
     * @assertion_ids: Servlet:JAVADOC:133
     *
     * @test_Strategy: Servlet which has a service method that is called
     */
    @Test
    @RunAsClient
    public void serviceTest() throws Exception {
        genericFilterClient.serviceTest();
    }

}
