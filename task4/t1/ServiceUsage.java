package task4.t1;

import java.time.LocalDate;

public class ServiceUsage {
    private Guest guest;
    private Service service;
    private LocalDate dateUsed;

    public ServiceUsage(Guest guest, Service service, LocalDate dateUsed) {
        this.guest = guest;
        this.service = service;
        this.dateUsed = dateUsed;
    }

    public Guest getGuest() {
        return guest;
    }
    public Service getService() {
        return service;
    }
    public LocalDate getDateUsed() {
        return dateUsed;
    }
}