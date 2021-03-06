/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
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

package org.jakartaee8.urlclient.http;

import static org.jakartaee8.urlclient.Util.getEncodedStringFromStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.HttpVersion;

/**
 * This class represents an HTTP response from the server.
 */

public class HttpResponse {

    /**
     * Default encoding based on Servlet Specification
     */
    private static final String DEFAULT_ENCODING = "ISO-8859-1";

    /**
     * Content-Type header
     */
    private static final String CONTENT_TYPE = "Content-Type";

    /**
     * Wrapped HttpMethod used to pull response info from.
     */
    private HttpMethod httpMethod;

    /**
     * HttpState obtained after execution of request
     */
    private HttpState httpState;

    /**
     * Charset encoding returned in the response
     */
    private String encoding = DEFAULT_ENCODING;

    /**
     * The response body. Initialized after first call to one of the getResponseBody methods and cached for subsequent
     * calls.
     */
    private String responseBody;

    /**
     * Host name used for processing request
     */
    private String host;

    /**
     * Port number used for processing request
     */
    private int port;

    /**
     * Issecure
     */
    private boolean isSecure;

    /** Creates new HttpResponse */
    public HttpResponse(String host, int port, boolean isSecure, HttpMethod method, HttpState state) {
        this.host = host;
        this.port = port;
        this.isSecure = isSecure;
        httpMethod = method;
        httpState = state;
    }

    /*
     * public methods ========================================================================
     */

    /**
     * Returns the HTTP status code returned by the server
     *
     * @return HTTP status code
     */
    public String getStatusCode() {
        return Integer.toString(httpMethod.getStatusCode());
    }

    /**
     * Returns the HTTP reason-phrase returned by the server
     *
     * @return HTTP reason-phrase
     */
    public String getReasonPhrase() {
        return httpMethod.getStatusText();
    }

    /**
     * Returns the headers received in the response from the server.
     *
     * @return response headers
     */
    public Header[] getResponseHeaders() {
        return httpMethod.getResponseHeaders();
    }

    /**
     * Returns the headers designated by the name provided.
     *
     * @return response headers
     */
    public Header[] getResponseHeaders(String headerName) {
        return httpMethod.getResponseHeaders(headerName);
    }

    /**
     * Returns the response header designated by the name provided.
     *
     * @return a specfic response header or null if the specified header doesn't exist.
     */
    public Header getResponseHeader(String headerName) {
        return httpMethod.getResponseHeader(headerName);
    }

    /**
     * Returns the response body as a byte array using the charset specified in the server's response.
     *
     * @return response body as an array of bytes.
     */
    public byte[] getResponseBodyAsBytes() throws IOException {
        return getEncodedResponse().getBytes();
    }

    /**
     * Returns the response as bytes (no encoding is performed by client.
     *
     * @return the raw response bytes
     * @throws IOException if an error occurs reading from server
     */
    public byte[] getResponseBodyAsRawBytes() throws IOException {
        return httpMethod.getResponseBody();
    }

    /**
     * Returns the response body as a string using the charset specified in the server's response.
     *
     * @return response body as a String
     */
    public String getResponseBodyAsString() throws IOException {
        return getEncodedResponse();
    }

    /**
     * Returns the response body of the server without being encoding by the client.
     *
     * @return an unecoded String representation of the response
     * @throws IOException if an error occurs reading from the server
     */
    public String getResponseBodyAsRawString() throws IOException {
        return httpMethod.getResponseBodyAsString();
    }

    /**
     * Returns the response body as an InputStream using the encoding specified in the server's response.
     *
     * @return response body as an InputStream
     */
    public InputStream getResponseBodyAsStream() throws IOException {
        return new ByteArrayInputStream(getEncodedResponse().getBytes());
    }

    /**
     * Returns the response body as an InputStream without any encoding applied by the client.
     *
     * @return an InputStream to read the response
     * @throws IOException if an error occurs reading from the server
     */
    public InputStream getResponseBodyAsRawStream() throws IOException {
        return httpMethod.getResponseBodyAsStream();
    }

    /**
     * Returns the charset encoding for this response.
     *
     * @return charset encoding
     */
    public String getResponseEncoding() {
        Header content = httpMethod.getResponseHeader(CONTENT_TYPE);
        if (content != null) {
            String headerVal = content.getValue();
            int idx = headerVal.indexOf(";charset=");
            if (idx > -1) {
                // content encoding included in response
                encoding = headerVal.substring(idx + 9);
            }
        }
        return encoding;
    }

    /**
     * Returns the post-request state.
     *
     * @return an HttpState object
     */
    public HttpState getState() {
        return httpState;
    }

    /**
     * Displays a String representation of the response.
     *
     * @return string representation of response
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(255);

        sb.append("[RESPONSE STATUS LINE] -> ");
        sb.append(((HttpMethodBase) httpMethod).getParams().getVersion().equals(HttpVersion.HTTP_1_1) ? "HTTP/1.1 " : "HTTP/1.0 ");
        sb.append(httpMethod.getStatusCode()).append(' ');
        sb.append(httpMethod.getStatusText()).append('\n');
        Header[] headers = httpMethod.getResponseHeaders();
        if (headers != null && headers.length != 0) {
            for (int i = 0; i < headers.length; i++) {
                sb.append("       [RESPONSE HEADER] -> ");
                sb.append(headers[i].toExternalForm()).append('\n');
            }
        }

        String resBody;
        try {
            resBody = httpMethod.getResponseBodyAsString();
        } catch (IOException ioe) {
            resBody = "UNEXECTED EXCEPTION: " + ioe.toString();
        }
        if (resBody != null && resBody.length() != 0) {
            sb.append("------ [RESPONSE BODY] ------\n");
            sb.append(resBody);
            sb.append("\n-----------------------------\n\n");
        }
        return sb.toString();
    }

    /*
     * Eventually they need to come from _method
     */

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getProtocol() {
        return isSecure ? "https" : "http";
    }

    public String getPath() {
        return httpMethod.getPath();
    }

    /*
     * Private Methods ==========================================================================
     */

    /**
     * Returns the response body using the encoding returned in the response.
     *
     * @return encoded response String.
     */
    private String getEncodedResponse() throws IOException {
        if (responseBody == null) {
            responseBody = getEncodedStringFromStream(httpMethod.getResponseBodyAsStream(), getResponseEncoding());
        }

        return responseBody;
    }
}
