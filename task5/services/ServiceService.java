package task5.services;

import task5.entity.Service;
import java.util.*;
import java.util.stream.Collectors;

public class ServiceService {
    private final List<Service> services = new ArrayList<>();

    public void addService(Service service) {
        services.add(service);
        System.out.println("Добавлена услуга: " + service.getName());
    }

    public void updateServicePrice(String name, double newPrice) {
        services.stream().filter(s -> s.getName().equalsIgnoreCase(name)).findFirst()
                .ifPresentOrElse(s -> {
                    s.setPrice(newPrice);
                    System.out.println("Цена услуги '" + name + "' обновлена до " + newPrice);
                }, () -> System.out.println("Услуга '" + name + "' не найдена."));
    }

    public void showServicesSortedByPrice() {
        services.stream().sorted(Comparator.comparing(Service::getPrice))
                .forEach(s -> System.out.println(s.getName() + " — " + s.getPrice()));
    }

    public List<Service> getAll() {
        return new ArrayList<>(services);
    }
}
