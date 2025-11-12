package task5.services;

import task5.entity.*;
import task5.entity.enums.RoomStatus;

import java.time.LocalDate;

public class HotelService {
    private final RoomService roomService = new RoomService();
    private final ServiceService serviceService = new ServiceService();
    private final ServiceUsageService usageService = new ServiceUsageService();

    // room proxies
    public void addRoom(Room room) { roomService.addRoom(room); }
    public void checkIn(int roomNumber, Guest guest, LocalDate checkInDate, int days) { roomService.checkIn(roomNumber, guest, checkInDate, days); }
    public void checkOut(int roomNumber) { roomService.checkOut(roomNumber); }
    public void updateRoomPrice(int roomNumber, double price) { roomService.updateRoomPrice(roomNumber, price); }
    public void updateRoomStatus(int roomNumber, RoomStatus status) { roomService.updateRoomStatus(roomNumber, status); }
    public void showAllRooms(String sortBy) { roomService.showAllRooms(sortBy); }
    public void showFreeRooms(String sortBy) { roomService.showFreeRooms(sortBy); }
    public void showGuestsWithRooms(String sortBy) { roomService.showGuestsWithRooms(sortBy); }
    public long countFreeRooms() { return roomService.countFreeRooms(); }
    public long countGuests() { return roomService.countGuests(); }
    public void showRoomsFreeOn(LocalDate date) { roomService.showRoomsFreeOn(date); }
    public double calculateRoomBill(int roomNumber) { return roomService.calculateRoomBill(roomNumber); }
    public void showLast3Guests(int roomNumber) { roomService.showLast3Guests(roomNumber); }
    public void showRoomDetails(int roomNumber) { roomService.showRoomDetails(roomNumber); }

    // services proxies
    public void addService(Service s) { serviceService.addService(s); }
    public void updateServicePrice(String name, double price) { serviceService.updateServicePrice(name, price); }
    public void showServicesByPrice() { serviceService.showServicesSortedByPrice(); }

    // usage proxies
    public void addServiceUsage(Guest guest, Service service, LocalDate date) { usageService.addUsage(guest, service, date); }
    public void showGuestServices(Guest guest, String sortBy) { usageService.showGuestServicesSorted(guest, sortBy); }
    public double getTotalServiceCost(Guest guest) { return usageService.getTotalForGuest(guest); }

    // accessors for controller/view if needed
    public RoomService getRoomService() { return roomService; }
    public ServiceService getServiceService() { return serviceService; }
    public ServiceUsageService getUsageService() { return usageService; }
}
