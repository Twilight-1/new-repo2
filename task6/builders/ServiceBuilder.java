package task6.builders;

import task6.entity.Service;
import java.util.Scanner;

public class ServiceBuilder {
    private final Scanner scanner;
    public ServiceBuilder(Scanner scanner){ this.scanner = scanner; }
    public Service buildFromConsole() {
        System.out.print("Service name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Price: ");
        double price = Double.parseDouble(scanner.nextLine().trim());
        return new Service(0, name, price);
    }
}
