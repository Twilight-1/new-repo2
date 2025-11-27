package task6.services;

import task6.entity.ServiceUsage;

import java.util.*;
import java.util.stream.Collectors;
import java.util.concurrent.atomic.AtomicLong;

public class ServiceUsageService {
    private final Map<Long, ServiceUsage> store = new LinkedHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);

    public void setNextId(long next) { idGen.set(next); }

    public ServiceUsage addOrUpdate(ServiceUsage u) {
        if (u == null) throw new IllegalArgumentException("Usage is null");
        if (u.getId() <= 0) {
            long id = idGen.getAndIncrement();
            ServiceUsage nu = new ServiceUsage(id, u.getGuestId(), u.getServiceId(), u.getDate());
            store.put(id, nu);
            return nu;
        } else {
            store.put(u.getId(), u);
            return u;
        }
    }

    public List<ServiceUsage> getForGuest(long guestId) {
        return store.values().stream().filter(u -> u.getGuestId() == guestId).collect(Collectors.toList());
    }

    public List<ServiceUsage> getAll() { return new ArrayList<>(store.values()); }
    public void clearAll() { store.clear(); }
    public long nextId() { return idGen.get(); }
}

