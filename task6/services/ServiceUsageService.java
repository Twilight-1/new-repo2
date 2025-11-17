package task6.services;

import task6.entity.ServiceUsage;

import java.util.*;
import java.util.stream.Collectors;

public class ServiceUsageService {
    private final Map<String, ServiceUsage> usages = new LinkedHashMap<>();

    public ServiceUsage addUsage(ServiceUsage u) {
        usages.put(u.getId(), u);
        return u;
    }

    // used by ImportExportService to add already-created usage objects
    public void addUsageDirect(ServiceUsage u) { addUsage(u); }

    public ServiceUsage addUsage(String id, String guestId, String serviceId, java.time.LocalDate date) {
        ServiceUsage u = new ServiceUsage(id, guestId, serviceId, date);
        usages.put(id, u);
        return u;
    }

    public List<ServiceUsage> getAll() { return new ArrayList<>(usages.values()); }

    public List<ServiceUsage> getForGuest(String guestId) {
        return usages.values().stream().filter(u -> u.getGuestId().equals(guestId)).collect(Collectors.toList());
    }
}

