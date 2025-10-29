package task3.t4;

public class Room {
    private final int number;
    private double price;
    private RoomStatus status;
    private Guest guest;

    public Room(int number, double price) {
        this.number = number;
        this.price = price;
        this.status = RoomStatus.AVAILABLE;
    }

    public int getNumber() {
        return number;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void checkIn(Guest guest) {
        if (status == RoomStatus.AVAILABLE) {
            this.guest = guest;
            this.status = RoomStatus.OCCUPIED;
            System.out.println("Гость " + guest.getName() + " поселён в номер " + number);
        } else {
            System.out.println("Номер " + number + " недоступен для заселения!");
        }
    }

    public void checkOut() {
        if (status == RoomStatus.OCCUPIED) {
            System.out.println("Гость " + guest.getName() + " выселен из номера " + number);
            this.guest = null;
            this.status = RoomStatus.AVAILABLE;
        } else {
            System.out.println("В номере " + number + " никто не проживает!");
        }
    }
}
