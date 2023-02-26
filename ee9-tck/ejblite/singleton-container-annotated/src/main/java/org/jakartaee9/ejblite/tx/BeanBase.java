/*
 * Copyright (c) 2008, 2020 Oracle and/or its affiliates. All rights reserved.
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

import static jakarta.ejb.LockType.READ;
import static jakarta.ejb.LockType.WRITE;

import jakarta.ejb.Lock;

abstract public class BeanBase implements ConcurrencyIF {

    private static final String msg = "Should not reach here. The interceptors should have returned "
            + "the result. Maybe the interceptors are not ignored?";

    // Interceptor3 in bm or cm, is bound to these methods in sub-class

    @Override
    @Lock(WRITE)
    public long getAndResetLockedSumFromInterceptor(String interceptorName) {
        throw new RuntimeException(msg);
    }

    @Override
    @Lock(WRITE)
    public void addLockedFromInterceptor(String interceptorName, int num) {
        throw new RuntimeException(msg);
    }

    @Override
    @Lock(READ)
    public long getAndResetUnlockedSumFromInterceptor(String interceptorName) {
        throw new RuntimeException(msg);
    }

    @Override
    @Lock(READ)
    public void addUnlockedFromInterceptor(String interceptorName, int num) {
        throw new RuntimeException(msg);
    }
}
