package org.jakartaee8.servlet.servletrequest.genericfilter;

import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

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
public class GenericFilterTest {

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
                        GetFilterName_Filter.class,
                        GetFilterNameTestServlet.class,
                        GetInitParam_Filter.class,
                        GetInitParamNames_Filter.class,
                        GetInitParamNamesNull_Filter.class,
                        GetInitParamNamesTestServlet.class,
                        GetInitParamNull_Filter.class,
                        GetInitParamNamesNullTestServlet.class,
                        GetInitParamNullTestServlet.class,
                        GetInitParamTestServlet.class,
                        GetServletContext_Filter.class,
                        GetServletContextTestServlet.class,
                        InitFilter_Filter.class,
                        InitFilterConfig_Filter.class,
                        InitFilterConfigTestServlet.class,
                        InitFilterTestServlet.class,
                        ServletTestUtil.class
                      )
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
     * @testName: initFilterTest
     *
     * @assertion_ids: NA;
     *
     * @test_Strategy: Client attempts to access a servlet and the filter
     * configured for that servlet should be invoked.
     */
    @Test
    @RunAsClient
    public void initFilterTest() throws Exception {
      testProperties.setProperty(APITEST, "InitFilterTest");
      invoke();
    }

    /*
     * @testName: initFilterConfigTest
     *
     * @assertion_ids: NA;
     *
     * @test_Strategy: Client attempts to access a servlet and the filter
     * configured for that servlet should be invoked.
     */

    public void initFilterConfigTest() throws Exception {
      testProperties.setProperty(APITEST, "InitFilterConfigTest");
      invoke();
    }

    /*
     * @testName: GetFilterNameTest
     *
     * @assertion_ids: NA;
     *
     * @test_Strategy: Client attempts to access a servlet and the filter
     * configured for that servlet should be invoked.
     */
    @Test
    @RunAsClient
    public void GetFilterNameTest() throws Exception {
      String testName = "GetFilterNameTest";
      testProperties.setProperty(APITEST, testName);
      invoke();
    }

    /*
     * @testName: GetInitParamTest
     *
     * @assertion_ids: NA;
     *
     * @test_Strategy: Client attempts to access a servlet and the filter
     * configured for that servlet should be invoked.
     */
    @Test
    @RunAsClient
    public void GetInitParamTest() throws Exception {
      String testName = "GetInitParamTest";
      testProperties.setProperty(APITEST, testName);
      invoke();
    }

    /*
     * @testName: GetInitParamNamesTest
     *
     * @assertion_ids: NA;
     *
     * @test_Strategy: Client attempts to access a servlet and the filter
     * configured for that servlet should be invoked.
     */
    @Test
    @RunAsClient
    public void GetInitParamNamesTest() throws Exception {
      String testName = "GetInitParamNamesTest";
      testProperties.setProperty(APITEST, testName);
      invoke();
    }

    /*
     * @testName: GetInitParamNamesNullTest
     *
     * @assertion_ids: NA;
     *
     * @test_Strategy: Client attempts to access a servlet and the filter
     * configured for that servlet should be invoked.
     */
    @Test
    @RunAsClient
    public void GetInitParamNamesNullTest() throws Exception {
      String testName = "GetInitParamNamesNullTest";
      testProperties.setProperty(APITEST, testName);
      invoke();
    }

    /*
     * @testName: GetInitParamNullTest
     *
     * @assertion_ids: NA;
     *
     * @test_Strategy: Client attempts to access a servlet and the filter
     * configured for that servlet should be invoked.
     */
    @Test
    @RunAsClient
    public void GetInitParamNullTest() throws Exception {
      String testName = "GetInitParamNullTest";
      testProperties.setProperty(APITEST, testName);
      invoke();
    }

    /*
     * @testName: GetServletContextTest
     *
     * @assertion_ids: NA;
     *
     * @test_Strategy: Client attempts to access a servlet and the filter
     * configured for that servlet should be invoked.
     */
    @Test
    @RunAsClient
    public void GetServletContextTest() throws Exception {
      String testName = "GetServletContextTest";
      testProperties.setProperty(APITEST, testName);
      invoke();
    }



    private void invoke() throws IOException {
        invoke(testProperties.getProperty(APITEST), testProperties.getProperty(SEARCH_STRING));
    }

    private void invoke(String testName, String searchStrings) throws IOException {
        System.out.println("Running " + testName);

        webClient.getOptions().setTimeout(0);

        TextPage page = webClient.getPage(base + "/" + testName);
        String content = page.getContent();

        System.out.println("\nContent for `"+ base + "/" + testName + "` :\n" + content + "\n");

        assertTrue("Test PASSED", content.contains("Test PASSED"));
    }

}
