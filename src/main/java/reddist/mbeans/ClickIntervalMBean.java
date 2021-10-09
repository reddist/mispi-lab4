package reddist.mbeans;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@ManagedResource
@Component
public class ClickIntervalMBean {
    private double interval = 0.0D;
    private Date firstClick = new Date();
    public AtomicInteger counter = new AtomicInteger(0);

    @ManagedOperation
    public void click () {
        Date newClickTime = new Date();
        int count = counter.incrementAndGet();
        if (count == 1) {
            firstClick = newClickTime;
            interval = 0.0D;
        } else {
            interval = (newClickTime.getTime() - firstClick.getTime()) / (count + 0.0D);
        }
    }

    @ManagedAttribute
    public double getInterval() {
        return interval;
    }

    public void clear() {
        this.interval = 0.0D;
        this.firstClick = new Date();
        this.counter.set(0);
    }
}
