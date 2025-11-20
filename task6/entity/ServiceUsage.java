package task6.entity;

import java.time.LocalDate;

public class ServiceUsage {
    private String id;
    private String guestId;
    private String serviceId;
    private LocalDate date;

    public ServiceUsage(String id, String guestId, String serviceId, LocalDate date) {
        this.id = id;
        this.guestId = guestId;
        this.serviceId = serviceId;
        this.date = date;
    }

    public String getId() { return id; }
    public String getGuestId() { return guestId; }
    public String getServiceId() { return serviceId; }
    public LocalDate getDate() { return date; }
}