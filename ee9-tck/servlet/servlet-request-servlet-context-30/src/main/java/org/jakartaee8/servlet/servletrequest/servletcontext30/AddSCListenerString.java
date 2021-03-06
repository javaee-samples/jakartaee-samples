/*
 * Copyright (c) 2012, 2018 Oracle and/or its affiliates. All rights reserved.
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
package org.jakartaee8.servlet.servletrequest.servletcontext30;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import org.jakartaee8.urlclient.StaticLog;

public class AddSCListenerString implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        StaticLog.add("AddSCListenerString Initialized.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        StaticLog.add("AddSCListenerString Destroyed.");
    }

}
