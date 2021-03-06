package org.jakartaee8.servlet.servletrequest.asynccontext;

import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.nio.file.Path;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Collection;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.jakartaee8.servlet.security.clientcert.ServletSecTestServlet;
import org.jakartaee8.urlclient.http.TSURL;
import org.jakartaee8.urlclient.https.TSHttpsURLConnection;
import org.javaee7.ServerOperations;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * @author Arjan Tijms
 */
@RunWith(Arquillian.class)
public class SecurityClientCertTest {

    @ArquillianResource
    private URL base;
    
    private URL httpsBase;


    // DN name for CTS certificate
    private final String username = "CN=CTS, OU=Java Software, O=Sun Microsystems Inc., L=Burlington, ST=MA, C=US";

    private TSURL ctsurl = new TSURL();
    
    private static PrintStream out;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        File clientKeyStoreFile = new File("src/test/resources/clientcert.jks");
        assertTrue(clientKeyStoreFile.exists());
        
        File clientPublicCertFile = new File("src/test/resources/cts_cert");
        assertTrue(clientPublicCertFile.exists());
        
        Path trustStorePath = ServerOperations.getContainerTrustStorePath();
        assertNotNull(trustStorePath);
        
        Collection<? extends Certificate> clientPublicCertificates;
        try {
            clientPublicCertificates = (Collection<? extends Certificate>) 
                CertificateFactory.getInstance("X.509")
                                  .generateCertificates(
                                      new ByteArrayInputStream(readFileToByteArray(clientPublicCertFile)));
        } catch (CertificateException | IOException e) {
            throw new IllegalStateException(e);
        }
        
        for (Certificate certificate : clientPublicCertificates) {
            ServerOperations.addCertificateToContainerTrustStore(certificate);
        }
        
        out = System.out;
        
        System.out.println("Using truststore from: " + trustStorePath.toAbsolutePath().toString());
        
        System.setProperty("javax.net.ssl.keyStore", clientKeyStoreFile.getAbsolutePath());
        System.setProperty("javax.net.ssl.keyStorePassword", "changeit");
        System.setProperty("javax.net.ssl.trustStore", trustStorePath.toAbsolutePath().toString());
        System.setProperty("jdk.tls.client.protocols", "TLSv1.2");
        // System.setProperty("javax.net.debug", "ssl:handshake");
        
        return create(WebArchive.class)
                .addClasses(ServletSecTestServlet.class)
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/web.xml"))
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/sun-web.xml"));
    }


    /*
     * @testName: clientCertTest
     *
     * @assertion_ids: Servlet:SPEC:140; Servlet:SPEC:368; Servlet:SPEC:369; Servlet:SPEC:26; Servlet:SPEC:26.1;
     * Servlet:SPEC:26.2; Servlet:SPEC:26.3; Servlet:JAVADOC:356
     *
     * @test_strategy: 1. Look for the following request attributes a) cipher-suite b) key-size c) SSL certificate If any of
     * the above attributes are not set report test failure.
     *
     * 2. Verify the request.getAuthType returns CLIENT_CERT
     *
     * Note: If a request has been transmitted over a secure protocol, such as HTTPS, this information must be exposed via
     * the isSecure method of the ServletRequest interface. The web container must expose the following attributes to the
     * servlet programmer. 1) The cipher suite 2) the bit size of the algorithm
     *
     * If there is an SSL certificate associated with the request, it must be exposed by the servlet container to the
     * servlet programmer as an array of objects of type java.security.cert.X509Certificate
     *
     */
    @Test
    @RunAsClient
    public void clientCertTest() throws Exception {
        out.println("******************88");
        
        httpsBase = ServerOperations.toContainerHttps(base);
        String testName = "clientCertTest";
        String url = ctsurl.getURLString("https", httpsBase.getHost(), httpsBase.getPort(), httpsBase.getFile() + "ServletSecTest");

        try {
            URL newURL = new URL(url);

            // open HttpsURLConnection using TSHttpsURLConnection
            TSHttpsURLConnection httpsURLConn = getHttpsURLConnection(newURL);

            InputStream content = (InputStream) httpsURLConn.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(content));

            String output = "";
            String line;
            while ((line = in.readLine()) != null) {
                output = output + line;
                System.out.println(line);
            }

            // Compare getRemoteUser() obtained from server's response
            // with the username stored in ts.jte
            //
            // Even though the output need not be identical (because
            // of appserver realms) the output should have substring
            // match for username stored in ts.jte.
            //
            String userNameToSearch = username;
            if (output.indexOf(userNameToSearch) == -1) {
                throw new IllegalStateException(testName + ": getRemoteUser(): " + "- did not find \"" + userNameToSearch + "\" in log.");
            }

            // verify output for expected test result
            verifyTestOutput(output, testName);

            // close connection
            httpsURLConn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(testName + ": FAILED", e);
        }

    }

    public TSHttpsURLConnection getHttpsURLConnection(URL newURL) throws IOException {
        // open HttpsURLConnection using TSHttpsURLConnection
        TSHttpsURLConnection httpsURLConn = new TSHttpsURLConnection();
        
        httpsURLConn.init(newURL);
        httpsURLConn.setDoInput(true);
        httpsURLConn.setDoOutput(true);
        httpsURLConn.setUseCaches(false);

        return httpsURLConn;
    }

    public void verifyTestOutput(String output, String testName) {
        
        // Check for the occurrence of <testName>+": PASSED"
        // message in server's response. If this message is not present
        // report test failure.
        
        if (output.indexOf(testName + ": PASSED") == -1) {
            System.out.println("Expected String from the output = " + testName + ": PASSED");
            System.out.println("received output = " + output);
            throw new IllegalStateException(testName + ": FAILED");
        }
    }

}
