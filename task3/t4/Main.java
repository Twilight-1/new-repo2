package task3.t4;

public class Main {
    public static void main(String[] args) {
        Hotel hotel = new Hotel();

        hotel.addRoom(new Room(101, 3000));
        hotel.addRoom(new Room(102, 4000));
        hotel.addService(new Service("Завтрак", 500));
        hotel.addService(new Service("Парковка", 300));

        Guest guest = new Guest("Иван Иванов", "G001");
        hotel.checkIn(guest, 101);

        hotel.showAllRooms();

        hotel.setRoomStatus(102, RoomStatus.UNDER_MAINTENANCE);
        hotel.updateRoomPrice(101, 3500);
        hotel.updateServicePrice("Завтрак", 600);

        hotel.checkOut(101);
        hotel.showAllRooms();
        hotel.showAllServices();
    }
}


