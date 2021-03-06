package org.jakartaee8.servlet.servletrequest.filter.on.forward;

import static org.jboss.shrinkwrap.api.ShrinkWrap.create;

import java.io.File;
import java.net.URL;

import org.jakartaee8.servlet.servletrequest.filter.request.dispatching.DummyServlet;
import org.jakartaee8.servlet.servletrequest.filter.request.dispatching.ErrorPage;
import org.jakartaee8.servlet.servletrequest.filter.request.dispatching.ForwardTest1Servlet;
import org.jakartaee8.servlet.servletrequest.filter.request.dispatching.ForwardTestServlet;
import org.jakartaee8.servlet.servletrequest.filter.request.dispatching.ForwardedServlet;
import org.jakartaee8.servlet.servletrequest.filter.request.dispatching.GenericTCKServlet;
import org.jakartaee8.servlet.servletrequest.filter.request.dispatching.IncludeTest1Servlet;
import org.jakartaee8.servlet.servletrequest.filter.request.dispatching.IncludeTestServlet;
import org.jakartaee8.servlet.servletrequest.filter.request.dispatching.IncludedServlet;
import org.jakartaee8.servlet.servletrequest.filter.request.dispatching.RequestTestServlet;
import org.jakartaee8.servlet.servletrequest.filter.request.dispatching.ServletTestUtil;
import org.jakartaee8.servlet.servletrequest.filter.request.dispatching.TestServlet;
import org.jakartaee8.servlet.servletrequest.filter.request.dispatching.Test_Filter;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;

/**
 * @author Arjan Tijms
 */
@RunWith(Arquillian.class)
public class FilterfilterRequestDispatcherTest {

    @ArquillianResource
    private URL base;
    private FilterfilterRequestDispatcherClient filterWrappedResponseClient;
    private static int count;

    @Rule
    public TestRule watcher = new TestWatcher() {
        @Override
        protected void starting(Description description) {
            count = count + 1;
            System.out.println("\n\nStarting test " + count + ": " + description.getMethodName());
        }
    };

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        try {
            return create(WebArchive.class)
                    .addClasses(
                        GenericTCKServlet.class,
                        DummyServlet.class,
                        ErrorPage.class,
                        ForwardedServlet.class,
                        ForwardTest1Servlet.class,
                        ForwardTestServlet.class,
                        GenericTCKServlet.class,
                        IncludedServlet.class,
                        IncludeTest1Servlet.class,
                        IncludeTestServlet.class,
                        RequestTestServlet.class,
                        ServletTestUtil.class,
                        Test_Filter.class,
                        TestServlet.class
                    )
                    .addAsWebInfResource(new File("src/main/webapp/WEB-INF/web.xml"))

                    ;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Before
    public void setup() {
        filterWrappedResponseClient = new FilterfilterRequestDispatcherClient(base, "TestServlet");
    }


    /* Run test */

    /*
     * @testName: RequestTest
     *
     * @assertion_ids: Servlet:SPEC:54; Servlet:SPEC:59;
     *
     * @test_Strategy:
     * 1. Create a servlet, a JSP page and a web page - /generic/DummyServlet, /generic/dummyJSP, and /dummy.html.
     * 2. Map a filter Test_Filter on all above three with dispatcher value set to REQUEST using
     * url-pattern, as well as ERROR, FORWARD and INCLUDE.
     * 3. Client try to access all of them directly.
     * 4. Verify that filter is properly invoked.
     */
    @Test
    @RunAsClient
    public void RequestTest() throws Exception {
        filterWrappedResponseClient.RequestTest();
    }

    /*
     * @testName: RequestTest1
     *
     * @assertion_ids: Servlet:SPEC:54;
     *
     * @test_Strategy:
     * 1. Create a servlet - RequestTestServlet.
     * 2. Map a filter Test_Filter on RequestTestServlet with dispatcher value set to REQUEST
     * using servlet-name
     * 3. Client try to access RequestTestServlet directly.
     * 4. Verify that filter is properly invoked.
     */
    @Test
    @RunAsClient
    public void RequestTest1() throws Exception {
        filterWrappedResponseClient.RequestTest1();
    }

    /*
     * @testName: RequestTest2
     *
     * @assertion_ids: Servlet:SPEC:54;
     *
     * @test_Strategy: 1. Create a servlet - forward/ForwardedServlet. 2. Map a
     * filter Test_Filter on forward/ForwardedServlet with dispatcher value not
     * set to REQUEST but to FORWARD only. 3. Client try to access
     * forward/ForwardedServlet directly. 4. Verify that filter is not invoked.
     */
    @Test
    @RunAsClient
    public void RequestTest2() throws Exception {
        filterWrappedResponseClient.RequestTest2();
    }

    /*
     * @testName: ForwardTest
     *
     * @assertion_ids: Servlet:SPEC:55; Servlet:JAVADOC:273;
     *
     * @test_Strategy: 1. Create two servlets - TestServlet, ForwardedServlet. 2.
     * Map a filter Test_Filter using <servlet-name> for ForwardedServlet with
     * dispacther value FORWARD. 3. Client try to use the RequestDispatcher to
     * forward to ForwardedServlet through TestServlet. 4. Verify that filter is
     * properly invoked.
     */
    @Test
    @RunAsClient
    public void ForwardTest() throws Exception {
        filterWrappedResponseClient.ForwardTest();
    }

    /*
     * @testName: ForwardTest1
     *
     * @assertion_ids: Servlet:SPEC:55; Servlet:SPEC:59; Servlet:JAVADOC:273;
     *
     * @test_Strategy: 1. Create two servlets - ForwardTest1Servlet,
     * /generic/TestServlet 2. Map a filter Test_Filter using <url-pattern> for
     * TestServlet with dispacther value FORWARD, as well as ERROR, INCLUDE and
     * REQUEST. 3. Client try to access ForwardTest1Servlet which in turn use the
     * RequestDispatcher to forward to TestServlet. 4. Verify that filter is
     * properly invoked.
     */
    @Test
    @RunAsClient
    public void ForwardTest1() throws Exception {
        filterWrappedResponseClient.ForwardTest1();
    }

    /*
     * @testName: IncludeTest
     *
     * @assertion_ids: Servlet:SPEC:56; Servlet:SPEC:59;
     *
     * @test_Strategy: 1. Create two servlets - TestServlet, IncludedServlet. 2.
     * Map a filter Test_Filter on IncludedServlet with dispacther value set to
     * INCLUDE only. 3. Client try to use the RequestDispatcher's include to
     * access IncludedServlet through TestServlet. 4. Verify that filter is
     * properly invoked.
     */
    @Test
    @RunAsClient
    public void IncludeTest() throws Exception {
        filterWrappedResponseClient.IncludeTest();
    }

    /*
     * @testName: IncludeTest1
     *
     * @assertion_ids: Servlet:SPEC:56; Servlet:SPEC:59;
     *
     * @test_Strategy: 1. Create two servlet - TestServlet, /generic/DummyServlet,
     * a JSP dummyJSP and a HTML file dummy.html. 2. Map a filter Test_Filter on
     * /generic/DummyServlet, dummyJSP and dummy.html with dispacther value set to
     * INCLUDE as well as ERROR, FORWARD and REQUEST using url-pattern. 3. Client
     * try to use the RequestDispatcher's include to access all three through
     * TestServlet. 4. Verify that filter is properly invoked.
     */
    @Test
    @RunAsClient
    public void IncludeTest1() throws Exception {
        filterWrappedResponseClient.IncludeTest1();
    }

    /*
     * @testName: ErrorTest
     *
     * @assertion_ids: Servlet:SPEC:57; Servlet:SPEC:59;
     *
     * @test_Strategy:
     * 1. Create an Error Page /generic/ErrorPage handling error-code 404.
     * 2. Map a filter Test_Filter on /generic/ErrorPage with dispacther value set to ERROR as well as FORWARD INCLUDE and REQUEST.
     * 3. Client try to access a non-existent Servlet
     * 4. Verify that filter is properly invoked.
     */
    @Test
    @RunAsClient
    public void ErrorTest() throws Exception {
        filterWrappedResponseClient.ErrorTest();
    }

}
