package org.jakartaee8.servlet.servletrequest.asyncevent;

import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.regex.Pattern;

import org.jakartaee8.servlet.servletrequest.asyncevent.AsyncTestServlet;
import org.jakartaee8.servlet.servletrequest.asyncevent.GenericTCKServlet;
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
public class ServletAsyncEventTest {

    private final static String APITEST = "APITEST";
    private final static String SEARCH_STRING = "SEARCH_STRING";

    @ArquillianResource
    private URL base;

    private WebClient webClient;
    Properties testProperties = new Properties();

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return create(WebArchive.class)
                    .addClasses(
                        AsyncTestServlet.class,
                        GenericTCKServlet.class)
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
     * @testName: constructorTest1
     *
     * @assertion_ids: Servlet:JAVADOC:842;
     *
     * @test_Strategy: test the constructor AsyncEvent( AsyncContext )
     */
    @Test
    @RunAsClient
    public void constructorTest1() throws Exception {
      testProperties.setProperty(APITEST, "constructorTest1");
      invoke();
    }

    /*
     * @testName: constructorTest2
     *
     * @assertion_ids: Servlet:JAVADOC:843;
     *
     * @test_Strategy: test the constructor AsyncEvent(AsyncContext,
     * ServletRequest, ServletResponse)
     */
    @Test
    @RunAsClient
    public void constructorTest2() throws Exception {
      testProperties.setProperty(APITEST, "constructorTest2");
      invoke();
    }

    /*
     * @testName: constructorTest3
     *
     * @assertion_ids: Servlet:JAVADOC:844;
     *
     * @test_Strategy: test the constructor AsyncEvent(AsyncContext, Throwable)
     */
    @Test
    @RunAsClient
    public void constructorTest3() throws Exception {
      testProperties.setProperty(APITEST, "constructorTest3");
      invoke();
    }

    /*
     * @testName: constructorTest4
     *
     * @assertion_ids: Servlet:JAVADOC:845;
     *
     * @test_Strategy: test the constructor AsyncEvent(AsyncContext,
     * ServletRequest, ServletResponse, Throwable)
     */
    @Test
    @RunAsClient
    public void constructorTest4() throws Exception {
      testProperties.setProperty(APITEST, "constructorTest4");
      invoke();
    }

    /*
     * @testName: getSuppliedRequestTest1
     *
     * @assertion_ids: Servlet:JAVADOC:847;
     *
     * @test_Strategy: test the constructor AsyncEvent(AsyncContext,
     * ServletRequest, ServletResponse) verify AsyncEvent.getSuplliedRequest()
     * works
     */
    @Test
    @RunAsClient
    public void getSuppliedRequestTest1() throws Exception {
      testProperties.setProperty(APITEST, "getSuppliedRequestTest1");
      invoke();
    }

    /*
     * @testName: getSuppliedRequestTest2
     *
     * @assertion_ids: Servlet:JAVADOC:847;
     *
     * @test_Strategy: test the constructor AsyncEvent(AsyncContext,
     * ServletRequest, ServletResponse, Throwable) verify
     * AsyncEvent.getSuplliedRequest() works
     */
    @Test
    @RunAsClient
    public void getSuppliedRequestTest2() throws Exception {
      testProperties.setProperty(APITEST, "getSuppliedRequestTest2");
      invoke();
    }

    /*
     * @testName: getSuppliedResponseTest1
     *
     * @assertion_ids: Servlet:JAVADOC:848;
     *
     * @test_Strategy: test the constructor AsyncEvent(AsyncContext,
     * ServletRequest, ServletResponse) verify AsyncEvent.getSuplliedResponse()
     * works
     */
    @Test
    @RunAsClient
    public void getSuppliedResponseTest1() throws Exception {
      testProperties.setProperty(APITEST, "getSuppliedResponseTest1");
      invoke();
    }

    /*
     * @testName: getSuppliedResponseTest2
     *
     * @assertion_ids: Servlet:JAVADOC:848;
     *
     * @test_Strategy: test the constructor AsyncEvent(AsyncContext,
     * ServletRequest, ServletResponse, Throwable) verify
     * AsyncEvent.getSuplliedResponse() works
     */
    @Test
    @RunAsClient
    public void getSuppliedResponseTest2() throws Exception {
      testProperties.setProperty(APITEST, "getSuppliedResponseTest2");
      invoke();
    }

    /*
     * @testName: getThrowableTest
     *
     * @assertion_ids: Servlet:JAVADOC:849;
     *
     * @test_Strategy: test the constructor AsyncEvent(AsyncContext,
     * ServletRequest, ServletResponse, Throwable) verify
     * AsyncEvent.getThrowable() works
     */
    @Test
    @RunAsClient
    public void getThrowableTest() throws Exception {
      testProperties.setProperty(APITEST, "getThrowableTest");
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

        if (searchStrings != null) {
            for (String searchString : searchStrings.split(Pattern.quote("|"))) {
                assertTrue(searchString, content.contains(searchString));
            }
        }
    }

}
