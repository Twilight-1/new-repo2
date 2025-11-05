package task4.t1;

import java.util.*;

public class ServiceService {
    private List<Service> services = new ArrayList<>();

    public void addService(Service service) { services.add(service); }

    public void updateServicePrice(String name, double newPrice) {
        services.stream()
                .filter(s -> s.getName().equalsIgnoreCase(name))
                .findFirst()
                .ifPresent(s -> {
                    s.setPrice(newPrice);
                    System.out.println("Цена услуги '" + name + "' обновлена до " + newPrice);
                });
    }

    public void showServicesSortedByPrice() {
        services.stream()
                .sorted(Comparator.comparing(Service::getPrice))
                .forEach(s -> System.out.println(s.getName() + ": " + s.getPrice()));
    }

    public List<Service> getServices() {
        return services;
    }
}
