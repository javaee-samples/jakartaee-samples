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
package org.jakartaee9.ejblite.tx.tests;

import java.util.logging.Logger;

public final class Asserts {
    private static Logger logger = Logger.getLogger("Asserts");

    private Asserts() {
    }

    public static Logger getLogger() {
        return logger;
    }

    /**
     * Similar to JUnit Assert, but this class appends message to both true and false outcome.
     *
     * @param messagePrefix
     * @param expected
     * @param actual
     * @return a String showing a true outcome
     * @throws java.lang.RuntimeException if comparison is false
     */
    public static String assertEquals(final String messagePrefix, final Object expected, final Object actual) throws RuntimeException {
        final StringBuilder sb = new StringBuilder();
        assertEquals(messagePrefix, expected, actual, sb);
        return sb.toString();
    }

    public static void assertEquals(final String messagePrefix, final Object expected, final Object actual, final StringBuilder sb) throws RuntimeException {
        sb.append("\n");
        if (messagePrefix != null) {
            sb.append("\n").append(messagePrefix).append(" ");
        }
        if (equalsOrNot(expected, actual)) {
            sb.append("Got the expected result:").append(actual).append("\t");
        } else {
            sb.append("Expecting ").append(expected).append(", but actual ").append(actual);
            throw new RuntimeException(sb.toString());
        }
    }

    public static String assertNotEquals(final String messagePrefix, final Object expected, final Object actual) throws RuntimeException {
        final StringBuilder sb = new StringBuilder();
        assertNotEquals(messagePrefix, expected, actual, sb);
        return sb.toString();
    }

    public static void assertNotEquals(final String messagePrefix, final Object expected, final Object actual, final StringBuilder sb) throws RuntimeException {
        sb.append("\n");
        if (messagePrefix != null) {
            sb.append(messagePrefix).append(" ");
        }
        if (!equalsOrNot(expected, actual)) {
            sb.append("Got the expected NotEquals result. compareTo:").append(expected).append(", actual:").append(actual).append("\t");
        } else {
            sb.append("Expecting NotEquals, but got equals. compareTo:").append(expected).append(", and actual: ").append(actual);
            throw new RuntimeException(sb.toString());
        }
    }

    public static void assertCloseEnough(final String messagePrefix, long expected, long actual, long ignoreableDiff, StringBuilder sb) {
        sb.append("\n");
        if (messagePrefix != null) {
            sb.append(messagePrefix).append(" ");
        }
        long dif = Math.abs(actual - expected);
        if (dif <= ignoreableDiff) {
            sb.append("Got the expected result:");
            sb.append("the diff between expected " + expected + ", and actual " + actual + " is " + dif
                    + ", equals or less than the ignoreableDiff " + ignoreableDiff);
        } else {
            throw new RuntimeException("the diff between expected " + expected + ", and actual " + actual + " is " + dif
                    + ", greater than the ignoreableDiff " + ignoreableDiff);
        }
    }

    public static String assertGreaterThan(final String messagePrefix, long arg1, long arg2) throws RuntimeException {
        final StringBuilder sb = new StringBuilder();
        assertGreaterThan(messagePrefix, arg1, arg2, sb);
        return sb.toString();
    }

    public static void assertGreaterThan(final String messagePrefix, long arg1, long arg2, final StringBuilder sb) throws RuntimeException {
        sb.append("\n");
        if (messagePrefix != null) {
            sb.append(messagePrefix).append(" ");
        }

        if (arg1 > arg2) {
            sb.append("Got the expected result:").append(arg1).append(">").append(arg2).append("\t");
        } else {
            sb.append("Expecting ").append(arg1).append(">").append(arg2).append(", but failed");
            throw new RuntimeException(sb.toString());
        }
    }

    private static boolean equalsOrNot(final Object expected, final Object actual) {
        boolean sameOrNot = false;
        if (expected == null) {
            if (actual == null) {
                sameOrNot = true;
            }
        } else {
            sameOrNot = expected.equals(actual);
        }

        return sameOrNot;
    }
}
