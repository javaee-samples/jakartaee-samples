package org.javaee7.ejb.timer;

import java.util.Collection;

import jakarta.annotation.Resource;
import jakarta.ejb.Schedule;
import jakarta.ejb.SessionContext;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.Timer;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

/**
 * @author Arun Gupta
 */
@Startup
@Singleton
public class AutomaticTimerBean {

    @Resource
    SessionContext ctx;

    @Inject
    Event<Ping> pingEvent;

    @Schedule(hour = "*", minute = "*", second = "*/5", info = "Every 5 second timer")
    public void printDate() {
        Collection<Timer> timers = ctx.getTimerService().getAllTimers();
        for (Timer t : timers) {
            pingEvent.fire(new Ping(t.getInfo().toString()));
        }
    }

}
