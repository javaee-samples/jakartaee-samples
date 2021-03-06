package org.jakartaee8.servlet.servletrequest.servletcontext.attributelistener;

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
public class ServletContextAttributeListenerTest {

    @ArquillianResource
    private URL base;
    private ServletContextAttributeListenerClient servletContextAttributeListenerClient;
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
                        TestServletContextAttributeListener.class,
                        TestServlet.class,
                        StaticLog.class,
                        ServletTestUtil.class
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
        servletContextAttributeListenerClient = new ServletContextAttributeListenerClient(base, "TestServlet");
    }


    /* Run test */


    /*
     * @testName: constructorTest
     *
     * @assertion_ids: Servlet:JAVADOC:112
     *
     * @test_Strategy: Servlet instanciate the constructor
     */
    @Test
    @RunAsClient
    public void constructorTest() throws Exception {
        servletContextAttributeListenerClient.constructorTest();
    }

    /*
     * @testName: addedTest
     *
     * @assertion_ids: Servlet:JAVADOC:113;Servlet:JAVADOC:114;Servlet:JAVADOC:117
     *
     * @test_Strategy:
     * Servlet adds an attribute. The listener should detect the
     * add and write a message out to a static log.
     * Servlet then reads the log and verifies the result. It also verifies the request and context that changed
     *
     */
    @Test
    @RunAsClient
    public void addedTest() throws Exception {
        servletContextAttributeListenerClient.addedTest();
    }

    /*
     * @testName: removedTest
     *
     * @assertion_ids: Servlet:JAVADOC:113;Servlet:JAVADOC:115;Servlet:JAVADOC:117
     *
     * @test_Strategy:
     * Servlet adds/removes an attribute. The listener should
     * detect the add and write a message out to a static log. Servlet then reads
     * the log and verifys the result. It also verifies the request and context
     * that changed
     */
    @Test
    @RunAsClient
    public void removedTest() throws Exception {
        servletContextAttributeListenerClient.removedTest();
    }

    /*
     * @testName: replacedTest
     *
     * @assertion_ids: Servlet:JAVADOC:113;Servlet:JAVADOC:116;Servlet:JAVADOC:117
     *
     * @test_Strategy:
     * Servlet adds/replaces an attribute. The listener should
     * detect the add and write a message out to a static log. Servlet then reads
     * the log and verifys the result. It also verifies the request and context
     * that changed
     */
    @Test
    @RunAsClient
    public void replacedTest() throws Exception {
        servletContextAttributeListenerClient.replacedTest();
    }

}
