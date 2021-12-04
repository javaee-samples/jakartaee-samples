package org.javaee7.ejb.stateful;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import jakarta.inject.Inject;

/**
 * This tests that a stateful bean is capable of calling a method via
 * a business proxy on itself.
 *
 * @author Arjan Tijms
 *
 */
@RunWith(Arquillian.class)
public class ReentrantCallTest {

    @Inject
    private ReentrantStatefulBean reentrantStatefulBean;

    @Deployment
    public static Archive<?> deployment() {
        return ShrinkWrap.create(WebArchive.class)
            .addClass(ReentrantStatefulBean.class);
    }

    @Test
    public void doReentrantCall() {
        // initialMethod() will internally call another method on itself.
        // This should not throw an exception. See e.g. https://issues.apache.org/jira/browse/OPENEJB-1099
        reentrantStatefulBean.initialMethod();
    }

}