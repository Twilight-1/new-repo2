package task6.controllers;

import task6.entity.*;
import task6.services.*;
import task6.util.AppException;
import java.time.LocalDate;
import java.util.Optional;

public class HotelController {
    private final GuestService guestService;
    private final RoomService roomService;
    private final ServiceUsageService usageService;
    private final ServiceService serviceService;

    public HotelController(GuestService gs, RoomService rs, ServiceUsageService us, ServiceService ss) {
        this.guestService = gs;
        this.roomService = rs;
        this.usageService = us;
        this.serviceService = ss;
    }
    public void checkInByNumber(int roomNumber, String guestId, int days) {
        if (guestId == null || guestId.isBlank()) throw new AppException("Guest id required for this operation via facade");
        Guest g = guestService.getById(guestId).orElseThrow(() -> new AppException("Guest not found: " + guestId));
        roomService.checkIn(roomNumber, g, LocalDate.now(), days);
    }

    public void checkOutByNumber(int roomNumber) {
        roomService.checkOut(roomNumber);
    }

    public void calculateBillForRoom(int roomNumber) {
        double roomSum = roomService.calculateRoomBill(roomNumber);
        Optional<Room> r = roomService.findRoomByNumber(roomNumber);
        if (r.isEmpty()) { System.out.println("Room not found"); return; }
        Room room = r.get();
        if (room.getCurrentGuestId() == null) { System.out.println("No guest in room"); return; }
        Guest g = guestService.getById(room.getCurrentGuestId()).orElseThrow(() -> new AppException("Guest not found"));
        double services = usageService.getForGuest(g.getId()).stream()
                .mapToDouble(u -> serviceService.getById(u.getServiceId()).map(Service::getPrice).orElse(0.0)).sum();
        System.out.println("Room: " + roomSum + " Services: " + services + " Total: " + (roomSum + services));
    }
}
