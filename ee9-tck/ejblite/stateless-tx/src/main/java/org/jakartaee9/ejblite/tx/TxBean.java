/*
 * Copyright (c) 2007, 2020 Oracle and/or its affiliates. All rights reserved.
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

import static jakarta.ejb.TransactionAttributeType.MANDATORY;
import static jakarta.ejb.TransactionAttributeType.REQUIRED;
import static jakarta.ejb.TransactionAttributeType.REQUIRES_NEW;
import static jakarta.ejb.TransactionAttributeType.SUPPORTS;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class TxBean {

    @PersistenceContext(unitName = "ejblite-pu")
    private EntityManager entityManager;

    /*
     * @testName: supports
     *
     * @test_Strategy:
     */
    @TransactionAttribute(SUPPORTS)
    public void supports(CoffeeEntity coffeeEntity, boolean flush) {
        updatePersist(coffeeEntity, flush);
    }

    /*
     * @testName: mandatory
     *
     * @test_Strategy:
     */
    @TransactionAttribute(MANDATORY)
    public void mandatory(CoffeeEntity coffeeEntity, boolean flush) {
        updatePersist(coffeeEntity, flush);
    }

    /*
     * @testName: required
     *
     * @test_Strategy:
     */
    @TransactionAttribute(REQUIRED)
    public void required(CoffeeEntity coffeeEntity, boolean flush) {
        updatePersist(coffeeEntity, flush);
    }

    /*
     * @testName: requiresNew
     *
     * @test_Strategy:
     */
    @TransactionAttribute(REQUIRES_NEW)
    public void requiresNew(CoffeeEntity coffeeEntity, boolean flush) {
        updatePersist(coffeeEntity, flush);
    }

    /*
     * @testName: requiredNoExistingTransaction
     *
     * @test_Strategy:
     */
    // Default REQUIRED
    public void requiredNoExistingTransaction(CoffeeEntity coffee, boolean flush) {
        updatePersist(coffee, flush);
    }


    protected void updatePersist(CoffeeEntity coffeeEntity, boolean flush) {
        coffeeEntity.setPrice(coffeeEntity.getPrice() + 100);
        entityManager.persist(coffeeEntity);

        if (flush) {
            entityManager.flush();
        }
    }

}
