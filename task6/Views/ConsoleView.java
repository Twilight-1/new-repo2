package task6.Views;

import task6.entity.Room;
import task6.entity.Guest;
import task6.services.RoomService;

import java.util.List;

public class ConsoleView {
    public void print(String s) { System.out.println(s); }
    public void printError(String s) { System.err.println("ERROR: " + s); }

    public void printMainMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Rooms");
        System.out.println("2. Guests");
        System.out.println("3. Services");
        System.out.println("4. Import/Export");
        System.out.println("0. Exit");
        System.out.print("Choice: ");
    }

    public void printRoomMenu() {
        System.out.println("\n--- Rooms Menu ---");
        System.out.println("1. Show all rooms");
        System.out.println("2. Show free rooms");
        System.out.println("3. Room details");
        System.out.println("4. Add room");
        System.out.println("5. Check-in");
        System.out.println("6. Check-out");
        System.out.println("7. Calculate bill");
        System.out.print("Choice: ");
    }

    public void printGuestMenu() {
        System.out.println("\n--- Guests Menu ---");
        System.out.println("1. Add guest");
        System.out.println("2. Show guests");
        System.out.print("Choice: ");
    }

    public void printServiceMenu() {
        System.out.println("\n--- Services Menu ---");
        System.out.println("1. Add service");
        System.out.println("2. Show services");
        System.out.print("Choice: ");
    }

    public void printImportExportMenu() {
        System.out.println("\n--- Import/Export Menu ---");
        System.out.println("1. Export guests");
        System.out.println("2. Import guests");
        System.out.println("3. Export services");
        System.out.println("4. Import services");
        System.out.println("5. Export rooms");
        System.out.println("6. Import rooms");
        System.out.println("7. Export stayrecords");
        System.out.println("8. Import stayrecords");
        System.out.println("9. Export usages");
        System.out.println("10. Import usages");
        System.out.print("Choice: ");
    }

    public void printRooms(List<Room> list) {
        for (Room r : list) {
            System.out.println("Room " + r.getNumber() + " | price: " + r.getPrice() + " | cap: " + r.getCapacity()
                    + " | stars: " + r.getStars() + " | status: " + r.getStatus()
                    + (r.getCurrentGuestId() != null ? " | guestId: " + r.getCurrentGuestId() : ""));
        }
    }

    public void printRoomDetails(Room r) {
        System.out.println("Room " + r.getNumber() + " details:");
        System.out.println("Price: " + r.getPrice());
        System.out.println("Capacity: " + r.getCapacity());
        System.out.println("Stars: " + r.getStars());
        System.out.println("Status: " + r.getStatus());
        System.out.println("Current guest id: " + r.getCurrentGuestId());
        System.out.println("History size: " + r.getHistory().size());
    }

    public void printGuestsWithRooms(List<Guest> guests, RoomService roomService) {
        for (Guest g : guests) {
            String roomNum = roomService.getAll().stream()
                    .filter(r -> g.getId().equals(r.getCurrentGuestId()))
                    .map(r -> Integer.toString(r.getNumber()))
                    .findFirst().orElse("—");
            System.out.println(g.getName() + " | id: " + g.getId() + " | room: " + roomNum);
        }
    }

    public void printGuests(List<Guest> guests) {
        for (Guest g : guests) System.out.println(g.getName() + " | id: " + g.getId());
    }

    public void printServices(java.util.List<task6.entity.Service> services) {
        for (task6.entity.Service s : services) System.out.println(s.getName() + " | " + s.getPrice() + " | id: " + s.getId());
    }
}

