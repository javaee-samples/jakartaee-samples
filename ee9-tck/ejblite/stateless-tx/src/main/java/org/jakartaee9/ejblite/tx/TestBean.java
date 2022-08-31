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

import static jakarta.ejb.TransactionManagementType.BEAN;

import jakarta.annotation.Resource;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.UserTransaction;

@Stateless
@TransactionManagement(BEAN)
public class TestBean {

    private EntityManager entityManager;
    private UserTransaction userTransaction;

    private TxBean txBean;

    @PersistenceContext(unitName = "ejblite-pu")
    public void setEm(EntityManager entityManager) {
      this.entityManager = entityManager;
    }

    @Resource
    public void setUt(UserTransaction userTransaction) {
      this.userTransaction = userTransaction;
    }

    @EJB(beanInterface = TxBean.class)
    public void setTxBean(TxBean b) {
        txBean = b;
    }

    public String mandatory(boolean flush) {
        int id = 1;
        float price = id;
        float targetPrice = price + 100;

        CoffeeEntity coffee = new CoffeeEntity(id, "mandatory", price);
        CoffeeDAO.deleteCoffeeInNewUserTransaction(coffee.getId(), entityManager, userTransaction);

        String result = "";

        try {
            userTransaction.begin();
            txBean.mandatory(coffee, flush);
            coffee = entityManager.find(CoffeeEntity.class, id);
            result += Asserts.assertEquals("Check coffee price ", targetPrice, coffee.getPrice());
            userTransaction.rollback();
            CoffeeEntity coffeeFound = entityManager.find(CoffeeEntity.class, id);
            result += Asserts.assertEquals("Check coffee from em.find()", null, coffeeFound);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public String required(boolean flush) {
        int id = 2;
        float price = id;
        float targetPrice = price + 100;

        CoffeeEntity coffee = new CoffeeEntity(id, "required", price);
        CoffeeDAO.deleteCoffeeInNewUserTransaction(coffee.getId(), entityManager, userTransaction);

        String result = "";

        try {
            userTransaction.begin();
            txBean.required(coffee, flush);
            coffee = entityManager.find(CoffeeEntity.class, id);
            result += Asserts.assertEquals("Check coffee price ", targetPrice, coffee.getPrice());
            userTransaction.rollback();
            CoffeeEntity coffeeFound = entityManager.find(CoffeeEntity.class, id);
            result += Asserts.assertEquals("Check coffee from em.find()", null, coffeeFound);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public String requiredNoExistingTransaction(boolean flush) {
        int id = 21;
        float price = id;
        float targetPrice = price + 100;

        CoffeeEntity coffee = new CoffeeEntity(id, "requiredNoExisting", price);
        CoffeeDAO.deleteCoffeeInNewUserTransaction(coffee.getId(), entityManager, userTransaction);

        txBean.requiredNoExistingTransaction(coffee, flush);
        coffee = entityManager.find(CoffeeEntity.class, id);
        return Asserts.assertEquals("Check coffee price ", targetPrice, coffee.getPrice());
    }

    public String supports(boolean flush) {
        int id = 3;
        float price = id;
        float targetPrice = price + 100;

        CoffeeEntity coffee = new CoffeeEntity(id, "supports", price);
        CoffeeDAO.deleteCoffeeInNewUserTransaction(coffee.getId(), entityManager, userTransaction);

        String result = "";

        try {
            userTransaction.begin();
            txBean.supports(coffee, flush);
            coffee = entityManager.find(CoffeeEntity.class, id);
            result += Asserts.assertEquals("Check coffee price ", targetPrice, coffee.getPrice());
            userTransaction.rollback();
            CoffeeEntity coffeeFound = entityManager.find(CoffeeEntity.class, id);
            result += Asserts.assertEquals("Check coffee from em.find()", null, coffeeFound);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public String requiresNew(boolean flush) {
        int id = 4;
        float price = id;
        float targetPrice = price + 100;

        CoffeeEntity coffee = new CoffeeEntity(id, "requiresNew", price);
        CoffeeDAO.deleteCoffeeInNewUserTransaction(coffee.getId(), entityManager, userTransaction);

        String result = "";

        try {
            userTransaction.begin();
            txBean.requiresNew(coffee, flush);
            coffee = entityManager.find(CoffeeEntity.class, id);
            result += Asserts.assertEquals("Check coffee price ", targetPrice, coffee.getPrice());
            userTransaction.rollback();
            CoffeeEntity coffeeFound = entityManager.find(CoffeeEntity.class, id);
            result += Asserts.assertEquals("Check coffee id from em.find()", id, coffeeFound.getId());
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

}
