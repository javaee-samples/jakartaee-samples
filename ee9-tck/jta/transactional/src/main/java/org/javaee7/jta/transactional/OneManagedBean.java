/*
 * Copyright (c) 2013, 2020 Oracle and/or its affiliates. All rights reserved.
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

package org.javaee7.jta.transactional;

import static jakarta.enterprise.inject.spi.InterceptionType.AROUND_INVOKE;
import static jakarta.transaction.Transactional.TxType.MANDATORY;
import static jakarta.transaction.Transactional.TxType.NEVER;
import static jakarta.transaction.Transactional.TxType.NOT_SUPPORTED;
import static jakarta.transaction.Transactional.TxType.REQUIRED;
import static jakarta.transaction.Transactional.TxType.REQUIRES_NEW;
import static jakarta.transaction.Transactional.TxType.SUPPORTS;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jakarta.annotation.Priority;
import jakarta.annotation.Resource;
import jakarta.enterprise.inject.spi.BeanManager;
import jakarta.enterprise.inject.spi.Interceptor;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.transaction.UserTransaction;

@OneManagedQualifier
public class OneManagedBean {
    public static final String NAME = "one-managed-bean";

    @Resource(lookup = "java:comp/UserTransaction")
    private UserTransaction userTransaction2;

    @Inject
    private BeanManager beanManager;

    String myString;

    private void setMyString(String s) {
        this.myString = s;
    }

    public String getName() {
        return NAME;
    }

    @Transactional(REQUIRED)
    public String txTypeRequired() {
        return "txTypeRequired called successfully";
    }

    @Transactional(REQUIRED)
    public String txTypeRequiredIllegalStateException() {
        String result = "not received IllegalStateException";

        try {
            userTransaction2.begin();
            String variable = "do nothing";
            userTransaction2.commit();
        } catch (IllegalStateException ise) {
            result = "IllegalStateException";
            setMyString(result);

        } catch (Exception e) {
            e.printStackTrace();
            result = "unexcepted Exception :" + e.getMessage();
            setMyString(result);
        }

        return result;
    }

    @Transactional(REQUIRES_NEW)
    public String txTypeRequiresNew() {
        return "txTypeRequiresNew called successfully";
    }

    @Transactional(MANDATORY)
    public String txTypeMandatory() {
        return "txTypeMandatory called successfully";
    }

    @Transactional(SUPPORTS)
    public String txTypeSupports() {
        return "txTypeSupports called successfully";
    }

    @Transactional(SUPPORTS)
    public String txTypeSupportsWithoutTransaction() {
        return "txTypeSupports run without active transaction";
    }

    @Transactional(NOT_SUPPORTED)
    public String txTypeNotSupported() {
        return "txTypeNotSupported run without active transaction";
    }

    @Transactional(NEVER)
    public String txTypeNever() {
        return "txTypeNever run without active transaction";
    }

    @Transactional(rollbackOn = CTSRollbackException.class)
    public void rollbackOnException() throws CTSRollbackException {
        throw new CTSRollbackException("CTSRollbackException");
    }

    @Transactional(dontRollbackOn = { CTSDontRollbackException.class })
    public void dontRollbackOnException() throws CTSDontRollbackException {
        throw new CTSDontRollbackException("CTSDontRollbackException");
    }

    @Transactional(rollbackOn = CTSRollbackException.class, dontRollbackOn = CTSRollbackException.class)
    public void rollbackAndDontRollback() throws CTSRollbackException {
        throw new CTSRollbackException("CTSRollbackException");
    }

    public List<Integer> getPriority(String methodName) {
        int priorityValue = 0;
        List<Integer> priorityList = new ArrayList<>();

        try {
            Annotation[] annotationArray = getClass().getMethod(methodName).getAnnotations();

            List<Interceptor<?>> interceptorList = beanManager.resolveInterceptors(AROUND_INVOKE, annotationArray);

            for (Interceptor<?> interceptor : interceptorList) {
                System.out.println("Interceptor Name = " + interceptor.getName());
                System.out.println("Interceptor toString = " + interceptor.toString());
                System.out.println("Interceptor Beanclass = " + interceptor.getBeanClass().getName());

                // Get Priority Annotation from interceptor bean class
                Annotation annotation = interceptor.getBeanClass().getAnnotation(Priority.class);
                if (annotation != null && annotation instanceof Priority) {
                    Priority myPriorityAnnotation = (Priority) annotation;
                    priorityValue = myPriorityAnnotation.value();
                    System.out.println("Priority value(From Interceptor bean class) = " + priorityValue);
                    priorityList.add(priorityValue);
                } else {
                    // Get Priority Annotation from Interceptor Bindings
                    Set<Annotation> annotations = interceptor.getInterceptorBindings();
                    System.out.println("InterceptorBindings set size =" + annotations.size());
                    for (Annotation ibAnnotation : annotations) {
                        System.out.println("InterceptorBindings Annototation : " + ibAnnotation.getClass().getName());
                        System.out.println("InterceptorBindings Annototation type : " + ibAnnotation.annotationType());
                        if (ibAnnotation != null && ibAnnotation instanceof Priority) {
                            Priority myPriorityAnnotation = (Priority) ibAnnotation;
                            priorityValue = myPriorityAnnotation.value();
                            System.out.println("Priority value(From Interceptor bindings) = " + priorityValue);
                            priorityList.add(priorityValue);
                        }
                    }
                }

                if (priorityValue == 0) {
                    System.out.println("Priority Annotation not found");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return priorityList;
    }
}
