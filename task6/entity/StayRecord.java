package task6.entity;

import java.time.LocalDate;

public class StayRecord {
    private String id;
    private String guestId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate; // computed from duration
    private int durationDays;

    public StayRecord(String id, String guestId, LocalDate checkInDate, int durationDays) {
        this.id = id;
        this.guestId = guestId;
        this.checkInDate = checkInDate;
        this.durationDays = durationDays;
        this.checkOutDate = checkInDate.plusDays(durationDays);
    }

    public String getId() { return id; }
    public String getGuestId() { return guestId; }
    public LocalDate getCheckInDate() { return checkInDate; }
    public LocalDate getCheckOutDate() { return checkOutDate; }
    public int getDurationDays() { return durationDays; }

    public void setCheckOutDate(LocalDate checkOutDate) { this.checkOutDate = checkOutDate; }
}