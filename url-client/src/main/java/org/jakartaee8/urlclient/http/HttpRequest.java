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

package org.jakartaee8.urlclient.http;

import static org.apache.commons.httpclient.cookie.CookiePolicy.RFC_2109;
import static org.jakartaee8.urlclient.TestUtil.logMsg;
import static org.jakartaee8.urlclient.TestUtil.logTrace;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.EntityEnclosingMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.protocol.DefaultProtocolSocketFactory;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.commons.httpclient.protocol.SSLProtocolSocketFactory;
import org.jakartaee8.urlclient.Util;

/**
 * Represents an executable HTTP client Request.
 *
 * <p>
 * After configuration, calling {@link HttpRequest#execute()} will actually do the HTTP request to the configured
 * URL.
 *
 */
public class HttpRequest {

    /**
     * Default HTTP port.
     */
    public static int DEFAULT_HTTP_PORT = 80;

    /**
     * Default HTTP SSL port.
     */
    public static final int DEFAULT_SSL_PORT = 443;

    /**
     * No authentication
     */
    public static final int NO_AUTHENTICATION = 0;

    /**
     * Basic authentication
     */
    public static final int BASIC_AUTHENTICATION = 1;

    /**
     * Digest authenctication
     */
    public static final int DIGEST_AUTHENTICATION = 2;

    /**
     * Method representation of request.
     */
    private HttpMethod httpMethod;

    /**
     * Target web container host
     */
    private String host;

    /**
     * Target web container port
     */
    private int port = DEFAULT_HTTP_PORT;

    /**
     * Is the request going over SSL
     */
    private boolean isSecure;

    /**
     * HTTP state
     */
    private HttpState state;

    /**
     * Original request line for this request.
     */
    private String requestLine;

    /**
     * Authentication type for current request
     */
    private int authType = NO_AUTHENTICATION;

    /**
     * Flag to determine if session tracking will be used or not.
     */
    private boolean useCookies;

    /**
     * Content length of request body.
     */
    private int contentLength;

    Header[] headers;

    protected HttpClient client;

    /**
     * Creates new HttpRequest based of the passed request line. The request line provied must be in the form of:<br>
     *
     * <pre>
     *     METHOD PATH HTTP-VERSION
     *     Ex.  GET /index.html HTTP/1.0
     * </pre>
     */
    public HttpRequest(String requestLine, String host, int port) {
        client = new HttpClient();
        httpMethod = MethodFactory.getInstance(requestLine);
        httpMethod.setFollowRedirects(false);
        this.host = host;
        this.port = port;

        if (port == DEFAULT_SSL_PORT) {
            isSecure = true;
        }

        // If we got this far, the request line is in the proper
        // format
        this.requestLine = requestLine;
    }

    /*
     * public methods ========================================================================
     */

    /**
     * <code>getRequestPath</code> returns the request path for this particular request.
     *
     * @return String request path
     */
    public String getRequestPath() {
        return httpMethod.getPath();
    }

    /**
     * <code>getRequestMethod</code> returns the request type, i.e., GET, POST, etc.
     *
     * @return String request type
     */
    public String getRequestMethod() {
        return httpMethod.getName();
    }

    /**
     * <code>isSecureConnection()</code> indicates if the Request is secure or not.
     *
     * @return boolean whether Request is using SSL or not.
     */
    public boolean isSecureRequest() {
        return isSecure;
    }

    /**
     * <code>setSecureRequest</code> configures this request to use SSL.
     *
     * @param secure - whether the Request uses SSL or not.
     */
    public void setSecureRequest(boolean secure) {
        isSecure = secure;
    }

    /**
     * <code>setContent</code> will set the body for this request. Note, this is only valid for POST and PUT operations,
     * however, if called and the request represents some other HTTP method, it will be no-op'd.
     *
     * @param content request content
     */
    public void setContent(String content) {
        if (httpMethod instanceof EntityEnclosingMethod) {
            ((EntityEnclosingMethod) httpMethod).setRequestEntity(new StringRequestEntity(content));
        }
        contentLength = content.length();
    }

    /**
     * <code>setAuthenticationCredentials configures the request to
     * perform authentication.
     *
     * <p><code>username</code> and <code>password</code> cannot be null.
     * </p>
     *
     * <p>
     * It is legal for <code>realm</code> to be null.
     * </p>
     *
     * @param username the user
     * @param password the user's password
     * @param authType authentication type
     * @param realm authentication realm
     */
    public void setAuthenticationCredentials(String username, String password, int authType, String realm) {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null");
        }
        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }

        getState().setCredentials(new AuthScope(host, port, realm), new UsernamePasswordCredentials(username, password));
        logTrace("[HttpRequest] Added credentials for '" + username + "' with password '" + password + "' in realm '" + realm + "'");

        this.authType = authType;
    }

    /**
     * <code>addRequestHeader</code> adds a request header to this request. If a request header of the same name already
     * exists, the new value, will be added to the set of already existing values.
     *
     * <strong>NOTE:</strong> that header names are not case-sensitive.
     *
     * @param headerName request header name
     * @param headerValue request header value
     */
    public void addRequestHeader(String headerName, String headerValue) {
        httpMethod.addRequestHeader(headerName, headerValue);
        logTrace("[HttpRequest] Added request header: " + httpMethod.getRequestHeader(headerName).toExternalForm());
    }

    public void addRequestHeader(String header) {
        StringTokenizer st = new StringTokenizer(header, "|");
        while (st.hasMoreTokens()) {
            String h = st.nextToken();
            if (h.toLowerCase().startsWith("cookie")) {
                createCookie(h);
                continue;
            }
            int col = h.indexOf(':');
            addRequestHeader(h.substring(0, col).trim(), h.substring(col + 1).trim());
        }
    }

    /**
     * <code>setRequestHeader</code> sets a request header for this request overwritting any previously existing
     * header/values with the same name.
     *
     * <strong>NOTE:</strong> Header names are not case-sensitive.
     *
     * @param headerName request header name
     * @param headerValue request header value
     */
    public void setRequestHeader(String headerName, String headerValue) {
        httpMethod.setRequestHeader(headerName, headerValue);
        logTrace("[HttpRequest] Set request header: " + httpMethod.getRequestHeader(headerName).toExternalForm());

    }

    /**
     * <code>setFollowRedirects</code> indicates whether HTTP redirects are followed. By default, redirects are not
     * followed.
     */
    public void setFollowRedirects(boolean followRedirects) {
        httpMethod.setFollowRedirects(followRedirects);
    }

    /**
     * <code>getFollowRedirects</code> indicates whether HTTP redirects are followed.
     */
    public boolean getFollowRedirects() {
        return httpMethod.getFollowRedirects();
    }

    /**
     * <code>setState</code> will set the HTTP state for the current request (i.e. session tracking). This has the side
     * affect
     */
    public void setState(HttpState state) {
        this.state = state;
        useCookies = true;
    }

    /**
     * <code>execute</code> will dispatch the current request to the target server.
     *
     * @return HttpResponse the server's response.
     * @throws IOException if an I/O error occurs during dispatch.
     */
    public HttpResponse execute() throws IOException {
        String scheme;
        int defaultPort;
        ProtocolSocketFactory factory;

        if (isSecure) {
            scheme = "https";
            defaultPort = DEFAULT_SSL_PORT;
            factory = new SSLProtocolSocketFactory();
        } else {
            scheme = "http";
            defaultPort = DEFAULT_HTTP_PORT;
            factory = new DefaultProtocolSocketFactory();
        }

        Protocol protocol = new Protocol(scheme, factory, defaultPort);
        HttpConnection httpConnection = new HttpConnection(host, port, protocol);

        if (httpMethod.getFollowRedirects()) {
            client = new HttpClient();

            if (httpConnection.isOpen()) {
                throw new IllegalStateException("Connection incorrectly opened");
            }

            httpConnection.open();

            logMsg("[HttpRequest] Dispatching request: '" + requestLine + "' to target server at '" + host + ":" + port + "'");

            addSupportHeaders();
            headers = httpMethod.getRequestHeaders();

            logTrace("########## The real value set: " + httpMethod.getFollowRedirects());

            client.getHostConfiguration().setHost(host, port, protocol);

            client.executeMethod(httpMethod);

            return new HttpResponse(host, port, isSecure, this.httpMethod, getState());
        } else {
            if (httpConnection.isOpen()) {
                throw new IllegalStateException("Connection incorrectly opened");
            }

            httpConnection.open();

            logMsg("[HttpRequest] Dispatching request: '" + requestLine + "' to target server at '" + host + ":" + port + "'");

            addSupportHeaders();
            headers = this.httpMethod.getRequestHeaders();

            logTrace("########## The real value set: " + httpMethod.getFollowRedirects());

            httpMethod.execute(getState(), httpConnection);

            return new HttpResponse(host, port, isSecure, httpMethod, getState());
        }
    }

    /**
     * Returns the current state for this request.
     *
     * @return HttpState current state
     */
    public HttpState getState() {
        if (state == null) {
            state = new HttpState();
        }
        return state;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(255);
        sb.append("[REQUEST LINE] -> ").append(requestLine).append('\n');

        if (headers != null && headers.length != 0) {

            for (Header _header : headers) {
                sb.append("       [REQUEST HEADER] -> ");
                sb.append(_header.toExternalForm()).append('\n');
            }
        }

        if (contentLength != 0) {
            sb.append("       [REQUEST BODY LENGTH] -> ").append(contentLength);
            sb.append('\n');
        }

        return sb.toString();
    }

    /*
     * private methods ========================================================================
     */

    private void createCookie(String cookieHeader) {
        String cookieLine = cookieHeader.substring(cookieHeader.indexOf(':') + 1).trim();
        StringTokenizer st = new StringTokenizer(cookieLine, " ;");
        Cookie cookie = new Cookie();
        cookie.setVersion(1);

        getState();

        if (cookieLine.indexOf("$Version") == -1) {
            cookie.setVersion(0);
            httpMethod.getParams().setCookiePolicy(CookiePolicy.NETSCAPE);
        }

        while (st.hasMoreTokens()) {
            String token = st.nextToken();

            if (token.charAt(0) != '$' && !token.startsWith("Domain") && !token.startsWith("Path")) {
                cookie.setName(token.substring(0, token.indexOf('=')));
                cookie.setValue(token.substring(token.indexOf('=') + 1));
            } else if (token.indexOf("Domain") > -1) {
                cookie.setDomainAttributeSpecified(true);
                cookie.setDomain(token.substring(token.indexOf('=') + 1));
            } else if (token.indexOf("Path") > -1) {
                cookie.setPathAttributeSpecified(true);
                cookie.setPath(token.substring(token.indexOf('=') + 1));
            }
        }
        state.addCookie(cookie);

    }

    /**
     * Adds any support request headers necessary for this request. These headers will be added based on the state of the
     * request.
     */
    private void addSupportHeaders() {

        // Authentication headers
        // NOTE: Possibly move logic to generic method
        switch (authType) {
        case NO_AUTHENTICATION:
            break;
        case BASIC_AUTHENTICATION:
            setBasicAuthorizationHeader();
            break;
        case DIGEST_AUTHENTICATION:
            throw new UnsupportedOperationException("Digest Authentication is not currently " + "supported");
        }

        // A Host header will be added to each request to handle
        // cases where virtual hosts are used, or there is no DNS
        // available on the system where the container is running.
        setHostHeader();

        // Content length header
        setContentLengthHeader();

        // Cookies
        setCookieHeader();
    }

    /**
     * Sets a basic authentication header in the request is Request is configured to use basic authentication
     */
    private void setBasicAuthorizationHeader() {
        UsernamePasswordCredentials cred = (UsernamePasswordCredentials) getState().getCredentials(new AuthScope(host, port, null));
        String authString = null;
        if (cred != null) {
            authString = "Basic " + Util.getBase64EncodedString(cred.getUserName() + ":" + cred.getPassword());
        } else {
            logTrace("[HttpRequest] NULL CREDENTIALS");
        }

        httpMethod.setRequestHeader("Authorization", authString);
    }

    /**
     * Sets a Content-Length header in the request if content is present
     */
    private void setContentLengthHeader() {
        if (contentLength > 0) {
            httpMethod.setRequestHeader("Content-Length", Integer.toString(contentLength));
        }
    }

    /**
     * Sets a host header in the request. If the configured host value is an IP address, the Host header will be sent, but
     * without any value.
     *
     * If we adhered to the HTTP/1.1 spec, the Host header must be empty of the target server is identified via IP address.
     * However, no user agents I've tested follow this. And if a custom client library does this, it may not work properly
     * with the target server. For now, the Host request-header will always have a value.
     */
    private void setHostHeader() {
        if (port == DEFAULT_HTTP_PORT || port == DEFAULT_SSL_PORT) {
            httpMethod.setRequestHeader("Host", host);
        } else {
            httpMethod.setRequestHeader("Host", host + ":" + port);
        }
    }

    /**
     * Sets a Cookie header if this request is using cookies.
     */
    private void setCookieHeader() {
        if (useCookies) {
            Cookie[] cookies = state.getCookies();
            if (cookies != null && cookies.length > 0) {
                Header header = CookiePolicy.getCookieSpec(RFC_2109).formatCookieHeader(state.getCookies());
                if (header != null) {
                    httpMethod.setRequestHeader(header);
                }
            }
        }
    }
}
