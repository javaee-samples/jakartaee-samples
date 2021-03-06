package org.jakartaee8.servlet.servletrequest.asynccontext;

import static org.jboss.shrinkwrap.api.ShrinkWrap.create;

import java.io.File;
import java.net.URL;

import org.jakartaee8.servlet.security.basic.GuestPageAnnoTestServlet;
import org.jakartaee8.servlet.security.basic.GuestPageTestServlet;
import org.jakartaee8.servlet.security.basic.RoleReverseAnnoTestServlet;
import org.jakartaee8.servlet.security.basic.RoleReverseTestServlet;
import org.jakartaee8.servlet.security.basic.ServletSecAnnoTestServlet;
import org.jakartaee8.servlet.security.basic.ServletSecTestServlet;
import org.jakartaee8.servlet.security.basic.UnProtectedAnnoTestServlet;
import org.jakartaee8.servlet.security.basic.UnProtectedTestServlet;
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
public class SecurityBasicTest {

    @ArquillianResource
    private URL base;

    private SecurityBasicClient securityBasicClient;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return create(WebArchive.class)
                    .addClasses(
                        GuestPageAnnoTestServlet.class,
                        GuestPageTestServlet.class,
                        RoleReverseAnnoTestServlet.class,
                        RoleReverseTestServlet.class,
                        ServletSecAnnoTestServlet.class,
                        ServletSecTestServlet.class,
                        UnProtectedAnnoTestServlet.class,
                        UnProtectedTestServlet.class)
                    .addAsWebInfResource(new File("src/main/webapp/WEB-INF/web.xml"))
                    .addAsWebInfResource(new File("src/main/webapp/WEB-INF/piranha-callers.xml"))
                    .addAsWebInfResource(new File("src/main/webapp/WEB-INF/sun-web.xml"))
                    ;
    }


    @Before
    public void setup() {
        securityBasicClient = new SecurityBasicClient(base);
    }

    @After
    public void teardown() {
        securityBasicClient = null;
    }


    /**
     * testName: test1
     *
     * assertion: Test BASIC authentication, specified in the Java Servlet Specification v2.2, Sec 11.5.1.
     *
     * <pre>
     * 1. If user has not been authenticated and user attempts to access a protected web resource, the web server requests
     * authentication.
     * </pre>
     *
     * test_Strategy:
     *
     * <pre>
     * 1. Send request to access page
     * 2. Receive authentication request.
     * </pre>
     */
    @Test
    @RunAsClient
    public void constructorTest1() throws Exception {
        securityBasicClient.test1();
    }

    /**
     * testName: test2
     *
     * assertion: Test BASIC authentication, specified in the Java Servlet Specification v2.2, Sec 11.5.1. Also tests API
     * assertions in section 11.3.
     *
     *<pre>
     * 1. If user has not been authenticated and user attempts to access a protected web resource, and user enters a valid
     * username and password, the original web resource is returned and user is authenticated.
     * 2. getRemoteUser() returns the user name that the client authenticated with.
     * </pre>
     *
     * test_Strategy:
     *
     * <pre>
     * 1. Send request with correct authentication.
     * 2. Receive page (ensure principal is correct, and ensure that getRemoteUser() returns the correct name)
     * </pre>
     */
    @Test
    @RunAsClient
    public void test2() throws Exception {
        securityBasicClient.test2();
    }

    /**
     * testName: test3
     *
     * assertion: Test BASIC authentication, specified in the Java Servlet Specification v2.2, Sec 11.5.1.
     *
     * <pre>
     * 1. If user has not been authenticated and user attempts to access a protected web resource, and user enters an
     * invalid username and password, the container denies access to the web resource.
     * </pre>
     *
     * test_Strategy:
     *
     * <pre>
     * 1. Re-send request with incorrect authentication.
     * 2. Receive authentication request.
     * </pre>
     */
    @Test
    @RunAsClient
    public void test3() throws Exception {
        securityBasicClient.test3();
    }

    /**
     * testName: test4
     *
     * assertion: Test BASIC authentication, specified in the Java Servlet Specification v2.2, Sec 11.5.1.
     *
     * 1. If user has not been authenticated and user attempts to access a protected web resource, and user enters an valid
     * username and password, but for a role that is not authorized to access the resource, the container denies access to
     * the web resource.
     *
     * test_Strategy:
     *
     * <pre>
     * 1. Send request with correct authentication for user javajoe for a page javajoe is allowed to access.
     * 2. Receive page (this verifies that the javajoe user is set up properly). 3. Send request with correct
     * authentication, but incorrect authorization to access resource 4. Receive error
     * </pre>
     */
    @Test
    @RunAsClient
    public void test4() throws Exception {
        securityBasicClient.test4();
    }

    /**
     * testName: test5
     *
     * @assertion: Test BASIC authentication, specified in the Java Servlet Specification v2.2, Sec 11.5.1. Also tests
     * assertions in section 11.3.
     *
     *<pre>
     * 1. If user has not been authenticated and user attempts to access an unprotected web resource, the web resource is
     * returned without need to authenticate.
     * 2. isUserInRole() must return false for any valid or invalid role reference.
     * 3. getRemoteUser() must return false
     * </pre>
     *
     * @test_Strategy: 1. Send request for unprotected.jsp with no authentication. 2. Receive page 3. Search the returned
     * page for "!true!", which would indicate that at least one call to isUserInRole attempted by unprotected.jsp returned
     * true. 4. check that getRemoteUser() returns null.
     */
    @Test
    @RunAsClient
    public void test5() throws Exception {
        securityBasicClient.test5();
    }

    /**
     * testName: test6
     *
     * @assertion: Test HTTP-Basic authentication, specified in the Java Servlet Specification v2.2, Sec 11.5.1. Also tests
     * assertions from section 11.3.
     *
     * Given two servlets in the same application, each of which calls isUserInRole(X), and where X is linked to different
     * roles in the scope of each of the servlets (i.e. R1 for servlet 1 and R2 for servlet 2), then a user whose identity
     * is mapped to R1 but not R2, shall get a true return value from isUserInRole( X ) in servlet 1, and a false return
     * value from servlet 2 (a user whose identity is mapped to R2 but not R1 should get the inverse set of return values).
     *
     * @test_Strategy: Since test1 already verifies the functionality for isUserInRole returning true, this test needs only
     * verify that it should return false for the other jsp. For this test, MGR and ADM are swapped, so isUserInRole()
     * should return opposite values from test1.
     *
     *
     * <pre>
     * 1. Send request to access rolereverse.jsp
     * 2. Receive redirect to login page, extract location and session id cookie.
     * 3. Send request to access new location, send cookie
     * 4. Receive login page
     * 5. Send form response with username and password
     * 6. Receive redirect to resource
     * 7. Request resource 8. Receive resource (check isUserInRole for all known roles)
     * </pre>
     */
    @Test
    @RunAsClient
    public void test6() throws Exception {
        securityBasicClient.test6();
    }

    /**
     * testName: test7
     *
     * assertion: Test BASIC authentication, specified in the Java Servlet Specification v2.2, Sec 11.5.1.
     *
     * 1. If user has not been authenticated and user attempts to access a protected web resource+<j_security_check> , and
     * user enters an invalid username and password, the container denies access to the web resource. IMPORTANT: this is not
     * just trying to access a protected resource but instead is trying to access pageSec + "/j_security_check" when
     * unauthenticated in an attempt to trick/confuse the impl into thinking authentication occurred when it did not.
     *
     * test_Strategy:
     *
     * <pre>
     * 1. send request with incorrect authentication to url + "/j_security_check"
     * 2. Receive authentication request.
     * </pre>
     */
    @Test
    @RunAsClient
    public void test7() throws Exception {
        securityBasicClient.test7();
    }

}
