/*
 * Copyright (c) 2007, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

/*
 * $Id$
 */

package org.jakartaee8.urlclient;

import static org.jakartaee8.urlclient.TestUtil.logMsg;
import static org.jakartaee8.urlclient.TestUtil.logTrace;

import java.util.Map;
import java.util.Properties;

import org.apache.commons.httpclient.HttpState;
import org.jakartaee8.urlclient.http.HttpRequest;

/**
 * Provides base test initialization and runtime logic into a common class.
 *
 * <p>
 * This class is configured by means of a sub-class. Upon calling {@link BaseUrlClient#invoke()} the
 * configured URL will be called, and its response evaluated.
 *
 * <p>
 * All test areas will need to extend this class to provide area specific functionality needed for
 * that particular technology.
 */
public abstract class BaseUrlClient {

    /**
     * Properties parameters
     */
    public Properties _props;

    /**
     * 401 - Unauthorized
     */
    protected static final String UNAUTHORIZED = "401";

    /**
     * 403 - Forbidden
     */
    protected static final String FORBIDDEN = "403";

    /**
     * 404 - not found
     */
    protected static final String NOT_FOUND = "404";

    /**
     * 200 - ok
     */
    protected static final String OK = "200";

    /**
     * 201 - created
     */
    protected static final String CREATED = "201";

    /**
     * 500 - internal server error
     */
    protected static final String INTERNAL_SERVER_ERROR = "500";

    /**
     * 503 - service unavailable
     */
    protected static final String SERVICE_UNAVAILABLE = "503";

    /**
     * 100 - continue
     */
    protected static final String CONTINUE = "100";

    /**
     * 302 - moved temporarily
     */
    protected static final String MOVED_TEMPORARY = "302";

    /**
     * 410 - GONE
     */
    protected static final String GONE = "410";

    /**
     * 411 - length required
     */
    protected static final String LENGTH_REQUIRED = "411";

    /**
     * TS Webserver host property
     */
    protected static final String SERVLETHOSTPROP = "webServerHost";

    /**
     * TS Webserver port property
     */
    protected static final String SERVLETPORTPROP = "webServerPort";

    /**
     * TS home property
     */
    protected static final String TSHOME = "ts_home";

    /**
     * Test properties
     */
    protected static final Properties TEST_PROPS = new Properties();

    /**
     * StatusCode property
     */
    protected static final String STATUS_CODE = "status-code";

    /**
     * Reason-Phrase property
     */
    protected static final String REASON_PHRASE = "reason-phrase";

    /**
     * Expected headers property
     */
    protected static final String EXPECTED_HEADERS = "expected_headers";

    /**
     * Unexpected header property
     */
    protected static final String UNEXPECTED_HEADERS = "unexpected_headers";

    /**
     * Expect response body property
     */
    protected static final String EXPECT_RESPONSE_BODY = "expect_response_body";

    /**
     * Request property
     */
    protected static final String REQUEST = "request";

    /**
     * Request headers property
     */
    protected static final String REQUEST_HEADERS = "request_headers";

    /**
     * Goldenfile property
     */
    protected static final String GOLDENFILE = "goldenfile";

    /**
     * Search string property
     */
    protected static final String SEARCH_STRING = "search_string";

    /**
     * Search string case insensitive property
     */
    protected static final String SEARCH_STRING_IGNORE_CASE = "search_string_ignore_case";

    /**
     * Basic Auth username
     */
    protected static final String BASIC_AUTH_USER = "basic_auth_user";

    /**
     * Basic Auth password
     */
    protected static final String BASIC_AUTH_PASSWD = "basic_auth_passwd";

    /**
     * Basic Auth realm
     */
    protected static final String BASIC_AUTH_REALM = "basic_auth_realm";

    /**
     * Unordered search string property
     */
    protected static final String UNORDERED_SEARCH_STRING = "unordered_search_string";

    /**
     * Content property
     */
    protected static final String CONTENT = "content";

    /**
     * Test name property
     */
    protected static final String TEST_NAME = "testname";

    /**
     * Response Match property
     */
    protected static final String RESPONSE_MATCH = "response_match";

    /**
     * Unexpected response match property
     */
    protected static final String UNEXPECTED_RESPONSE_MATCH = "unexpected_response_match";

    /**
     * Standard test property
     */
    protected static final String STANDARD = "standard";

    /**
     * Ignore response body
     */
    protected static final String IGNORE_BODY = "ignore_body";

    /**
     * Validation strategy
     */
    protected static final String STRATEGY = "strategy";

    /**
     * Current test directory
     */
    protected String TESTDIR;

    /**
     * Goldenfile directory
     */
    protected String GOLDENFILEDIR = "/src/web";

    /**
     * DeException request method
     */
    protected static final String GET = "GET ";

    /**
     * HTTP 1.0
     */
    protected static final String HTTP10 = " HTTP/1.0";

    /**
     * HTTP 1.1
     */
    protected static final String HTTP11 = " HTTP/1.1";

    /**
     * Forward slash
     */
    protected static final String SL = "/";

    /**
     * Goldenfile suffix
     */
    protected static final String GF_SUFFIX = ".gf";

    /**
     * JSP suffix
     */
    protected static final String JSP_SUFFIX = ".jsp";

    /**
     * Use any saved state
     */
    protected static final String USE_SAVED_STATE = "use_saved_state";

    /**
     * Save current HTTP state.
     */
    protected static final String SAVE_STATE = "save_state";

    /**
     * Ignore HTTP status codes
     */
    protected static final String IGNORE_STATUS_CODE = "ignore_status_code";

    /**
     * Current test name
     */
    protected String _testName;

    /**
     * location of _tsHome
     */
    protected String _tsHome;

    /**
     * Context root of target tests
     */
    protected String contextRoot;

    /**
     * General file/request URI for both gfiles and tests
     */
    protected String _generalURI;

    /**
     * Target webserver hostname
     */
    protected String hostname;


    /**
     * Target webserver port
     */
    protected int port = 0;



    /**
     * HttpState that may be used for multiple invocations requiring state.
     */
    protected HttpState _state;

    /**
     * Test case.
     */
    protected WebTestCase testCase;

    /**
     * Use saved state.
     */
    protected boolean _useSavedState = false;

    /**
     * Save state.
     */
    protected boolean _saveState = false;

    /**
     * Follow redirect.
     */
    protected String FOLLOW_REDIRECT = "follow_redirect";

    protected boolean redirect = false;

    /*
     * public methods ========================================================================
     */

    /**
     * <code>setTestDir</code> sets the current test directory.
     *
     * @param testDir a <code>String</code> value
     */
    public void setTestDir(String testDir) {
        TESTDIR = testDir;
    }

    public void setGeneralURI(String URI) {
        _generalURI = URI;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setContextRoot(String root) {
        contextRoot = root;
    }

    public String getContextRoot() {
        return contextRoot;
    }

    /**
     * Sets the goldenfile directory
     *
     * @param goldenDir goldenfile directory based off test directory
     */
    public void setGoldenFileDir(String goldenDir) {
        GOLDENFILEDIR = goldenDir;
    }

    /**
     * <code>setup</code> is by the test harness to initialize the tests.
     *
     * @param args a <code>String[]</code> value
     * @param properties a <code>Properties</code> value
     * @exception Exception if an error occurs
     */
    public void setup(String[] args, Properties properties) throws Exception {
        _props = properties;
        String hostname = properties.getProperty(SERVLETHOSTPROP).trim();
        if (isNullOrEmpty(hostname)) {
            throw new Exception("[BaseUrlClient] 'webServerHost' was not set");
        }
        this.hostname = hostname;

        String portnumber = properties.getProperty(SERVLETPORTPROP).trim();
        if (isNullOrEmpty(portnumber)) {
            throw new Exception("[BaseUrlClient] 'webServerPort' was not set");
        }
        port = Integer.parseInt(portnumber);

        String tshome = properties.getProperty(TSHOME).trim();
        if (isNullOrEmpty(tshome)) {
            throw new Exception("[BaseUrlClient] 'tshome' was not set.");
        }
        _tsHome = tshome;

        logMsg("[BaseUrlClient] Test setup OK");
    }

    /**
     * <code>cleanup</code> is called by the test harness to cleanup after text execution
     *
     * @exception Exception if an error occurs
     */
    public void cleanup() throws Exception {
        logMsg("[BaseUrlClient] Test cleanup OK");
    }

    /*
     * protected methods ========================================================================
     */

    /**
     * <PRE>
     * Invokes a test based on the properties
      * stored in TEST_PROPS.  Once the test has completed,
      * the properties in TEST_PROPS will be cleared.
     * </PRE>
     *
     * @throws Exception If an error occurs during the test run
     */
    protected void invoke() throws Exception {
        try {
            testCase = new WebTestCase();
            setTestProperties(testCase);
            logTrace("[BaseUrlClient] EXECUTING");

            if (_useSavedState && _state != null) {
                testCase.getRequest().setState(_state);
            }
            if (redirect != false) {
                logTrace("##########Call setFollowRedirects");
                testCase.getRequest().setFollowRedirects(redirect);
            }

            testCase.execute();

            if (_saveState) {
                _state = testCase.getResponse().getState();
            }
        } catch (Exception tfe) {
            Throwable t = tfe.getCause();
            if (t != null) {
                TestUtil.logErr("Root cause of Failure: " + t.getMessage(), t);
            }
            throw new Exception("[BaseUrlClient] " + _testName + " failed!  Check output for cause of failure.", tfe);
        } finally {
            _useSavedState = false;
            _saveState = false;
            redirect = false;
            clearTestProperties();
        }
    }

    /**
     * <PRE>
     * Sets the appropriate test properties based
      * on the values stored in TEST_PROPS
     * </PRE>
     */
    protected void setTestProperties(WebTestCase testCase) {
        HttpRequest httpRequest = testCase.getRequest();

        // Check for a request object. If doesn't exist, then
        // check for a REQUEST property and create the request object.

        if (httpRequest == null) {
            String request = TEST_PROPS.getProperty(REQUEST);

            if (request.startsWith("GET") || request.startsWith("POST") || request.startsWith("OPTIONS") ||
                request.startsWith("PUT") || request.startsWith("DELETE") || request.startsWith("HEAD") ||
                request.endsWith(HTTP10) || request.endsWith(HTTP11)) {

                // User has overridden deException request behavior
                httpRequest = new HttpRequest(request, hostname, port);
                testCase.setRequest(httpRequest);
            } else {
                httpRequest = new HttpRequest(getTSRequest(request), hostname, port);
                testCase.setRequest(httpRequest);
            }
        }

        // Proces the remainder of the properties
        for (Map.Entry<Object, Object> entry : TEST_PROPS.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();

            if (key.equals(STATUS_CODE)) {
                testCase.setExpectedStatusCode(value);
            } else if (key.equals(IGNORE_STATUS_CODE)) {
                testCase.setExpectedStatusCode("-1");
            } else if (key.equals(REASON_PHRASE)) {
                testCase.setExpectedReasonPhrase(value);
            } else if (key.equals(EXPECTED_HEADERS)) {
                testCase.addExpectedHeader(value);
            } else if (key.equals(UNEXPECTED_HEADERS)) {
                testCase.addUnexpectedHeader(value);
            } else if (key.equals(SEARCH_STRING)) {
                testCase.setResponseSearchString(value);
            } else if (key.equals(SEARCH_STRING_IGNORE_CASE)) {
                testCase.setResponseSearchStringIgnoreCase(value);
            } else if (key.equals(STRATEGY)) {
                testCase.setStrategy(value);
            } else if (key.equals(GOLDENFILE)) {
                StringBuffer sb = new StringBuffer(50);
                sb.append(_tsHome).append(GOLDENFILEDIR);
                sb.append(_generalURI).append(SL);
                sb.append(value);
                testCase.setGoldenFilePath(sb.toString());
            } else if (key.equals(CONTENT)) {
                httpRequest.setContent(value);
            } else if (key.equals(TEST_NAME) || key.equals(RESPONSE_MATCH)) {
                // testName = TEST_PROPS.getProperty(key);
            } else if (key.equals(REQUEST_HEADERS)) {
                httpRequest.addRequestHeader(TEST_PROPS.getProperty(key));
            } else if (key.equals(EXPECT_RESPONSE_BODY)) {
                // FIXME
                // setExpectResponseBody(false);
            } else if (key.equals(IGNORE_BODY)) {
                // FIXME
                // setIgnoreResponseBody(true);
                testCase.setGoldenFilePath(null);
            } else if (key.equals(UNEXPECTED_RESPONSE_MATCH)) {
                testCase.setUnexpectedResponseSearchString(value);
            } else if (key.equals(UNORDERED_SEARCH_STRING)) {
                testCase.setUnorderedSearchString(value);
            } else if (key.equals(USE_SAVED_STATE)) {
                _useSavedState = true;
            } else if (key.equals(SAVE_STATE)) {
                _saveState = true;
            } else if (key.equals(FOLLOW_REDIRECT)) {
                logTrace("##########Found redirect Property");
                redirect = true;
            } else if (key.equals(BASIC_AUTH_USER) || key.equals(BASIC_AUTH_PASSWD) || key.equals(BASIC_AUTH_REALM)) {
                String user = TEST_PROPS.getProperty(BASIC_AUTH_USER);
                String password = TEST_PROPS.getProperty(BASIC_AUTH_PASSWD);
                String realm = TEST_PROPS.getProperty(BASIC_AUTH_REALM);
                httpRequest.setAuthenticationCredentials(user, password, HttpRequest.BASIC_AUTHENTICATION, realm);
            }
        }
    }

    /*
     * private methods ========================================================================
     */

    private String getTSRequest(String request) {
        return
            new StringBuffer(50)
                .append(GET)
                .append(contextRoot)
                .append(SL)
                .append(_generalURI)
                .append(SL)
                .append(request)
                .append(HTTP10)
                .toString();
    }

    /**
     * Clears the contents of TEST_PROPS
     */
    private void clearTestProperties() {
        TEST_PROPS.clear();
    }

    private boolean isNullOrEmpty(String val) {
        if (val == null || val.equals("")) {
            return true;
        }

        return false;
    }
}
