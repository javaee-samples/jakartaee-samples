# Jakarta EE 8 TCK Samples: URLClient

URLClient is a retro mini-framework to issue HTTP requests and examine its response. It uses Apache Commons Httpclient under the hood.

Instead of using a discoverable Java API, it fully works with properties that have to be inserted into a "magic map" (a map that's inherited from a base class).

For example, define a test by extending from `AbstractUrlClient`:

```java
public class MyTester extends AbstractUrlClient {
    public RequestClient(URL base, String testServlet) {
        super(base, testServlet);
    }

    public void getContentLengthTest() throws Exception {
        TEST_PROPS.setProperty(REQUEST, "POST " + getContextRoot() + "/" + getServletName() + "?testname=getContentLengthTest HTTP/1.1");
        TEST_PROPS.setProperty(REQUEST_HEADERS, "Content-Type:text/plain");
        TEST_PROPS.setProperty(CONTENT, "calling getContentLengthTest");
        TEST_PROPS.setProperty(SEARCH_STRING, "bar");
        invoke();
    }
```

From your actual test class (say a JUnit test), instantiate `MyTest` with a URL to reach the test server and test app, and a servlet within the app to call. For example, when using Arquillian:

```java
@RunWith(Arquillian.class)
public class MyTest {

    @ArquillianResource
    private URL base;

    private MyTester myTester;

    @Before
    public void setup() {
        myTester = new MyTester(base, "TestServlet");
    }

    @Test
    @RunAsClient
    public void getContentLengthTest() throws Exception {
        myTester.getContentLengthTest();
    }
 }
 ```
 
Assume the base URL here is `http://localhost:8080/foo`, then the result is that URLClient will do a POST request to `http://localhost:8080/foo/TestServlet?testname=getContentLengthTest` with a request header `Content-Type:text/plain` and a POST body of `calling getContentLengthTest`. URLCLient will then check that the response contains the string "bar". If not, an exception will be thrown failing the test.

Yes, this is all a little unorthodox, but it originates from darker times when (fluent) Java APIs were not much in vogue yet.



