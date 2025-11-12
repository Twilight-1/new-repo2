package task5.entity;

import java.time.LocalDate;

public class StayRecord {
    private Guest guest;
    private LocalDate checkInDate;
    private LocalDate checkOutDate; // computed from duration
    private int durationDays;

    public StayRecord(Guest guest, LocalDate checkInDate, int durationDays) {
        this.guest = guest;
        this.checkInDate = checkInDate;
        this.durationDays = durationDays;
        this.checkOutDate = checkInDate.plusDays(durationDays);
    }

    public Guest getGuest() { return guest; }
    public LocalDate getCheckInDate() { return checkInDate; }
    public LocalDate getCheckOutDate() { return checkOutDate; }
    public int getDurationDays() { return durationDays; }

    public void setCheckOutDate(LocalDate checkOutDate) { this.checkOutDate = checkOutDate; }
}
