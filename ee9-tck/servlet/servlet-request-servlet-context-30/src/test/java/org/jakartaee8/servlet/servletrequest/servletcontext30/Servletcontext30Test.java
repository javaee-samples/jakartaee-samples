package org.jakartaee8.servlet.servletrequest.servletcontext30;

import static org.jboss.shrinkwrap.api.ShrinkWrap.create;

import java.io.File;
import java.net.URL;

import org.jakartaee8.urlclient.ServletTestUtil;
import org.jakartaee8.urlclient.StaticLog;
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
public class Servletcontext30Test {

    @ArquillianResource
    private URL base;
    private Servletcontext30Client filterWrappedResponseClient;
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
                        BadFilter.class,
                        BadServlet.class,
                        BadListener.class,
                        CreateFilter.class,
                        CreateServlet.class,
                        AddFilterClass.class,
                        AddServletClass.class,
                        AddFilterString.class,
                        AddServletString.class,
                        CreateSCListener.class,
                        CreateSRListener.class,
                        AddFilterNotFound.class,
                        AddSCListenerClass.class,
                        AddServletNotFound.class,
                        AddSRListenerClass.class,
                        AddSCListenerString.class,
                        AddSRListenerString.class,
                        DuplicateFilterClass.class,
                        DuplicateFilterString.class,
                        DuplicateServletClass.class,
                        DuplicateServletString.class,
                        CreateSCAttributeListener.class,
                        CreateSRAttributeListener.class,
                        AddSCAttributeListenerClass.class,
                        AddSRAttributeListenerClass.class,
                        AddSRAttributeListenerString.class,
                        AddSCAttributeListenerString.class,
                        FilterTestServlet.class,
                        GenericTCKServlet.class,
                        ServletTestUtil.class,
                        TestListener.class,
                        TestServlet.class,
                        StaticLog.class
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
        filterWrappedResponseClient = new Servletcontext30Client(base, "TestServlet");
    }


    /*
     * @class.setup_props: webServerHost; webServerPort; ts_home;
     */
    /* Run test */
    /*
     * Test setup; In Deployment Descriptor, a Servlet TestServlet and Listener
     * TestListener; TestListener extends ServletContextListener;
     *
     * In TestListener, three Servlets are added using the following methods: -
     * ServletContext.addServlet(String, String); -
     * ServletContext.addServlet(String, Class); -
     * ServletContext.createServlet(Class);
     *
     * In TestListener, three Filters are added using the following methods: -
     * ServletContext.addFilter(String, String); -
     * ServletContext.addFilter(String, Class); -
     * ServletContext.createServlet(Class);
     *
     * In TestListener, three Listeners are added using the following methods: -
     * ServletContext.addListener(Listener Class) -
     * ServletContext.addListener(Listener name) - EventListener listener =
     * ServletContext.createListener(class); ServletContext.addListener(listener);
     * for all three following Listeners: - ServletContextAttributeListener -
     * ServletRequestListener - ServletRequestAttributesListener
     */
    /*
     * @testName: getContextPathTest
     *
     * @assertion_ids: Servlet:JAVADOC:124; Servlet:JAVADOC:258;
     * Servlet:JAVADOC:637; Servlet:JAVADOC:671.1; Servlet:JAVADOC:671.2;
     * Servlet:JAVADOC:671.3; Servlet:JAVADOC:672.1; Servlet:JAVADOC:672.2;
     * Servlet:JAVADOC:672.3; Servlet:JAVADOC:673.1; Servlet:JAVADOC:673.2;
     * Servlet:JAVADOC:673.3; Servlet:JAVADOC:679;
     *
     * @test_Strategy: In TestServlet verify that the result from the
     * ServletContext.getServletContextPath call returns the context path.
     *
     * In client verify that all Listeners are added correctly and invoked in the
     * order added.
     */
    @Test
    @RunAsClient
    public void getContextPathTest() throws Exception {
        filterWrappedResponseClient.getContextPathTest();
    }

    /*
     * @testName: testAddServletString
     *
     * @assertion_ids: Servlet:JAVADOC:655; Servlet:JAVADOC:668;
     * Servlet:JAVADOC:671.1; Servlet:JAVADOC:671.2; Servlet:JAVADOC:671.3;
     * Servlet:JAVADOC:672.1; Servlet:JAVADOC:672.2; Servlet:JAVADOC:672.3;
     * Servlet:JAVADOC:673.1; Servlet:JAVADOC:673.2; Servlet:JAVADOC:673.3;
     * Servlet:JAVADOC:674; Servlet:JAVADOC:679; Servlet:JAVADOC:696;
     *
     * @test_Strategy: Create a ServletContextListener, in which, 1. create a
     * Servlet by calling ServletContext.addServlet(String, String), 2. mapping
     * the new Servlet programmatically. 3. create a Filter by
     * ServletContext.addFilter(String, String) 4. map the filter to the new
     * Servlet programmatically for FORWARD only 5. client send a request to the
     * new servlet, Verify in client that request goes through and Filter is NOT
     * invoked. Verify in client that all Listeners are added correctly and
     * invoked in the order added.
     */
    @Test
    @RunAsClient
    public void testAddServletString() throws Exception {
        filterWrappedResponseClient.testAddServletString();
    }

    /*
     * @testName: testAddFilterString
     *
     * @assertion_ids: Servlet:JAVADOC:655; Servlet:JAVADOC:668;
     * Servlet:JAVADOC:671.1; Servlet:JAVADOC:671.2; Servlet:JAVADOC:671.3;
     * Servlet:JAVADOC:672.1; Servlet:JAVADOC:672.2; Servlet:JAVADOC:672.3;
     * Servlet:JAVADOC:673.1; Servlet:JAVADOC:673.2; Servlet:JAVADOC:673.3;
     * Servlet:JAVADOC:674; Servlet:JAVADOC:679; Servlet:JAVADOC:696;
     *
     * @test_Strategy: Create a ServletContextListener, in which, 1. create a
     * Servlet by calling ServletContext.addServlet(String, String), 2. mapping
     * the new Servlet programmatically. 3. create a Filter by
     * ServletContext.addFilter(String, String) 4. map the filter to the new
     * Servlet programmatically for FORWARD only 5. client send a request to
     * another servlet, 6. which then forward to the newly created Servlet Verify
     * in client that request goes through and Filter IS invoked. Verify in client
     * that all Listeners are added correctly and invoked in the order added.
     */
    @Test
    @RunAsClient
    public void testAddFilterString() throws Exception {
        filterWrappedResponseClient.testAddFilterString();
    }

    /*
     * @testName: testAddServletClass
     *
     * @assertion_ids: Servlet:JAVADOC:655; Servlet:JAVADOC:670;
     * Servlet:JAVADOC:671.1; Servlet:JAVADOC:671.2; Servlet:JAVADOC:671.3;
     * Servlet:JAVADOC:672.1; Servlet:JAVADOC:672.2; Servlet:JAVADOC:672.3;
     * Servlet:JAVADOC:676; Servlet:JAVADOC:679; Servlet:JAVADOC:696;
     *
     * @test_Strategy: Create a ServletContextListener, in which, 1. create a
     * Servlet by calling ServletContext.addServlet(String, Class), 2. mapping the
     * new Servlet programmatically. 3. create a Filter by
     * ServletContext.addFilter(String, Class) 4. map the filter to the new
     * Servlet programmatically for REQUEST only 5. client send a request to the
     * new servlet, Verify in client that request goes through and Filter is
     * invoked. Verify in client that all Listeners are added correctly and
     * invoked in the order added.
     */
    @Test
    @RunAsClient
    public void testAddServletClass() throws Exception {
        filterWrappedResponseClient.testAddServletClass();
    }

    /*
     * @testName: testAddFilterClass
     *
     * @assertion_ids: Servlet:JAVADOC:655; Servlet:JAVADOC:670;
     * Servlet:JAVADOC:671.1; Servlet:JAVADOC:671.2; Servlet:JAVADOC:671.3;
     * Servlet:JAVADOC:672.1; Servlet:JAVADOC:672.2; Servlet:JAVADOC:672.3;
     * Servlet:JAVADOC:676; Servlet:JAVADOC:679; Servlet:JAVADOC:696;
     *
     * @test_Strategy: Create a ServletContextListener, in which, 1. create a
     * Servlet by calling ServletContext.addServlet(String, Class), 2. mapping the
     * new Servlet programmatically. 3. create a Filter by
     * ServletContext.addFilter(String, Class) 4. map the filter to the new
     * Servlet programmatically for REQUEST only 5. client send a request to
     * another servlet, 6. which then dispatch by include to the newly created
     * Servlet Verify in client that request goes through and Filter IS NOT
     * invoked. Verify in client that all Listeners are added correctly and
     * invoked in the order added.
     */
    @Test
    @RunAsClient
    public void testAddFilterClass() throws Exception {
        filterWrappedResponseClient.testAddFilterClass();
    }

    /*
     * @testName: testAddServlet
     *
     * @assertion_ids: Servlet:JAVADOC:655; Servlet:JAVADOC:669;
     * Servlet:JAVADOC:670; Servlet:JAVADOC:671.1; Servlet:JAVADOC:671.2;
     * Servlet:JAVADOC:671.3; Servlet:JAVADOC:672.1; Servlet:JAVADOC:672.2;
     * Servlet:JAVADOC:672.3; Servlet:JAVADOC:673.1; Servlet:JAVADOC:673.2;
     * Servlet:JAVADOC:673.3; Servlet:JAVADOC:675; Servlet:JAVADOC:677;
     * Servlet:JAVADOC:679; Servlet:JAVADOC:681; Servlet:JAVADOC:696;
     *
     * @test_Strategy: Create a ServletContextListener, in which, 1. Create a
     * Servlet instance using ServletContext.createServlet; 2. Add the Servlet
     * instance: ServletContext.addServlet(String, Servlet), 3. mapping the new
     * Servlet programmatically. 4. create a Filter instance by
     * ServletContext.createFilter; 5 Add the Filter instance:
     * ServletContext.addFilter(String, Filter) 6. map the filter to the new
     * Servlet programmatically for REQUEST only 7. client send a request to the
     * new servlet, Verify in client that request goes through and Filter is
     * invoked. Verify in client that all Listeners are added correctly and
     * invoked in the order added.
     */
    @Test
    @RunAsClient
    public void testAddServlet() throws Exception {
        filterWrappedResponseClient.testAddServlet();
    }

    /*
     * @testName: testAddFilterForward
     *
     * @assertion_ids: Servlet:JAVADOC:655; Servlet:JAVADOC:669;
     * Servlet:JAVADOC:670; Servlet:JAVADOC:671.1; Servlet:JAVADOC:671.2;
     * Servlet:JAVADOC:671.3; Servlet:JAVADOC:672.1; Servlet:JAVADOC:672.2;
     * Servlet:JAVADOC:672.3; Servlet:JAVADOC:673.1; Servlet:JAVADOC:673.2;
     * Servlet:JAVADOC:673.3; Servlet:JAVADOC:675; Servlet:JAVADOC:677;
     * Servlet:JAVADOC:679; Servlet:JAVADOC:681; Servlet:JAVADOC:696;
     *
     * @test_Strategy: Create a ServletContextListener, in which, 1. Create a
     * Servlet instance using ServletContext.createServlet; 2. Add the Servlet
     * instance: ServletContext.addServlet(String, Servlet), 3. mapping the new
     * Servlet programmatically. 4. create a Filter instance by
     * ServletContext.createFilter; 5 Add the Filter instance:
     * ServletContext.addFilter(String, Filter) 6. map the filter to the new
     * Servlet programmatically for REQUEST only 7. client send a request to the
     * new servlet via FORWARD, Verify in client that request does go through and
     * Filter is NOT invoked. Verify in client that all Listeners are added
     * correctly and invoked in the order added.
     */
    @Test
    @RunAsClient
    public void testAddFilterForward() throws Exception {
        filterWrappedResponseClient.testAddFilterForward();
    }

    /*
     * @testName: testAddFilterInclude
     *
     * @assertion_ids: Servlet:JAVADOC:655; Servlet:JAVADOC:669;
     * Servlet:JAVADOC:670; Servlet:JAVADOC:671.1; Servlet:JAVADOC:671.2;
     * Servlet:JAVADOC:671.3; Servlet:JAVADOC:672.1; Servlet:JAVADOC:672.2;
     * Servlet:JAVADOC:672.3; Servlet:JAVADOC:673.1; Servlet:JAVADOC:673.2;
     * Servlet:JAVADOC:673.3; Servlet:JAVADOC:675; Servlet:JAVADOC:677;
     * Servlet:JAVADOC:679; Servlet:JAVADOC:681; Servlet:JAVADOC:696;
     *
     * @test_Strategy: Create a ServletContextListener, in which, 1. Create a
     * Servlet instance using ServletContext.createServlet; 2. Add the Servlet
     * instance: ServletContext.addServlet(String, Servlet), 3. mapping the new
     * Servlet programmatically. 4. create a Filter instance by
     * ServletContext.createFilter; 5 Add the Filter instance:
     * ServletContext.addFilter(String, Filter) 6. map the filter to the new
     * Servlet programmatically for REQUEST only 7. client send a request to the
     * new servlet via INCLUDE, Verify in client that request does go through and
     * Filter is NOT invoked. Verify in client that all Listeners are added
     * correctly and invoked in the order added.
     */
    @Test
    @RunAsClient
    public void testAddFilterInclude() throws Exception {
        filterWrappedResponseClient.testAddFilterInclude();
    }

    /*
     * @testName: testAddServletNotFound
     *
     * @assertion_ids: Servlet:JAVADOC:655; Servlet:JAVADOC:670;
     * Servlet:JAVADOC:671.1; Servlet:JAVADOC:671.2; Servlet:JAVADOC:671.3;
     * Servlet:JAVADOC:672.1; Servlet:JAVADOC:672.2; Servlet:JAVADOC:672.3;
     * Servlet:JAVADOC:673.1; Servlet:JAVADOC:673.2; Servlet:JAVADOC:673.3;
     * Servlet:JAVADOC:676; Servlet:JAVADOC:679; Servlet:JAVADOC:696;
     *
     * @test_Strategy: Create a ServletContextListener, in which, 1. create a
     * Servlet by calling ServletContext.addServlet(String, Class), 2. mapping the
     * new Servlet programmatically to multiple URLs, one of them is used by
     * another Servlet already. 3. create a Filter by
     * ServletContext.addFilter(String, Class) 4. map the filter to the new
     * Servlet programmatically for all DispatcherType 5. client send a request to
     * the new servlet, Verify in client that request does NOT go through and
     * Filter is NOT invoked.
     */
    @Test
    @RunAsClient
    public void testAddServletNotFound() throws Exception {
        filterWrappedResponseClient.testAddServletNotFound();
    }

    /*
     * @testName: testCreateSRAListener
     *
     * @assertion_ids: Servlet:JAVADOC:655; Servlet:JAVADOC:669;
     * Servlet:JAVADOC:670; Servlet:JAVADOC:671.1; Servlet:JAVADOC:671.2;
     * Servlet:JAVADOC:671.3; Servlet:JAVADOC:672.1; Servlet:JAVADOC:672.2;
     * Servlet:JAVADOC:672.3; Servlet:JAVADOC:673.1; Servlet:JAVADOC:673.2;
     * Servlet:JAVADOC:673.3; Servlet:JAVADOC:675; Servlet:JAVADOC:677;
     * Servlet:JAVADOC:679; Servlet:JAVADOC:681; Servlet:JAVADOC:696;
     *
     * @test_Strategy: Create a ServletContextListener, in which, one
     * ServletContextAttributeListener, one ServletRequestListener one
     * ServletRequestAttributeListener are added: 1. Create a Servlet instance
     * using ServletContext.createServlet; 2. Add the Servlet instance:
     * ServletContext.addServlet(String, Servlet), 3. mapping the new Servlet
     * programmatically. 4. create a Filter instance by
     * ServletContext.createFilter; 5 Add the Filter instance:
     * ServletContext.addFilter(String, Filter) 6. map the filter to the new
     * Servlet programmatically for REQUEST only 7. client send a request to
     * another servlet, in which, ServletRequestAttributes are added, then
     * dispatch to the new servlet via FORWARD Verify in client that - create
     * Listener works - request does NOT through and Filter is NOT invoked. - all
     * Listeners are added correctly and invoked in the order added.
     */
    @Test
    @RunAsClient
    public void testCreateSRAListener() throws Exception {
        filterWrappedResponseClient.testCreateSRAListener();
    }

    /*
     * @testName: negativeCreateTests
     *
     * @assertion_ids: Servlet:JAVADOC:243; Servlet:JAVADOC:678;
     * Servlet:JAVADOC:680; Servlet:JAVADOC:682; Servlet:JAVADOC:694;
     * Servlet:JAVADOC:671.10; Servlet:JAVADOC:672.10; Servlet:JAVADOC:673.10;
     *
     * @test_Strategy:
     * 1. Create a Servlet which throws ServletException at init;
     * 2. Create a Filter which throws ServletException at init
     * 3. Create a EventListener which throws NullPointerException at init
     * 4. Create a ServletContextListener
     *
     * Create another ServletContextListener, in which:
     *    5. Call ServletContext.createServlet(Servlet) which fails (SERVLET_TEST)
     *    6. Call ServletContext.createFilter(Filter) which should fail; (FILTER_TEST)
     *    7. Call ServletContext.createListener(EventListener) which fails (LISTENER_TEST)
     *    8. Call ServletContext.addListener(ServletContextListener.class) which fails (SCC_LISTENER_TEST)
     *    9. Call ServletContext.addListener(ServletContextListenerClassName) which fails (SCS_LISTENER_TEST)
     *    10. Call ServletContext.createListener(ServletContextListener.class) which fails (CSC_LISTENER_TEST)
     *    11. Call ServletContext.setInitParameter to pass information about status on 4-6
     *
     * 12. In another Servlet, get all information stored in ServletContext InitParameter
     *
     * 13. Send a request to the new Servlet Verify that
     *    - createServlet failed accordingly;
     *    - createFilter failed accordingly;
     *    - createListener failed accordingly;
     *    - addListener failed accordingly;
     *    - setInitParameter works properly
     */
    @Test
    @RunAsClient
    public void negativeCreateTests() throws Exception {
        filterWrappedResponseClient.negativeCreateTests();
    }

    /*
     * @testName: duplicateServletTest1
     *
     * @assertion_ids: Servlet:JAVADOC:675.5; Servlet:JAVADOC:676.5;
     *
     * @test_Strategy: 1. Create a Servlet Servlet, define it in web.xml; 2.
     * Create a ServletContextListener, in which: 3. try to add the Servlet again
     * by calling ServletContext.addServlet(servletName, Servlet.class) 6. Verify
     * that the Servlet can be invoked as defined in web.xml1.
     */
    @Test
    @RunAsClient
    public void duplicateServletTest1() throws Exception {
        filterWrappedResponseClient.duplicateServletTest1();
    }

    /*
     * @testName: duplicateServletTest2
     *
     * @assertion_ids: Servlet:JAVADOC:674.5;
     *
     * @test_Strategy: 1. Create a Servlet Servlet, define it in web.xml; 2.
     * Create a ServletContextListener, in which: 3. try to add the Servlet again
     * by calling ServletContext.addServlet(servletName, "Servlet.class") 4.
     * Verify that the Servlet can be invoked as defined in web.xm1.
     */
    @Test
    @RunAsClient
    public void duplicateServletTest2() throws Exception {
        filterWrappedResponseClient.duplicateServletTest2();
    }

    /*
     * @testName: duplicateServletTest3
     *
     * @assertion_ids: Servlet:JAVADOC:674.5; Servlet:JAVADOC:675.5;
     * Servlet:JAVADOC:676.5;
     *
     * @test_Strategy: 1. Create a Servlet Servlet1, define it in web.xml; 2.
     * Create a Servlet Servlet2, define it in web.xml; 3. Create a
     * ServletContextListener, in which: 4. try to add the Servlet1 again by
     * calling ServletContext.addServlet(servletName, Servlet.class) 4. try to add
     * the Servlet2 again by calling ServletContext.addServlet(servletName,
     * "Servlet.class") 4. Verify null is returned in both cases.
     */
    @Test
    @RunAsClient
    public void duplicateServletTest3() throws Exception {
        filterWrappedResponseClient.duplicateServletTest3();
    }

    /*
     * @testName: duplicateFilterTest
     *
     * @assertion_ids: Servlet:JAVADOC:668.4; Servlet:JAVADOC:669.4;
     *
     * @test_Strategy: 1. Create a Filter Filter1, define it in web.xml; 2. Create
     * a Filter Filter2, define it in web.xml; 3. Create a ServletContextListener,
     * in which: 4. try to add Filter1 again by calling
     * ServletContext.addFilter(FilterName, Filter.class) 5. try to add Filter2
     * again by calling ServletContext.addFilter(FilterName,,"Filter.class") 6.
     * Verify that both Filter can be invoked as defined in web.xml
     */
    @Test
    @RunAsClient
    public void duplicateFilterTest() throws Exception {
        filterWrappedResponseClient.duplicateFilterTest();
    }

    /*
     * @testName: duplicateFilterTest1
     *
     * @assertion_ids: Servlet:JAVADOC:668.4; Servlet:JAVADOC:669.4;
     *
     * @test_Strategy:
     * 1. Create a Filter Filter1, define it in web.xml;
     * 2. Create a Filter Filter2, define it in web.xml;
     * 3. Create a ServletContextListener, in which:
     *    4. try to add Filter1 again by calling ServletContext.addFilter(FilterName, Filter.class)
     *    5. try to add Filter2 again by calling ServletContext.addFilter(FilterName,,"Filter.class")
     * 6. Verify that null is returned in both cases
     */
    @Test
    @RunAsClient
    public void duplicateFilterTest1() throws Exception {
        filterWrappedResponseClient.duplicateFilterTest1();
    }

    /*
     * @testName: getEffectiveMajorVersionTest
     *
     * @assertion_ids: Servlet:JAVADOC:685;
     *
     * @test_Strategy: Create a Servlet, in which call
     * ServletContext.getEffectiveMajorVersion() Verify that 3 is returned.
     */
    @Test
    @RunAsClient
    public void getEffectiveMajorVersionTest() throws Exception {
        filterWrappedResponseClient.getEffectiveMajorVersionTest();
    }

    /*
     * @testName: getEffectiveMinorVersionTest
     *
     * @assertion_ids: Servlet:JAVADOC:686;
     *
     * @test_Strategy: Create a Servlet, in which call
     * ServletContext.getEffectiveMinorVersion() Verify that 0 is returned.
     */
    @Test
    @RunAsClient
    public void getEffectiveMinorVersionTest() throws Exception {
        filterWrappedResponseClient.getEffectiveMinorVersionTest();
    }

    /*
     * @testName: getDefaultSessionTrackingModes
     *
     * @assertion_ids: Servlet:JAVADOC:684;
     *
     * @test_Strategy: Create a Servlet, in which call
     * ServletContext.getDeExceptionSessionTrackingModes() Verify it works.
     */
    @Test
    @RunAsClient
    public void getDefaultSessionTrackingModes() throws Exception {
        filterWrappedResponseClient.getDefaultSessionTrackingModes();
    }

    /*
     * @testName: sessionTrackingModesValueOfTest
     *
     * @assertion_ids: Servlet:JAVADOC:747;
     *
     * @test_Strategy: Create a Servlet, verify SessionTrackingModes.valueOf()
     * works
     */
    @Test
    @RunAsClient
    public void sessionTrackingModesValueOfTest() throws Exception {
        filterWrappedResponseClient.sessionTrackingModesValueOfTest();
    }

    /*
     * @testName: sessionTrackingModesValuesTest
     *
     * @assertion_ids: Servlet:JAVADOC:748;
     *
     * @test_Strategy: Create a Servlet, verify SessionTrackingModes.values()
     * works
     */
    @Test
    @RunAsClient
    public void sessionTrackingModesValuesTest() throws Exception {
        filterWrappedResponseClient.sessionTrackingModesValuesTest();
    }

}
