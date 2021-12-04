package org.javaee7.ejb.timer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.enterprise.event.Observes;

@Startup
@Singleton
public class PingsListener {

    final List<Ping> pings = new CopyOnWriteArrayList<>();

    public void listen(@Observes Ping ping) {
        System.out.println("ping = " + ping);
        pings.add(ping);
    }

    public List<Ping> getPings() {
        return pings;
    }
}
