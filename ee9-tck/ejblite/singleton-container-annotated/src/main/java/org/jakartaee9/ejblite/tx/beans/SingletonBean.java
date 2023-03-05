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
package org.jakartaee9.ejblite.tx.beans;

import static jakarta.ejb.LockType.READ;
import static jakarta.ejb.LockType.WRITE;

import java.util.LinkedList;

import org.jakartaee9.ejblite.tx.interceptors.Interceptor0;
import org.jakartaee9.ejblite.tx.interceptors.Interceptor3;

import jakarta.ejb.Lock;
import jakarta.ejb.Singleton;
import jakarta.interceptor.Interceptors;

/**
 * This class is similar to ../bm/SingletonBean. But the latter uses java language primitives like volatile and
 * synchronized.
 */
@Singleton
@Interceptors(Interceptor0.class)
public class SingletonBean extends BeanBase implements ConcurrencyIF {

    private long unlockedSum;
    private long lockedSum;

    private LinkedList<Integer> data = new LinkedList<Integer>();

    @Override
    @Lock(WRITE)
    public void addToLinkedList(Integer i) {
        data.add(i);
    }

    @Override
    @Lock(WRITE)
    public int getLinkedListSizeAndClear() {
        int i = data.size();
        data.clear();
        return i;
    }

    // Default WRITE lock
    @Override
    public long getAndResetLockedSum() {
        long result = lockedSum;
        lockedSum = 0;
        return result;
    }

    // Default WRITE lock
    @Override
    public void addLocked(int num) {
        for (int i = 0; i < num; i++) {
            lockedSum++;
        }
    }

    @Override
    @Lock(READ)
    public long getAndResetUnlockedSum() {
        long result = unlockedSum;
        unlockedSum = 0;
        return result;
    }

    @Override
    @Lock(READ)
    public void addUnlocked(int num) {
        for (int i = 0; i < num; i++) {
            unlockedSum++;
        }
    }


    @Override
    @Interceptors(Interceptor3.class)
    public void addLockedFromInterceptor(String interceptorName, int num) {
        super.addLockedFromInterceptor(interceptorName, num);
    }

    @Override
    @Interceptors(Interceptor3.class)
    @Lock(READ)
    public void addUnlockedFromInterceptor(String interceptorName, int num) {
        super.addUnlockedFromInterceptor(interceptorName, num);
    }

    @Override
    @Interceptors(Interceptor3.class)
    public long getAndResetLockedSumFromInterceptor(String interceptorName) {
        return super.getAndResetLockedSumFromInterceptor(interceptorName);
    }

    @Override
    @Interceptors(Interceptor3.class)
    @Lock(READ)
    public long getAndResetUnlockedSumFromInterceptor(String interceptorName) {
        return super.getAndResetUnlockedSumFromInterceptor(interceptorName);
    }
}
