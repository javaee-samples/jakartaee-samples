package org.jakartaee9.servlet.servletresponsewrapper;

import static org.jboss.shrinkwrap.api.ShrinkWrap.create;

import java.io.File;
import java.net.URL;

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
public class ServletResponseWrapperTest {

    @ArquillianResource
    private URL base;
    private ServletResponseWrapperClient servletResponseWrapperClient;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        try {
            return create(WebArchive.class)
                    .addClasses(
                        ResponseTestServlet.class,
                        SetCharacterEncodingTestServlet.class,
                        ResponseTests.class,
                        TestServlet.class,
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
        servletResponseWrapperClient = new ServletResponseWrapperClient(base, "TestServlet");
    }

    @Test
    @RunAsClient
    public void responseWrapperConstructorTest() throws Exception {
        servletResponseWrapperClient.responseWrapperConstructorTest();
    }
    
    /*
     * @testName: responseWrapperGetResponseTest
     * 
     * @assertion_ids: Servlet:JAVADOC:10
     * 
     * @test_Strategy: Servlet gets wrapped response object
     */
    @Test
    @RunAsClient
    public void responseWrapperGetResponseTest() throws Exception {
        servletResponseWrapperClient.responseWrapperGetResponseTest();
    }

    /*
     * @testName: responseWrapperSetResponseTest
     * 
     * @assertion_ids: Servlet:JAVADOC:11
     * 
     * @test_Strategy: Servlet sets wrapped response object
     */
    @Test
    @RunAsClient
    public void responseWrapperSetResponseTest() throws Exception {
        servletResponseWrapperClient.responseWrapperSetResponseTest();
    }

    /*
     * @testName: responseWrapperSetResponseIllegalArgumentExceptionTest
     * 
     * @assertion_ids: Servlet:JAVADOC:12
     * 
     * @test_Strategy: Servlet sets wrapped response object
     */
    @Test
    @RunAsClient
    public void responseWrapperSetResponseIllegalArgumentExceptionTest() throws Exception {
        servletResponseWrapperClient.responseWrapperSetResponseIllegalArgumentExceptionTest();
    }

    /*
     * @testName: flushBufferTest
     * 
     * @assertion_ids: Servlet:JAVADOC:24
     * 
     * @test_Strategy: Servlet wraps response. Servlet writes data in the buffer
     * and flushes it
     */
    @Test
    @RunAsClient
    public void flushBufferTest() throws Exception {
        servletResponseWrapperClient.flushBufferTest();
    }

    /*
     * @testName: getBufferSizeTest
     * 
     * @assertion_ids: Servlet:JAVADOC:23
     * 
     * @test_Strategy: Servlet wraps response. Servlet flushes buffer and verifies
     * the size of the buffer
     */
    @Test
    @RunAsClient
    public void getBufferSizeTest() throws Exception {
        servletResponseWrapperClient.getBufferSizeTest();
    }

    /*
     * @testName: getLocaleTest
     * 
     * @assertion_ids: Servlet:JAVADOC:30
     * 
     * @test_Strategy: Servlet wraps response. Servlet set Locale and then
     * verifies it
     *
     */
    @Test
    @RunAsClient
    public void getLocaleTest() throws Exception {
        servletResponseWrapperClient.getLocaleTest();
    }

    /*
     * @testName: getOutputStreamTest
     * 
     * @assertion_ids: Servlet:JAVADOC:15
     * 
     * @test_Strategy: Servlet wraps response. Servlet gets an output stream and
     * writes to it.
     */
    @Test
    @RunAsClient
    public void getOutputStreamTest() throws Exception {
        servletResponseWrapperClient.getOutputStreamTest();
    }

    /*
     * @testName: getWriterTest
     * 
     * @assertion_ids: Servlet:JAVADOC:17
     * 
     * @test_Strategy: Servlet wraps response. Servlet gets a Writer object, then
     * sets the content type; Verify that content type didn't get set by servlet
     */
    @Test
    @RunAsClient
    public void getWriterTest() throws Exception {
        servletResponseWrapperClient.getWriterTest();
    }

    /*
     * @testName: isCommittedTest
     * 
     * @assertion_ids: Servlet:JAVADOC:26
     * 
     * @test_Strategy: Servlet wraps response. Servlet checks before and after
     * response is flushed
     *
     */
    @Test
    @RunAsClient
    public void isCommittedTest() throws Exception {
        servletResponseWrapperClient.isCommittedTest();
    }

    /*
     * @testName: resetBufferTest
     * 
     * @assertion_ids: Servlet:JAVADOC:28
     * 
     * @test_Strategy: Servlet wraps response. Servlet writes data to the
     * response, resets the buffer and then writes new data
     */
    @Test
    @RunAsClient
    public void resetBufferTest() throws Exception {
        servletResponseWrapperClient.resetBufferTest();
    }

    /*
     * @testName: resetTest
     * 
     * @assertion_ids: Servlet:JAVADOC:27
     * 
     * @test_Strategy: Servlet wraps response. Servlet writes data to the
     * response, does a reset, then writes new data
     */
    @Test
    @RunAsClient
    public void resetTest() throws Exception {
        servletResponseWrapperClient.resetTest();
    }

    /*
     * @testName: resetTest1
     * 
     * @assertion_ids: Servlet:JAVADOC:27; Servlet:JAVADOC:162; Servlet:SPEC:31;
     * 
     * @test_Strategy: Servlet writes data to the response, set the Headers, does
     * a reset, then writes new data, set the new Header
     */
    @Test
    @RunAsClient
    public void resetTest1() throws Exception {
        servletResponseWrapperClient.resetTest1();
    }

    /*
     * @testName: getCharacterEncodingTest
     * 
     * @assertion_ids: Servlet:JAVADOC:14
     * 
     * @test_Strategy: Servlet wraps response. Servlet checks for the deException
     * encoding
     */
    @Test
    @RunAsClient
    public void getCharacterEncodingTest() throws Exception {
        servletResponseWrapperClient.getCharacterEncodingTest();
    }

    /*
     * @testName: setCharacterEncodingTest
     * 
     * @assertion_ids: Servlet:JAVADOC:13
     * 
     * @test_Strategy: Servlet wraps response. Servlet set the encoding and client
     * verifies it
     */
    @Test
    @RunAsClient
    public void setCharacterEncodingTest() throws Exception {
        servletResponseWrapperClient.setCharacterEncodingTest();
    }

    /*
     * @testName: setBufferSizeTest
     * 
     * @assertion_ids: Servlet:JAVADOC:22
     * 
     * @test_Strategy: Servlet wraps response. Servlet sets the buffer size then
     * verifies it was set
     */
    @Test
    @RunAsClient
    public void setBufferSizeTest() throws Exception {
        servletResponseWrapperClient.setBufferSizeTest();
    }

    /*
     * @testName: setContentLengthTest
     * 
     * @assertion_ids: Servlet:JAVADOC:19
     * 
     * @test_Strategy: Servlet wraps response. Servlet sets the content length
     */
    @Test
    @RunAsClient
    public void setContentLengthTest() throws Exception {
        servletResponseWrapperClient.setContentLengthTest();
    }

    /*
     * @testName: getContentTypeTest
     * 
     * @assertion_ids: Servlet:JAVADOC:21; Servlet:SPEC:34;
     * 
     * @test_Strategy: Servlet wraps response. Servlet verifies the content type
     * sent by the client
     */
    @Test
    @RunAsClient
    public void getContentTypeTest() throws Exception {
        servletResponseWrapperClient.getContentTypeTest();
    }

    /*
     * @testName: setContentTypeTest
     * 
     * @assertion_ids: Servlet:JAVADOC:20; Servlet:SPEC:34;
     * 
     * @test_Strategy: Servlet wraps response. Servlet sets the content type
     *
     */
    @Test
    @RunAsClient
    public void setContentTypeTest() throws Exception {
        servletResponseWrapperClient.setContentTypeTest();
    }

    /*
     * @testName: setLocaleTest
     * 
     * @assertion_ids: Servlet:JAVADOC:29
     * 
     * @test_Strategy: Servlet wraps response. Servlet sets the Locale
     */
    @Test
    @RunAsClient
    public void setLocaleTest() throws Exception {
        servletResponseWrapperClient.setLocaleTest();
    }

}
