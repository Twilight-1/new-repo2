package task6.builders;

import task6.entity.Room;
import task6.entity.enums.RoomStatus;

import java.util.Scanner;
import java.util.UUID;

public class RoomBuilder {
    private final Scanner scanner;

    public RoomBuilder(Scanner scanner) { this.scanner = scanner; }

    public Room buildFromConsole() {
        System.out.print("Room ID (enter to auto-generate): ");
        String id = scanner.nextLine().trim();
        if (id.isEmpty()) id = UUID.randomUUID().toString();
        System.out.print("Room number (int): ");
        int number = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Price: ");
        double price = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Capacity: ");
        int cap = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Stars: ");
        int stars = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Status (AVAILABLE/OCCUPIED/UNDER_MAINTENANCE): ");
        RoomStatus status = RoomStatus.valueOf(scanner.nextLine().trim());
        return new Room(id, number, price, cap, stars, status);
    }
}

