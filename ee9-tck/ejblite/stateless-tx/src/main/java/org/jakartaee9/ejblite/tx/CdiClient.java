/*
 * Copyright (c) 2022 Oracle and/or its affiliates. All rights reserved.
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

package org.jakartaee9.ejblite.tx;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named("client")
@RequestScoped
public class CdiClient {

    protected TestBean testBean;

    protected TestBean getTestBean() {
        return testBean;
    }

    @EJB(beanInterface = TestBean.class, beanName = "TestBean")
    private void setTestBean(TestBean b) {
        testBean = b;
    }

    public void runTest(String testName) {
        this.testName = testName;
        try {
            Method testMethod = getClass().getMethod(testName);
            testMethod.invoke(this, (Object[]) null);
            passOrFail(true);
        } catch (NoSuchMethodException e) {
            passOrFail(false, "Failed to get test method " + testName, e.toString());
        } catch (IllegalAccessException e) {
            passOrFail(false, "Failed to access test method (is it public?) " + testName, e.toString());
        } catch (InvocationTargetException e) {
            // If it's EJBException with a cause, just include the cause in the
            // message
            String msg = "Failed with exception ";
            Throwable root = e.getCause();
            if (root == null) {
                root = e;
            } else if (root instanceof EJBException) {
                Throwable cause2 = root.getCause();
                if (cause2 != null) {
                    root = cause2;
                    msg = "Failed with EJBException caused by ";
                }
            }
            passOrFail(false, msg, printStackTraceToString(root));
        } catch (Exception e) {
            passOrFail(false, "Unexpected exception for test " + testName, printStackTraceToString(e));
        }
    }


    /*
     * testName: mandatory
     *
     * @test_Strategy:
     */
    public void mandatory() {
        appendReason("Persist and flush:", getTestBean().mandatory(true));
        appendReason("Persist without flush:", getTestBean().mandatory(false));
    }

    /*
     * testName: required
     *
     * @test_Strategy:
     */
    public void required() {
        appendReason("Persist without flush:", getTestBean().required(false));
        appendReason("Persist and flush:", getTestBean().required(true));

        appendReason("Persist without flush:", getTestBean().requiredNoExistingTransaction(false));
        appendReason("Persist and flush:", getTestBean().requiredNoExistingTransaction(true));
    }

    /*
     * testName: requiredNoExistingTransaction
     *
     * @test_Strategy:
     */
    public void requiredNoExistingTransaction() {
        appendReason("Persist without flush:", getTestBean().requiredNoExistingTransaction(false));
        appendReason("Persist and flush:", getTestBean().requiredNoExistingTransaction(true));
    }

    /*
     * testName: supports
     *
     * @test_Strategy:
     */
    public void supports() {
        appendReason("Persist without flush:", getTestBean().supports(false));
        appendReason("Persist and flush:", getTestBean().supports(true));
    }

    /*
     * testName: requiresNew
     *
     * @test_Strategy:
     */
    public void requiresNew() {
        appendReason("Persist without flush:", getTestBean().requiresNew(false));
        appendReason("Persist and flush:", getTestBean().requiresNew(true));
    }


    /////////////////////////////////////////////////////////////////////
    // properties that may communicate with runtime environment (vehicles) and
    // may be configured as managed-property
    /////////////////////////////////////////////////////////////////////
    /**
     * status and reason indicate test status and may be configured as managed-property. They must be updated by subclass
     * test clients, typically indirectly via invoking pass or fail method.
     */
    private String status;

    private String reason;

    /**
     * the current testName
     */
    private String testName;


    private Context context;

    /**
     * Maps the lookup name of EJB injections to their portable global jndi names.
     */
    private Map<String, String> jndiMapping = new HashMap<String, String>();


    /////////////////////////////////////////////////////////////////////
    // internal fields
    /////////////////////////////////////////////////////////////////////
    /** a StringBuilder for reason property */
    private StringBuilder reasonBuffer;


    /////////////////////////////////////////////////////////////////////
    // property accessors
    /////////////////////////////////////////////////////////////////////

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    /**
     * Similar to getStatus() method, and subclass test client should not invoke it. However, this method is used by both
     * standalone and vehicle clients.
     */
    public String getReason() {
        if (reason == null) {
            if (reasonBuffer == null) {
                reason = "WARNING: both reason and reasonBuffer is null.";
            } else {
                reason = reasonBuffer.toString();
            }
        }
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }



    public Context getContext() {
        if (context != null) {
            return context;
        }

        Context ctx = null;
        try {
            ctx = new InitialContext();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        return ctx;
    }

    public void setContext(javax.naming.Context context) {
        this.context = context;
    }


    public Map<String, String> getJndiMapping() {
        return jndiMapping;
    }

    public void setContextClassLoader() {
    }

    public Map<String, Object> getContainerInitProperties() {
        return null;
    }


    /////////////////////////////////////////////////////////////////////
    // convenience methods
    /////////////////////////////////////////////////////////////////////

    /**
     * In embeddable usage, only the portable global jndi name lookup is supported. In JavaEE or web environment, the
     * following lookupName format can be used:
     *
     * x will be expanded to java:comp/env/x java:comp/env/x java:global/app-name/module-name/bean-name!FQN
     * java:app/module-name/bean-name!FQN java:module/bean-name!FQN
     *
     * beanName and beanInterface is to be used in embed mode to create a java:global name
     */
    protected Object lookup(String lookupName, String beanName, Class<?> beanInterface) {
        String nameNormalized = lookupName;

        // in JavaEE or Web environment
        if (!lookupName.startsWith("java:")) {
            nameNormalized = "java:comp/env/" + lookupName;
        }

        try {
            return getContext().lookup(nameNormalized);
        } catch (javax.naming.NamingException e) {
            throw new RuntimeException(e);
        }
    }


    protected StringBuilder getReasonBuffer() {
        if (reasonBuffer == null) {
            reasonBuffer = new StringBuilder(); // single-threaded access
        }
        return reasonBuffer;
    }

    protected void appendReason(Object... oo) {
        for (Object o : oo) {
            getReasonBuffer().append(o).append(System.getProperty("line.separator"));
        }
    }

    protected void assertEquals(final String messagePrefix, final Object expected, final Object actual) throws RuntimeException {
        Asserts.assertEquals(messagePrefix, expected, actual, getReasonBuffer());
    }

    protected void assertNotEquals(final String messagePrefix, final Object expected, final Object actual) throws RuntimeException {
        Asserts.assertNotEquals(messagePrefix, expected, actual, getReasonBuffer());
    }

    protected void assertGreaterThan(final String messagePrefix, long arg1, long arg2) throws RuntimeException {
        Asserts.assertGreaterThan(messagePrefix, arg1, arg2, getReasonBuffer());
    }


    private void passOrFail(boolean passOrFail, String... why) {
        this.status = (passOrFail ? "passed" : "failed") + " testName=" + testName;
        appendReason((Object[]) why);
        if (reasonBuffer != null) {
            this.reason = reasonBuffer.toString();
        }
    }

    private static String printStackTraceToString(Throwable e) {
        String sTrace = "";
        if (e == null) {
            return "";
        }

        try {
            StringWriter sw = new StringWriter();
            PrintWriter writer = new PrintWriter(sw);
            e.printStackTrace(writer);
            sTrace = sw.toString();
            writer.close();
        } catch (Exception E) {
        }

        return sTrace;
    }

}
