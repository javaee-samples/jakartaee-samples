/** Copyright Payara Services Limited **/
package org.javaee7.ejb.remote.remote;

import java.io.Serializable;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;

@Stateless
public class Bean implements BeanRemote, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    @RolesAllowed("g1")
    public String method() {
        return "method";
    }

}
