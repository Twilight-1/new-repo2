package task6.builders;
import task6.entity.Service;

import java.util.Scanner;
import java.util.UUID;

public class ServiceBuilder {
    private final Scanner scanner;

    public ServiceBuilder(Scanner scanner) { this.scanner = scanner; }

    public Service buildFromConsole() {
        System.out.print("Service ID (enter to auto-generate): ");
        String id = scanner.nextLine().trim();
        if (id.isEmpty()) id = UUID.randomUUID().toString();
        System.out.print("Service name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Service price: ");
        double price = Double.parseDouble(scanner.nextLine().trim());
        return new Service(id, name, price);
    }
}
