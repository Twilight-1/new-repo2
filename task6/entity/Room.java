package task6.entity;

import task6.entity.enums.RoomStatus;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Room implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private int number;
    private double price;
    private int capacity;
    private int stars;
    private RoomStatus status;
    private Long currentGuestId; // nullable
    private final List<StayRecord> history = new ArrayList<>();

    public Room(long id, int number, double price, int capacity, int stars, RoomStatus status) {
        this.id = id;
        this.number = number;
        this.price = price;
        this.capacity = capacity;
        this.stars = stars;
        this.status = status;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public int getNumber() { return number; }
    public double getPrice() { return price; }
    public int getCapacity() { return capacity; }
    public int getStars() { return stars; }
    public RoomStatus getStatus() { return status; }
    public void setPrice(double price) { this.price = price; }
    public void setStatus(RoomStatus status) { this.status = status; }

    public Long getCurrentGuestId() { return currentGuestId; }
    public void setCurrentGuestId(Long currentGuestId) { this.currentGuestId = currentGuestId; }

    public List<StayRecord> getHistory() { return history; }
}