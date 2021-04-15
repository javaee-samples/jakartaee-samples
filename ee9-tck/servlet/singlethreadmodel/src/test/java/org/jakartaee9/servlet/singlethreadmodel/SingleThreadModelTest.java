package org.jakartaee9.servlet.singlethreadmodel;

import static org.jboss.shrinkwrap.api.ShrinkWrap.create;

import java.io.File;
import java.net.URL;

import org.jakartaee9.servlet.singlethreadmodel.GenericTCKServlet;
import org.jakartaee9.servlet.singlethreadmodel.STMClientServlet;
import org.jakartaee9.servlet.singlethreadmodel.ServletTestUtil;
import org.jakartaee9.servlet.singlethreadmodel.SingleModelTestServlet;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Arjan Tijms
 */
@RunWith(Arquillian.class)
public class SingleThreadModelTest {

    @ArquillianResource
    private URL base;
    private SingleThreadModelClient singleThreadModelClient;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        try {
            return create(WebArchive.class)
                    .addClasses(
                        SingleModelTestServlet.class,
                        STMClientServlet.class,
                        GenericTCKServlet.class,
                        ServletTestUtil.class)
                    .addAsWebInfResource(
                        new File("src/main/webapp/WEB-INF/web.xml"))

            ;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Before
    public void setup() {
        singleThreadModelClient = new SingleThreadModelClient(base, "TestServlet");
    }

    /* Run test */

    /*
     * @testName: singleModelTest
     *
     * @assertion_ids: Servlet:SPEC:200; Servlet:SPEC:10;
     *
     * @test_Strategy: This test will use a multi-threaded client to validate that
     * SingleThreadModel servlets are handled properly. To do this, the following
     * jte property needs to be set: ServletClientThreads. This configures the
     * number of threads that the client will use.
     *
     * If a servlet implements this interface, you are guaranteed that no two
     * threads will execute concurrently in the servlet's service method.
     *
     * If the container implementation is one that doesn't pool SingleThreadModel
     * servlets, then the value can be left at the default of 2. However, if the
     * container does pool SingleThreadModel servlets, the ServletClientThreads
     * property should be set to twice the size of the instance pool. For example,
     * if the container's pool size for SingleThreadModel servlets is 10, then set
     * the ServletClientThreads property to 20.
     *
     * Also take note, that each thread will make 3 requests to the test servlet.
     * The outcome of this test for both pooled and non-pooled implementations
     * will be the same, however by configuring the threads, we can validate
     * implementations which pool these type of servlets.
     */
    @Test
    @RunAsClient
    public void singleModelTest() throws Exception {
        singleThreadModelClient.singleModelTest();
    }
    

}
