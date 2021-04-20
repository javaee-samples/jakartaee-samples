package org.jakartaee9.servlet.servletresponse;

import static org.jboss.shrinkwrap.api.ShrinkWrap.create;

import java.io.File;
import java.net.URL;

import org.jakartaee9.servlet.servletresponse.GetContentTypeNullTestServlet;
import org.jakartaee9.servlet.servletresponse.HttpResponseTestServlet;
import org.jakartaee9.servlet.servletresponse.RedirectedTestServlet;
import org.jakartaee9.servlet.servletresponse.ResponseTests;
import org.jakartaee9.servlet.servletresponse.ServletErrorPage;
import org.jakartaee9.servlet.servletresponse.ServletTestUtil;
import org.jakartaee9.servlet.servletresponse.SetCharacterEncodingTestServlet;
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
public class ServletResponseTest {

    @ArquillianResource
    private URL base;
    private ServletResponseClient servletResponseClient;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        try {
            return create(WebArchive.class)
                    .addClasses(
                            GetContentTypeNullTestServlet.class,
                            HttpResponseTestServlet.class,
                            RedirectedTestServlet.class,
                            ResponseTests.class,
                            ServletErrorPage.class,
                            ServletTestUtil.class,
                            SetCharacterEncodingTestServlet.class
                        )
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
        servletResponseClient = new ServletResponseClient(base, "TestServlet");
    }
    
    
    /*
     * @testName: getContentTypeTest
     * 
     * @assertion_ids: Servlet:JAVADOC:607; Servlet:SPEC:34;
     * 
     * @test_Strategy: Servlet verifies the content type
     */
    @Test
    @RunAsClient
    public void getContentTypeTest() throws Exception {
        servletResponseClient.getContentTypeTest();
    }

    /*
     * @testName: flushBufferTest
     * 
     * @assertion_ids: Servlet:JAVADOC:603
     * 
     * @test_Strategy: Servlet writes data in the buffer and flushes it
     */
    @Test
    @RunAsClient
    public void flushBufferTest() throws Exception {
        servletResponseClient.flushBufferTest();
    }

    /*
     * @testName: getBufferSizeTest
     * 
     * @assertion_ids: Servlet:JAVADOC:605
     * 
     * @test_Strategy: Servlet flushes buffer and verifies the size of the buffer
     */
    @Test
    @RunAsClient
    public void getBufferSizeTest() throws Exception {
        servletResponseClient.getBufferSizeTest();
    }

    /*
     * @testName: getLocaleDefaultTest
     * 
     * @assertion_ids: Servlet:JAVADOC:625;
     * 
     * @test_Strategy: Validate that getLocale() will return the default locale of
     * the VM that the container is running in when setLocale() is not called.
     */
    @Test
    @RunAsClient
    public void getLocaleDefaultTest() throws Exception {
        // servletResponseClient.getLocaleDefaultTest();
    }

    /*
     * @testName: getLocaleTest
     * 
     * @assertion_ids: Servlet:JAVADOC:608
     * 
     * @test_Strategy: Servlet set Locale and then verifies it
     *
     */
    @Test
    @RunAsClient
    public void getLocaleTest() throws Exception {
        servletResponseClient.getLocaleTest();
    }

    /*
     * @testName: getOutputStreamTest
     * 
     * @assertion_ids: Servlet:JAVADOC:609
     * 
     * @test_Strategy: Servlet gets an output stream and writes to it.
     */
    @Test
    @RunAsClient
    public void getOutputStreamTest() throws Exception {
        servletResponseClient.getOutputStreamTest();
    }

    /*
     * @testName: getOutputStreamIllegalStateExceptionTest
     * 
     * @assertion_ids: Servlet:JAVADOC:611
     * 
     * @test_Strategy: Servlet tries to get an stream object after calling
     * getWriter
     */
    @Test
    @RunAsClient
    public void getOutputStreamIllegalStateExceptionTest() throws Exception {
        servletResponseClient.getOutputStreamIllegalStateExceptionTest();
    }

    /*
     * @testName: getWriterTest
     * 
     * @assertion_ids: Servlet:JAVADOC:612; Servlet:JAVADOC:151.2;
     * Servlet:JAVADOC:151.3;
     * 
     * @test_Strategy: Servlet gets a Writer object and writes data; sets content
     * type by calling ServletResponse.setContentType; Verify that content type
     * gets set, not encoding
     */
    @Test
    @RunAsClient
    public void getWriterTest() throws Exception {
        servletResponseClient.getWriterTest();
    }

    /*
     * @testName: getWriterIllegalStateExceptionTest
     * 
     * @assertion_ids: Servlet:JAVADOC:615
     * 
     * @test_Strategy: Servlet tries to get a Writer object after calling
     * getOutputStream
     */
    @Test
    @RunAsClient
    public void getWriterIllegalStateExceptionTest() throws Exception {
        servletResponseClient.getWriterIllegalStateExceptionTest();
    }

    /*
     * @testName: isCommittedTest
     * 
     * @assertion_ids: Servlet:JAVADOC:616
     * 
     * @test_Strategy: Servlet checks before and after response is flushed
     *
     */
    @Test
    @RunAsClient
    public void isCommittedTest() throws Exception {
        servletResponseClient.isCommittedTest();
    }

    /*
     * @testName: resetBufferTest
     * 
     * @assertion_ids: Servlet:JAVADOC:619; Servlet:SPEC:31;
     * 
     * @test_Strategy: Servlet writes data to the response, resets the buffer and
     * then writes new data
     */
    @Test
    @RunAsClient
    public void resetBufferTest() throws Exception {
        servletResponseClient.resetBufferTest();
    }

    /*
     * @testName: resetTest
     * 
     * @assertion_ids: Servlet:JAVADOC:617; Servlet:SPEC:31;
     * 
     * @test_Strategy: Servlet writes data to the response, does a reset, then
     * writes new data
     */
    @Test
    @RunAsClient
    public void resetTest() throws Exception {
        servletResponseClient.resetTest();
    }

    /*
     * @testName: resetTest1
     * 
     * @assertion_ids: Servlet:JAVADOC:617; Servlet:SPEC:31;
     * 
     * @test_Strategy: Servlet writes data to the response, set the Headers, does
     * a reset, then writes new data, set the new Header
     */
    @Test
    @RunAsClient
    public void resetTest1() throws Exception {
        servletResponseClient.resetTest1();
    }

    /*
     * @testName: resetIllegalStateExceptionTest
     * 
     * @assertion_ids: Servlet:JAVADOC:618; Servlet:SPEC:31;
     * 
     * @test_Strategy: Servlet writes data, flushes the buffer then tries to do a
     * reset
     */
    @Test
    @RunAsClient
    public void resetIllegalStateExceptionTest() throws Exception {
        servletResponseClient.resetIllegalStateExceptionTest();
    }

    /*
     * @testName: getCharacterEncodingDefaultTest
     * 
     * @assertion_ids: Servlet:JAVADOC:606
     * 
     * @test_Strategy: Servlet checks for the default encoding
     */
    @Test
    @RunAsClient
    public void getCharacterEncodingDefaultTest() throws Exception {
        // servletResponseClient.getCharacterEncodingDefaultTest();
    }

    /*
     * @testName: getCharacterEncodingTest
     * 
     * @assertion_ids: Servlet:JAVADOC:606
     * 
     * @test_Strategy: Servlet sets encoding then checks it.
     */
    @Test
    @RunAsClient
    public void getCharacterEncodingTest() throws Exception {
        servletResponseClient.getCharacterEncodingTest();
    }

    /*
     * @testName: setCharacterEncodingTest
     * 
     * @assertion_ids: Servlet:JAVADOC:622; Servlet:JAVADOC:151.3;
     * 
     * @test_Strategy: Servlet set encoding by calling
     * ServletResponse.setCharcaterEncoding; client verifies it is set
     */
    @Test
    @RunAsClient
    public void setCharacterEncodingTest() throws Exception {
        servletResponseClient.setCharacterEncodingTest();
    }

    /*
     * @testName: setBufferSizeTest
     * 
     * @assertion_ids: Servlet:JAVADOC:620
     * 
     * @test_Strategy: Servlet sets the buffer size then verifies it was set
     */
    @Test
    @RunAsClient
    public void setBufferSizeTest() throws Exception {
        servletResponseClient.setBufferSizeTest();
    }

    /*
     * @testName: setBufferSizeIllegalStateExceptionTest
     * 
     * @assertion_ids: Servlet:JAVADOC:621
     * 
     * @test_Strategy: Servlet writes data and flushes buffer then tries to get
     * the buffer size
     */
    @Test
    @RunAsClient
    public void setBufferSizeIllegalStateExceptionTest() throws Exception {
        servletResponseClient.setBufferSizeIllegalStateExceptionTest();
    }

    /*
     * @testName: setContentLengthTest
     * 
     * @assertion_ids: Servlet:JAVADOC:623
     * 
     * @test_Strategy: Servlet sets the content length
     */
    @Test
    @RunAsClient
    public void setContentLengthTest() throws Exception {
        servletResponseClient.setContentLengthTest();
    }

    /*
     * @testName: setContentTypeTest
     * 
     * @assertion_ids: Servlet:JAVADOC:624; Servlet:JAVADOC:151.3;
     * Servlet:SPEC:34;
     * 
     * @test_Strategy: Servlet sets the content type; Verify Content-Type is set
     * in Client
     */
    @Test
    @RunAsClient
    public void setContentTypeTest() throws Exception {
        servletResponseClient.setContentTypeTest();
    }

    /*
     * @testName: setContentType1Test
     * 
     * @assertion_ids: Servlet:JAVADOC:151; Servlet:SPEC:34;
     * 
     * @test_Strategy: Servlet sets the content-type, and verifies it with
     * getContentType()
     */
    @Test
    @RunAsClient
    public void setContentType1Test() throws Exception {
        servletResponseClient.setContentType1Test();
    }

    /*
     * @testName: setContentType2Test
     * 
     * @assertion_ids: Servlet:JAVADOC:151.2; Servlet:JAVADOC:151.3;
     * Servlet:SPEC:34;
     * 
     * @test_Strategy: Servlet sets the content-type, Commit the response; Set the
     * content-type again Verifies that content-type is set the first time The
     * second setting is ignored.
     */
    @Test
    @RunAsClient
    public void setContentType2Test() throws Exception {
        servletResponseClient.setContentType2Test();
    }

    /*
     * @testName: setLocaleTest
     * 
     * @assertion_ids: Servlet:JAVADOC:625
     * 
     * @test_Strategy: Servlet sets the Locale
     */
    @Test
    @RunAsClient
    public void setLocaleTest() throws Exception {
        servletResponseClient.setLocaleTest();
    }

    // ---------------------------- END ServletResponse
    // -----------------------------

    

    
    // ------------------------ HttpServletResponse
    // ---------------------------------

    /*
     * @testName: addCookieTest
     * 
     * @assertion_ids: Servlet:JAVADOC:502
     * 
     * @test_Strategy: Servlet adds 2 cookies, client verifies them
     */
    @Test
    @RunAsClient
    public void addCookieTest() throws Exception {
        servletResponseClient.addCookieTest();
    }

    /*
     * @testName: addDateHeaderTest
     * 
     * @assertion_ids: Servlet:JAVADOC:522
     * 
     * @test_Strategy: Servlet adds a date header and client verifies it
     */
    @Test
    @RunAsClient
    public void addDateHeaderTest() throws Exception {
        servletResponseClient.addDateHeaderTest();
    }

    /*
     * @testName: addHeaderTest
     * 
     * @assertion_ids: Servlet:JAVADOC:525
     * 
     * @test_Strategy: Servlet adds 2 headers with 3 values and client verifies
     * them
     */
    @Test
    @RunAsClient
    public void addHeaderTest() throws Exception {
        servletResponseClient.addHeaderTest();
    }

    /*
     * @testName: addIntHeaderTest
     * 
     * @assertion_ids: Servlet:JAVADOC:527
     * 
     * @test_Strategy: Servlet adds 2 int headers with 3 values and client
     * verifies them
     */
    @Test
    @RunAsClient
    public void addIntHeaderTest() throws Exception {
        servletResponseClient.addIntHeaderTest();
    }

    /*
     * @testName: containsHeaderTest
     * 
     * @assertion_ids: Servlet:JAVADOC:503
     * 
     * @test_Strategy: Servlet sets a header and verifies it exists, then the
     * servlet tries to verify that a header does not exist.
     */
    @Test
    @RunAsClient
    public void containsHeaderTest() throws Exception {
        servletResponseClient.containsHeaderTest();
    }

    /*
     * @testName: sendErrorIllegalStateExceptionTest
     * 
     * @assertion_ids: Servlet:JAVADOC:514
     * 
     * @test_Strategy: Servlet adds a header, a cookie, and content, then flushes
     * the buffer. Servlet verifies exception is generated
     */
    @Test
    @RunAsClient
    public void sendErrorIllegalStateExceptionTest() throws Exception {
        servletResponseClient.sendErrorIllegalStateExceptionTest();
    }

    /*
     * @testName: sendError_StringIllegalStateExceptionTest
     * 
     * @assertion_ids: Servlet:JAVADOC:511
     * 
     * @test_Strategy: Servlet adds a header, a cookie, and content, then flushes
     * the buffer. Servlet verifies exception is generated
     */
    @Test
    @RunAsClient
    public void sendError_StringIllegalStateExceptionTest() throws Exception {
        servletResponseClient.sendError_StringIllegalStateExceptionTest();
    }

    /*
     * @testName: sendErrorClearBufferTest
     * 
     * @assertion_ids: Servlet:JAVADOC:512; Servlet:SPEC:39;
     * 
     * @test_Strategy: Servlet adds content and an error, client verifies the
     * error and that the content was cleared
     */
    @Test
    @RunAsClient
    public void sendErrorClearBufferTest() throws Exception {
        servletResponseClient.sendErrorClearBufferTest();
    }

    /*
     * @testName: sendError_StringTest
     * 
     * @assertion_ids: Servlet:JAVADOC:508
     * 
     * @test_Strategy: Servlet adds a header, a cookie and an error, client
     * verifies the error and that the header still exists
     */
    @Test
    @RunAsClient
    public void sendError_StringTest() throws Exception {
        servletResponseClient.sendError_StringTest();
    }

    /*
     * @testName: sendError_StringErrorPageTest
     * 
     * @assertion_ids: Servlet:JAVADOC:509
     * 
     * @test_Strategy: Servlet adds a header, a cookie and content and an error.
     * There also is an error page configured to catch error. Client verifies the
     * error.
     */
    @Test
    @RunAsClient
    public void sendError_StringErrorPageTest() throws Exception {
        servletResponseClient.sendError_StringErrorPageTest();
    }

    /*
     * @testName: sendRedirectWithLeadingSlashTest
     * 
     * @assertion_ids: Servlet:JAVADOC:516
     * 
     * @test_Strategy: Servlet redirects to another servlet
     */
    @Test
    @RunAsClient
    public void sendRedirectWithLeadingSlashTest() throws Exception {
        servletResponseClient.sendRedirectWithLeadingSlashTest();
    }

    /*
     * @testName: sendRedirectWithoutLeadingSlashTest
     * 
     * @assertion_ids: Servlet:JAVADOC:515
     * 
     * @test_Strategy: Servlet redirects to another servlet
     */
    @Test
    @RunAsClient
    public void sendRedirectWithoutLeadingSlashTest() throws Exception {
        servletResponseClient.sendRedirectWithoutLeadingSlashTest();
    }

    /*
     * @testName: sendRedirectIllegalStateExceptionTest
     * 
     * @assertion_ids: Servlet:JAVADOC:519
     * 
     * @test_Strategy: Servlet flushes the buffer then tries to redirect
     */
    @Test
    @RunAsClient
    public void sendRedirectIllegalStateExceptionTest() throws Exception {
        servletResponseClient.sendRedirectIllegalStateExceptionTest();
    }

    /*
     * @testName: setDateHeaderTest
     * 
     * @assertion_ids: Servlet:JAVADOC:520
     * 
     * @test_Strategy: Servlet sets a date header and client verifies it
     */
    @Test
    @RunAsClient
    public void setDateHeaderTest() throws Exception {
        servletResponseClient.setDateHeaderTest();
    }

    /*
     * @testName: setDateHeaderOverrideTest
     * 
     * @assertion_ids: Servlet:JAVADOC:521
     * 
     * @test_Strategy: Servlet sets the same date header twice and client verifies
     * it
     */
    @Test
    @RunAsClient
    public void setDateHeaderOverrideTest() throws Exception {
        servletResponseClient.setDateHeaderOverrideTest();
    }

    /*
     * @testName: setHeaderTest
     * 
     * @assertion_ids: Servlet:JAVADOC:523
     * 
     * @test_Strategy: Servlet sets a header and client verifies it
     */
    @Test
    @RunAsClient
    public void setHeaderTest() throws Exception {
        servletResponseClient.setHeaderTest();
    }

    /*
     * @testName: setHeaderOverrideTest
     * 
     * @assertion_ids: Servlet:JAVADOC:524
     * 
     * @test_Strategy: Servlet sets the same header twice and client verifies it
     */
    @Test
    @RunAsClient
    public void setHeaderOverrideTest() throws Exception {
        servletResponseClient.setHeaderOverrideTest();
    }

    /*
     * @testName: setMultiHeaderTest
     *
     * @assertion_ids: Servlet:SPEC:183; Servlet:JAVADOC:523; Servlet:JAVADOC:525;
     * Servlet:JAVADOC:524
     *
     * @test_Strategy: Servlet sets the multivalues for the same header; verify
     * that setHeader clear all with new value
     */
    @Test
    @RunAsClient
    public void setMultiHeaderTest() throws Exception {
        servletResponseClient.setMultiHeaderTest();
    }

    /*
     * @testName: setIntHeaderTest
     * 
     * @assertion_ids: Servlet:JAVADOC:526
     * 
     * @test_Strategy: Servlet sets an int header and client verifies it
     */
    @Test
    @RunAsClient
    public void setIntHeaderTest() throws Exception {
        servletResponseClient.setIntHeaderTest();
    }

    /*
     * @testName: setStatusTest
     * 
     * @assertion_ids: Servlet:JAVADOC:528
     * 
     * @test_Strategy: Servlet sets a status and client verifies it
     */
    @Test
    @RunAsClient
    public void setStatusTest() throws Exception {
        servletResponseClient.setStatusTest();
    }
    
    

}
