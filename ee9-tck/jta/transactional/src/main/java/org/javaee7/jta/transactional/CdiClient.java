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

package org.javaee7.jta.transactional;

import static jakarta.interceptor.Interceptor.Priority.PLATFORM_BEFORE;
import static java.util.logging.Level.INFO;
import static org.javaee7.jta.transactional.Helper.getLogger;

import java.util.Arrays;
import java.util.List;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.ContextNotActiveException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.InvalidTransactionException;
import jakarta.transaction.Status;
import jakarta.transaction.SystemException;
import jakarta.transaction.TransactionRequiredException;
import jakarta.transaction.TransactionalException;
import jakarta.transaction.UserTransaction;

@Named("client")
@RequestScoped
public class CdiClient {
    private static StringBuilder callRecords = new StringBuilder();

    @Inject
    private TransactionScopedBean transactionScopedBean;

    @Resource(lookup = "java:comp/UserTransaction")
    private UserTransaction userTransaction;

    @Inject
    @OneManagedQualifier
    private OneManagedBean one;

    @Inject
    @TwoManagedQualifier
    private TwoManagedBean two;

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
    public void txTypeRequired_withoutTransaction() throws Exception {
        Helper.assertEquals("\n", "txTypeRequired called successfully", one.txTypeRequired(), callRecords);
        appendReason(Helper.compareResult("txTypeRequired called successfully", one.txTypeRequired()));
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
    public void txTypeRequired_withTransaction() throws Exception {
        try {
            userTransaction.begin();
            Helper.assertEquals(null, "txTypeRequired called successfully", one.txTypeRequired(), callRecords);
            appendReason(Helper.compareResult("txTypeRequired called successfully", one.txTypeRequired()));
            userTransaction.commit();
        } catch (Exception e) {
            getLogger().log(INFO, null, e);
            throw new IllegalStateException("txTypeRequired_withTransaction failed");
        }
    }

    /*
     * @testName: txTypeRequired_IllegalStateException
     *
     * @test_Strategy: If an attempt is made to call any method of the UserTransaction interface from within a bean or
     * method annotated with
     *
     * @Transactional and a Transactional.TxType other than NOT_SUPPORTED or NEVER, an IllegalStateException must be thrown.
     */
    public void txTypeRequired_IllegalStateException() throws Exception {
        Helper.assertEquals(null, "IllegalStateException", one.txTypeRequiredIllegalStateException(), callRecords);
        // Helper.getLogger().info(callRecords.toString());
        appendReason(Helper.compareResult("IllegalStateException", one.txTypeRequiredIllegalStateException()));
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
    public void txTypeRequiresNew() throws Exception {
        Helper.assertEquals(null, "txTypeRequiresNew called successfully", one.txTypeRequiresNew(), callRecords);
        // Helper.getLogger().info(callRecords.toString());
        appendReason(Helper.compareResult("txTypeRequiresNew called successfully", one.txTypeRequiresNew()));
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
    public void txTypeRequiresNew_withTransaction() throws Exception {
        try {
            userTransaction.begin();
            Helper.assertEquals(null, "txTypeRequiresNew called successfully", one.txTypeRequiresNew(), callRecords);
            // Helper.getLogger().info(callRecords.toString());
            appendReason(Helper.compareResult("txTypeRequiresNew called successfully", one.txTypeRequiresNew()));
            userTransaction.commit();
        } catch (Exception e) {
            getLogger().log(INFO, null, e);
            throw new IllegalStateException("txTypeRequiresNew_withTransaction failed");
        }
    }

    /*
     * @testName: txTypeMandatory_withoutTransaction
     *
     * @test_Strategy: If called outside a transaction context, a TransactionalException with a nested
     * TransactionRequiredException must be thrown.
     *
     * If called inside a transaction context, managed bean method execution will then continue under that context.
     */
    public void txTypeMandatory_withoutTransaction() throws Exception {
        String result = "TransactionalException not received";

        try {
            getLogger().info("Invoking OneManagedBean.txTypeManadatory() without a transaction Context");
            one.txTypeMandatory();
        } catch (TransactionalException te) {
            if (te.getCause() instanceof TransactionRequiredException) {
                result = "Received expected TransactionalException with nested TransactionRequiredException";
            } else {
                throw new IllegalStateException("Received TransactionalException without nested TransactionRequiredExecption");
            }

        } catch (Exception e) {
            e.printStackTrace();
            result = "Received unexcepted Exception :" + e.getMessage();
        }

        if (result.equals("Received expected TransactionalException with nested TransactionRequiredException")) {
            getLogger().log(INFO, result);
            appendReason("Received expected TransactionalException with nested TransactionRequiredException");
        } else {
            throw new IllegalStateException(result);
        }

    }

    /*
     * @testName: txTypeMandatory_withTransaction
     *
     * @test_Strategy: If called outside a transaction context, a TransactionalException with a nested
     * TransactionRequiredException must be thrown.
     *
     * If called inside a transaction context, managed bean method execution will then continue under that context.
     */
    public void txTypeMandatory_withTransaction() throws Exception {
        try {
            userTransaction.begin();
            Helper.assertEquals(null, "txTypeMandatory called successfully", one.txTypeMandatory(), callRecords);
            // Helper.getLogger().info(callRecords.toString());
            appendReason(Helper.compareResult("txTypeMandatory called successfully", one.txTypeMandatory()));
            userTransaction.commit();
        } catch (Exception e) {
            getLogger().log(INFO, null, e);
            throw new IllegalStateException("txTypeRequiresNew_withTransaction failed");
        }
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
    public void txTypeSupports_withoutTransaction() throws Exception {

        Helper.assertEquals(null, "txTypeSupports run without active transaction", one.txTypeSupportsWithoutTransaction(), callRecords);
        // Helper.getLogger().info(callRecords.toString());
        appendReason(Helper.compareResult("txTypeSupports run without active transaction", one.txTypeSupportsWithoutTransaction()));

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
    public void txTypeSupports_withTransaction() throws Exception {
        try {
            userTransaction.begin();
            Helper.assertEquals(null, "txTypeSupports called successfully", one.txTypeSupports(), callRecords);
            // Helper.getLogger().info(callRecords.toString());
            appendReason(Helper.compareResult("txTypeSupports called successfully", one.txTypeSupports()));
            userTransaction.commit();
        } catch (Exception e) {
            getLogger().log(INFO, null, e);
            throw new IllegalStateException("txTypeSupports failed");
        }
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
    public void txTypeNotSupported_withoutTransaction() throws Exception {

        Helper.assertEquals(null, "txTypeNotSupported run without active transaction", one.txTypeNotSupported(), callRecords);
        // Helper.getLogger().info(callRecords.toString());
        appendReason(Helper.compareResult("txTypeNotSupported run without active transaction", one.txTypeNotSupported()));

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
    public void txTypeNotSupported_withTransaction() throws Exception {
        try {
            userTransaction.begin();
            Helper.assertEquals(null, "txTypeNotSupported run without active transaction", one.txTypeNotSupported(), callRecords);
            // Helper.getLogger().info(callRecords.toString());
            appendReason(Helper.compareResult("txTypeNotSupported run without active transaction", one.txTypeNotSupported()));
            userTransaction.commit();
        } catch (Exception e) {
            getLogger().log(INFO, null, e);
            throw new IllegalStateException("txTypeSupports failed");
        }
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
    public void txTypeNever_withoutTransaction() throws Exception {
        Helper.assertEquals(null, "txTypeNever run without active transaction", one.txTypeNever(), callRecords);
        // Helper.getLogger().info(callRecords.toString());
        appendReason(Helper.compareResult("txTypeNever run without active transaction", one.txTypeNever()));

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
    public void txTypeNever_withTransaction() throws Exception {
        String result = "Expected TransactionalException not received";

        try {
            getLogger().info("Invoking OneManagedBean.txTypeNever() with a transaction Context");
            userTransaction.begin();
            one.txTypeNever();
            userTransaction.commit();
        } catch (TransactionalException te) {

            if (te.getCause() instanceof InvalidTransactionException) {
                result = "Received expected TransactionalException with nested InvalidTransactionException";
            } else {
                throw new IllegalStateException("Received expected TransactionalException without nested InvalidTransactionException");
            }

        } catch (Exception e) {
            e.printStackTrace();
            result = "Received unexcepted Exception :" + e.getMessage();
        }

        if (result.equals("Received expected TransactionalException with nested InvalidTransactionException")) {
            getLogger().log(INFO, result);
            appendReason("Received expected TransactionalException with nested InvalidTransactionException");
        } else {
            throw new IllegalStateException(result);
        }
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
    public void rollbackOnException() throws Exception {
        String result = "failed to set STATUS_MARKED_ROLLBACK on CTSRollbackException";
        int status;

        try {
            userTransaction.begin();
            if (userTransaction.getStatus() == Status.STATUS_ACTIVE) {
                getLogger().info("Current Transaction Status is = " + userTransaction.getStatus());
                getLogger().info("Transaction Status value for Status.STATUS_ACTIVE = " + Status.STATUS_ACTIVE);
                getLogger().info("Transaction Status value for Status.STATUS_NO_TRANSACTION = " + Status.STATUS_NO_TRANSACTION);
                getLogger().info("Transaction Status value for Status.STATUS_MARKED_ROLLBACK = " + Status.STATUS_MARKED_ROLLBACK);
                getLogger().info("Calling one.rollbackOnException()");
                one.rollbackOnException();
            }

        } catch (CTSRollbackException ce) {
            getLogger().info("Received Expected CTSRollbackException");
            try {

                if (userTransaction.getStatus() == Status.STATUS_MARKED_ROLLBACK) {
                    result = "Transaction STATUS_MARKED_ROLLBACK on CTSRollbackException";
                } else {
                    result = "Transaction Status is set to : " + userTransaction.getStatus();

                }
            } catch (SystemException se) {
                result = "failed to get transaction status";
            }

        } catch (Exception e) {
            result = "Received unexpected exception :" + e.getClass();
        }

        if (result.equals("Transaction STATUS_MARKED_ROLLBACK on CTSRollbackException")) {
            getLogger().log(INFO, result);
            appendReason(result);
        } else {
            appendReason(result);
            throw new IllegalStateException(result);
        }

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
     */
    public void rollbackOnExceptionTwo() throws Exception {
        String result = "failed to set STATUS_MARKED_ROLLBACK on CTSRollbackException";
        int status;

        try {
            userTransaction.begin();
            if (userTransaction.getStatus() == Status.STATUS_ACTIVE) {
                getLogger().info("Current Transaction Status is = " + userTransaction.getStatus());
                getLogger().info("Transaction Status value for Status.STATUS_ACTIVE = " + Status.STATUS_ACTIVE);
                getLogger().info("Transaction Status value for Status.STATUS_NO_TRANSACTION = " + Status.STATUS_NO_TRANSACTION);
                getLogger().info("Transaction Status value for Status.STATUS_MARKED_ROLLBACK = " + Status.STATUS_MARKED_ROLLBACK);
                getLogger().info("Calling one.rollbackOnException()");
                two.rollbackOnException();
            }

        } catch (CTSRollbackException ce) {
            getLogger().info("Received Expected CTSRollbackException");
            try {

                if (userTransaction.getStatus() == Status.STATUS_MARKED_ROLLBACK) {
                    result = "Transaction STATUS_MARKED_ROLLBACK on CTSRollbackException";
                } else {
                    result = "Transaction Status is set to : " + userTransaction.getStatus();

                }
            } catch (SystemException se) {
                result = "failed to get transaction status";
            }

        } catch (Exception e) {
            result = "Received unexpected exception :" + e.getClass();
        }

        if (result.equals("Transaction STATUS_MARKED_ROLLBACK on CTSRollbackException")) {
            getLogger().log(INFO, result);
            appendReason(result);
        } else {
            appendReason(result);
            throw new IllegalStateException(result);
        }

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
    public void dontRollbackOnException() throws Exception {
        String result = "";
        int status;

        try {
            userTransaction.begin();
            if (userTransaction.getStatus() == Status.STATUS_ACTIVE) {
                getLogger().info("Current Transaction Status is = " + userTransaction.getStatus());
                getLogger().info("Transaction Status value for Status.STATUS_ACTIVE = " + Status.STATUS_ACTIVE);
                getLogger().info("Transaction Status value for Status.STATUS_NO_TRANSACTION = " + Status.STATUS_NO_TRANSACTION);
                getLogger().info("Transaction Status value for Status.STATUS_MARKED_ROLLBACK = " + Status.STATUS_MARKED_ROLLBACK);
                getLogger().info("Calling one.dontRollbackOnException()");
                one.dontRollbackOnException();
            }

        } catch (CTSDontRollbackException ce) {
            getLogger().info("Received Expected CTSDontRollbackException");
            try {

                if (userTransaction.getStatus() == Status.STATUS_ACTIVE) {
                    result = "Transaction Status not changed on CTSDontRollbackException";
                } else {
                    result = "Transaction Status is set to : " + userTransaction.getStatus();

                }
            } catch (SystemException se) {
                result = "failed to get transaction status";
            }

        } catch (Exception e) {
            result = "Received unexpected exception :" + e.getClass();
            e.printStackTrace();
        }

        if (result.equals("Transaction Status not changed on CTSDontRollbackException")) {
            getLogger().log(INFO, result);
            appendReason(result);
        } else {
            appendReason(result);
            throw new IllegalStateException(result);
        }

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
    public void dontRollbackOnExceptionTwo() throws Exception {
        String result = "";
        int status;

        try {
            userTransaction.begin();
            if (userTransaction.getStatus() == Status.STATUS_ACTIVE) {
                getLogger().info("Current Transaction Status is = " + userTransaction.getStatus());
                getLogger().info("Transaction Status value for Status.STATUS_ACTIVE = " + Status.STATUS_ACTIVE);
                getLogger().info("Transaction Status value for Status.STATUS_NO_TRANSACTION = " + Status.STATUS_NO_TRANSACTION);
                getLogger().info("Transaction Status value for Status.STATUS_MARKED_ROLLBACK = " + Status.STATUS_MARKED_ROLLBACK);
                getLogger().info("Calling two.dontRollbackOnException()");
                two.dontRollbackOnException();
            }

        } catch (CTSDontRollbackException ce) {
            getLogger().info("Received Expected CTSDontRollbackException");
            try {

                if (userTransaction.getStatus() == Status.STATUS_ACTIVE) {
                    result = "Transaction Status not changed on CTSDontRollbackException";
                } else {
                    result = "Transaction Status is set to : " + userTransaction.getStatus();

                }
            } catch (SystemException se) {
                result = "failed to get transaction status";
            }

        } catch (Exception e) {
            result = "Received unexpected exception :" + e.getClass();
            e.printStackTrace();
        }

        if (result.equals("Transaction Status not changed on CTSDontRollbackException")) {
            getLogger().log(INFO, result);
            appendReason(result);
        } else {
            appendReason(result);
            throw new IllegalStateException(result);
        }

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
    public void rollbackAndDontRollback() throws Exception {
        String result = "";
        int status;

        try {
            userTransaction.begin();
            if (userTransaction.getStatus() == Status.STATUS_ACTIVE) {
                getLogger().info("Current Transaction Status is = " + userTransaction.getStatus());
                getLogger().info("Transaction Status value for Status.STATUS_ACTIVE = " + Status.STATUS_ACTIVE);
                getLogger().info("Transaction Status value for Status.STATUS_NO_TRANSACTION = " + Status.STATUS_NO_TRANSACTION);
                getLogger().info("Transaction Status value for Status.STATUS_MARKED_ROLLBACK = " + Status.STATUS_MARKED_ROLLBACK);
                getLogger().info("Calling one.rollbackAndDontRollback()");
                one.rollbackAndDontRollback();
            }

        } catch (CTSRollbackException ce) {
            getLogger().info("Received Expected CTSRollbackException");
            try {

                if (userTransaction.getStatus() == Status.STATUS_ACTIVE) {
                    result = "Transaction Status not changed on CTSRollbackException";
                } else {
                    result = "Transaction Status is set to : " + userTransaction.getStatus();

                }
            } catch (SystemException se) {
                result = "failed to get transaction status";
            }

        } catch (Exception e) {
            result = "Received unexpected exception :" + e.getClass();
        }

        if (result.equals("Transaction Status not changed on CTSRollbackException")) {
            getLogger().log(INFO, result);
            appendReason(result);
        } else {
            appendReason(result);
            throw new IllegalStateException(result);
        }

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
    public void rollbackAndDontRollbackTwo() throws Exception {
        String result = "";
        int status;

        try {
            userTransaction.begin();
            if (userTransaction.getStatus() == Status.STATUS_ACTIVE) {
                getLogger().info("Current Transaction Status is = " + userTransaction.getStatus());
                getLogger().info("Transaction Status value for Status.STATUS_ACTIVE = " + Status.STATUS_ACTIVE);
                getLogger().info("Transaction Status value for Status.STATUS_NO_TRANSACTION = " + Status.STATUS_NO_TRANSACTION);
                getLogger().info("Transaction Status value for Status.STATUS_MARKED_ROLLBACK = " + Status.STATUS_MARKED_ROLLBACK);
                getLogger().info("Calling two.rollbackAndDontRollback()");
                two.rollbackAndDontRollback();
            }

        } catch (CTSRollbackException ce) {
            getLogger().info("Received Expected CTSRollbackException");
            try {

                if (userTransaction.getStatus() == Status.STATUS_ACTIVE) {
                    result = "Transaction Status not changed on CTSRollbackException";
                } else {
                    result = "Transaction Status is set to : " + userTransaction.getStatus();

                }
            } catch (SystemException se) {
                result = "failed to get transaction status";
            }

        } catch (Exception e) {
            result = "Received unexpected exception :" + e.getClass();
        }

        if (result.equals("Transaction Status not changed on CTSRollbackException")) {
            getLogger().log(INFO, result);
            appendReason(result);
        } else {
            appendReason(result);
            throw new IllegalStateException(result);
        }

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
    public void transactionScopedBean_withoutTransaction() throws Exception {

        String result = "ContextNotActiveException not received";

        try {
            getLogger().info("Invoking TransactionScopedBean.test() without a transaction Context");
            transactionScopedBean.test();
        } catch (ContextNotActiveException te) {
            result = "Received expected ContextNotActiveException";

        } catch (Exception e) {
            result = "Received unexcepted Exception :" + e.getClass();
            e.printStackTrace();
        }

        if (result.equals("Received expected ContextNotActiveException")) {
            getLogger().log(INFO, result);
            appendReason(result);
        } else {
            throw new IllegalStateException(result);
        }
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
    public void transactionScopedBean_withTransaction() throws Exception {

        String result = "";

        try {
            userTransaction.begin();
            getLogger().info("Invoking TransactionScopedBean.test() with a transaction Context");
            result = transactionScopedBean.test();
            userTransaction.commit();
        } catch (Exception e) {
            result = "Received unexcepted Exception :" + e.getClass();
            e.printStackTrace();
        }

        if (result.equals("TransactionScopedBean.test called with active transaction")) {
            getLogger().log(INFO, result);
            appendReason(result);
        } else {
            throw new IllegalStateException(result);
        }
    }

    /*
     * @testName: getInterceptorPriorityForTxTypeRequired
     *
     * @test_Strategy: The Transactional interceptors must have a priority of Interceptor.Priority.PLATFORM_BEFORE+200
     *
     */
    public void getInterceptorPriorityForTxTypeRequired() throws Exception {
        String methodName = "txTypeRequired";
        List<Integer> priorityList = one.getPriority(methodName);
        verifyInterceptorPriority(priorityList, "TxType.REQUIRED");
    }

    /*
     * @testName: getInterceptorPriorityForTxTypeRequiresNew
     *
     * @test_Strategy: The Transactional interceptors must have a priority of Interceptor.Priority.PLATFORM_BEFORE+200
     *
     */
    public void getInterceptorPriorityForTxTypeRequiresNew() throws Exception {
        String methodName = "txTypeRequiresNew";
        List<Integer> priorityList = one.getPriority(methodName);
        verifyInterceptorPriority(priorityList, "TxType.REQUIRES_NEW");
    }

    /*
     * @testName: getInterceptorPriorityForTxTypeMandatory
     *
     * @test_Strategy: The Transactional interceptors must have a priority of Interceptor.Priority.PLATFORM_BEFORE+200
     *
     */
    public void getInterceptorPriorityForTxTypeMandatory() throws Exception {
        verifyInterceptorPriority(one.getPriority("txTypeMandatory"), "TxType.MANDATORY");
    }

    /*
     * @testName: getInterceptorPriorityForTxTypeSupports
     *
     * @test_Strategy: The Transactional interceptors must have a priority of Interceptor.Priority.PLATFORM_BEFORE+200
     *
     */
    public void getInterceptorPriorityForTxTypeSupports() throws Exception {
        String methodName = "txTypeSupports";
        List<Integer> priorityList = one.getPriority(methodName);
        verifyInterceptorPriority(priorityList, "TxType.SUPPORTS");
    }

    /*
     * @testName: getInterceptorPriorityForTxTypeNotSupported
     *
     * @test_Strategy: The Transactional interceptors must have a priority of Interceptor.Priority.PLATFORM_BEFORE+200
     *
     */
    public void getInterceptorPriorityForTxTypeNotSupported() throws Exception {
        String methodName = "txTypeNotSupported";
        List<Integer> priorityList = one.getPriority(methodName);
        verifyInterceptorPriority(priorityList, "TxType.NOT_SUPPORTED");
    }

    /*
     * @testName: getInterceptorPriorityForTxTypeNever
     *
     * @test_Strategy: The Transactional interceptors must have a priority of Interceptor.Priority.PLATFORM_BEFORE+200
     *
     */
    public void getInterceptorPriorityForTxTypeNever() throws Exception {
        String methodName = "txTypeNever";
        List<Integer> priorityList = one.getPriority(methodName);
        verifyInterceptorPriority(priorityList, "TxType.NEVER");

    }

    private void verifyInterceptorPriority(List<Integer> priorityList, String txType) throws Exception {
        String result = null;
        if (priorityList.contains(PLATFORM_BEFORE + 200)) {
            getLogger().log(INFO, "Transactional Interceptor for " + txType + " has right interceptor priority");
            result = "Transactional Interceptor for " + txType + " has right interceptor priority";
        } else {
            throw new IllegalStateException(
                "Transactional Interceptor for " + txType +
                " has incorrect interceptor priority : " + Arrays.toString(priorityList.toArray()) +
                " Excpected value is :" + PLATFORM_BEFORE + 200);
        }

        if (result != null) {
            appendReason(result);
        }

    }

    private StringBuilder reasonBuffer;

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
}
