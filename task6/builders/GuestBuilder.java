package task6.builders;

import task6.entity.Guest;
import java.util.Scanner;


public class GuestBuilder {
    private final Scanner scanner;
    public GuestBuilder(Scanner scanner){ this.scanner = scanner; }
    public Guest buildFromConsole() {
        System.out.print("Guest name: ");
        String name = scanner.nextLine().trim();
        return new Guest(0, name); // id=0 -> service will assign
    }
}

