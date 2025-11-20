package task6.builders;

import task6.entity.Guest;

import java.util.Scanner;
import java.util.UUID;

public class GuestBuilder {
    private final Scanner scanner;

    public GuestBuilder(Scanner scanner) { this.scanner = scanner; }

    public Guest buildFromConsole() {
        System.out.print("Guest ID (enter to auto-generate): ");
        String id = scanner.nextLine().trim();
        if (id.isEmpty()) id = UUID.randomUUID().toString();
        System.out.print("Guest name: ");
        String name = scanner.nextLine().trim();
        return new Guest(id, name);
    }

    public Guest buildFromParams(String id, String name) {
        if (id == null || id.isBlank()) id = UUID.randomUUID().toString();
        return new Guest(id, name);
    }
}

