package org.jakartaee8.servlet.servletrequest.requestdispatcher;

import static org.jboss.shrinkwrap.api.ShrinkWrap.create;

import java.io.File;
import java.net.URL;

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
public class RequestDispatcherTest {

    @ArquillianResource
    private URL base;
    private RequestDispatcherClient filterWrappedResponseClient;
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
            return create(WebArchive.class).addClasses(ForwardedServlet.class, ForwardedServlet2.class, ForwardedServlet3.class, GenericTCKServlet.class,
                    IncludedServlet.class, ServletTestUtil.class, TestServlet.class).addAsWebInfResource(new File("src/main/webapp/WEB-INF/web.xml"))

            ;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Before
    public void setup() {
        filterWrappedResponseClient = new RequestDispatcherClient(base, "TestServlet");
    }

    /* Run test */

    /*
     * @testName: forwardTest
     *
     * @assertion_ids: Servlet:SPEC:80; Servlet:JAVADOC:230; Servlet:JAVADOC:272; Servlet:JAVADOC:274;
     *
     * @test_Strategy:
     * Create a servlet, get its RequestDispatcher and use it to forward to a servlet
     */
    @Test
    @RunAsClient
    public void forwardTest() throws Exception {
        filterWrappedResponseClient.forwardTest();
    }

    /*
     * @testName: forward_1Test
     *
     * @assertion_ids: Servlet:SPEC:77; Servlet:SPEC:80; Servlet:JAVADOC:230; Servlet:JAVADOC:277;
     *
     * @test_Strategy:
     * A negative test for RequestDispatcher.forward() method.
     *
     * Create a servlet, print a string to the buffer, flush the buffer to commit the string,
     * get its RequestDispatcher and use it to forward to a servlet.
     */
    @Test
    @RunAsClient
    public void forward_1Test() throws Exception {
        filterWrappedResponseClient.forward_1Test();
    }

    /*
     * @testName: includeTest
     *
     * @assertion_ids: Servlet:JAVADOC:230; Servlet:JAVADOC:278;
     *
     * @test_Strategy:
     * Create a servlet, get its RequestDispatcher and use it to include a servlet
     */
    @Test
    @RunAsClient
    public void includeTest() throws Exception {
        filterWrappedResponseClient.includeTest();
    }

    /*
     * @testName: include_1Test
     *
     * @assertion_ids: Servlet:JAVADOC:230; Servlet:JAVADOC:278;
     *
     * @test_Strategy:
     * A negative test for RequestDispatcher.include() test.
     *
     * Create a servlet, set its Content-Type to be 'text/html', get its RequestDispatcher and use it to include a servlet.
     * The included servlet tries to change the Content-Type to be text/html.
     * Test at the client side for correct Content-Type.
     */
    @Test
    @RunAsClient
    public void include_1Test() throws Exception {
        filterWrappedResponseClient.include_1Test();
    }

    /*
     * @testName: include_2Test
     *
     * @assertion_ids: Servlet:SPEC:82; Servlet:SPEC:80; Servlet:JAVADOC:230; Servlet:JAVADOC:279;
     *
     * @test_Strategy:
     * A negative test for RequestDispatcher.include() method.
     *
     * Create a servlet with service() method throws ServletException. Use RequestDispatcher to include to this servlet.
     * Verify that include() method throws ServletException.
     */
    @Test
    @RunAsClient
    public void include_2Test() throws Exception {
        filterWrappedResponseClient.include_2Test();
    }

    /*
     * @testName: include_3Test
     *
     * @assertion_ids: Servlet:SPEC:82; Servlet:SPEC:80; Servlet:JAVADOC:230; Servlet:JAVADOC:280;
     *
     * @test_Strategy:
     * A negative test for RequestDispatcher.include() method.
     *
     * Create a servlet with service() method throws IOException. Use RequestDispatcher to include to this servlet. V
     * erify that include() method throws IOException.
     */
    @Test
    @RunAsClient
    public void include_3Test() throws Exception {
        filterWrappedResponseClient.include_3Test();
    }

}
