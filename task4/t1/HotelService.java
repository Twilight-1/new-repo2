package task4.t1;

import java.time.LocalDate;

public class HotelService {
    private RoomService roomService = new RoomService();
    private ServiceService serviceService = new ServiceService();
    private ServiceUsageService usageService = new ServiceUsageService();

    public void addRoom(Room room) {
        roomService.addRoom(room);
    }
    public void addService(Service service) {
        serviceService.addService(service);
    }

    public void checkIn(int roomNumber, Guest guest, LocalDate checkInDate) {
        roomService.checkIn(roomNumber, guest, checkInDate);
    }

    public void checkOut(int roomNumber) {
        roomService.checkOut(roomNumber);
    }

    public void updateRoomStatus(int roomNumber, RoomStatus status) {
        roomService.updateRoomStatus(roomNumber, status);
    }

    public void updateRoomPrice(int roomNumber, double price) {
        roomService.updateRoomPrice(roomNumber, price);
    }

    public void showAllRooms(String criterion) {
        roomService.showRoomsSortedBy(criterion);
    }

    public void showFreeRooms(String criterion) {
        roomService.showFreeRooms(criterion);
    }

    public void showGuestsSortedByName() {
        roomService.showGuestsSortedByName();
    }

    public void showGuestsSortedByCheckoutDate() {
        roomService.showGuestsSortedByCheckoutDate();
    }

    public void addServiceUsage(Guest guest, Service service, LocalDate dateUsed) {
        usageService.addServiceUsage(guest, service, dateUsed);
    }

    public void showGuestServices(Guest guest, String criterion) {
        usageService.showGuestServicesSorted(guest, criterion);
    }

    public double getTotalServiceCost(Guest guest) {
        return usageService.getTotalServiceCost(guest);
    }

    public RoomService getRoomService() {
        return roomService;
    }
}

