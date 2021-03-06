package org.jakartaee8.servlet.servletrequest.filter.on.forward;

import static org.jboss.shrinkwrap.api.ShrinkWrap.create;

import java.io.File;
import java.net.URL;

import org.jakartaee8.servlet.servletrequest.filter.on.forward.CTSResponseWrapper;
import org.jakartaee8.servlet.servletrequest.filter.on.forward.ForwardedServlet;
import org.jakartaee8.servlet.servletrequest.filter.on.forward.GenericTCKServlet;
import org.jakartaee8.servlet.servletrequest.filter.on.forward.ServletTestUtil;
import org.jakartaee8.servlet.servletrequest.filter.on.forward.TestServlet;
import org.jakartaee8.servlet.servletrequest.filter.on.forward.WrapResponseFilter;
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
public class FilterWrappedResponseTest {

    @ArquillianResource
    private URL base;
    private FilterWrappedResponseClient filterWrappedResponseClient;
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
                        TestServlet.class,
                        CTSResponseWrapper.class,
                        ForwardedServlet.class,
                        ServletTestUtil.class,
                        WrapResponseFilter.class
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
        filterWrappedResponseClient = new FilterWrappedResponseClient(base, "TestServlet");
    }


    /* Run test */
    /*
     * @testName: wrapResponseTest
     *
     * @assertion_ids: Servlet:SPEC:54; Servlet:SPEC:59;
     *
     * @test_Strategy:
     *     1. Create two servlets - TestServlet, ForwardedServlet
     *     2. Invoke ForwardedServlet using forward in TestServlet
     *     3. Map a filter WrapResponseFilter with dispatcher value set to FORWARD
     *     4. In the filter, wrap the response with custom implementation of ServletResponse CTSResponseWrapper
     *     5. Verify that filter is properly invoked.
     */
    @Test
    @RunAsClient
    public void dispatchReturnTest() throws Exception {
        filterWrappedResponseClient.wrapResponseTest();
    }

}
