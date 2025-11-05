package task4.t1;

import java.time.LocalDate;

public class StayRecord {
    private Guest guest;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    public StayRecord(Guest guest, LocalDate checkInDate, LocalDate checkOutDate ) {
        this.guest = guest;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Guest getGuest() {
        return guest;
    }
    public LocalDate getCheckInDate() {
        return checkInDate;
    }
    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
}
