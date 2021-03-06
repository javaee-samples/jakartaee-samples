package org.jakartaee8.servlet.servletrequest.dispatch;

import static org.jboss.shrinkwrap.api.ShrinkWrap.create;

import java.io.File;
import java.net.URL;
import java.util.Properties;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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
public class DispatchTest {

    @ArquillianResource
    private URL base;

    private DispatchClient dispatchClient;
    private Properties testProperties = new Properties();
    private static int count;


    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        try {
        return create(WebArchive.class)
                    .addClasses(
                        GenericTCKServlet.class,
                        DispatchTests.class,
                        DispatchTests1.class,
                        DispatchTests2.class,
                        DispatchTests3.class,
                        DispatchTests4.class,
                        DispatchTests5.class,
                        DispatchTests6.class,
                        DispatchTestServlet.class,
                        GenericTCKServlet.class,
                        TestListener.class,
                        TestListener0.class,
                        TestListener1.class,
                        TestListener2.class,
                        TestListener3.class
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
        dispatchClient = new DispatchClient(base, "DispatchTestServlet");
    }

    @After
    public void teardown() {
        testProperties.clear();
    }

    @Rule
    public TestRule watcher = new TestWatcher() {
        @Override
        protected void starting(Description description) {
            count = count + 1;
            System.out.println("\n\nStarting test " + count + ": " + description.getMethodName());
        }
    };


    /* Run test */


    /* Run test */
    /*
     * @testName: dispatchReturnTest
     *
     * @assertion_ids: Servlet:JAVADOC:639; Servlet:JAVADOC:639.2;
     * Servlet:JAVADOC:639.3; Servlet:JAVADOC:639.4; Servlet:JAVADOC:639.5;
     * Servlet:JAVADOC:703; Servlet:JAVADOC:707; Servlet:JAVADOC:708;
     * Servlet:JAVADOC:710;
     *
     * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
     * Client send a request to DispatchTestServlet; StartAsync in
     * DispatchTestServlet ServletRequest.startAsync(); call ac.dispatch(); call
     * ServletRequest.isAsyncSupported() call ServletRequest.isAsyncStarted() call
     * ServletRequest.getDispatcherType() check System times: before calling
     * dispatch; dispatch returns dispatch operation starts. verifies all work
     * accordingly.
     */
    @Test
    @RunAsClient
    public void dispatchReturnTest() throws Exception {
        dispatchClient.dispatchReturnTest();
    }

    /*
     * @testName: dispatchReturnTest1
     *
     * @assertion_ids: Servlet:JAVADOC:639; Servlet:JAVADOC:639.2;
     * Servlet:JAVADOC:639.3; Servlet:JAVADOC:639.4; Servlet:JAVADOC:639.5;
     * Servlet:JAVADOC:703; Servlet:JAVADOC:707; Servlet:JAVADOC:708;
     * Servlet:JAVADOC:712;
     *
     * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
     * Client send a request to DispatchTestServlet; StartAsync in
     * DispatchTestServlet ServletRequest.startAsync(request, response); call
     * ac.dispatch(); call ServletRequest.isAsyncSupported() call
     * ServletRequest.isAsyncStarted() call ServletRequest.getDispatcherType()
     * check System times: before calling dispatch; dispatch returns dispatch
     * operation starts. verifies all work accordingly.
     */
    @Test
    @RunAsClient
    public void dispatchReturnTest1() throws Exception {
        dispatchClient.dispatchReturnTest1();
    }

    /*
     * @testName: dispatchReturnTest2
     *
     * @assertion_ids: Servlet:JAVADOC:640; Servlet:JAVADOC:640.1;
     * Servlet:JAVADOC:703; Servlet:JAVADOC:707; Servlet:JAVADOC:708;
     * Servlet:JAVADOC:710;
     *
     * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
     * Client send a request to DispatchTestServlet; StartAsync in
     * DispatchTestServlet ServletRequest.startAsync(); call ac.dispatch(URI);
     * call ServletRequest.isAsyncSupported() call ServletRequest.isAsyncStarted()
     * call ServletRequest.getDispatcherType() check System times: before calling
     * dispatch; dispatch returns dispatch operation starts. verifies all work
     * accordingly.
     */
    @Test
    @RunAsClient
    public void dispatchReturnTest2() throws Exception {
        dispatchClient.dispatchReturnTest2();
    }

    /*
     * @testName: dispatchReturnTest3
     *
     * @assertion_ids: Servlet:JAVADOC:640; Servlet:JAVADOC:640.1;
     * Servlet:JAVADOC:703; Servlet:JAVADOC:707; Servlet:JAVADOC:708;
     * Servlet:JAVADOC:712;
     *
     * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
     * Client send a request to DispatchTestServlet; StartAsync in
     * DispatchTestServlet ServletRequest.startAsync(request, response); call
     * ac.dispatch(URI); call ServletRequest.isAsyncSupported() call
     * ServletRequest.isAsyncStarted() call ServletRequest.getDispatcherType()
     * check System times: before calling dispatch; dispatch returns dispatch
     * operation starts. verifies all work accordingly.
     */
    @Test
    @RunAsClient
    public void dispatchReturnTest3() throws Exception {
        dispatchClient.dispatchReturnTest3();
    }

    /*
     * @testName: dispatchReturnTest4
     *
     * @assertion_ids: Servlet:JAVADOC:219; Servlet:JAVADOC:641;
     * Servlet:JAVADOC:641.1; Servlet:JAVADOC:703; Servlet:JAVADOC:706;
     * Servlet:JAVADOC:707; Servlet:JAVADOC:708; Servlet:JAVADOC:710;
     *
     * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
     * Client send a request to DispatchTestServlet; StartAsync in
     * DispatchTestServlet ServletRequest.startAsync(); call
     * ac.dispatch(ServletContext,URI); call ServletRequest.isAsyncSupported()
     * call ServletRequest.isAsyncStarted() call
     * ServletRequest.getDispatcherType() check System times: before calling
     * dispatch; dispatch returns dispatch operation starts. verifies all work
     * accordingly.
     */
    @Test
    @RunAsClient
    @Ignore
    public void dispatchReturnTest4() throws Exception {
        dispatchClient.dispatchReturnTest4();
    }

    /*
     * @testName: dispatchReturnTest5
     *
     * @assertion_ids: Servlet:JAVADOC:219; Servlet:JAVADOC:641;
     * Servlet:JAVADOC:641.1; Servlet:JAVADOC:703; Servlet:JAVADOC:706;
     * Servlet:JAVADOC:707; Servlet:JAVADOC:708; Servlet:JAVADOC:712;
     *
     * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
     * Client send a request to DispatchTestServlet; StartAsync in
     * DispatchTestServlet ServletRequest.startAsync(request, response); call
     * ac.dispatch(ServletContext,URI); call ServletRequest.isAsyncSupported()
     * call ServletRequest.isAsyncStarted() call
     * ServletRequest.getDispatcherType() check System times: before calling
     * dispatch; dispatch returns dispatch operation starts. verifies all work
     * accordingly.
     */
    @Test
    @RunAsClient
    @Ignore
    public void dispatchReturnTest5() throws Exception {
        dispatchClient.dispatchReturnTest5();
    }

    /*
     * @testName: startAsyncAgainTest
     *
     * @assertion_ids: Servlet:JAVADOC:639; Servlet:JAVADOC:639.2;
     * Servlet:JAVADOC:639.3; Servlet:JAVADOC:639.4; Servlet:JAVADOC:639.5;
     * Servlet:JAVADOC:703; Servlet:JAVADOC:707; Servlet:JAVADOC:708;
     * Servlet:JAVADOC:710;
     *
     * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
     * Client send a request to DispatchTestServlet; StartAsync in
     * DispatchTestServlet ServletRequest.startAsync(); call ac.dispatch(); call
     * ServletRequest.isAsyncSupported() call ServletRequest.isAsyncStarted() call
     * ServletRequest.getDispatcherType() check System times: before calling
     * dispatch; dispatch returns dispatch operation starts. StartAsync and
     * dispatch again, and check all above; verifies all work accordingly.
     */
    @Test
    @RunAsClient
    public void startAsyncAgainTest() throws Exception {
        dispatchClient.startAsyncAgainTest();
    }

    /*
     * @testName: startAsyncAgainTest1
     *
     * @assertion_ids: Servlet:JAVADOC:639; Servlet:JAVADOC:639.2;
     * Servlet:JAVADOC:639.3; Servlet:JAVADOC:639.4; Servlet:JAVADOC:639.5;
     * Servlet:JAVADOC:703; Servlet:JAVADOC:707; Servlet:JAVADOC:708;
     * Servlet:JAVADOC:712;
     *
     * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
     * Client send a request to DispatchTestServlet; StartAsync in
     * DispatchTestServlet ServletRequest.startAsync(request, response); call
     * ac.dispatch(); call ServletRequest.isAsyncSupported() call
     * ServletRequest.isAsyncStarted() call ServletRequest.getDispatcherType()
     * check System times: before calling dispatch; dispatch returns dispatch
     * operation starts. StartAsync and dispatch again, and check all above;
     * verifies all work accordingly.
     */
    @Test
    @RunAsClient
    public void startAsyncAgainTest1() throws Exception {
        dispatchClient.startAsyncAgainTest1();
    }

    /*
     * @testName: startAsyncAgainTest2
     *
     * @assertion_ids: Servlet:JAVADOC:639; Servlet:JAVADOC:639.2;
     * Servlet:JAVADOC:639.3; Servlet:JAVADOC:639.4; Servlet:JAVADOC:639.5;
     * Servlet:JAVADOC:639.10; Servlet:JAVADOC:703; Servlet:JAVADOC:707;
     * Servlet:JAVADOC:708; Servlet:JAVADOC:710;
     *
     * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
     * Client send a request to DispatchTestServlet; StartAsync in
     * DispatchTestServlet ServletRequest.startAsync(); call ac.dispatch(); call
     * ServletRequest.isAsyncSupported() call ServletRequest.isAsyncStarted() call
     * ServletRequest.getDispatcherType() check System times: before calling
     * dispatch; dispatch returns dispatch operation starts. StartAsync again in
     * dispatched thread, and check all above; ac.complete(); verifies all work
     * accordingly.
     */
    @Test
    @RunAsClient
    public void startAsyncAgainTest2() throws Exception {
        dispatchClient.startAsyncAgainTest2();
    }

    /*
     * @testName: startAsyncAgainTest3
     *
     * @assertion_ids: Servlet:JAVADOC:639; Servlet:JAVADOC:639.2;
     * Servlet:JAVADOC:639.3; Servlet:JAVADOC:639.4; Servlet:JAVADOC:639.5;
     * Servlet:JAVADOC:639.10; Servlet:JAVADOC:703; Servlet:JAVADOC:707;
     * Servlet:JAVADOC:708; Servlet:JAVADOC:712;
     *
     * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
     * Client send a request to DispatchTestServlet; StartAsync in
     * DispatchTestServlet ServletRequest.startAsync(request, response); call
     * ac.dispatch(); call ServletRequest.isAsyncSupported() call
     * ServletRequest.isAsyncStarted() call ServletRequest.getDispatcherType()
     * check System times: before calling dispatch; dispatch returns dispatch
     * operation starts. StartAsync again in dispatched thread, and check all
     * above; call ac.complete(); verifies all work accordingly.
     */
    @Test
    @RunAsClient
    public void startAsyncAgainTest3() throws Exception {
        dispatchClient.startAsyncAgainTest3();
    }

    /*
     * @testName: startAsyncAgainTest4
     *
     * @assertion_ids: Servlet:JAVADOC:639; Servlet:JAVADOC:639.2;
     * Servlet:JAVADOC:639.3; Servlet:JAVADOC:639.4; Servlet:JAVADOC:639.5;
     * Servlet:JAVADOC:639.9; Servlet:JAVADOC:703; Servlet:JAVADOC:707;
     * Servlet:JAVADOC:708; Servlet:JAVADOC:710;
     *
     * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
     * Client send a request to DispatchTestServlet; StartAsync in
     * DispatchTestServlet ServletRequest.startAsync(); call ac.dispatch(); call
     * ServletRequest.isAsyncSupported() call ServletRequest.isAsyncStarted() call
     * ServletRequest.getDispatcherType() check System times: before calling
     * dispatch; dispatch returns dispatch operation starts. StartAsync in
     * dispatched thread, and check all above; StartAsync again in the
     * asynchrounous thread verifies all work accordingly.
     */
    @Test
    @RunAsClient
    public void startAsyncAgainTest4() throws Exception {
        dispatchClient.startAsyncAgainTest4();
    }

    /*
     * @testName: startAsyncAgainTest5
     *
     * @assertion_ids: Servlet:JAVADOC:639; Servlet:JAVADOC:639.2;
     * Servlet:JAVADOC:639.3; Servlet:JAVADOC:639.4; Servlet:JAVADOC:639.5;
     * Servlet:JAVADOC:639.9; Servlet:JAVADOC:703; Servlet:JAVADOC:707;
     * Servlet:JAVADOC:708; Servlet:JAVADOC:712;
     *
     * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
     * Client send a request to DispatchTestServlet; StartAsync in
     * DispatchTestServlet ServletRequest.startAsync(request, response); call
     * ac.dispatch(); call ServletRequest.isAsyncSupported() call
     * ServletRequest.isAsyncStarted() call ServletRequest.getDispatcherType()
     * check System times: before calling dispatch; dispatch returns dispatch
     * operation starts. StartAsync in dispatched thread, and check all above;
     * StartAsync again in the asynchrounous thread verifies all work accordingly.
     */
    @Test
    @RunAsClient
    public void startAsyncAgainTest5() throws Exception {
        dispatchClient.startAsyncAgainTest5();
    }

    /*
     * @testName: startAsyncAgainTest6
     *
     * @assertion_ids: Servlet:JAVADOC:640; Servlet:JAVADOC:640.1;
     * Servlet:JAVADOC:640.2; Servlet:JAVADOC:640.4; Servlet:JAVADOC:703;
     * Servlet:JAVADOC:707; Servlet:JAVADOC:708; Servlet:JAVADOC:710;
     *
     * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
     * Client send a request to DispatchTestServlet; StartAsync in
     * DispatchTestServlet ServletRequest.startAsync(); call ac.dispatch(URI);
     * call ServletRequest.isAsyncSupported() call ServletRequest.isAsyncStarted()
     * call ServletRequest.getDispatcherType() check request's attributes:
     * REQUEST_URI CONTEXT_PATH PATH_INFO SERVLET_PATH QUERY_STRING
     * ASYNC_REQUEST_URI ASYNC_CONTEXT_PATH ASYNC_PATH_INFO ASYNC_SERVLET_PATH
     * ASYNC_QUERY_STRING check System times: before calling dispatch; dispatch
     * returns dispatch operation starts. StartAsync again, and check all above;
     * call ac.dispatch(URI); verifies all work accordingly.
     */
    @Test
    @RunAsClient
    public void startAsyncAgainTest6() throws Exception {
        dispatchClient.startAsyncAgainTest6();
    }

    /*
     * @testName: startAsyncAgainTest7
     *
     * @assertion_ids: Servlet:JAVADOC:640; Servlet:JAVADOC:640.1;
     * Servlet:JAVADOC:640.2; Servlet:JAVADOC:640.4; Servlet:JAVADOC:703;
     * Servlet:JAVADOC:707; Servlet:JAVADOC:708; Servlet:JAVADOC:712;
     *
     * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
     * Client send a request to DispatchTestServlet; StartAsync in
     * DispatchTestServlet ServletRequest.startAsync(request, response); call
     * ac.dispatch(URI); call ServletRequest.isAsyncSupported() call
     * ServletRequest.isAsyncStarted() call ServletRequest.getDispatcherType()
     * check request's attributes: REQUEST_URI CONTEXT_PATH PATH_INFO SERVLET_PATH
     * QUERY_STRING ASYNC_REQUEST_URI ASYNC_CONTEXT_PATH ASYNC_PATH_INFO
     * ASYNC_SERVLET_PATH ASYNC_QUERY_STRING check System times: before calling
     * dispatch; dispatch returns dispatch operation starts. StartAsync again, and
     * check all above; call ac.dispatch(URI); verifies all work accordingly.
     */
    @Test
    @RunAsClient
    public void startAsyncAgainTest7() throws Exception {
        dispatchClient.startAsyncAgainTest7();
    }

    /*
     * @testName: startAsyncAgainTest8
     *
     * @assertion_ids: Servlet:JAVADOC:640; Servlet:JAVADOC:640.1;
     * Servlet:JAVADOC:640.2; Servlet:JAVADOC:640.4; Servlet:JAVADOC:703;
     * Servlet:JAVADOC:707; Servlet:JAVADOC:708; Servlet:JAVADOC:710;
     *
     * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
     * Client send a request to DispatchTestServlet; StartAsync in
     * DispatchTestServlet ServletRequest.startAsync(); call ac.dispatch(URI);
     * call ServletRequest.isAsyncSupported() call ServletRequest.isAsyncStarted()
     * call ServletRequest.getDispatcherType() check request's attributes:
     * REQUEST_URI CONTEXT_PATH PATH_INFO SERVLET_PATH QUERY_STRING
     * ASYNC_REQUEST_URI ASYNC_CONTEXT_PATH ASYNC_PATH_INFO ASYNC_SERVLET_PATH
     * ASYNC_QUERY_STRING check System times: before calling dispatch; dispatch
     * returns dispatch operation starts. StartAsync again in dispatched thread,
     * and check all above; ac.complete(); verifies all work accordingly.
     */
    @Test
    @RunAsClient
    public void startAsyncAgainTest8() throws Exception {
        dispatchClient.startAsyncAgainTest8();
    }

    /*
     * @testName: startAsyncAgainTest9
     *
     * @assertion_ids: Servlet:JAVADOC:640; Servlet:JAVADOC:640.1;
     * Servlet:JAVADOC:640.2; Servlet:JAVADOC:640.4; Servlet:JAVADOC:703;
     * Servlet:JAVADOC:707; Servlet:JAVADOC:708; Servlet:JAVADOC:712;
     *
     * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
     * Client send a request to DispatchTestServlet; StartAsync in
     * DispatchTestServlet ServletRequest.startAsync(request, response); call
     * ac.dispatch(URI); call ServletRequest.isAsyncSupported() call
     * ServletRequest.isAsyncStarted() call ServletRequest.getDispatcherType()
     * check request's attributes: REQUEST_URI CONTEXT_PATH PATH_INFO SERVLET_PATH
     * QUERY_STRING ASYNC_REQUEST_URI ASYNC_CONTEXT_PATH ASYNC_PATH_INFO
     * ASYNC_SERVLET_PATH ASYNC_QUERY_STRING check System times: before calling
     * dispatch; dispatch returns dispatch operation starts. StartAsync again in
     * dispatched thread, and check all above; call ac.complete(); verifies all
     * work accordingly.
     */
    @Test
    @RunAsClient
    public void startAsyncAgainTest9() throws Exception {
        dispatchClient.startAsyncAgainTest9();
    }

    /*
     * @testName: startAsyncAgainTest10
     *
     * @assertion_ids: Servlet:JAVADOC:640; Servlet:JAVADOC:640.1;
     * Servlet:JAVADOC:640.2; Servlet:JAVADOC:640.3; Servlet:JAVADOC:703;
     * Servlet:JAVADOC:707; Servlet:JAVADOC:708; Servlet:JAVADOC:710;
     *
     * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
     * Client send a request to DispatchTestServlet; StartAsync in
     * DispatchTestServlet ServletRequest.startAsync(); call ac.dispatch(URI);
     * call ServletRequest.isAsyncSupported() call ServletRequest.isAsyncStarted()
     * call ServletRequest.getDispatcherType() check request's attributes:
     * REQUEST_URI CONTEXT_PATH PATH_INFO SERVLET_PATH QUERY_STRING
     * ASYNC_REQUEST_URI ASYNC_CONTEXT_PATH ASYNC_PATH_INFO ASYNC_SERVLET_PATH
     * ASYNC_QUERY_STRING check System times: before calling dispatch; dispatch
     * returns dispatch operation starts. StartAsync in dispatched thread, and
     * check all above; StartAsync again in the asynchrounous thread verifies all
     * work accordingly.
     */
    @Test
    @RunAsClient
    public void startAsyncAgainTest10() throws Exception {
        dispatchClient.startAsyncAgainTest10();
    }

    /*
     * @testName: startAsyncAgainTest11
     *
     * @assertion_ids: Servlet:JAVADOC:640; Servlet:JAVADOC:640.1;
     * Servlet:JAVADOC:640.2; Servlet:JAVADOC:640.3; Servlet:JAVADOC:703;
     * Servlet:JAVADOC:707; Servlet:JAVADOC:708; Servlet:JAVADOC:712;
     *
     * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
     * Client send a request to DispatchTestServlet; StartAsync in
     * DispatchTestServlet ServletRequest.startAsync(request, response); call
     * ac.dispatch(); call ServletRequest.isAsyncSupported() call
     * ServletRequest.isAsyncStarted() call ServletRequest.getDispatcherType()
     * check request's attributes: REQUEST_URI CONTEXT_PATH PATH_INFO SERVLET_PATH
     * QUERY_STRING ASYNC_REQUEST_URI ASYNC_CONTEXT_PATH ASYNC_PATH_INFO
     * ASYNC_SERVLET_PATH ASYNC_QUERY_STRING check System times: before calling
     * dispatch; dispatch returns dispatch operation starts. StartAsync in
     * dispatched thread, and check all above; StartAsync again in the
     * asynchrounous thread verifies all work accordingly.
     */
    @Test
    @RunAsClient
    public void startAsyncAgainTest11() throws Exception {
        dispatchClient.startAsyncAgainTest11();
    }

    /*
     * @testName: startAsyncAgainTest12
     *
     * @assertion_ids: Servlet:JAVADOC:219; Servlet:JAVADOC:641;
     * Servlet:JAVADOC:641.1; Servlet:JAVADOC:641.4; Servlet:JAVADOC:703;
     * Servlet:JAVADOC:706; Servlet:JAVADOC:707; Servlet:JAVADOC:708;
     * Servlet:JAVADOC:710;
     *
     * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
     * Client send a request to DispatchTestServlet; StartAsync in
     * DispatchTestServlet ServletRequest.startAsync(); call
     * ac.dispatch(ServletContext, URI); call ServletRequest.isAsyncSupported()
     * call ServletRequest.isAsyncStarted() call
     * ServletRequest.getDispatcherType() check System times: before calling
     * dispatch; dispatch returns dispatch operation starts. StartAsync and
     * dispatch again, and check all above; verifies all work accordingly.
     */
    @Test
    @RunAsClient
    @Ignore
    public void startAsyncAgainTest12() throws Exception {
        dispatchClient.startAsyncAgainTest12();
    }

    /*
     * @testName: startAsyncAgainTest13
     *
     * @assertion_ids: Servlet:JAVADOC:219; Servlet:JAVADOC:641;
     * Servlet:JAVADOC:641.1; Servlet:JAVADOC:641.4; Servlet:JAVADOC:703;
     * Servlet:JAVADOC:706; Servlet:JAVADOC:707; Servlet:JAVADOC:708;
     * Servlet:JAVADOC:712;
     *
     * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
     * Client send a request to DispatchTestServlet; StartAsync in
     * DispatchTestServlet ServletRequest.startAsync(request, response); call
     * ac.dispatch(ServletContext, URI); call ServletRequest.isAsyncSupported()
     * call ServletRequest.isAsyncStarted() call
     * ServletRequest.getDispatcherType() check System times: before calling
     * dispatch; dispatch returns dispatch operation starts. StartAsync and
     * dispatch again, and check all above; verifies all work accordingly.
     */
    @Test
    @RunAsClient
    @Ignore
    public void startAsyncAgainTest13() throws Exception {
        dispatchClient.startAsyncAgainTest13();
    }

    /*
     * @testName: startAsyncAgainTest14
     *
     * @assertion_ids: Servlet:JAVADOC:219; Servlet:JAVADOC:641;
     * Servlet:JAVADOC:641.1; Servlet:JAVADOC:641.4; Servlet:JAVADOC:703;
     * Servlet:JAVADOC:706;; Servlet:JAVADOC:707; Servlet:JAVADOC:708;
     * Servlet:JAVADOC:710;
     *
     * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
     * Client send a request to DispatchTestServlet; StartAsync in
     * DispatchTestServlet ServletRequest.startAsync(); call
     * ac.dispatch(ServletContext, URI); call ServletRequest.isAsyncSupported()
     * call ServletRequest.isAsyncStarted() call
     * ServletRequest.getDispatcherType() check System times: before calling
     * dispatch; dispatch returns dispatch operation starts. StartAsync again in
     * dispatched thread, and check all above; ac.complete(); verifies all work
     * accordingly.
     */
    @Test
    @RunAsClient
    @Ignore
    public void startAsyncAgainTest14() throws Exception {
        dispatchClient.startAsyncAgainTest14();
    }

    /*
     * @testName: startAsyncAgainTest15
     *
     * @assertion_ids: Servlet:JAVADOC:219; Servlet:JAVADOC:641;
     * Servlet:JAVADOC:641.1; Servlet:JAVADOC:641.4; Servlet:JAVADOC:703;
     * Servlet:JAVADOC:706; Servlet:JAVADOC:707; Servlet:JAVADOC:708;
     * Servlet:JAVADOC:712;
     *
     * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
     * Client send a request to DispatchTestServlet; StartAsync in
     * DispatchTestServlet ServletRequest.startAsync(request, response); call
     * ac.dispatch(); call ServletRequest.isAsyncSupported() call
     * ServletRequest.isAsyncStarted() call ServletRequest.getDispatcherType()
     * check System times: before calling dispatch; dispatch returns dispatch
     * operation starts. StartAsync again in dispatched thread, and check all
     * above; call ac.complete(); verifies all work accordingly.
     */
    @Test
    @RunAsClient
    @Ignore
    public void startAsyncAgainTest15() throws Exception {
        dispatchClient.startAsyncAgainTest15();
    }

    /*
     * @testName: startAsyncAgainTest16
     *
     * @assertion_ids: Servlet:JAVADOC:219; Servlet:JAVADOC:641;
     * Servlet:JAVADOC:641.3; Servlet:JAVADOC:703; Servlet:JAVADOC:706;
     * Servlet:JAVADOC:707; Servlet:JAVADOC:708; Servlet:JAVADOC:710;
     *
     * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
     * Client send a request to DispatchTestServlet; StartAsync in
     * DispatchTestServlet ServletRequest.startAsync(); call
     * ac.dispatch(ServletContext, URI); call ServletRequest.isAsyncSupported()
     * call ServletRequest.isAsyncStarted() call
     * ServletRequest.getDispatcherType() check System times: before calling
     * dispatch; dispatch returns dispatch operation starts. StartAsync in
     * dispatched thread, and check all above; StartAsync again in the
     * asynchrounous thread verifies all work accordingly.
     */
    @Test
    @RunAsClient
    @Ignore
    public void startAsyncAgainTest16() throws Exception {
        dispatchClient.startAsyncAgainTest16();
    }

    /*
     * @testName: startAsyncAgainTest17
     *
     * @assertion_ids: Servlet:JAVADOC:219; Servlet:JAVADOC:641;
     * Servlet:JAVADOC:641.3; Servlet:JAVADOC:703; Servlet:JAVADOC:706;
     * Servlet:JAVADOC:707; Servlet:JAVADOC:708; Servlet:JAVADOC:712;
     *
     * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
     * Client send a request to DispatchTestServlet; StartAsync in
     * DispatchTestServlet ServletRequest.startAsync(request, response); call
     * ac.dispatch(ServletContext, URI); call ServletRequest.isAsyncSupported()
     * call ServletRequest.isAsyncStarted() call
     * ServletRequest.getDispatcherType() check System times: before calling
     * dispatch; dispatch returns dispatch operation starts. StartAsync in
     * dispatched thread, and check all above; StartAsync again in the
     * asynchrounous thread verifies all work accordingly.
     */
    @Test
    @RunAsClient
    @Ignore
    public void startAsyncAgainTest17() throws Exception {
        dispatchClient.startAsyncAgainTest17();
    }

    /*
     * @testName: negativeDispatchTest
     *
     * @assertion_ids: Servlet:JAVADOC:639; Servlet:JAVADOC:639.2;
     * Servlet:JAVADOC:639.3; Servlet:JAVADOC:639.4; Servlet:JAVADOC:639.5;
     * Servlet:JAVADOC:639.11; Servlet:JAVADOC:703; Servlet:JAVADOC:707;
     * Servlet:JAVADOC:708; Servlet:JAVADOC:710;
     *
     * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
     * Client send a request to DispatchTestServlet; StartAsync in
     * DispatchTestServlet ServletRequest.startAsync(); call
     * ServletRequest.isAsyncSupported() call ServletRequest.isAsyncStarted() call
     * ServletRequest.getDispatcherType() call ac.dispatch(); check System times:
     * before calling dispatch; dispatch returns dispatch operation starts. call
     * ac.dispatch() again verifies all work accordingly.
     */
    @Test
    @RunAsClient
    public void negativeDispatchTest() throws Exception {
        dispatchClient.negativeDispatchTest();
    }

    /*
     * @testName: negativeDispatchTest1
     *
     * @assertion_ids: Servlet:JAVADOC:639; Servlet:JAVADOC:639.2;
     * Servlet:JAVADOC:639.3; Servlet:JAVADOC:639.4; Servlet:JAVADOC:639.5;
     * Servlet:JAVADOC:639.11; Servlet:JAVADOC:703; Servlet:JAVADOC:707;
     * Servlet:JAVADOC:708; Servlet:JAVADOC:712;
     *
     * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
     * Client send a request to DispatchTestServlet; StartAsync in
     * DispatchTestServlet ServletRequest.startAsync(request, response); call
     * ServletRequest.isAsyncSupported() call ServletRequest.isAsyncStarted() call
     * ServletRequest.getDispatcherType() call ac.dispatch(); check System times:
     * before calling dispatch; dispatch returns dispatch operation starts. call
     * ac.dispatch() again verifies all work accordingly.
     */
    @Test
    @RunAsClient
    public void negativeDispatchTest1() throws Exception {
        dispatchClient.negativeDispatchTest1();
    }

    /*
     * @testName: negativeDispatchTest4
     *
     * @assertion_ids: Servlet:JAVADOC:640; Servlet:JAVADOC:640.3;
     * Servlet:JAVADOC:640.8; Servlet:JAVADOC:703; Servlet:JAVADOC:707;
     * Servlet:JAVADOC:708; Servlet:JAVADOC:710;
     *
     * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
     * Client send a request to DispatchTestServlet; StartAsync in
     * DispatchTestServlet ServletRequest.startAsync(request, response); call
     * ServletRequest.isAsyncSupported() call ServletRequest.isAsyncStarted() call
     * ServletRequest.getDispatcherType() call ac.dispatch(URI); check System
     * times: before calling dispatch; dispatch returns dispatch operation starts.
     * call ac.dispatch(URI) again verifies all work accordingly.
     */
    @Test
    @RunAsClient
    public void negativeDispatchTest4() throws Exception {
        dispatchClient.negativeDispatchTest4();
    }

    /*
     * @testName: negativeDispatchTest5
     *
     * @assertion_ids: Servlet:JAVADOC:640; Servlet:JAVADOC:640.3;
     * Servlet:JAVADOC:640.8; Servlet:JAVADOC:703; Servlet:JAVADOC:707;
     * Servlet:JAVADOC:708; Servlet:JAVADOC:712;
     *
     * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
     * Client send a request to DispatchTestServlet; StartAsync in
     * DispatchTestServlet ServletRequest.startAsync(request, response); call
     * ServletRequest.isAsyncSupported() call ServletRequest.isAsyncStarted() call
     * ServletRequest.getDispatcherType() call ac.dispatch(URI); check System
     * times: before calling dispatch; dispatch returns dispatch operation starts.
     * call ac.dispatch(URI) again verifies all work accordingly.
     */
    @Test
    @RunAsClient
    public void negativeDispatchTest5() throws Exception {
        dispatchClient.negativeDispatchTest5();
    }

    /*
     * @testName: negativeDispatchTest8
     *
     * @assertion_ids: SServlet:JAVADOC:219; Servlet:JAVADOC:641;
     * Servlet:JAVADOC:641.1; Servlet:JAVADOC:641.2; Servlet:JAVADOC:641.3;
     * Servlet:JAVADOC:703; Servlet:JAVADOC:706; Servlet:JAVADOC:707;
     * Servlet:JAVADOC:708; Servlet:JAVADOC:710;
     *
     * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
     * Client send a request to DispatchTestServlet; StartAsync in
     * DispatchTestServlet ServletRequest.startAsync(); call
     * ServletRequest.isAsyncSupported() call ServletRequest.isAsyncStarted() call
     * ServletRequest.getDispatcherType() call ac.dispatch(ServletContext, URI);
     * check System times: before calling dispatch; dispatch returns dispatch
     * operation starts. call ac.dispatch(ServletContext, URI) again verifies all
     * work accordingly.
     */
    @Test
    @RunAsClient
    @Ignore
    public void negativeDispatchTest8() throws Exception {
        dispatchClient.negativeDispatchTest8();
    }

    /*
     * @testName: negativeDispatchTest9
     *
     * @assertion_ids: SServlet:JAVADOC:219; Servlet:JAVADOC:641;
     * Servlet:JAVADOC:641.1; Servlet:JAVADOC:641.2; Servlet:JAVADOC:641.3;
     * Servlet:JAVADOC:703; Servlet:JAVADOC:706; Servlet:JAVADOC:707;
     * Servlet:JAVADOC:708; Servlet:JAVADOC:712;
     *
     * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
     * Client send a request to DispatchTestServlet; StartAsync in
     * DispatchTestServlet ServletRequest.startAsync(request, response); call
     * ServletRequest.isAsyncSupported() call ServletRequest.isAsyncStarted() call
     * ServletRequest.getDispatcherType() call ac.dispatch(ServletContext, URI);
     * check System times: before calling dispatch; dispatch returns dispatch
     * operation starts. call ac.dispatch(ServletContext, URI) again verifies all
     * work accordingly.
     */
    @Test
    @RunAsClient
    @Ignore
    public void negativeDispatchTest9() throws Exception {
        dispatchClient.negativeDispatchTest9();
    }

    /*
     * @testName: negativeDispatchTest12
     *
     * @assertion_ids: SServlet:JAVADOC:219; Servlet:JAVADOC:641;
     * Servlet:JAVADOC:641.1; Servlet:JAVADOC:641.2; Servlet:JAVADOC:641.3;
     * Servlet:JAVADOC:703; Servlet:JAVADOC:706; Servlet:JAVADOC:707;
     * Servlet:JAVADOC:708; Servlet:JAVADOC:710;
     *
     * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
     * Client send a request to DispatchTestServlet; StartAsync in
     * DispatchTestServlet ServletRequest.startAsync(); call
     * ServletRequest.isAsyncSupported() call ServletRequest.isAsyncStarted() call
     * ServletRequest.getDispatcherType() call ac.dispatch(ServletContext,URI);
     * check System times: before calling dispatch; dispatch returns dispatch
     * operation starts. call ac.dispatch(URI) again verifies all work
     * accordingly.
     */
    @Test
    @RunAsClient
    @Ignore
    public void negativeDispatchTest12() throws Exception {
        dispatchClient.negativeDispatchTest12();
    }

    /*
     * @testName: negativeDispatchTest13
     *
     * @assertion_ids: SServlet:JAVADOC:219; Servlet:JAVADOC:641;
     * Servlet:JAVADOC:641.1; Servlet:JAVADOC:641.2; Servlet:JAVADOC:641.3;
     * Servlet:JAVADOC:703; Servlet:JAVADOC:706; Servlet:JAVADOC:707;
     * Servlet:JAVADOC:708; Servlet:JAVADOC:712;
     *
     * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
     * Client send a request to DispatchTestServlet; StartAsync in
     * DispatchTestServlet ServletRequest.startAsync(request, response); call
     * ServletRequest.isAsyncSupported() call ServletRequest.isAsyncStarted() call
     * ServletRequest.getDispatcherType() call ac.dispatch(ServletContext,URI);
     * check System times: before calling dispatch; dispatch returns dispatch
     * operation starts. call ac.dispatch(URI) again verifies all work
     * accordingly.
     */
    @Test
    @RunAsClient
    @Ignore
    public void negativeDispatchTest13() throws Exception {
        dispatchClient.negativeDispatchTest13();
    }

    /*
     * @testName: dispatchAfterCommitTest
     *
     * @assertion_ids: Servlet:JAVADOC:639.12; Servlet:JAVADOC:707;
     * Servlet:JAVADOC:708; Servlet:JAVADOC:710; Servlet:JAVADOC:866;
     * Servlet:JAVADOC:872;
     *
     * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
     * Client send a request to DispatchTestServlet; DispatchTestServlet commits
     * the response; StartAsync in DispatchTestServlet
     * ServletRequest.startAsync(); call ac.dispatch(); verifies all works
     */
    @Test
    @RunAsClient
    public void dispatchAfterCommitTest() throws Exception {
        dispatchClient.dispatchAfterCommitTest();
    }

    /*
     * @testName: dispatchAfterCommitTest1
     *
     * @assertion_ids: Servlet:JAVADOC:639.12; Servlet:JAVADOC:707;
     * Servlet:JAVADOC:708; Servlet:JAVADOC:712; Servlet:JAVADOC:866;
     * Servlet:JAVADOC:872;
     *
     * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
     * Client send a request to DispatchTestServlet; DispatchTestServlet commits
     * the response; StartAsync in DispatchTestServlet
     * ServletRequest.startAsync(request, response); call ac.dispatch(); verifies
     * all works
     */
    @Test
    @RunAsClient
    public void dispatchAfterCommitTest1() throws Exception {
        dispatchClient.dispatchAfterCommitTest1();
    }

    /*
     * @testName: dispatchAfterCommitTest2
     *
     * @assertion_ids: Servlet:JAVADOC:707; Servlet:JAVADOC:708;
     * Servlet:JAVADOC:710; Servlet:JAVADOC:866; Servlet:JAVADOC:872;
     *
     * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
     * Client send a request to DispatchTestServlet; DispatchTestServlet commits
     * the response; StartAsync in DispatchTestServlet
     * ServletRequest.startAsync(); call ac.dispatch(URI); verifies all works
     */
    @Test
    @RunAsClient
    public void dispatchAfterCommitTest2() throws Exception {
        dispatchClient.dispatchAfterCommitTest2();
    }

    /*
     * @testName: dispatchAfterCommitTest3
     *
     * @assertion_ids: Servlet:JAVADOC:707; Servlet:JAVADOC:708;
     * Servlet:JAVADOC:712; Servlet:JAVADOC:866; Servlet:JAVADOC:872;
     *
     * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
     * Client send a request to DispatchTestServlet; DispatchTestServlet commits
     * the response; StartAsync in DispatchTestServlet
     * ServletRequest.startAsync(request, response); call ac.dispatch(URI);
     * verifies all works
     */
    @Test
    @RunAsClient
    public void dispatchAfterCommitTest3() throws Exception {
        dispatchClient.dispatchAfterCommitTest3();
    }

    /*
     * @testName: dispatchAfterCommitTest4
     *
     * @assertion_ids: Servlet:JAVADOC:707; Servlet:JAVADOC:708;
     * Servlet:JAVADOC:710; Servlet:JAVADOC:866; Servlet:JAVADOC:872;
     *
     * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
     * Client send a request to DispatchTestServlet; DispatchTestServlet commits
     * the response; StartAsync in DispatchTestServlet
     * ServletRequest.startAsync(); call ac.dispatch(ServletContext,URI); verifies
     * all works
     */
    @Test
    @RunAsClient
    @Ignore("cross-context")
    public void dispatchAfterCommitTest4() throws Exception {
        dispatchClient.dispatchAfterCommitTest4();
    }

    /*
     * @testName: dispatchAfterCommitTest5
     *
     * @assertion_ids: Servlet:JAVADOC:707; Servlet:JAVADOC:708;
     * Servlet:JAVADOC:712; Servlet:JAVADOC:866; Servlet:JAVADOC:872;
     *
     * @test_Strategy: Create a Servlet DispatchTestServlet which supports async;
     * Client send a request to DispatchTestServlet; DispatchTestServlet commits
     * the response; StartAsync in DispatchTestServlet
     * ServletRequest.startAsync(request, response); call
     * ac.dispatch(ServletContext,URI); verifies all works
     */
    @Test
    @RunAsClient
    @Ignore
    public void dispatchAfterCommitTest5() throws Exception {
        dispatchClient.dispatchAfterCommitTest5();
    }

}
