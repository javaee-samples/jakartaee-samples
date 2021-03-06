package org.jakartaee8.servlet.servletrequest.asynccontext;

import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.regex.Pattern;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gargoylesoftware.htmlunit.TextPage;
import com.gargoylesoftware.htmlunit.WebClient;

/**
 * @author Arjan Tijms
 */
@RunWith(Arquillian.class)
public class ServletAsyncContextTest {

    private final static String APITEST = "APITEST";
    private final static String SEARCH_STRING = "SEARCH_STRING";
    private final static String STATUS_CODE = "STATUS_CODE";
    private final static String UNEXPECTED_RESPONSE_MATCH = "UNEXPECTED_RESPONSE_MATCH";

    @ArquillianResource
    private URL base;

    private WebClient webClient;
    Properties testProperties = new Properties();

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return create(WebArchive.class)
                    .addClasses(
                        ACListener.class, ACListener1.class, ACListener2.class, ACListenerBad.class,
                        AsyncTests.class, AsyncTestServlet.class,
                        GenericTCKServlet.class,
                        RequestWrapper.class, ResponseWrapper.class)
                    .addAsWebInfResource(new File("src/main/webapp/WEB-INF/web.xml"));
    }

    @Before
    public void setup() {
        webClient = new WebClient();
    }

    @After
    public void teardown() {
        webClient.close();
        testProperties.clear();
    }


    /* Run test */
    /*
     * Test set up:
     *
     * Define two servets AsyncTestServlet and AsyncTests that supports async in
     * web.xml; Define three AsyncListeners using annotation: ACListener,
     * ACListener1, ACListenerBad; - ACListener1 does not complete properly -
     * ACListenerBad does not instantiate; Define a ServletRequestWrapper that
     * wraps the original request; Define a ServletResponseWrapper that wraps the
     * original response;
     *
     */

    /*
     * @testName: dispatchZeroArgTest
     *
     * @assertion_ids: Servlet:JAVADOC:639; Servlet:JAVADOC:639.2;
     * Servlet:JAVADOC:639.4; Servlet:JAVADOC:703; Servlet:JAVADOC:707;
     * Servlet:JAVADOC:708; Servlet:JAVADOC:710;
     *
     * @test_Strategy: Create a Servlet AsyncTestServlet which supports async;
     * Client send a request to AsyncTestServlet; StartAsync in AsyncTestServlet
     * ServletRequest.startAsync(); call ac.dispatch(); call
     * ServletRequest.isAsyncSupported() call ServletRequest.isAsyncStarted() call
     * ServletRequest.getDispatcherType() verifies all work accordingly.
     */
    @Test
    @RunAsClient
    public void dispatchZeroArgTest() throws Exception {
      testProperties.setProperty(APITEST, "dispatchZeroArgTest");
      testProperties.setProperty(SEARCH_STRING,
          "ASYNC_NOT_STARTED_dispatchZeroArgTest|" + "IsAsyncSupported=true|"
              + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
              + "ASYNC_STARTED_dispatchZeroArgTest|" + "IsAsyncSupported=true|"
              + "IsAsyncStarted=false|" + "DispatcherType=ASYNC");
      invoke();
    }

    /*
     * @testName: dispatchZeroArgTest1
     *
     * @assertion_ids: Servlet:JAVADOC:639; Servlet:JAVADOC:639.2;
     * Servlet:JAVADOC:639.4; Servlet:JAVADOC:703; Servlet:JAVADOC:707;
     * Servlet:JAVADOC:708; Servlet:JAVADOC:710;
     *
     * @test_Strategy: Create a Servlet AsyncTestServlet which supports async;
     * Client send a request to AsyncTestServlet; StartAsync in AsyncTestServlet
     * ServletRequest.startAsync(ServletRequest, ServletResponse); call
     * ac.dispatch(); call ServletRequest.isAsyncSupported() call
     * ServletRequest.isAsyncStarted() call ServletRequest.getDispatcherType()
     * verifies all work accordingly.
     */
    @Test
    @RunAsClient
    public void dispatchZeroArgTest1() throws Exception {
      testProperties.setProperty(APITEST, "dispatchZeroArgTest1");
      testProperties.setProperty(SEARCH_STRING,
          "ASYNC_NOT_STARTED_dispatchZeroArgTest1|" + "IsAsyncSupported=true|"
              + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
              + "ASYNC_STARTED_dispatchZeroArgTest1|" + "IsAsyncSupported=true|"
              + "IsAsyncStarted=false|" + "DispatcherType=ASYNC");
      invoke();
    }

    /*
     * @testName: dispatchZeroArgTest2
     *
     * @assertion_ids: Servlet:JAVADOC:639; Servlet:JAVADOC:639.2;
     * Servlet:JAVADOC:639.4; Servlet:JAVADOC:703; Servlet:JAVADOC:707;
     * Servlet:JAVADOC:708; Servlet:JAVADOC:710;
     *
     * @test_Strategy: Create a Servlet AsyncTestServlet which supports async;
     * Client send a request to AsyncTestServlet; StartAsync in AsyncTestServlet
     * ServletRequest.startAsync(ServletRequestWrapper, ServletResponseWrapper);
     * call ac.dispatch(); call ServletRequest.isAsyncSupported() call
     * ServletRequest.isAsyncStarted() call ServletRequest.getDispatcherType()
     * verifies all work accordingly.
     */
    @Test
    @RunAsClient
    public void dispatchZeroArgTest2() throws Exception {
      testProperties.setProperty(APITEST, "dispatchZeroArgTest2");
      testProperties.setProperty(SEARCH_STRING,
          "ASYNC_NOT_STARTED_dispatchZeroArgTest2|" + "IsAsyncSupported=true|"
              + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
              + "ASYNC_STARTED_dispatchZeroArgTest2|" + "IsAsyncSupported=true|"
              + "IsAsyncStarted=false|" + "DispatcherType=ASYNC");
      invoke();
    }

    /*
     * @testName: dispatchContextPathTest
     *
     * @assertion_ids: Servlet:JAVADOC:641; Servlet:JAVADOC:703;
     * Servlet:JAVADOC:707; Servlet:JAVADOC:708; Servlet:JAVADOC:710;
     *
     * @test_Strategy: Create two Servlets AsyncTestServlet and AsynTest both
     * support async; Client send a request to AsyncTestServlet; StartAsync in
     * AsyncTestServlet ServletRequest.startAsync(); call
     * ac.dispatch(ServletContext, path to AsynTest); call
     * ServletRequest.isAsyncSupported() call ServletRequest.isAsyncStarted() call
     * ServletRequest.getDispatcherType() verifies all work accordingly.
     */
    @Test
    @RunAsClient
    public void dispatchContextPathTest() throws Exception {
      testProperties.setProperty(APITEST, "dispatchContextPathTest");
      testProperties.setProperty(SEARCH_STRING,
          "ASYNC_NOT_STARTED_dispatchContextPathTest|" + "IsAsyncSupported=true|"
              + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
              + "ASYNC_STARTED_asyncTest|" + "IsAsyncSupported=true|"
              + "IsAsyncStarted=false|" + "DispatcherType=ASYNC");
      invoke();
    }

    /*
     * @testName: dispatchContextPathTest1
     *
     * @assertion_ids: Servlet:JAVADOC:641; Servlet:JAVADOC:703;
     * Servlet:JAVADOC:707; Servlet:JAVADOC:708; Servlet:JAVADOC:710;
     *
     * @test_Strategy: Create two Servlets AsyncTestServlet and AsynTest both
     * support async; Client send a request to AsyncTestServlet; StartAsync in
     * AsyncTestServlet ServletRequest.startAsync(ServletRequest,
     * ServletResponse); call ac.dispatch(ServletContext, path to AsynTest); call
     * ServletRequest.isAsyncSupported() call ServletRequest.isAsyncStarted() call
     * ServletRequest.getDispatcherType() verifies all work accordingly.
     */
    @Test
    @RunAsClient
    public void dispatchContextPathTest1() throws Exception {
      testProperties.setProperty(APITEST, "dispatchContextPathTest1");
      testProperties.setProperty(SEARCH_STRING,
          "ASYNC_NOT_STARTED_dispatchContextPathTest1|" + "IsAsyncSupported=true|"
              + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
              + "ASYNC_STARTED_asyncTest|" + "IsAsyncSupported=true|"
              + "IsAsyncStarted=false|" + "DispatcherType=ASYNC");
      invoke();
    }

    /*
     * @testName: dispatchContextPathTest2
     *
     * @assertion_ids: Servlet:JAVADOC:641; Servlet:JAVADOC:703;
     * Servlet:JAVADOC:707; Servlet:JAVADOC:708; Servlet:JAVADOC:710;
     *
     * @test_Strategy: Create two Servlets AsyncTestServlet and AsynTest both
     * support async; Client send a request to AsyncTestServlet; StartAsync in
     * AsyncTestServlet ServletRequest.startAsync(ServletRequestWrapper,
     * ServletResponseWrapper); call ac.dispatch(ServletContext, path to
     * AsynTest); call ServletRequest.isAsyncSupported() call
     * ServletRequest.isAsyncStarted() call ServletRequest.getDispatcherType()
     * verifies all work accordingly.
     */
    public void dispatchContextPathTest2() throws Exception {
      testProperties.setProperty(APITEST, "dispatchContextPathTest2");
      testProperties.setProperty(SEARCH_STRING,
          "ASYNC_NOT_STARTED_dispatchContextPathTest2|" + "IsAsyncSupported=true|"
              + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
              + "ASYNC_STARTED_asyncTest|" + "IsAsyncSupported=true|"
              + "IsAsyncStarted=false|" + "DispatcherType=ASYNC");
      invoke();
    }

    /*
     * @testName: forwardTest1
     *
     * @assertion_ids: Servlet:JAVADOC:639; Servlet:JAVADOC:639.2;
     * Servlet:JAVADOC:639.4; Servlet:JAVADOC:703; Servlet:JAVADOC:707;
     * Servlet:JAVADOC:708; Servlet:JAVADOC:710;
     *
     * @test_Strategy: Create a Servlet AsyncTestServlet which supports async;
     * Client send a request to AsyncTestServlet at
     * "/AsyncTestServlet?testname=forwardTest1";
     * getRequestDispatcher("/AsyncTestServlet?testname=forwardDummy1").forward(
     * request, response); In forwardDummy1: AsyncContext ac =
     * request.startAsync(request, response); ac.dispatch(); verifies that it
     * dispatches to "/AsyncTestServlet?testname=forwardDummy1".
     */
    @Test
    @RunAsClient
    public void forwardTest1() throws Exception {
      testProperties.setProperty(APITEST, "forwardTest1");
      testProperties.setProperty(SEARCH_STRING,
          "forwardDummy1|" + "ASYNC_NOT_STARTED_forwardDummy1|"
              + "IsAsyncSupported=true|" + "IsAsyncStarted=false|"
              + "DispatcherType=FORWARD|" + "forwardDummy1|"
              + "ASYNC_STARTED_forwardDummy1|" + "IsAsyncSupported=true|"
              + "IsAsyncStarted=false|" + "DispatcherType=ASYNC");
      testProperties.setProperty(UNEXPECTED_RESPONSE_MATCH,
          "ASYNC_STARTED_forwardTest1");
      invoke();
    }

    /*
     * @testName: getRequestTest
     *
     * @assertion_ids: Servlet:JAVADOC:642; Servlet:JAVADOC:710;
     * Servlet:JAVADOC:710.1; Servlet:SPEC:270;
     *
     * @test_Strategy: Create a Servlet AsyncTestServlet which support async;
     * Client send a request to AsyncTestServlet; StartAsync in AsyncTestServlet
     * ServletRequest.startAsync(); call AsyncContext.getRequest() verifies it
     * works.
     */
    @Test
    @RunAsClient
    public void getRequestTest() throws Exception {
      testProperties.setProperty(APITEST, "getRequestTest");
      invoke();
    }

    /*
     * @testName: getRequestTest1
     *
     * @assertion_ids: Servlet:JAVADOC:642; Servlet:JAVADOC:712;
     * Servlet:JAVADOC:712.1; Servlet:SPEC:270;
     *
     * @test_Strategy: Create a Servlet AsyncTestServlet which support async;
     * Client send a request to AsyncTestServlet; StartAsync in AsyncTestServlet
     * ServletRequest.startAsync(Request, Response); call
     * AsyncContext.getRequest() verifies it works.
     */
    @Test
    @RunAsClient
    public void getRequestTest1() throws Exception {
      testProperties.setProperty(APITEST, "getRequestTest1");
      invoke();
    }

    /*
     * @testName: getRequestTest2
     *
     * @assertion_ids: Servlet:JAVADOC:642; Servlet:JAVADOC:712;
     * Servlet:JAVADOC:712.1; Servlet:SPEC:270;
     *
     * @test_Strategy: Create a Servlet AsyncTestServlet which support async;
     * Client send a request to AsyncTestServlet; StartAsync in AsyncTestServlet
     * ServletRequest.startAsync(RequestWrapper, ResponseWrapper); call
     * AsyncContext.getRequest() verifies it works.
     */
    @Test
    @RunAsClient
    public void getRequestTest2() throws Exception {
      testProperties.setProperty(APITEST, "getRequestTest2");
      invoke();
    }

    /*
     * @testName: getRequestTest3
     *
     * @assertion_ids: Servlet:JAVADOC:642; Servlet:JAVADOC:712;
     * Servlet:JAVADOC:712.1; Servlet:SPEC:270;
     *
     * @test_Strategy: Create a Servlet AsyncTestServlet which support async;
     * Client send a request to AsyncTestServlet; StartAsync in AsyncTestServlet
     * ServletRequest.startAsync(Request, ResponseWrapper); call
     * AsyncContext.getRequest() verifies it works.
     */
    @Test
    @RunAsClient
    public void getRequestTest3() throws Exception {
      testProperties.setProperty(APITEST, "getRequestTest3");
      invoke();
    }

    /*
     * @testName: getRequestTest4
     *
     * @assertion_ids: Servlet:JAVADOC:642; Servlet:JAVADOC:712;
     * Servlet:JAVADOC:712.1; Servlet:SPEC:270;
     *
     * @test_Strategy: Create a Servlet AsyncTestServlet which support async;
     * Client send a request to AsyncTestServlet; StartAsync in AsyncTestServlet
     * ServletRequest.startAsync(RequestWrapper, Response); call
     * AsyncContext.getRequest() verifies it works.
     */
    @Test
    @RunAsClient
    public void getRequestTest4() throws Exception {
      testProperties.setProperty(APITEST, "getRequestTest4");
      invoke();
    }

    /*
     * @testName: getRequestTest6
     *
     * @assertion_ids: Servlet:JAVADOC:642; Servlet:JAVADOC:710;
     * Servlet:JAVADOC:710.1; Servlet:SPEC:270;
     *
     * @test_Strategy: Create a Servlet AsyncTestServlet which support async;
     * Client send a request to AsyncTestServlet; StartAsync in AsyncTestServlet
     * ServletRequest.startAsync(); In a separate thread, call
     * AsyncContext.getRequest() verifies it works.
     */
    @Test
    @RunAsClient
    public void getRequestTest6() throws Exception {
      testProperties.setProperty(APITEST, "getRequestTest6");
      invoke();
    }

    /*
     * @testName: getRequestTest7
     *
     * @assertion_ids: Servlet:JAVADOC:642; Servlet:JAVADOC:712;
     * Servlet:JAVADOC:712.1; Servlet:SPEC:270;
     *
     * @test_Strategy: Create a Servlet AsyncTestServlet which support async;
     * Client send a request to AsyncTestServlet; StartAsync in AsyncTestServlet
     * ServletRequest.startAsync(Request, Response); In a separate thread, call
     * AsyncContext.getRequest() verifies it works.
     */
    @Test
    @RunAsClient
    public void getRequestTest7() throws Exception {
      testProperties.setProperty(APITEST, "getRequestTest7");
      invoke();
    }

    /*
     * @testName: getRequestTest8
     *
     * @assertion_ids: Servlet:JAVADOC:642; Servlet:JAVADOC:712;
     * Servlet:JAVADOC:712.1; Servlet:SPEC:270;
     *
     * @test_Strategy: Create a Servlet AsyncTestServlet which support async;
     * Client send a request to AsyncTestServlet; StartAsync in AsyncTestServlet
     * ServletRequest.startAsync(RequestWrapper, ResponseWrapper); In a separate
     * thread, call AsyncContext.getRequest() verifies it works.
     */
    @Test
    @RunAsClient
    public void getRequestTest8() throws Exception {
      testProperties.setProperty(APITEST, "getRequestTest8");
      invoke();
    }

    /*
     * @testName: getRequestTest9
     *
     * @assertion_ids: Servlet:JAVADOC:642; Servlet:JAVADOC:712;
     * Servlet:JAVADOC:712.1; Servlet:SPEC:270;
     *
     * @test_Strategy: Create a Servlet AsyncTestServlet which support async;
     * Client send a request to AsyncTestServlet; StartAsync in AsyncTestServlet
     * ServletRequest.startAsync(Request, ResponseWrapper); In a separate thread,
     * call AsyncContext.getRequest() verifies it works.
     */
    public void getRequestTest9() throws Exception {
      testProperties.setProperty(APITEST, "getRequestTest9");
      invoke();
    }

    /*
     * @testName: getRequestTest10
     *
     * @assertion_ids: Servlet:JAVADOC:642; Servlet:JAVADOC:712;
     * Servlet:JAVADOC:712.1; Servlet:SPEC:270;
     *
     * @test_Strategy: Create a Servlet AsyncTestServlet which support async;
     * Client send a request to AsyncTestServlet; StartAsync in AsyncTestServlet
     * ServletRequest.startAsync(RequestWrapper, Response); In a separate thread,
     * call AsyncContext.getRequest() verifies it works.
     */
    public void getRequestTest10() throws Exception {
      testProperties.setProperty(APITEST, "getRequestTest10");
      invoke();
    }

    /*
     * @testName: getRequestTest12
     *
     * @assertion_ids: Servlet:JAVADOC:642; Servlet:JAVADOC:645;
     * Servlet:JAVADOC:710; Servlet:JAVADOC:710.1; Servlet:SPEC:270;
     *
     * @test_Strategy: Create a Servlet AsyncTestServlet which support async;
     * Client send a request to AsyncTestServlet; StartAsync in AsyncTestServlet
     * ServletRequest.startAsync(); In a separate thread, call
     * AsyncContext.getRequest() verifies it works.
     */
    @Test
    @RunAsClient
    public void getRequestTest12() throws Exception {
      testProperties.setProperty(APITEST, "getRequestTest12");
      invoke();
    }

    /*
     * @testName: getRequestTest13
     *
     * @assertion_ids: Servlet:JAVADOC:642; Servlet:JAVADOC:645;
     * Servlet:JAVADOC:712; Servlet:JAVADOC:712.1; Servlet:SPEC:270;
     *
     * @test_Strategy: Create a Servlet AsyncTestServlet which support async;
     * Client send a request to AsyncTestServlet; StartAsync in AsyncTestServlet
     * ServletRequest.startAsync(Request, Response); In a separate thread, call
     * AsyncContext.getRequest() verifies it works.
     */
    @Test
    @RunAsClient
    public void getRequestTest13() throws Exception {
      testProperties.setProperty(APITEST, "getRequestTest13");
      invoke();
    }

    /*
     * @testName: getRequestTest14
     *
     * @assertion_ids: Servlet:JAVADOC:642; Servlet:JAVADOC:645;
     * Servlet:JAVADOC:712; Servlet:JAVADOC:712.1; Servlet:SPEC:270;
     *
     * @test_Strategy: Create a Servlet AsyncTestServlet which support async;
     * Client send a request to AsyncTestServlet; StartAsync in AsyncTestServlet
     * ServletRequest.startAsync(RequestWrapper, ResponseWrapper); In a separate
     * thread, call AsyncContext.getRequest() verifies it works.
     */
    @Test
    @RunAsClient
    public void getRequestTest14() throws Exception {
      testProperties.setProperty(APITEST, "getRequestTest14");
      invoke();
    }

    /*
     * @testName: getRequestTest15
     *
     * @assertion_ids: Servlet:JAVADOC:642; Servlet:JAVADOC:645;
     * Servlet:JAVADOC:712; Servlet:JAVADOC:712.1; Servlet:SPEC:270;
     *
     * @test_Strategy: Create a Servlet AsyncTestServlet which support async;
     * Client send a request to AsyncTestServlet; StartAsync in AsyncTestServlet
     * ServletRequest.startAsync(Request, ResponseWrapper); In a separate thread,
     * call AsyncContext.getRequest() verifies it works.
     */
    @Test
    @RunAsClient
    public void getRequestTest15() throws Exception {
      testProperties.setProperty(APITEST, "getRequestTest15");
      invoke();
    }

    /*
     * @testName: getRequestTest16
     *
     * @assertion_ids: Servlet:JAVADOC:642; Servlet:JAVADOC:645;
     * Servlet:JAVADOC:712; Servlet:JAVADOC:712.1; Servlet:SPEC:270;
     *
     * @test_Strategy: Create a Servlet AsyncTestServlet which support async;
     * Client send a request to AsyncTestServlet; StartAsync in AsyncTestServlet
     * ServletRequest.startAsync(RequestWrapper, Response); In a separate thread,
     * call AsyncContext.getRequest() verifies it works.
     */
    @Test
    @RunAsClient
    public void getRequestTest16() throws Exception {
      testProperties.setProperty(APITEST, "getRequestTest16");
      invoke();
    }

    /*
     * @testName: asyncListenerTest1
     *
     * @assertion_ids: Servlet:JAVADOC:649; Servlet:JAVADOC:710;
     * Servlet:JAVADOC:846; Servlet:JAVADOC:866; Servlet:JAVADOC:873;
     *
     * @test_Strategy: Create a Servlet AsyncTestServlet which support async;
     * Create an AsyncListenerBad; Client send a request to AsyncTestServlet;
     * StartAsync in AsyncTestServlet; AsyncContext.createistener(clazz) verifies
     * ServletException is thrown when clazz fails to be instantiated.
     */
    @Test
    @RunAsClient
    public void asyncListenerTest1() throws Exception {
      testProperties.setProperty(APITEST, "asyncListenerTest1");
      invoke();
    }

    /*
     * @testName: asyncListenerTest6
     *
     * @assertion_ids: Servlet:JAVADOC:645; Servlet:JAVADOC:649;
     * Servlet:JAVADOC:710; Servlet:JAVADOC:846; Servlet:JAVADOC:866;
     * Servlet:JAVADOC:873;
     *
     * @test_Strategy: Create a Servlet AsyncTestServlet which support async;
     * Create an AsyncListenerBad; Client send a request to AsyncTestServlet;
     * StartAsync in AsyncTestServlet; AsyncContext.createistener(clazz) In a
     * separate thread, call AsyncContext.complete(); verifies ServletException is
     * thrown when clazz fails to be instantiated.
     */
    @Test
    @RunAsClient
    public void asyncListenerTest6() throws Exception {
      testProperties.setProperty(APITEST, "asyncListenerTest6");
      invoke();
    }

    /*
     * @testName: timeOutTest
     *
     * @assertion_ids: Servlet:JAVADOC:649; Servlet:JAVADOC:710;
     * Servlet:JAVADOC:846; Servlet:JAVADOC:868; Servlet:JAVADOC:869;
     *
     * @test_Strategy: Create a Servlet AsyncTestServlet which support async;
     * Client send a request to AsyncTestServlet; StartAsync in AsyncTestServlet;
     * AsyncContext.setTimeout(L) verifies it works using getTimeout.
     */
    @Test
    @RunAsClient
    public void timeOutTest() throws Exception {
      testProperties.setProperty(APITEST, "timeOutTest");
      invoke();
    }

    /*
     * @testName: timeOutTest1
     *
     * @assertion_ids: Servlet:JAVADOC:649; Servlet:JAVADOC:710;
     * Servlet:JAVADOC:846; Servlet:JAVADOC:868; Servlet:JAVADOC:868.1;
     * Servlet:JAVADOC:869; Servlet:JAVADOC:869.3;
     *
     * @test_Strategy: Create a Servlet AsyncTestServlet which support async;
     * Client send a request to AsyncTestServlet; StartAsync in AsyncTestServlet;
     * AsyncContext.setTimeout(0L) verifies it works using getTimeout.
     */
    @Test
    @RunAsClient
    public void timeOutTest1() throws Exception {
      testProperties.setProperty(APITEST, "timeOutTest1");
      invoke();
    }

    /*
     * @testName: timeOutTest2
     *
     * @assertion_ids: Servlet:JAVADOC:651; Servlet:JAVADOC:710;
     * Servlet:JAVADOC:846; Servlet:JAVADOC:868; Servlet:JAVADOC:869;
     * Servlet:JAVADOC:869.1; Servlet:JAVADOC:869.2; Servlet:JAVADOC:869.5;
     *
     * @test_Strategy: Create a Servlet AsyncTestServlet which support async;
     * Client send a request to AsyncTestServlet; StartAsync in AsyncTestServlet;
     * AsyncContext.setTimeout(L) verifies it works by letting it timeout.
     */
    @Test
    @RunAsClient
    public void timeOutTest2() throws Exception {
      testProperties.setProperty(APITEST, "timeOutTest2");
      testProperties.setProperty(SEARCH_STRING, "in onTimeout method of ACListener2");
      testProperties.setProperty(STATUS_CODE, "-1");
      invoke();
    }

    /*
     * @testName: timeOutTest4
     *
     * @assertion_ids: Servlet:JAVADOC:651; Servlet:JAVADOC:710;
     * Servlet:JAVADOC:846; Servlet:JAVADOC:868; Servlet:JAVADOC:869.4;
     *
     * @test_Strategy: Create a Servlet AsyncTestServlet which support async;
     * Client send a request to AsyncTestServlet; StartAsync in AsyncTestServlet;
     * verifies it times out at deException timeout.
     */
    @Test
    @RunAsClient
    public void timeOutTest4() throws Exception {
      testProperties.setProperty(APITEST, "timeOutTest4");
      testProperties.setProperty(SEARCH_STRING, "in onTimeout method of ACListener2");
      testProperties.setProperty(STATUS_CODE, "-1");
      invoke();
    }

    /*
     * @testName: originalRequestTest
     *
     * @assertion_ids: Servlet:JAVADOC:638; Servlet:JAVADOC:644;
     * Servlet:JAVADOC:710; Servlet:JAVADOC:710.2;
     *
     * @test_Strategy: Create a Servlet AsyncTestServlet which support async;
     * Client send a request to AsyncTestServlet; Call ServletRequest.startAsync()
     * in AsyncTestServlet; verifies AsyncContext.hasOriginalRequestAndResponse
     * works.
     */
    @Test
    @RunAsClient
    public void originalRequestTest() throws Exception {
      testProperties.setProperty(APITEST, "originalRequestTest");
      invoke();
    }

    /*
     * @testName: originalRequestTest1
     *
     * @assertion_ids: Servlet:JAVADOC:638; Servlet:JAVADOC:644;
     * Servlet:JAVADOC:712; Servlet:JAVADOC:712.1; Servlet:JAVADOC:712.3; *
     *
     * @test_Strategy: Create a Servlet AsyncTestServlet which support async;
     * Client send a request to AsyncTestServlet; Call
     * ServletRequest.startAsync(ServletRequest, ServletResponse); verifies
     * AsyncContext.hasOriginalRequestAndResponse works.
     */
    @Test
    @RunAsClient
    public void originalRequestTest1() throws Exception {
      testProperties.setProperty(APITEST, "originalRequestTest1");
      invoke();
    }

    /*
     * @testName: originalRequestTest2
     *
     * @assertion_ids: Servlet:JAVADOC:638; Servlet:JAVADOC:644;
     * Servlet:JAVADOC:712; Servlet:JAVADOC:712.1; Servlet:JAVADOC:712.3;
     *
     * @test_Strategy: Create a Servlet AsyncTestServlet which support async;
     * Client send a request to AsyncTestServlet; Call
     * ServletRequest.startAsync(ServletRequestWrapper, ServletResponseWrapper);
     * verifies AsyncContext.hasOriginalRequestAndResponse works.
     */
    @Test
    @RunAsClient
    public void originalRequestTest2() throws Exception {
      testProperties.setProperty(APITEST, "originalRequestTest2");
      invoke();
    }

    /*
     * @testName: originalRequestTest3
     *
     * @assertion_ids: Servlet:JAVADOC:638; Servlet:JAVADOC:644;
     * Servlet:JAVADOC:712; Servlet:JAVADOC:712.1; Servlet:JAVADOC:712.3;
     *
     * @test_Strategy: Create a Servlet AsyncTestServlet which support async;
     * Client send a request to AsyncTestServlet; Call
     * ServletRequest.startAsync(ServletRequestWrapper, ServletResponse); verifies
     * AsyncContext.hasOriginalRequestAndResponse works.
     */
    @Test
    @RunAsClient
    public void originalRequestTest3() throws Exception {
      testProperties.setProperty(APITEST, "originalRequestTest3");
      invoke();
    }

    /*
     * @testName: originalRequestTest4
     *
     * @assertion_ids: Servlet:JAVADOC:638; Servlet:JAVADOC:644;
     * Servlet:JAVADOC:712; Servlet:JAVADOC:712.1; Servlet:JAVADOC:712.3;
     *
     * @test_Strategy: Create a Servlet AsyncTestServlet which support async;
     * Client send a request to AsyncTestServlet; Call
     * ServletRequest.startAsync(ServletRequest, ServletResponseWrapper); verifies
     * AsyncContext.hasOriginalRequestAndResponse works.
     */
    @Test
    @RunAsClient
    public void originalRequestTest4() throws Exception {
      testProperties.setProperty(APITEST, "originalRequestTest4");
      invoke();
    }

    /*
     * @testName: getResponseTest
     *
     * @assertion_ids: Servlet:JAVADOC:643; Servlet:JAVADOC:710;
     * Servlet:JAVADOC:710.1; Servlet:SPEC:271;
     *
     * @test_Strategy: Create a Servlet AsyncTestServlet which support async;
     * Client send a request to AsyncTestServlet; StartAsync in AsyncTestServlet
     * ServletRequest.startAsync(); call AsyncContext.getResponse() verifies it
     * works.
     */
    @Test
    @RunAsClient
    public void getResponseTest() throws Exception {
      testProperties.setProperty(APITEST, "getResponseTest");
      invoke();
    }

    /*
     * @testName: getResponseTest1
     *
     * @assertion_ids: Servlet:JAVADOC:643; Servlet:JAVADOC:712;
     * Servlet:JAVADOC:712.1; Servlet:SPEC:271;
     *
     * @test_Strategy: Create a Servlet AsyncTestServlet which support async;
     * Client send a request to AsyncTestServlet; StartAsync in AsyncTestServlet
     * ServletRequest.startAsync(Request, Response); call
     * AsyncContext.getResponse() verifies it works.
     */
    @Test
    @RunAsClient
    public void getResponseTest1() throws Exception {
      testProperties.setProperty(APITEST, "getResponseTest1");
      invoke();
    }

    /*
     * @testName: getResponseTest2
     *
     * @assertion_ids: Servlet:JAVADOC:643; Servlet:JAVADOC:712;
     * Servlet:JAVADOC:712.1; Servlet:SPEC:271;
     *
     * @test_Strategy: Create a Servlet AsyncTestServlet which support async;
     * Client send a request to AsyncTestServlet; StartAsync in AsyncTestServlet
     * ServletRequest.startAsync(RequestWrapper, ResponseWrapper); call
     * AsyncContext.getResponse() verifies it works.
     */
    @Test
    @RunAsClient
    public void getResponseTest2() throws Exception {
      testProperties.setProperty(APITEST, "getResponseTest2");
      invoke();
    }

    /*
     * @testName: getResponseTest3
     *
     * @assertion_ids: Servlet:JAVADOC:643; Servlet:JAVADOC:712;
     * Servlet:JAVADOC:712.1; Servlet:SPEC:271;
     *
     * @test_Strategy: Create a Servlet AsyncTestServlet which support async;
     * Client send a request to AsyncTestServlet; StartAsync in AsyncTestServlet
     * ServletRequest.startAsync(Request, ResponseWrapper); call
     * AsyncContext.getResponse() verifies it works.
     */
    @Test
    @RunAsClient
    public void getResponseTest3() throws Exception {
      testProperties.setProperty(APITEST, "getResponseTest3");
      invoke();
    }

    /*
     * @testName: getResponseTest4
     *
     * @assertion_ids: Servlet:JAVADOC:643; Servlet:JAVADOC:712;
     * Servlet:JAVADOC:712.1; Servlet:SPEC:271;
     *
     * @test_Strategy: Create a Servlet AsyncTestServlet which support async;
     * Client send a request to AsyncTestServlet; StartAsync in AsyncTestServlet
     * ServletRequest.startAsync(RequestWrapper, Response); call
     * AsyncContext.getResponse() verifies it works.
     */
    @Test
    @RunAsClient
    public void getResponseTest4() throws Exception {
      testProperties.setProperty(APITEST, "getResponseTest4");
      invoke();
    }

    /*
     * @testName: startAsyncAgainTest
     *
     * @assertion_ids: Servlet:JAVADOC:639; Servlet:JAVADOC:639.2;
     * Servlet:JAVADOC:639.3; Servlet:JAVADOC:639.4; Servlet:JAVADOC:639.5;
     * Servlet:JAVADOC:639.9; Servlet:JAVADOC:703; Servlet:JAVADOC:707;
     * Servlet:JAVADOC:708; Servlet:JAVADOC:710;
     *
     * @test_Strategy: Create a Servlet AsyncTestServlet which supports async;
     * Client send a request to AsyncTestServlet; StartAsync in AsyncTestServlet
     * ServletRequest.startAsync(); call ServletRequest.isAsyncSupported() call
     * ServletRequest.isAsyncStarted() call ServletRequest.getDispatcherType()
     * StartAsync again verifies all work accordingly.
     */
    @Test
    @RunAsClient
    public void startAsyncAgainTest() throws Exception {
      testProperties.setProperty(APITEST, "startAsyncAgainTest");
      testProperties.setProperty(SEARCH_STRING,
          "ASYNC_NOT_STARTED_startAsyncAgainTest|" + "IsAsyncSupported=true|"
              + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
              + "startAsync called|" + "IsAsyncSupported=true|"
              + "IsAsyncStarted=true|" + "DispatcherType=REQUEST|"
              + "startAsync called again|"
              + "Expected IllegalStateException thrown");
      invoke();
    }

    /*
     * @testName: startAsyncAgainTest1
     *
     * @assertion_ids: Servlet:JAVADOC:639; Servlet:JAVADOC:639.2;
     * Servlet:JAVADOC:639.3; Servlet:JAVADOC:639.4; Servlet:JAVADOC:639.5;
     * Servlet:JAVADOC:639.9; Servlet:JAVADOC:703; Servlet:JAVADOC:707;
     * Servlet:JAVADOC:708; Servlet:JAVADOC:712;
     *
     * @test_Strategy: Create a Servlet AsyncTestServlet which supports async;
     * Client send a request to AsyncTestServlet; StartAsync in AsyncTestServlet
     * ServletRequest.startAsync(request,response); call
     * ServletRequest.isAsyncSupported() call ServletRequest.isAsyncStarted() call
     * ServletRequest.getDispatcherType() StartAsync again verifies all work
     * accordingly.
     */
    @Test
    @RunAsClient
    public void startAsyncAgainTest1() throws Exception {
      testProperties.setProperty(APITEST, "startAsyncAgainTest1");
      testProperties.setProperty(SEARCH_STRING,
          "ASYNC_NOT_STARTED_startAsyncAgainTest1|" + "IsAsyncSupported=true|"
              + "IsAsyncStarted=false|" + "DispatcherType=REQUEST|"
              + "startAsync called|" + "IsAsyncSupported=true|"
              + "IsAsyncStarted=true|" + "DispatcherType=REQUEST|"
              + "startAsync called again|"
              + "Expected IllegalStateException thrown");
      invoke();
    }

    private void invoke() throws IOException {
        invoke(testProperties.getProperty(APITEST), testProperties.getProperty(SEARCH_STRING));
    }

    private void invoke(String testName, String searchStrings) throws IOException {
        System.out.println("Running " + testName);

        webClient.getOptions().setTimeout(0);

        TextPage page = webClient.getPage(base + "/AsyncTestServlet?testname=" + testName);
        String content = page.getContent();

        System.out.println("\nContent for `"+ base + "AsyncTestServlet?testname=" + testName + "` :\n" + content + "\n");

        assertFalse(content.contains("Test FAILED"));

        if (searchStrings != null) {
            int index = 0;
            for (String searchString : searchStrings.split(Pattern.quote("|"))) {
                index = content.indexOf(searchString, index);
                assertTrue(searchString, index != -1);
                index += searchString.length();
            }
        }
    }

}
