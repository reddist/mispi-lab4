package reddist.mbeans;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import javax.management.Notification;

import org.springframework.jmx.export.notification.NotificationPublisher;
import org.springframework.jmx.export.notification.NotificationPublisherAware;

import java.util.concurrent.atomic.AtomicLong;

@ManagedResource
@Component
public class PointCounterMBean implements NotificationPublisherAware {
    private int pointCount = 0;
    private int hitCount = 0;
    private int missCounter = 0;

    private final AtomicLong notificationSequence = new AtomicLong();
    private NotificationPublisher notificationPublisher;

    @ManagedOperation
    public void count(boolean isMiss) {
        if (isMiss) {
            missCounter++;
        } else {
            hitCount++;
            missCounter = 0;
        }
        if (missCounter >= 4){
            this.trigger();
        }
        pointCount++;
    }

    @ManagedAttribute
    public int getHitCount() {
        return hitCount;
    }

    @ManagedAttribute
    public int getPointCount() {
        return pointCount;
    }

    @Override
    public void setNotificationPublisher(NotificationPublisher notificationPublisher) {
        this.notificationPublisher = notificationPublisher;
    }

    @ManagedOperation
    public void trigger() {
        if (notificationPublisher != null) {
            final Notification notification = new Notification(
                    "user.fourMisses",
                    this,
                    notificationSequence.getAndIncrement(),
                    "Вы промазали 4 раза подряд!"
            );
            notificationPublisher.sendNotification(notification);
        }
    }

    public void clear () {
        this.pointCount = 0;
        this.missCounter = 0;
        this.hitCount = 0;
        this.notificationSequence.set(0);
    }
}

