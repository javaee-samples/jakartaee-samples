package org.jakartaee8.servlet.servletrequest.servletrequest;

import static org.jboss.shrinkwrap.api.ShrinkWrap.create;

import java.io.File;
import java.net.URL;
import java.util.Properties;

import org.jakartaee8.servlet.common.client.RequestClient;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Arjan Tijms
 */
@RunWith(Arquillian.class)
public class ServletRequestTest {

    @ArquillianResource
    private URL base;

    private RequestClient requestClient;
    Properties testProperties = new Properties();

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        JavaArchive[] archiveWithServlet =
                Maven.resolver()
                     .loadPomFromFile("pom.xml")
                     .resolve("org.jakartaee8:servlet-common-server")
                     .withTransitivity()
                     .as(JavaArchive.class);

        return create(WebArchive.class)
                    .addClasses(
                        GenericTCKServlet.class,
                        GetParameterNamesEmptyEnumTestServlet.class,
                        GetReaderUnsupportedEncodingExceptionTestServlet.class,
                        SetCharacterEncodingTest.class,
                        SetCharacterEncodingUnsupportedEncodingExceptionTest.class)
                    .addAsWebInfResource(new File("src/main/webapp/WEB-INF/web.xml"))
                    .addAsLibraries(archiveWithServlet);
    }

    @Before
    public void setup() {
        requestClient = new RequestClient(base, "TestServlet");
    }

    @After
    public void teardown() {
        testProperties.clear();
    }


    /* Run test */

    /*
     * @testName: getAttributeNamesTest
     *
     * @assertion_ids: Servlet:JAVADOC:174
     *
     * @test_Strategy: Servlet sets some attributes and verifies they can be
     * retrieved.
     *
     */
    @Test
    @RunAsClient
    public void getAttributeNamesTest() throws Exception {
        requestClient.getAttributeNamesTest();
    }

    /*
     * @testName: getAttributeNamesEmptyEnumTest
     *
     * @assertion_ids: Servlet:JAVADOC:175
     *
     * @test_Strategy: No attributes exist in request.
     */
    @Test
    @RunAsClient
    public void getAttributeNamesEmptyEnumTest() throws Exception {
        requestClient.getAttributeNamesEmptyEnumTest();
    }


    /*
     * @testName: getAttributeTest
     *
     * @assertion_ids: Servlet:JAVADOC:172
     *
     * @test_Strategy: Servlet sets an attribute and retrieves it.
     */
    @Test
    @RunAsClient
    public void getAttributeTest() throws Exception {
        requestClient.getAttributeTest();
    }

    /*
     * @testName: getAttributeDoesNotExistTest
     *
     * @assertion_ids: Servlet:JAVADOC:173
     *
     * @test_Strategy: Servlet tries to retrieve a non-existant attribute.
     *
     */
    @Test
    @RunAsClient
    public void getAttributeDoesNotExistTest() throws Exception {
        requestClient.getAttributeDoesNotExistTest();
    }


    /*
     * @testName: getCharacterEncodingTest
     *
     * @assertion_ids: Servlet:JAVADOC:176
     *
     * @test_Strategy: Client sets an encoding and Servlet tries to retrieve it.
     */
    @Test
    @RunAsClient
    public void getCharacterEncodingTest() throws Exception {
        requestClient.getCharacterEncodingTest();
    }

    /*
     * @testName: getCharacterEncodingNullTest
     *
     * @assertion_ids: Servlet:JAVADOC:177
     *
     * @test_Strategy: Client does not set an encoding and Servlet tries to
     * retrieve it.
     *
     */
    @Test
    @RunAsClient
    public void getCharacterEncodingNullTest() throws Exception {
        requestClient.getCharacterEncodingNullTest();
    }


    /*
     * @testName: getContentLengthTest
     *
     * @assertion_ids: Servlet:JAVADOC:180
     *
     * @test_Strategy: Servlet compares this length to the actual length of the
     * content body read in using getInputStream
     *
     */
    @Test
    @RunAsClient
    public void getContentLengthTest() throws Exception {
        requestClient.getContentLengthTest();
    }

    /*
     * @testName: getContentTypeTest
     *
     * @assertion_ids: Servlet:JAVADOC:182; Servlet:SPEC:34;
     *
     * @test_Strategy: Client sets the content type and servlet reads it.
     */
    @Test
    @RunAsClient
    public void getContentTypeTest() throws Exception {
        requestClient.getContentTypeTest();
    }


    /*
     * @testName: getContentTypeNullTest
     *
     * @assertion_ids: Servlet:JAVADOC:183; Servlet:SPEC:34;
     *
     * @test_Strategy: Servlet tries to read content type.
     */
    @Test
    @RunAsClient
    public void getContentTypeNullTest() throws Exception {
        requestClient.getContentTypeNullTest();
    }

    /*
     * @testName: getInputStreamTest
     *
     * @assertion_ids: Servlet:JAVADOC:184
     *
     * @test_Strategy: Servlet tries to read the input stream.
     */
    @Test
    @RunAsClient
    public void getInputStreamTest() throws Exception {
        requestClient.getInputStreamTest();
    }


    /*
     * @testName: getInputStreamIllegalStateExceptionTest
     *
     * @assertion_ids: Servlet:JAVADOC:186
     *
     * @test_Strategy: Servlet gets a Reader object using
     * ServletRequest.getReader() then tries to get the inputStream Object
     *
     */
    @Test
    @RunAsClient
    public void getInputStreamIllegalStateExceptionTest() throws Exception {
        requestClient.getInputStreamIllegalStateExceptionTest();
    }

    /*
     * @testName: getLocaleTest
     *
     * @assertion_ids: Servlet:JAVADOC:206
     *
     * @test_Strategy: Client specifics a locale and the servlet verifies it.
     */
    @Test
    @RunAsClient
    public void getLocaleTest() throws Exception {
        requestClient.getLocalesTest();
    }


    /*
     * @testName: getLocaleDefaultTest
     *
     * @assertion_ids: Servlet:JAVADOC:207
     *
     * @test_Strategy: Client does not specify a locale and the servlet verifies
     * the default.
     */
    @Test
    @RunAsClient
    public void getLocaleDefaultTest() throws Exception {
        requestClient.getLocaleDefaultTest();
    }


    /*
     * @testName: getLocalesTest
     *
     * @assertion_ids: Servlet:JAVADOC:208
     *
     * @test_Strategy: Client specifics 2 locales and the servlet verifies it.
     */
    @Test
    @RunAsClient
    public void getLocalesTest() throws Exception {
        requestClient.getLocalesTest();
    }


    /*
     * @testName: getLocalesDefaultTest
     *
     * @assertion_ids: Servlet:JAVADOC:209
     *
     * @test_Strategy: Client does not specify a locale and the servlet verifies
     * the default.
     */

    /*
     * @testName: getParameterMapTest
     *
     * @assertion_ids: Servlet:JAVADOC:193
     *
     * @test_Strategy: Client sets several parameters and the servlet attempts to
     * access them.
     */
    @Test
    @RunAsClient
    public void getParameterMapTest() throws Exception {
        requestClient.getParameterMapTest();
    }

    /*
     * @testName: getParameterNamesTest
     *
     * @assertion_ids: Servlet:JAVADOC:189
     *
     * @test_Strategy: Client sets several parameters and the servlet attempts to
     * access them.
     */
    @Test
    @RunAsClient
    public void getParameterNamesTest() throws Exception {
        requestClient.getParameterNamesTest();
    }

    /*
     * @testName: getParameterNamesEmptyEnumTest
     *
     * @assertion_ids: Servlet:JAVADOC:190
     *
     * @test_Strategy: Client does not set any parameters and the servlet attempts
     * to access them.
     */
    @Test
    @RunAsClient
    public void getParameterNamesEmptyEnumTest() throws Exception {
        requestClient.getParameterNamesEmptyEnumTest();
    }


    /*
     * @testName: getParameterTest
     *
     * @assertion_ids: Servlet:JAVADOC:187
     *
     * @test_Strategy: Client sets a parameter and servlet retrieves it.
     */
    @Test
    @RunAsClient
    public void getParameterTest() throws Exception {
        requestClient.getParameterTest();
    }


    /*
     * @testName: getParameterDoesNotExistTest
     *
     * @assertion_ids: Servlet:JAVADOC:188
     *
     * @test_Strategy: Servlet tries to access a non-existent parameter
     */
    @Test
    @RunAsClient
    public void getParameterDoesNotExistTest() throws Exception {
        requestClient.getParameterDoesNotExistTest();
    }


    /*
     * @testName: getParameterValuesTest
     *
     * @assertion_ids: Servlet:JAVADOC:191
     *
     * @test_Strategy: Client sets a parameter which has 2 values and servlet
     * verifies boths values.
     */
    @Test
    @RunAsClient
    public void getParameterValuesTest() throws Exception {
        requestClient.getParameterValuesTest();
    }

    /*
     * @testName: getParameterValuesDoesNotExistTest
     *
     * @assertion_ids: Servlet:JAVADOC:192
     *
     * @test_Strategy: Servlet tries to retrieve a parameter that does not exist
     */
    @Test
    @RunAsClient
    public void getParameterValuesDoesNotExistTest() throws Exception {
        requestClient.getParameterValuesDoesNotExistTest();
    }

    /*
     * @testName: getProtocolTest
     *
     * @assertion_ids: Servlet:JAVADOC:194
     *
     * @test_Strategy: Servlet verifies the protocol used by the client
     */
    @Test
    @RunAsClient
    public void getProtocolTest() throws Exception {
        requestClient.getProtocolTest();
    }

    /*
     * @testName: getReaderTest
     *
     * @assertion_ids: Servlet:JAVADOC:198
     *
     * @test_Strategy: Client sets some content and servlet reads the content
     */
    @Test
    @RunAsClient
    public void getReaderTest() throws Exception {
        requestClient.getReaderTest();
    }

    /*
     * @testName: getReaderIllegalStateExceptionTest
     *
     * @assertion_ids: Servlet:JAVADOC:201
     *
     * @test_Strategy: Servlet gets an InputStream Object then tries to get a
     * Reader Object.
     */
    @Test
    @RunAsClient
    public void getReaderIllegalStateExceptionTest() throws Exception {
        requestClient.getReaderIllegalStateExceptionTest();
    }

    /*
     * @testName: getReaderUnsupportedEncodingExceptionTest
     *
     * @assertion_ids: Servlet:JAVADOC:200
     *
     * @test_Strategy: Client sets some content but with an invalid encoding,
     * servlet tries to read content.
     */
    @Test
    @RunAsClient
    public void getReaderUnsupportedEncodingExceptionTest() throws Exception {
        requestClient.getReaderUnsupportedEncodingExceptionTest();
    }


    /*
     * @testName: getRemoteAddrTest
     *
     * @assertion_ids: Servlet:JAVADOC:202
     *
     * @test_Strategy: Servlet reads and verifies where the request originated
     */
    @Test
    @RunAsClient
    public void getRemoteAddrTest() throws Exception {
        requestClient.getRemoteAddrTest();
    }


    /*
     * @testName: getLocalAddrTest
     *
     * @assertion_ids: Servlet:JAVADOC:704
     *
     * @test_Strategy: Servlet reads and verifies where the request originated
     */
    @Test
    @RunAsClient
    public void getLocalAddrTest() throws Exception {
        requestClient.getLocalAddrTest();
    }


    /*
     * @testName: getRemoteHostTest
     *
     * @assertion_ids: Servlet:JAVADOC:203;
     *
     * @test_Strategy: Servlet reads and verifies where the request originated
     */
    @Test
    @RunAsClient
    public void getRemoteHostTest() throws Exception {
        requestClient.getRemoteHostTest();
    }


    /*
     * @testName: getRequestDispatcherTest
     *
     * @assertion_ids: Servlet:JAVADOC:211
     *
     * @test_Strategy: Servlet tries to get a dispatcher
     */
    @Test
    @RunAsClient
    public void getRequestDispatcherTest() throws Exception {
        requestClient.getRequestDispatcherTest();
    }


    /*
     * @testName: getSchemeTest
     *
     * @assertion_ids: Servlet:JAVADOC:195
     *
     * @test_Strategy: Servlet verifies the scheme of the url used in the request
     */
    @Test
    @RunAsClient
    public void getSchemeTest() throws Exception {
        requestClient.getSchemeTest();
    }


    /*
     * @testName: getServerNameTest
     *
     * @assertion_ids: Servlet:JAVADOC:196
     *
     * @test_Strategy: Servlet verifies the destination of the request
     */
    @Test
    @RunAsClient
    public void getServerNameTest() throws Exception {
        requestClient.getServerNameTest();
    }


    /*
     * @testName: getServerPortTest
     *
     * @assertion_ids: Servlet:JAVADOC:197
     *
     * @test_Strategy: Servlet verifies the destination port of the request
     */
    @Test
    @RunAsClient
    public void getServerPortTest() throws Exception {
        requestClient.getServerPortTest();
    }


    /*
     * @testName: isSecureTest
     *
     * @assertion_ids: Servlet:JAVADOC:210
     *
     * @test_Strategy: Servlet verifies the isSecure method for the non-secure
     * case.
     */
    @Test
    @RunAsClient
    public void isSecureTest() throws Exception {
        requestClient.isSecureTest();
    }


    /*
     * @testName: removeAttributeTest
     *
     * @assertion_ids: Servlet:JAVADOC:205
     *
     * @test_Strategy: Servlet adds then removes an attribute, then verifies it
     * was removed.
     */
    @Test
    @RunAsClient
    public void removeAttributeTest() throws Exception {
        requestClient.removeAttributeTest();
    }


    /*
     * @testName: setAttributeTest
     *
     * @assertion_ids: Servlet:JAVADOC:204
     *
     * @test_Strategy: Servlet adds an attribute, then verifies it was added
     */
    @Test
    @RunAsClient
    public void setAttributeTest() throws Exception {
        requestClient.setAttributeTest();
    }


    /*
     * @testName: setCharacterEncodingUnsupportedEncodingExceptionTest
     *
     * @assertion_ids: Servlet:JAVADOC:179
     *
     * @test_Strategy: Servlet tries to set an invalid encoding.
     *
     */
    @Test
    @RunAsClient
    public void setCharacterEncodingUnsupportedEncodingExceptionTest() throws Exception {
        requestClient.setCharacterEncodingUnsupportedEncodingExceptionTest();
    }


    /*
     * @testName: setCharacterEncodingTest
     *
     * @assertion_ids: Servlet:JAVADOC:178
     *
     * @test_Strategy: Servlet sets a new encoding and tries to retrieve it.
     */
    @Test
    @RunAsClient
    public void setCharacterEncodingTest() throws Exception {
        requestClient.setCharacterEncodingTest();
    }


    /*
     * @testName: setCharacterEncodingTest1
     *
     * @assertion_ids: Servlet:JAVADOC:178; Servlet:JAVADOC:177; Servlet:SPEC:28;
     * Servlet:SPEC:213;
     *
     * @test_Strategy: ServletRequest calls getReader()first; then sets a new
     * encoding and tries to retrieve it. verifies that the new encoding is
     * ignored.
     */
    @Test
    @RunAsClient
    public void setCharacterEncodingTest1() throws Exception {
        requestClient.setCharacterEncodingTest1();
    }

}
