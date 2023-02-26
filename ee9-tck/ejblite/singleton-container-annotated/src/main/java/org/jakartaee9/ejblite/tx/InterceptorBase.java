/*
 * Copyright (c) 2008, 2018 Oracle and/or its affiliates. All rights reserved.
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

package org.jakartaee9.ejblite.tx;

import java.util.logging.Logger;

import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import jakarta.ejb.EJBContext;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;

public class InterceptorBase {

    private static Logger logger = Logger.getLogger(InterceptorBase.class.toString());

    // copied from SingletonBean

    private long unlockedSum;
    private long lockedSum;

    @Resource
    protected EJBContext ejbContext;

    @AroundInvoke
    private Object intercept(InvocationContext inv) throws Exception {

        return inv.proceed();

//
//        String methodName = inv.getMethod().getName();
//        Object[] params = inv.getParameters();
//        String interceptorName = null;
//        if (params != null && params.length > 0) {
//            if (params[0] instanceof String) {
//                interceptorName = (String) params[0]; // simple class name
//            }
//        }
//
//        return intercept0(inv, methodName, interceptorName, params);
    }

    /**
     * If the interceptor class (e.g., Interceptor1) is subclassed by the container, getClass().getSimpleName() returns the
     * subclass' name, which is not what we are expecting. The safest way is to always override it.
     */
    protected String getSimpleName() {
        return getClass().getSimpleName();
    }

    @PreDestroy
    private void preDestroy(InvocationContext inv) {
        logger.info("In SingletonInterceptorBase.preDestroy()");
    }

    final protected Object intercept0(InvocationContext inv, String methodName, String interceptorName, Object[] params) throws Exception {
        if (!getSimpleName().equals(interceptorName)) {
            logger.finest(() -> "interceptorName does not match, so proceed:" + interceptorName + " vs " + getSimpleName());
            return inv.proceed();
        }

        logger.finest(() -> "Intercepting it and skipping the rest of interceptor chain. " + " methodName:" + methodName
                + ", interceptorName:" + interceptorName);

        if ("addLockedFromInterceptor".equals(methodName)) {
            addLocked((Integer) params[1]);
            return null;
        }

        if ("getAndResetLockedSumFromInterceptor".equals(methodName)) {
            return getAndResetLockedSum();
        }

        if ("addUnlockedFromInterceptor".equals(methodName)) {
            addUnlocked((Integer) params[1]);
            return null;
        }

        if ("getAndResetUnlockedSumFromInterceptor".equals(methodName)) {
            return getAndResetUnlockedSum();
        }

        throw new IllegalStateException("Invalid methodName: " + methodName);
    }

    protected long getAndResetLockedSum() {
        long result = lockedSum;
        lockedSum = 0;
        return result;
    }

    protected void addLocked(int num) {
        for (int i = 0; i < num; i++) {
            lockedSum++;
        }
    }

    protected long getAndResetUnlockedSum() {
        long result = unlockedSum;
        unlockedSum = 0;
        return result;
    }

    protected void addUnlocked(int num) {
        for (int i = 0; i < num; i++) {
            unlockedSum++;
        }
    }
}
