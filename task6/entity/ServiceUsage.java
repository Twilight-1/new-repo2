package task6.entity;

import java.time.LocalDate;
import java.io.Serializable;

public class ServiceUsage implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private long guestId;
    private long serviceId;
    private LocalDate date;

    public ServiceUsage(long id, long guestId, long serviceId, LocalDate date) {
        this.id = id;
        this.guestId = guestId;
        this.serviceId = serviceId;
        this.date = date;
    }

    public long getId() { return id; }
    public long getGuestId() { return guestId; }
    public long getServiceId() { return serviceId; }
    public LocalDate getDate() { return date; }
}