/** Copyright Payara Services Limited **/
package org.javaee7.ejb.remote.ssl;

import jakarta.ejb.Remote;

@Remote
public interface BeanRemote {
    String method();
}
