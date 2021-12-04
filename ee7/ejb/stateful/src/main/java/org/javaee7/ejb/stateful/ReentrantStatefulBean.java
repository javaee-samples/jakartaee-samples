package org.javaee7.ejb.stateful;

import jakarta.annotation.Resource;
import jakarta.ejb.SessionContext;
import jakarta.ejb.Stateful;

/**
 *
 * @author Arjan Tijms
 *
 */
@Stateful
public class ReentrantStatefulBean {

    @Resource
    private SessionContext sessionConext;

    public void initialMethod() {
        sessionConext.getBusinessObject(ReentrantStatefulBean.class).reentrantMehthod();
    }

    public void reentrantMehthod() {

    }

}
