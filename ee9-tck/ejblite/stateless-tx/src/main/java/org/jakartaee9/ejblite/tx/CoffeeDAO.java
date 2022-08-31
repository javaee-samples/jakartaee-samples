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

package org.jakartaee9.ejblite.tx;

import static java.util.logging.Level.FINE;
import static org.jakartaee9.ejblite.tx.Asserts.assertEquals;
import static org.jakartaee9.ejblite.tx.Asserts.getLogger;

import jakarta.persistence.EntityManager;
import jakarta.transaction.UserTransaction;

public class CoffeeDAO {

    private CoffeeDAO() {
    }

    public static void deleteCoffeeInNewUserTransaction(int id, EntityManager entityManager, UserTransaction userTransaction) {
        try {
            userTransaction.begin();

            findDelete(id, false, entityManager);

            userTransaction.commit();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void findDelete(int id, boolean existingDataExpected, EntityManager entityManager) {
        CoffeeEntity coffeeFound = entityManager.find(CoffeeEntity.class, id);

        if (coffeeFound != null) {
            getLogger().logp(FINE, "TestBean", "findDelete",
                    "Current coffee bean already exists in db, need to delete it first: " + coffeeFound);
            entityManager.remove(coffeeFound);
            entityManager.flush();
        } else if (existingDataExpected) {
            throw new IllegalStateException("Tried to find coffee but got null, id=" + id);
        }

        getLogger().logp(FINE, "TestBean", "findDelete",
                "Current coffee bean not in db, no need to delete it. id=" + id);

    }

    public static CoffeeEntity findDeletePersist(int id, String brandName, EntityManager entityManager) {
        findDelete(id, false, entityManager);
        CoffeeEntity CoffeeEntity = new CoffeeEntity(id, brandName, id);
        entityManager.persist(CoffeeEntity);

        return CoffeeEntity;
    }

    public static String verifyCoffee(int id, String brandName, EntityManager entityManager, boolean expectingFound) {
        CoffeeEntity coffeeFound = entityManager.find(CoffeeEntity.class, id);

        if (expectingFound) {
            return assertEquals("verify coffee", brandName, coffeeFound.getBrandName());
        }

        return assertEquals("verify coffee", null, coffeeFound);
    }
}
