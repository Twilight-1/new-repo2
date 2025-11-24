package task6.entity;

import java.io.Serializable;
import java.time.LocalDate;

public class StayRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private long guestId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int durationDays;

    public StayRecord(long id, long guestId, LocalDate checkInDate, int durationDays) {
        this.id = id;
        this.guestId = guestId;
        this.checkInDate = checkInDate;
        this.durationDays = durationDays;
        this.checkOutDate = checkInDate.plusDays(durationDays);
    }

    public long getId() { return id; }
    public long getGuestId() { return guestId; }
    public LocalDate getCheckInDate() { return checkInDate; }
    public LocalDate getCheckOutDate() { return checkOutDate; }
    public int getDurationDays() { return durationDays; }
    public void setCheckOutDate(LocalDate d) { this.checkOutDate = d; }
}