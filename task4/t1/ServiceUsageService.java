package task4.t1;

import java.util.*;

public class ServiceUsageService {
    private List<ServiceUsage> usages = new ArrayList<>();

    public void addServiceUsage(Guest guest, Service service, java.time.LocalDate dateUsed) {
        usages.add(new ServiceUsage(guest, service, dateUsed));
        System.out.println("Гость " + guest.getName() + " воспользовался услугой " + service.getName());
    }

    public void showGuestServicesSorted(Guest guest, String criterion) {
        Comparator<ServiceUsage> comparator = criterion.equalsIgnoreCase("price")
                ? Comparator.comparing(u -> u.getService().getPrice())
                : Comparator.comparing(ServiceUsage::getDateUsed);

        usages.stream()
                .filter(u -> u.getGuest().equals(guest))
                .sorted(comparator)
                .forEach(u -> System.out.println(u.getService().getName() + " — " + u.getService().getPrice()
                        + " руб., дата: " + u.getDateUsed()));
    }

    public double getTotalServiceCost(Guest guest) {
        return usages.stream()
                .filter(u -> u.getGuest().equals(guest))
                .mapToDouble(u -> u.getService().getPrice())
                .sum();
    }
}
