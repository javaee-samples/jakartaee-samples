package org.javaee7.ejb.timer;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.ScheduleExpression;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.Timeout;
import jakarta.ejb.Timer;
import jakarta.ejb.TimerConfig;
import jakarta.ejb.TimerService;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

/**
 * author: Jacek Jackowiak
 */
@Startup
@Singleton
public class ProgrammaticTimerBean {

    @Inject
    Event<Ping> pingEvent;

    @Resource
    TimerService timerService;

    @PostConstruct
    public void initialize() {
        ScheduleExpression scheduleExpression = new ScheduleExpression()
                .hour("*")
                .minute("*")
                .second("*/5");

        TimerConfig timerConfig = new TimerConfig();
        timerConfig.setInfo("Every 5 second timer");

        timerService.createCalendarTimer(scheduleExpression, timerConfig);
    }

    @Timeout
    public void programmaticTimout(Timer timer) {
        pingEvent.fire(new Ping(timer.getInfo().toString()));
    }
}
