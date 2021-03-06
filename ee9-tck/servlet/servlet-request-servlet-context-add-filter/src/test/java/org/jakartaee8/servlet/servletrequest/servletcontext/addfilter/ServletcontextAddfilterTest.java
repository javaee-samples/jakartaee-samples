package org.jakartaee8.servlet.servletrequest.servletcontext.addfilter;

import static org.jboss.shrinkwrap.api.ShrinkWrap.create;

import java.io.File;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
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
public class ServletcontextAddfilterTest {

    @ArquillianResource
    private URL base;
    private ServletcontextAddfilterClient filterWrappedResponseClient;
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
            WebArchive webArchive = create(WebArchive.class)
                    .addClasses(
                        GenericTCKServlet.class,
                        TestServlet.class,
                        ServletTestUtil.class
                    )
                    .addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml")
                            .resolve("org.jakartaee8:servlet-context-add-filter-jar")
                            .withTransitivity().asFile())

                    .addAsWebInfResource(new File("src/main/webapp/WEB-INF/web.xml"))

                    ;

            System.out.println(webArchive.toString(true));



            Maven.resolver().loadPomFromFile("pom.xml")
            .resolve("org.jakartaee8:servlet-context-add-filter-jar")
            .withTransitivity().asSingleFile();

            JavaArchive javaArchive = ShrinkWrap.create(ZipImporter.class, "foo").importFrom(Maven.resolver().loadPomFromFile("pom.xml")
                    .resolve("org.jakartaee8:servlet-context-add-filter-jar")
                    .withTransitivity().asSingleFile()).as(JavaArchive.class);

            System.out.println(javaArchive.toString(true));

            return webArchive;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Before
    public void setup() {
        filterWrappedResponseClient = new ServletcontextAddfilterClient(base, "TestServlet");
    }

    /* Run test */
    /*
     * @testName: addFilterTest
     *
     * @assertion_ids: Servlet:JAVADOC:668.6;
     *
     * @test_Strategy:
     * Create a ServletContextInitializer, in which,
     *    Add a ServletContextListener instance using ServletContext.addListener;
     *    in the ServletContextListener
     *        call ServletContext.addFilter(String, String)
     * Verify that UnsupportedOperationException is thrown.
     */
    @Test
    @RunAsClient
    public void addFilterTest() throws Exception {
        filterWrappedResponseClient.addFilterTest();
    }

}
