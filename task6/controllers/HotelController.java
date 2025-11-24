package task6.controllers;

import task6.Views.ConsoleView;
import task6.entity.*;
import task6.services.*;
import task6.entity.Guest;
import task6.entity.Room;
import task6.entity.Service;
import java.time.LocalDate;
import java.util.Optional;

public class HotelController {
    private final GuestService guestService;
    private final RoomService roomService;
    private final ServiceUsageService usageService;
    private final ServiceService serviceService;
    private final ConsoleView view;

    public HotelController(GuestService gs, RoomService rs, ServiceUsageService us, ServiceService ss, ConsoleView v) {
        this.guestService = gs; this.roomService = rs; this.usageService = us; this.serviceService = ss; this.view = v;
    }

    // helper: if guestId blank -> create guest interactively via view (not ideal here, but simple)
    public void checkInByNumber(int number, String guestIdStr, int days) {
        Guest g;
        if (guestIdStr == null || guestIdStr.isBlank()) {
            view.print("Guest name: ");
            String name = new java.util.Scanner(System.in).nextLine().trim();
            g = new Guest(0, name);
        } else {
            long gid = Long.parseLong(guestIdStr);
            Optional<Guest> og = guestService.getById(gid);
            if (og.isEmpty()) throw new RuntimeException("Guest id not found: " + gid);
            g = og.get();
        }
        roomService.checkIn(number, g, LocalDate.now(), days, guestService);
        view.print("Checked in guest id=" + g.getId());
    }

    public void checkOutByNumber(int number) {
        roomService.checkOut(number);
        view.print("Checked out room number " + number);
    }

    public void calculateBillForRoom(int number) {
        double roomSum = roomService.calculateRoomBill(number);
        Optional<Room> or = roomService.findByNumber(number);
        if (or.isEmpty()) { view.print("Room not found"); return; }
        Room r = or.get();
        if (r.getCurrentGuestId() == null) { view.print("No guest in room"); return; }
        long gid = r.getCurrentGuestId();
        double servicesSum = usageService.getForGuest(gid).stream()
                .mapToDouble(u -> serviceService.getById(u.getServiceId()).map(Service::getPrice).orElse(0.0))
                .sum();
        view.print("Room: " + roomSum + " Services: " + servicesSum + " Total: " + (roomSum + servicesSum));
    }

    public void populateDemoData() {
        // minimal demo data
        Guest g1 = new Guest(0, "Demo Guest");
        guestService.addOrUpdate(g1);
        Service s1 = new Service(0, "Demo Breakfast", 300);
        serviceService.addOrUpdate(s1);
        Room r1 = new Room(0, 201, 2000, 2, 3, task6.entity.enums.RoomStatus.AVAILABLE);
        roomService.addOrUpdate(r1);
        view.print("Demo data added");
    }
}
