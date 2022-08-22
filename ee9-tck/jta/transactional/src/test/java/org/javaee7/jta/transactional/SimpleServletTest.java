/*
 * Copyright (c) 2017, 2020 Oracle and/or its affiliates. All rights reserved.
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

import java.io.File;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gargoylesoftware.htmlunit.TextPage;
import com.gargoylesoftware.htmlunit.WebClient;

@RunWith(Arquillian.class)
public class SimpleServletTest {

    @ArquillianResource
    private URL base;

    WebClient webClient;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        WebArchive war =
            ShrinkWrap.create(WebArchive.class)
                      .addPackage(CdiClient.class.getPackage())
                      .addAsWebInfResource((new File("src/main/webapp" + "/WEB-INF", "beans.xml")))

                      ;

//        if ("piranha-embedded".equals(System.getProperty("javaEEServer"))) {
//            war.addAsLibraries(
//                    Maven.resolver()
//                    .loadPomFromFile("pom.xml")
//                    .resolve(
//                        "org.omnifish.transact:transact-cdi-beans:1.0.0-SNAPSHOT")
//                    .withTransitivity()
//                    .as(JavaArchive.class)
//
//                );
//        }

        // javaEEServer

        System.out.println(war.toString(true));

        return war;
    }

    @Before
    public void setup() {
        webClient = new WebClient();
        webClient.getOptions().setTimeout(0);
    }

    /*
     * @testName: txTypeRequired_withoutTransaction
     *
     * @test_Strategy: TxType.REQUIRED: If called outside a transaction context, the interceptor must begin a new JTA
     * transaction, the managed bean method execution must then continue inside this transaction context, and the
     * transaction must be completed by the interceptor.
     *
     * If called inside a transaction context, the managed bean method execution must then continue inside this transaction
     * context.
     */
    @Test
    public void txTypeRequired_withoutTransaction() throws Exception {
        TextPage page = webClient.getPage(base + "SimpleServlet?test=" + "txTypeRequired_withoutTransaction");
    }

    /*
     * @testName: txTypeRequired_withTransaction
     *
     * @test_Strategy: TxType.REQUIRED: If called outside a transaction context, the interceptor must begin a new JTA
     * transaction, the managed bean method execution must then continue inside this transaction context, and the
     * transaction must be completed by the interceptor.
     *
     * If called inside a transaction context, the managed bean method execution must then continue inside this transaction
     * context.
     */
    @Test
    public void txTypeRequired_withTransaction() throws Exception {
        TextPage page = webClient.getPage(base + "SimpleServlet?test=" + "txTypeRequired_withTransaction");
    }

    /*
     * @testName: txTypeRequired_IllegalStateException
     *
     * @test_Strategy: If an attempt is made to call any method of the UserTransaction interface from within a bean or
     * method annotated with
     *
     * @Transactional and a Transactional.TxType other than NOT_SUPPORTED or NEVER, an IllegalStateException must be thrown.
     */
    @Test
    public void txTypeRequired_IllegalStateException() throws Exception {
        TextPage page = webClient.getPage(base + "SimpleServlet?test=" + "txTypeRequired_IllegalStateException");
    }

    /*
     * @testName: txTypeRequiresNew
     *
     * @test_Strategy: If called outside a transaction context, the interceptor must begin a new JTA transaction, the
     * managed bean method execution must then continue inside this transaction context, and the transaction must be
     * completed by the interceptor.
     *
     * If called inside a transaction context, the current transaction context must be suspended, a new JTA transaction will
     * begin, the managed bean method execution must then continue inside this transaction context, the transaction must be
     * completed, and the previously suspended transaction must be resumed.
     */
    @Test
    public void txTypeRequiresNew() throws Exception {
        TextPage page = webClient.getPage(base + "SimpleServlet?test=" + "txTypeRequiresNew");
    }

    /*
     * @testName: txTypeRequiresNew_withTransaction
     *
     * @test_Strategy: If called outside a transaction context, the interceptor must begin a new JTA transaction, the
     * managed bean method execution must then continue inside this transaction context, and the transaction must be
     * completed by the interceptor.
     *
     * If called inside a transaction context, the current transaction context must be suspended, a new JTA transaction will
     * begin, the managed bean method execution must then continue inside this transaction context, the transaction must be
     * completed, and the previously suspended transaction must be resumed.
     */
    @Test
    public void txTypeRequiresNew_withTransaction() throws Exception {
        TextPage page = webClient.getPage(base + "SimpleServlet?test=" + "txTypeRequiresNew_withTransaction");
    }

    /*
     * @testName: txTypeMandatory_withoutTransaction
     *
     * @test_Strategy: If called outside a transaction context, a TransactionalException with a nested
     * TransactionRequiredException must be thrown.
     *
     * If called inside a transaction context, managed bean method execution will then continue under that context.
     */
    @Test
    public void txTypeMandatory_withoutTransaction() throws Exception {
        TextPage page = webClient.getPage(base + "SimpleServlet?test=" + "txTypeMandatory_withoutTransaction");

    }

    /*
     * @testName: txTypeMandatory_withTransaction
     *
     * @test_Strategy: If called outside a transaction context, a TransactionalException with a nested
     * TransactionRequiredException must be thrown.
     *
     * If called inside a transaction context, managed bean method execution will then continue under that context.
     */
    @Test
    public void txTypeMandatory_withTransaction() throws Exception {
        TextPage page = webClient.getPage(base + "SimpleServlet?test=" + "txTypeMandatory_withTransaction");
    }

    /*
     * @testName: txTypeSupports_withoutTransaction
     *
     * @test_Strategy: If called outside a transaction context, managed bean method execution must then continue outside a
     * transaction context.
     *
     * If called inside a transaction context, the managed bean method execution must then continue inside this transaction
     * context.
     */
    @Test
    public void txTypeSupports_withoutTransaction() throws Exception {
        TextPage page = webClient.getPage(base + "SimpleServlet?test=" + "txTypeSupports_withoutTransaction");
    }

    /*
     * @testName: txTypeSupports_withTransaction
     *
     * @test_Strategy: If called outside a transaction context, managed bean method execution must then continue outside a
     * transaction context.
     *
     * If called inside a transaction context, the managed bean method execution must then continue inside this transaction
     * context.
     */
    @Test
    public void txTypeSupports_withTransaction() throws Exception {
        TextPage page = webClient.getPage(base + "SimpleServlet?test=" + "txTypeSupports_withTransaction");
    }

    /*
     * @testName: txTypeNotSupported_withoutTransaction
     *
     * @test_Strategy: If called outside a transaction context, managed bean method execution must then continue outside a
     * transaction context.
     *
     * If called inside a transaction context, the current transaction context must be suspended, the managed bean method
     * execution must then continue outside a transaction context, and the previously suspended transaction must be resumed
     * by the interceptor that suspended it after the method execution has completed.
     */
    @Test
    public void txTypeNotSupported_withoutTransaction() throws Exception {
        TextPage page = webClient.getPage(base + "SimpleServlet?test=" + "txTypeNotSupported_withoutTransaction");
    }

    /*
     * @testName: txTypeNotSupported_withTransaction
     *
     * @test_Strategy: If called outside a transaction context, managed bean method execution must then continue outside a
     * transaction context.
     *
     * If called inside a transaction context, the current transaction context must be suspended, the managed bean method
     * execution must then continue outside a transaction context, and the previously suspended transaction must be resumed
     * by the interceptor that suspended it after the method execution has completed.
     */
    @Test
    public void txTypeNotSupported_withTransaction() throws Exception {
        TextPage page = webClient.getPage(base + "SimpleServlet?test=" + "txTypeNotSupported_withTransaction");
    }

    /*
     * @testName: txTypeNever_withoutTransaction
     *
     * @test_Strategy: If called outside a transaction context, managed bean method execution must then continue outside a
     * transaction context.
     *
     * If called inside a transaction context, a TransactionalException with a nested InvalidTransactionException must be
     * thrown
     */
    @Test
    public void txTypeNever_withoutTransaction() throws Exception {
        TextPage page = webClient.getPage(base + "SimpleServlet?test=" + "txTypeNever_withoutTransaction");

    }

    /*
     * @testName: txTypeNever_withTransaction
     *
     * @test_Strategy: If called outside a transaction context, managed bean method execution must then continue outside a
     * transaction context.
     *
     * If called inside a transaction context, a TransactionalException with a nested InvalidTransactionException must be
     * thrown
     */
    @Test
    public void txTypeNever_withTransaction() throws Exception {
        TextPage page = webClient.getPage(base + "SimpleServlet?test=" + "txTypeNever_withTransaction");
    }

    /*
     * @testName: rollbackOnException
     *
     * @test_Strategy: The rollbackOn element can be set to indicate exceptions that must cause the interceptor to mark the
     * transaction for rollback.
     *
     * Conversely, the dontRollbackOn element can be set to indicate exceptions that must not cause the interceptor to mark
     * the transaction for rollback.
     */
    @Test
    public void rollbackOnException() throws Exception {
        TextPage page = webClient.getPage(base + "SimpleServlet?test=" + "rollbackOnException");

    }

    /*
     * @testName: rollbackOnExceptionTwo
     *
     * @test_Strategy: The rollbackOn element can be set to indicate exceptions that must cause the interceptor to mark the
     * transaction for rollback.
     *
     * Conversely, the dontRollbackOn element can be set to indicate exceptions that must not cause the interceptor to mark
     * the transaction for rollback.
     *
     * When a class is specified for either of these elements, the designated behavior applies to subclasses of that class
     * as well.
     *
     * Note: This test verifies the behavior in SubClass
     *
     *
     */
    @Test
    public void rollbackOnExceptionTwo() throws Exception {
        TextPage page = webClient.getPage(base + "SimpleServlet?test=" + "rollbackOnExceptionTwo");

    }

    /*
     * @testName: dontRollbackOnException
     *
     * @test_Strategy: The rollbackOn element can be set to indicate exceptions that must cause the interceptor to mark the
     * transaction for rollback.
     *
     * Conversely, the dontRollbackOn element can be set to indicate exceptions that must not cause the interceptor to mark
     * the transaction for rollback.
     */
    @Test
    public void dontRollbackOnException() throws Exception {
        TextPage page = webClient.getPage(base + "SimpleServlet?test=" + "dontRollbackOnException");
    }

    /*
     * @testName: dontRollbackOnExceptionTwo
     *
     * @test_Strategy: The rollbackOn element can be set to indicate exceptions that must cause the interceptor to mark the
     * transaction for rollback.
     *
     * Conversely, the dontRollbackOn element can be set to indicate exceptions that must not cause the interceptor to mark
     * the transaction for rollback.
     *
     * When a class is specified for either of these elements, the designated behavior applies to subclasses of that class
     * as well.
     *
     * Note: This test verifies the behavior in SubClass
     *
     */
    @Test
    public void dontRollbackOnExceptionTwo() throws Exception {
        TextPage page = webClient.getPage(base + "SimpleServlet?test=" + "dontRollbackOnExceptionTwo");

    }

    /*
     * @testName: rollbackAndDontRollback
     *
     * @test_Strategy: The rollbackOn element can be set to indicate exceptions that must cause the interceptor to mark the
     * transaction for rollback.
     *
     * Conversely, the dontRollbackOn element can be set to indicate exceptions that must not cause the interceptor to mark
     * the transaction for rollback.
     *
     * When a class is specified for either of these elements, the designated behavior applies to subclasses of that class
     * as well. If both elements are specified, dontRollbackOn takes precedence.
     */
    @Test
    public void rollbackAndDontRollback() throws Exception {
        TextPage page = webClient.getPage(base + "SimpleServlet?test=" + "rollbackAndDontRollback");

    }

    /*
     * @testName: rollbackAndDontRollbackTwo
     *
     * @test_Strategy: The rollbackOn element can be set to indicate exceptions that must cause the interceptor to mark the
     * transaction for rollback.
     *
     * Conversely, the dontRollbackOn element can be set to indicate exceptions that must not cause the interceptor to mark
     * the transaction for rollback.
     *
     * When a class is specified for either of these elements, the designated behavior applies to subclasses of that class
     * as well. If both elements are specified, dontRollbackOn takes precedence.
     *
     * Note: This test verifies the behavior in SubClass
     *
     */
    @Test
    public void rollbackAndDontRollbackTwo() throws Exception {
        TextPage page = webClient.getPage(base + "SimpleServlet?test=" + "rollbackAndDontRollbackTwo");

    }

    /*
     * @testName: transactionScopedBean_withoutTransaction
     *
     * @test_Strategy:
     *
     * The jakarta.transaction.TransactionScoped annotation provides the ability to specify a standard CDI scope to define
     * bean instances whose lifecycle is scoped to the currently active JTA transaction.
     *
     * The transaction scope is active when the return from a call to UserTransaction.getStatus or
     * TransactionManager.getStatus is one of the following states: Status.STATUS_ACTIVE Status.STATUS_MARKED_ROLLBACK
     * Status.STATUS_PREPARED Status.STATUS_UNKNOWN Status.STATUS_PREPARING Status.STATUS_COMMITTING
     * Status.STATUS_ROLLING_BACK
     *
     * A jakarta.enterprise.context.ContextNotActiveException must be thrown if a bean with this annotation is used when the
     * transaction context is not active.
     *
     */
    @Test
    public void transactionScopedBean_withoutTransaction() throws Exception {
        TextPage page = webClient.getPage(base + "SimpleServlet?test=" + "transactionScopedBean_withoutTransaction");
    }

    /*
     * @testName: transactionScopedBean_withTransaction
     *
     * @test_Strategy:
     *
     * The jakarta.transaction.TransactionScoped annotation provides the ability to specify a standard CDI scope to define
     * bean instances whose lifecycle is scoped to the currently active JTA transaction.
     *
     * The transaction scope is active when the return from a call to UserTransaction.getStatus or
     * TransactionManager.getStatus is one of the following states: Status.STATUS_ACTIVE Status.STATUS_MARKED_ROLLBACK
     * Status.STATUS_PREPARED Status.STATUS_UNKNOWN Status.STATUS_PREPARING Status.STATUS_COMMITTING
     * Status.STATUS_ROLLING_BACK
     *
     * A jakarta.enterprise.context.ContextNotActiveException must be thrown if a bean with this annotation is used when the
     * transaction context is not active.
     */
    @Test
    public void transactionScopedBean_withTransaction() throws Exception {
        TextPage page = webClient.getPage(base + "SimpleServlet?test=" + "transactionScopedBean_withTransaction");
    }

    /*
     * @testName: getInterceptorPriorityForTxTypeRequired
     *
     * @test_Strategy: The Transactional interceptors must have a priority of Interceptor.Priority.PLATFORM_BEFORE+200
     *
     */
    @Test
    public void getInterceptorPriorityForTxTypeRequired() throws Exception {
        TextPage page = webClient.getPage(base + "SimpleServlet?test=" + "getInterceptorPriorityForTxTypeRequired");
    }

    /*
     * @testName: getInterceptorPriorityForTxTypeRequiresNew
     *
     * @test_Strategy: The Transactional interceptors must have a priority of Interceptor.Priority.PLATFORM_BEFORE+200
     *
     */
    @Test
    public void getInterceptorPriorityForTxTypeRequiresNew() throws Exception {
        TextPage page = webClient.getPage(base + "SimpleServlet?test=" + "getInterceptorPriorityForTxTypeRequiresNew");
    }

    /*
     * @testName: getInterceptorPriorityForTxTypeMandatory
     *
     * @test_Strategy: The Transactional interceptors must have a priority of Interceptor.Priority.PLATFORM_BEFORE+200
     *
     */
    @Test
    public void getInterceptorPriorityForTxTypeMandatory() throws Exception {
        TextPage page = webClient.getPage(base + "SimpleServlet?test=" + "getInterceptorPriorityForTxTypeMandatory");
    }

    /*
     * @testName: getInterceptorPriorityForTxTypeSupports
     *
     * @test_Strategy: The Transactional interceptors must have a priority of Interceptor.Priority.PLATFORM_BEFORE+200
     *
     */
    @Test
    public void getInterceptorPriorityForTxTypeSupports() throws Exception {
        TextPage page = webClient.getPage(base + "SimpleServlet?test=" + "getInterceptorPriorityForTxTypeSupports");
    }

    /*
     * @testName: getInterceptorPriorityForTxTypeNotSupported
     *
     * @test_Strategy: The Transactional interceptors must have a priority of Interceptor.Priority.PLATFORM_BEFORE+200
     *
     */
    @Test
    public void getInterceptorPriorityForTxTypeNotSupported() throws Exception {
        TextPage page = webClient.getPage(base + "SimpleServlet?test=" + "getInterceptorPriorityForTxTypeNotSupported");
    }

    /*
     * @testName: getInterceptorPriorityForTxTypeNever
     *
     * @test_Strategy: The Transactional interceptors must have a priority of Interceptor.Priority.PLATFORM_BEFORE+200
     *
     */
    @Test
    public void getInterceptorPriorityForTxTypeNever() throws Exception {
        TextPage page = webClient.getPage(base + "SimpleServlet?test=" + "getInterceptorPriorityForTxTypeNever");

    }


}