package task5.services;

import task5.entity.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ServiceUsageService {
    private final List<ServiceUsage> usages = new ArrayList<>();

    public void addUsage(Guest guest, Service service, LocalDate date) {
        usages.add(new ServiceUsage(guest, service, date));
        System.out.println("Гость " + guest.getName() + " воспользовался услугой " + service.getName() + " (" + date + ")");
    }

    public List<ServiceUsage> getUsagesForGuest(Guest guest) {
        return usages.stream()
                .filter(u -> u.getGuest() != null && u.getGuest().getId().equals(guest.getId()))
                .collect(Collectors.toList());
    }

    public void showGuestServicesSorted(Guest guest, String sortBy) {
        List<ServiceUsage> list = getUsagesForGuest(guest);
        Comparator<ServiceUsage> cmp = "price".equalsIgnoreCase(sortBy)
                ? Comparator.comparing(u -> u.getService().getPrice())
                : Comparator.comparing(ServiceUsage::getDate);
        list.stream().sorted(cmp).forEach(u -> System.out.println(u.getService().getName()
                + " — " + u.getService().getPrice() + " руб. | " + u.getDate()));
    }

    public double getTotalForGuest(Guest guest) {
        return getUsagesForGuest(guest).stream().mapToDouble(u -> u.getService().getPrice()).sum();
    }
}
