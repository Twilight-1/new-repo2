package task4.t1;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private int number;
    private double price;
    private int capacity;
    private int stars;
    private RoomStatus status;
    private Guest currentGuest;
    private List<StayRecord> history = new ArrayList<>();

    public Room(int number, double price, int capacity, int stars, RoomStatus status) {
        this.number = number;
        this.price = price;
        this.capacity = capacity;
        this.stars = stars;
        this.status = status;
    }

    public int getNumber() {
        return number;
    }
    public double getPrice() {
        return price;
    }
    public int getCapacity() {
        return capacity;
    }
    public int getStars() {
        return stars;
    }
    public RoomStatus getStatus() {
        return status;
    }
    public Guest getCurrentGuest() {
        return currentGuest;
    }
    public List<StayRecord> getHistory() {
        return history;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public void setStatus(RoomStatus status) {
        this.status = status;
    }
    public void setCurrentGuest(Guest guest) {
        this.currentGuest = guest;
    }
}

