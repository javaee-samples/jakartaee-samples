/** Copyright Payara Services Limited **/
package org.javaee7.ejb.remote.remote;

import jakarta.ejb.Remote;

@Remote
public interface BeanRemote {
    String method();
}
