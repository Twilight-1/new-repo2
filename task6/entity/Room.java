package task6.entity;

import task6.entity.enums.RoomStatus;
import java.util.ArrayList;
import java.util.List;

public class Room {
    private String id;
    private int number;
    private double price;
    private int capacity;
    private int stars;
    private RoomStatus status;
    private String currentGuestId; // nullable
    private List<StayRecord> history = new ArrayList<>();

    public Room(String id, int number, double price, int capacity, int stars, RoomStatus status) {
        this.id = id;
        this.number = number;
        this.price = price;
        this.capacity = capacity;
        this.stars = stars;
        this.status = status;
    }

    public String getId() { return id; }
    public int getNumber() { return number; }
    public double getPrice() { return price; }
    public int getCapacity() { return capacity; }
    public int getStars() { return stars; }
    public RoomStatus getStatus() { return status; }
    public String getCurrentGuestId() { return currentGuestId; }
    public List<StayRecord> getHistory() { return history; }

    public void setPrice(double price) { this.price = price; }
    public void setStatus(RoomStatus status) { this.status = status; }
    public void setCurrentGuestId(String guestId) { this.currentGuestId = guestId; }
}