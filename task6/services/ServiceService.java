package task6.services;

import task6.entity.Service;
import java.util.*;
import task6.util.AppException;

public class ServiceService {
    private final Map<String, Service> services = new LinkedHashMap<>();

    public Service addOrUpdate(Service s) {
        if (s.getId() == null || s.getId().isBlank()) throw new AppException("Service id is required");
        services.put(s.getId(), s);
        return s;
    }

    public Optional<Service> getById(String id) { return Optional.ofNullable(services.get(id)); }
    public List<Service> getAll() { return new ArrayList<>(services.values()); }
    public void clearAll() { services.clear(); }
}

