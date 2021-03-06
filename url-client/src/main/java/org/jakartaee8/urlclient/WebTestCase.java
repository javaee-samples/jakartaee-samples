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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpState;
import org.jakartaee8.urlclient.http.HttpRequest;
import org.jakartaee8.urlclient.http.HttpResponse;
import org.jakartaee8.urlclient.validation.TokenizedValidator;
import org.jakartaee8.urlclient.validation.ValidationFactory;
import org.jakartaee8.urlclient.validation.ValidationStrategy;

/**
 * A TestCase implementation for HTTP-based testing. This allows the user to set criteria for test success which will be
 * compared against the response from the server.
 */
public class WebTestCase implements TestCase {

    /**
     * Tokenized response validation strategy
     */
    public static final String TOKENIZED_STRATEGY = TokenizedValidator.class.getName();

    /**
     * The request for this case.
     */
    private HttpRequest httpRequest;

    /**
     * The server's response.
     */
    private HttpResponse httpResponse;

    // use HttpMethod instances to store test
    // case headers that are expected or not
    // expected.

    /**
     * Storage for headers that are expected in the response
     */
    private Map<String, Header> expectedHeaders;

    /**
     * Storage for headers that are not expected in the response
     */
    private Map<String, Header> unexpectedHeaders;

    /**
     * Expected response status code.
     */
    private String responseSatusCode;

    /**
     * Expected response reason phrase.
     */
    private String _reasonPhrase;

    /**
     * Path to goldenfile.
     */
    private String _goldenfilePath;

    /**
     * A List of strings that will be searched for in the response in the order they appear in the list.
     */
    private List<String> responseSearchStrings;

    /**
     * A List of case insensitive strings that will be searched for in the response in the order they appear in the list.
     */
    private List<String> searchStringsNoCase;

    /**
     * A List of strings that will be search for in the response with no attention paid to the order they appear.
     */
    private List<String> unorderedSearchStrings;

    /**
     * A List of strings that should not be in the server's response.
     */
    private List<String> searchStringsNOTInResponse;

    /**
     * Indicates whether a response body should be expected or not.
     */
    private boolean _expectResponseBody = true;

    /**
     * Strategy to use when validating the test case against the server's response.
     */
    private ValidationStrategy validationStrategy;

    /**
     * Logical name for test case.
     */
    private String name = "Test Case";

    /**
     * Creates a new instance of WebTestCase By default, a new WebTestCase instance will use the TokenizedValidator to
     * verify the response with the configured properties of the test case.
     */
    public WebTestCase() {
        validationStrategy = ValidationFactory.getInstance(TOKENIZED_STRATEGY);
    }

    /*
     * public methods ========================================================================
     */
    /**
     * Executes the test case.
     *
     * @throws Exception if the test fails for any reason.
     * @throws IllegalStateException if no request was configured or if no Validator is available at runtime.
     */
    @Override
    public void execute() throws Exception {

        // If no request was created, we can't run.
        if (httpRequest == null) {
            throw new IllegalStateException("[FATAL] HttpRequest is null.");
        }

        // If no stragey instance is available (strange, but has happened),
        // fail.
        if (validationStrategy == null) {
            throw new IllegalStateException("[FATAL] No Validator available.");
        }

        try {
            httpResponse = httpRequest.execute();
        } catch (Throwable t) {
            String message = t.getMessage();
            throw new Exception("[FATAL] Unexpected failure during " + "test execution." + (message == null ? t.toString() : message), t);
        }

        // Validate this test case instance
        if (!validationStrategy.validate(this)) {
            throw new Exception("Test FAILED!");
        }

    }

    /**
     * Sets the status code to be expected in the response from the server, i.e. 500 or 404, etc.
     *
     * @param statusCode the expected status code
     */
    public void setExpectedStatusCode(String statusCode) {
        responseSatusCode = statusCode;
    }

    /**
     * Sets the reason phrase to be expected in the response from the server.
     *
     * @param reasonPhrase the expected reason-phrase
     */
    public void setExpectedReasonPhrase(String reasonPhrase) {
        _reasonPhrase = reasonPhrase;
    }

    /**
     * Adds a header that is to be expected in the response from the server.
     *
     * @param header in the format of <headername>:<value> (test:foo)
     */
    public void addExpectedHeader(String header) {
        if (expectedHeaders == null) {
            expectedHeaders = new HashMap<>();
        }
        addHeader(expectedHeaders, header);
    }

    /**
     * Sets the path to the goldenfile the test case should use.
     *
     * @param gfPath a fully qualified path including filename.
     */
    public void setGoldenFilePath(String gfPath) {
        _goldenfilePath = gfPath;
    }

    /**
     * Sets the request that should be dispatched by this test case.
     *
     * @param request the HTTP request used for this test case
     */
    public void setRequest(HttpRequest request) {
        httpRequest = request;
    }

    /**
     * Adds a header that is should not be in the server's response.
     *
     * @param header in the format of <headername>:<value> (test:foo)
     */
    public void addUnexpectedHeader(String header) {
        if (unexpectedHeaders == null) {
            unexpectedHeaders = new HashMap<>();
        }

        addHeader(unexpectedHeaders, header);
    }

    /**
     * Enables/Disables an assertion that a response body is present.
     *
     * @param value a value of true will enable the assertion.
     */
    public void setAssertNoResponseBody(boolean value) {
    }

    /**
     * Sets a string that will be scanned for and expected in the response body from the server.
     *
     * If multiple search strings are required, one can either call this method for each string, or pass in one string with
     * pipe <code>|<code> delimiting the individual search strings within the large string.
     *
     * @param searchString a string expected in the server's response body
     */
    public void setResponseSearchString(String searchString) {
        if (responseSearchStrings == null) {
            responseSearchStrings = new ArrayList<>();
        }

        addSearchStrings(responseSearchStrings, searchString);
    }

    /**
     * Sets a string that will be scanned for and expected in the response body from the server.
     *
     * If multiple search strings are required, one can either call this method for each string, or pass in one string with
     * pipe <code>|<code> delimiting the individual search strings within the large string.
     *
     * @param searchString a case insensitive string expected in the server's response body
     */
    public void setResponseSearchStringIgnoreCase(String searchString) {
        if (searchStringsNoCase == null) {
            searchStringsNoCase = new ArrayList<>();
        }

        addSearchStrings(searchStringsNoCase, searchString);
    }

    /**
     * Sets a string that will be scanned for and should not be present in the response body from the server.
     *
     * If multiple search strings are required, one can either call this method for each string, or pass in one string with
     * pipe <code>|<code> delimiting the individual search strings within the large string.
     *
     * @param searchString a string that is not expected in the server's response body
     */
    public void setUnexpectedResponseSearchString(String searchString) {
        if (searchStringsNOTInResponse == null) {
            searchStringsNOTInResponse = new ArrayList<>();
        }

        addSearchStrings(searchStringsNOTInResponse, searchString);
    }

    /**
     * Sets a string or series of strings that will be searched for in the response. If multiple search strings are
     * required, one can either call this method for each string, or pass in one string with pipe <code>|<code> delimiting
     * the individual search strings within the large string.
     *
     * @param searchString a string that is not expected in the server's response body
     */
    public void setUnorderedSearchString(String searchString) {
        if (unorderedSearchStrings == null) {
            unorderedSearchStrings = new ArrayList<>();
        }

        addSearchStrings(unorderedSearchStrings, searchString);
    }

    /**
     * Returns the list of search strings.
     *
     * @return the list of search strings.
     */
    public List<String> getUnorderedSearchStrings() {
        return unorderedSearchStrings;
    }

    /**
     * Returns the response for this particular test case.
     *
     * @return an HttpResponse object
     */
    public HttpResponse getResponse() {
        return httpResponse;
    }

    /**
     * Returns an array of Header objects that are expected to be found in the responst.
     *
     * @return an array of headers
     */
    public Header[] getExpectedHeaders() {
        if (expectedHeaders == null) {
            return null;
        }
        return expectedHeaders.values().toArray(new Header[expectedHeaders.size()]);
    }

    /**
     * Returns an array of Header objects that are not expected to be found in the responst.
     *
     * @return an array of headers
     */
    public Header[] getUnexpectedHeaders() {
        if (unexpectedHeaders == null) {
            return null;
        }
        return unexpectedHeaders.values().toArray(new Header[unexpectedHeaders.size()]);
    }

    /**
     * Returns the status code expected to be found in the server's response
     *
     * @return status code
     */
    public String getStatusCode() {
        return responseSatusCode;
    }

    /**
     * Returns the reason phrase that is expected to be found in the server's response.
     *
     * @return reason phrase
     */
    public String getReasonPhrase() {
        return _reasonPhrase;
    }

    /**
     * Returns the configured list of strings that will be used when scanning the server's response.
     *
     * @return list of Strings
     */
    public List<String> getSearchStrings() {
        if (responseSearchStrings == null) {
            return null;
        }
        return responseSearchStrings;
    }

    /**
     * Returns the configured list of strings that will be used when scanning the server's response.
     *
     * @return list of case insensitive Strings
     */
    public List<String> getSearchStringsNoCase() {
        if (searchStringsNoCase == null) {
            return null;
        }
        return searchStringsNoCase;
    }

    /**
     * Returns the configured list of strings that will be used when scanning the server's response (these strings are not
     * expected in the response).
     *
     * @return list of Strings
     */
    public List<String> getUnexpectedSearchStrings() {
        if (searchStringsNOTInResponse == null) {
            return null;
        }
        return searchStringsNOTInResponse;
    }

    /**
     * Returns an indicator on whether a response body is expected or not.
     *
     * @return boolean value
     */
    public boolean getExpectResponseBody() {
        return _expectResponseBody;
    }

    /**
     * Returns the HttpRequest for this particular test case.
     *
     * @return HttpRequest of this test case
     */
    public org.jakartaee8.urlclient.http.HttpRequest getRequest() {
        return httpRequest;
    }

    /**
     * Returns the path to the goldenfile.
     *
     * @return path to the goldenfile
     */
    public String getGoldenfilePath() {
        return _goldenfilePath;
    }

    /**
     * Returns the state for this particular test case.
     *
     * @return test state
     */
    @Override
    public Object getState() {
        if (httpResponse != null) {
            return httpResponse.getState();
        }

        // an initial request for state
        return httpRequest.getState();
    }

    /**
     * Sets the state for this test case.
     *
     * @param state test state
     */
    @Override
    public void setState(Object state) {
        httpRequest.setState((HttpState) state);
    }

    /**
     * Sets a logical name for this test case.
     *
     * @param name the logical name for this test
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the logical name for this test case.
     *
     * @return test name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Sets the validation strategy for this test case instance.
     *
     * @param validator - the fully qualified class name of the response validator to use.
     */
    public void setStrategy(String validator) {
        ValidationStrategy validationStrategy = ValidationFactory.getInstance(validator);
        if (validationStrategy != null) {
            this.validationStrategy = validationStrategy;
        } else {
            TestUtil.logMsg("[WebTestCase][WARNING] An attempt was made to use a " + "non-existing validator (" + validator + ")"
                    + ".  Falling back to the TokenizedValidator");
        }
    }

    /**
     * Returns the class name of the response validator used.
     *
     * @return the fully qualified class of the validator used
     */
    public String getStrategy() {
        return validationStrategy.getClass().getName();
    }

    /*
     * Private Methods ===========================================================================
     */

    /**
     * Utility method to add headers to test case holder objects.
     *
     * @param map the object in which to add header to
     * @param headerString String representation of a header in the form of <headername>:<value>
     */
    private void addHeader(Map<String, Header> map, String headerString) {
        TestUtil.logTrace("[WebTestCase] addHeader utility method called: " + headerString);

        StringTokenizer st = new StringTokenizer(headerString, "|");
        while (st.hasMoreTokens()) {
            String head = st.nextToken();
            int colIdx = head.indexOf(':');
            String name = head.substring(0, colIdx).trim();
            String value = head.substring(colIdx + 1).trim();
            TestUtil.logTrace("[WebTestCase] Adding test header: " + name + ", " + value);

            Header header = map.get(name);
            if (header != null) {
                map.put(name, createNewHeader(value, header));
            } else {
                map.put(name, new Header(name, value));
            }
        }
    }

    /**
     * Creates a new header based of the provided header and value.
     *
     * @param newValue - the new value to add to an existing header
     * @param header - the original header
     * @return new Header
     */
    private Header createNewHeader(String newValue, Header header) {
        return new Header(header.getName(), header.getValue() + ", " + newValue);
    }

    /**
     * Adds a search string to the provided list
     *
     * @param stringList - list to add the string to
     * @param s - the search string
     */
    private void addSearchStrings(List<String> stringList, String s) {
        StringTokenizer st = new StringTokenizer(s, "|");
        while (st.hasMoreTokens()) {
            stringList.add(st.nextToken());
        }
    }
}
