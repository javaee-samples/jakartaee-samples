package org.javaee7.ejb.timer;

import jakarta.ejb.Schedule;
import jakarta.ejb.Schedules;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.Timer;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

/**
 * @author Jacek Jackowiak
 */
@Startup
@Singleton
public class SchedulesTimerBean {

    @Inject
    Event<Ping> pingEvent;

    @Schedules({
            @Schedule(hour = "*", minute = "*", second = "*/5", info = "Every 5 second timer"),
            @Schedule(hour = "*", minute = "*", second = "*/10", info = "Every 10 second timer")
    })
    public void automaticallyScheduled(Timer timer) {
        pingEvent.fire(new Ping(timer.getInfo().toString()));
    }

}
