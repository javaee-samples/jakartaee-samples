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

package org.jakartaee8.urlclient.validation;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

import org.jakartaee8.urlclient.Goldenfile;
import org.jakartaee8.urlclient.TestUtil;

/**
 * <pre>
 * This class provides all of the functionality of the
 * WebValidatorBase class.  Additionally, it will compare
 * the server's response body with the test case's configured
 * goldenfile using a StringTokenizer.
 * </pre>
 */
public class TokenizedValidator extends WebValidatorBase {

    /**
     * System property that will cause the specified goldenfile to be written if it doesn't already exist.
     */
    private static final String RECORD_GF = "ts.record.gf";

    /**
     * Creates a new instance of TokenizedValidator
     */
    public TokenizedValidator() {
    }

    /*
     * protected methods ========================================================================
     */

    /**
     * Compare the server response and golenfile using a StringTokenizer.
     *
     * @return true if response and goldenfile are the same.
     * @throws IOException if an error occurs will processing the Goldenfile
     */
    @Override
    protected boolean checkGoldenfile() throws IOException {
        String gf;
        String path = testCase.getGoldenfilePath();
        String enc = response.getResponseEncoding();

        if (path == null) {
            return true;
        }

        Goldenfile file = new Goldenfile(testCase.getGoldenfilePath(), enc);

        try {
            gf = file.getGoldenFileAsString();
        } catch (IOException ioe) {
            TestUtil.logErr("[TokenizedValidator] Unexpected exception while accessing " + "goldenfile! " + ioe.toString());
            return false;
        }

        String responseBody = response.getResponseBodyAsString();
        StringTokenizer gfTokenizer = new StringTokenizer(gf);
        StringTokenizer resTokenizer = new StringTokenizer(responseBody);
        int gfCount = gfTokenizer.countTokens();
        int resCount = resTokenizer.countTokens();

        // Logic to handle the recording of goldenfiles.
        if (gf.equals("NO GOLDENFILE FOUND") && Boolean.getBoolean(RECORD_GF)) {

            TestUtil.logTrace("[TokenizedValidator][INFO] RECORDING GOLDENFILE: " + path);
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(path), enc);
            out.write(responseBody);
            out.flush();
            out.close();
        }

        // If the token counts are the same, continue checking
        // each individual token, otherwise, immediately fail.
        if (gfCount == resCount) {
            while (gfTokenizer.hasMoreTokens()) {
                String expected = gfTokenizer.nextToken();
                String actual = resTokenizer.nextToken();
                if (!expected.equals(actual)) {
                    StringBuffer sb = new StringBuffer(255);
                    sb.append("[TokenizedValidator]: Server's response and ");
                    sb.append("goldenfile to not match!\n");
                    sb.append("\n            Goldenfile token: ").append(expected);
                    sb.append("\n            Response token:   ").append(actual);
                    TestUtil.logErr(sb.toString());
                    dumpResponseInfo(responseBody, gf);
                    return false;
                }
            }
        } else {
            TestUtil.logErr("[TokenizedValidator]: Token count between server response " + "and goldenfile do not match.\n Response Token" + "count: "
                    + resCount + "\nGoldenfile Token count: " + gfCount);

            dumpResponseInfo(responseBody, gf);
            return false;
        }
        TestUtil.logTrace("[TokenizedValidator]: Server's response matches the " + "configured goldenfile.");
        return true;
    }

    /*
     * private methods ========================================================================
     */

    /**
     * Dumps the response from the server and the content of the Goldenfile/
     *
     * @param serverResponse the response body from the server.
     * @param goldenFile the test goldenfile
     */
    private static void dumpResponseInfo(String serverResponse, String goldenFile) {
        StringBuffer sb = new StringBuffer(255);
        sb.append("\nServer Response (below):\n");
        sb.append("------------------------------------------\n");
        sb.append(serverResponse);
        sb.append("\n------------------------------------------\n");
        sb.append("\nGoldenfile (below):\n");
        sb.append("------------------------------------------\n");
        sb.append(goldenFile);
        sb.append("\n------------------------------------------\n");
        TestUtil.logErr(sb.toString());
    }
}
