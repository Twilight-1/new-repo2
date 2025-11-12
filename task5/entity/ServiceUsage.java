package task5.entity;

import java.time.LocalDate;

public class ServiceUsage {
    private Guest guest;
    private Service service;
    private LocalDate date;

    public ServiceUsage(Guest guest, Service service, LocalDate date) {
        this.guest = guest;
        this.service = service;
        this.date = date;
    }

    public Guest getGuest() { return guest; }
    public Service getService() { return service; }
    public LocalDate getDate() { return date; }
}
