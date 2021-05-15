package org.jakartaee8.servlet.servletrequest.servletrequest;

import static org.jboss.shrinkwrap.api.ShrinkWrap.create;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

import org.jakartaee8.simpleclient.SimpleClient;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.sun.istack.logging.Logger;

/**
 * @author Arjan Tijms
 */
@RunWith(Arquillian.class)
public class ServletRequestTest {
    
    private static final Logger logger = Logger.getLogger(ServletRequestTest.class);

    public static final String DELIMITER = "\r\n";
    public static final String ENCODING = "ISO-8859-1";

    @ArquillianResource
    private URL base;

    private SimpleClient.Response response;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return create(WebArchive.class)
                    .addClasses(
                        DispatchServlet.class,
                        ForwardFilter.class,
                        ForwardServlet.class,
                        IncludeServlet.class,
                        NamedForwardServlet.class,
                        NamedIncludeServlet.class,
                        TestServlet.class,
                        TrailerTestServlet.class,
                        Utilities.class)
                    .addAsWebInfResource(
                        new File("src/main/webapp/WEB-INF/web.xml"))
                    ;
    }

    /*
     * @testName: httpServletMappingTest
     * 
     * @assertion_ids: servlet40:httpServletMappingTest;
     * 
     * @test_Strategy:
     */
    @Test
    @RunAsClient
    public void httpServletMappingTest() throws Exception {
        simpleTest("httpServletMappingTest", "/TestServlet", "GET",
            "matchValue=TestServlet, pattern=/TestServlet, servletName=TestServlet, mappingMatch=EXACT");
    }

    /*
     * @testName: httpServletMappingTest2
     * 
     * @assertion_ids: servlet40:httpServletMappingTest2;
     * 
     * @test_Strategy:
     */
    @Test
    @RunAsClient
    public void httpServletMappingTest2() throws Exception {
        simpleTest("httpServletMappingTest2", "/a.ts", "GET", 
            "matchValue=a, pattern=*.ts, servletName=TestServlet, mappingMatch=EXTENSION");
    }

    /*
     * @testName: httpServletMappingTest3
     * 
     * @assertion_ids: servlet40:httpServletMappingTest3;
     * 
     * @test_Strategy:
     */
    @Test
    @RunAsClient
    public void httpServletMappingTest3() throws Exception {
        simpleTest("httpServletMappingTest3", "/default", "GET",
            "matchValue=, pattern=/, servletName=defaultServlet, mappingMatch=DEFAULT");
    }

    /*
     * @testName: httpServletMappingForwardTest
     * 
     * @assertion_ids: servlet40:httpServletMappingForwardTest;
     * 
     * @test_Strategy:
     */
    @Test
    @RunAsClient
    public void httpServletMappingForwardTest() throws Exception {
        simpleTest("httpServletMappingForwardTest", "/ForwardServlet", "GET",
            "matchValue=a, pattern=*.ts, servletName=TestServlet, mappingMatch=EXTENSION");
    }

    /*
     * @testName: httpServletMappingNamedForwardTest
     * 
     * @assertion_ids: servlet40:httpServletMappingNamedForwardTest;
     * 
     * @test_Strategy:
     */
    @Test
    @RunAsClient
    public void httpServletMappingNamedForwardTest() throws Exception {
        simpleTest("httpServletMappingNamedForwardTest", "/NamedForwardServlet", "GET",
            "matchValue=NamedForwardServlet, pattern=/NamedForwardServlet, servletName=NamedForwardServlet, mappingMatch=EXACT");
    }

    /*
     * @testName: httpServletMappingNamedIncludeTest
     * 
     * @assertion_ids: servlet40:httpServletMappingNamedIncludeTest;
     * 
     * @test_Strategy:
     */
    @Test
    @RunAsClient
    public void httpServletMappingNamedIncludeTest() throws Exception {
        simpleTest("httpServletMappingNamedIncludeTest", "/NamedIncludeServlet", "GET",
            "matchValue=NamedIncludeServlet, pattern=/NamedIncludeServlet, servletName=NamedIncludeServlet, mappingMatch=EXACT");
    }

    /*
     * @testName: httpServletMappingIncludeTest
     * 
     * @assertion_ids: servlet40:httpServletMappingIncludeTest;
     * 
     * @test_Strategy:
     */
    @Test
    @RunAsClient
    public void httpServletMappingIncludeTest() throws Exception {
        simpleTest("httpServletMappingIncludeTest", "/IncludeServlet", "POST",
            "matchValue=IncludeServlet, pattern=/IncludeServlet, servletName=IncludeServlet, mappingMatch=EXACT");
    }

    /*
     * @testName: httpServletMappingFilterTest
     * 
     * @assertion_ids: servlet40:httpServletMappingFilterTest;
     * 
     * @test_Strategy:
     */
    @Test
    @RunAsClient
    public void httpServletMappingFilterTest() throws Exception {
        simpleTest("httpServletMappingFilterTest", "/ForwardFilter", "GET",
            "matchValue=, pattern=/, servletName=defaultServlet, mappingMatch=DEFAULT");
    }

    /*
     * @testName: httpServletMappingDispatchTest
     * 
     * @assertion_ids: servlet40:httpServletMappingDispatchTest;
     * 
     * @test_Strategy:
     */
    @Test
    @RunAsClient
    public void httpServletMappingDispatchTest() throws Exception {
        simpleTest("httpServletMappingDispatchTest", "/DispatchServlet", "GET",
            "matchValue=DispatchServlet, pattern=/DispatchServlet, servletName=DispatchServlet, mappingMatch=EXACT");
    }

    private void simpleTest(String testName, String request, String method, String expected) throws Exception {
        try {
            request = (base.getFile() + request).replace("//", "/");
            
            logger.info("Sending request \"" + InetAddress.getByName(base.getHost()) + request + "\"");

            response = SimpleClient.sendRequest(
                method, 
                InetAddress.getByName(base.getHost()), 
                base.getPort(), 
                request, 
                null, null);

        } catch (Exception e) {
            logger.warning("Caught exception: " + e.getMessage());
            e.printStackTrace();
            throw new Exception(testName + " failed: ", e);
        }

        logger.info("response.statusToken:" + response.statusToken);
        logger.info("response.content:" + response.content);

        // Check that the page was found (no error).
        if (response.isError()) {
            logger.warning("Could not find " + request);
            throw new Exception(testName + " failed.");
        }

        if (response.content.indexOf(expected) < 0) {
            logger.warning("Expected: " + expected);
            throw new Exception(testName + " failed.");
        }
    }

    /*
     * @testName: TrailerTest
     * 
     * @assertion_ids: servlet40:TrailerTest;
     * 
     * @test_Strategy:
     */
    //@Test
    @RunAsClient
    public void TrailerTest() throws Exception {
        URL url;
        Socket socket = null;
        OutputStream output;
        InputStream input;

        try {
            url = new URL(base + "TrailerTestServlet");
            socket = new Socket(url.getHost(), url.getPort());
            socket.setKeepAlive(true);
            output = socket.getOutputStream();

            String path = url.getPath();
            StringBuffer outputBuffer = new StringBuffer();
            outputBuffer.append("POST " + path + " HTTP/1.1" + DELIMITER);
            outputBuffer.append("Host: " + url.getHost() + DELIMITER);
            outputBuffer.append("Connection: keep-alive" + DELIMITER);
            outputBuffer.append("Content-Type: text/plain" + DELIMITER);
            outputBuffer.append("Transfer-Encoding: chunked" + DELIMITER);
            outputBuffer.append("Trailer: myTrailer, myTrailer2" + DELIMITER);
            outputBuffer.append(DELIMITER);
            outputBuffer.append("3" + DELIMITER);
            outputBuffer.append("ABC" + DELIMITER);
            outputBuffer.append("0" + DELIMITER);
            outputBuffer.append("myTrailer:foo");
            outputBuffer.append(DELIMITER);
            outputBuffer.append("myTrailer2:bar");
            outputBuffer.append(DELIMITER);
            outputBuffer.append(DELIMITER);

            byte[] outputBytes = outputBuffer.toString().getBytes(ENCODING);
            output.write(outputBytes);
            output.flush();

            input = socket.getInputStream();
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            int read = 0;
            while ((read = input.read()) >= 0) {
                bytes.write(read);
            }
            String response = new String(bytes.toByteArray());
            logger.info(response);
            if (response.indexOf("isTrailerFieldsReady: true") < 0) {
                logger.warning("isTrailerFieldsReady should be true");
                throw new Exception("TrailerTest failed.");
            }

            if (response.toLowerCase().indexOf("mytrailer=foo") < 0) {
                logger.warning("failed to get trailer field: mytrailer=foo");
                throw new Exception("TrailerTest failed.");
            }

            if (response.toLowerCase().indexOf("mytrailer2=bar") < 0) {
                logger.warning("failed to get trailer field: mytrailer=foo");
                throw new Exception("TrailerTest failed.");
            }
        } catch (Exception e) {
            logger.warning("Caught exception: " + e.getMessage());
            e.printStackTrace();
            throw new Exception("TrailerTest failed: ", e);
        } finally {
            try {
                if (socket != null)
                    socket.close();
            } catch (Exception e) {
            }
        }
    }

    /*
     * @testName: TrailerTest2
     * 
     * @assertion_ids: servlet40:TrailerTest2;
     * 
     * @test_Strategy:
     */
   // @Test
    @RunAsClient
    public void TrailerTest2() throws Exception {
        simpleTest("TrailerTest2", "/TrailerTestServlet", "POST", "isTrailerFieldsReady: true");
        simpleTest("TrailerTest2", "/TrailerTestServlet", "POST", "Trailer: {}");
    }

}
