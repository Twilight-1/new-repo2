package task6.builders;

import task6.entity.Room;
import task6.entity.enums.RoomStatus;

import java.util.Scanner;

public class RoomBuilder {
    private final Scanner scanner;
    public RoomBuilder(Scanner scanner){ this.scanner = scanner; }
    public Room buildFromConsole() {
        System.out.print("Number (int): ");
        int number = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Price: ");
        double price = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Capacity: ");
        int cap = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Stars: ");
        int stars = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Status (AVAILABLE/OCCUPIED/UNDER_MAINTENANCE): ");
        RoomStatus rs = RoomStatus.valueOf(scanner.nextLine().trim());
        return new Room(0, number, price, cap, stars, rs);
    }
}

