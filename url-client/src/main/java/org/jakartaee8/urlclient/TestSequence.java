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

/**
 * This class represents a logic sequence for executing a series of test cases in a specific order. In this case, the
 * execution order will be the same order that the test cases were added to the sequence.
 *
 * The <code>TestSequence</code> has the added benefit of managing state between the test invocations.
 */
public class TestSequence implements TestCase {

    private Map<String, TestCase> testMap;
    private List<String> testNameList;
    private String name = "DEFAULT";
    private boolean managed;

    /** Creates a new instance of TestSequence */
    public TestSequence() {
        testMap = new HashMap<>();
        testNameList = new ArrayList<>();
    }

    /*
     * public methods ========================================================================
     */

    /**
     * Executes the test sequence.
     *
     * @throws TestFailureException if any test in the sequence fails.
     */
    @Override
    public void execute() throws Exception {
        TestUtil.logTrace("[TestSequence] Beginning execution of sequence '" + name + "' containing '" + testNameList.size() + "' test entities.");

        for (String testName : testNameList) {
            TestUtil.logTrace("[TestSequence] Executing test case: " + testName);

            TestCase testCase = testMap.get(testName);
            if (managed) {
                testCase.setState(testCase.getState());
            }
            testCase.execute();

            TestUtil.logTrace("[TestSequence] Test case: " + testName + "complete.");
        }

        TestUtil.logTrace("[TestSequence] Sequence complete!");
    }

    /**
     * <code>enableStateManagement</code>, when enabled, will cause the test sequence to manage state between test case
     * invocations. By default, a test sequence will not manage state.
     *
     * @param value a value of true enables session management.
     */
    public void enableStateManagement(boolean value) {
        managed = value;
    }

    /**
     * Returns a value indicating whether state management is enabled or not.
     *
     * @return boolean value indicating state management status
     */
    public boolean isStateManagementEnabled() {
        return managed;
    }

    /**
     * Adds a test case to the sequence denoted by a unique identifier.
     *
     * @param identifier for this test case
     * @param cs the test case
     */
    public void addTestCase(String identifier, TestCase cs) {
        testMap.put(identifier, cs);
        testNameList.add(identifier);
    }

    /**
     * Removes a test case from the sequence.
     *
     * @param identifier
     */
    public void removeTestCase(String identifier) {
        testMap.remove(identifier);
        testNameList.remove(identifier);
    }

    /**
     * Sets the name of this TestSequence. If not set, the default value is "DEFAULT".
     *
     * @param name
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the name of this TestSequence.
     *
     * @return sequence name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Sets the initial state for the test sequence to use when invoking test cases.
     *
     * @param state the initial state
     */
    @Override
    public void setState(Object state) {
    }

    /**
     * Returns the state of the sequence. Note: This value can differ depending on when it has been called in relation to
     * when execute has been called.
     *
     * @return state of the sequence
     */
    @Override
    public Object getState() {
        throw new UnsupportedOperationException();
    }

}
