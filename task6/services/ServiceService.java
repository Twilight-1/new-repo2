package task6.services;

import task6.entity.Service;
import java.util.*;
import task6.exceptions.ServiceException;
import java.util.concurrent.atomic.AtomicLong;

public class ServiceService {
    private final Map<Long, Service> store = new LinkedHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);

    public void setNextId(long next) { idGen.set(next); }

    public Service addOrUpdate(Service s) {
        if (s == null) throw new ServiceException("Service is null");
        if (s.getId() <= 0) {
            long id = idGen.getAndIncrement();
            s.setId(id);
            store.put(id, s);
            return s;
        } else {
            store.put(s.getId(), s);
            return s;
        }
    }

    public Optional<Service> getById(long id) { return Optional.ofNullable(store.get(id)); }
    public List<Service> getAll() { return new ArrayList<>(store.values()); }
    public void clearAll() { store.clear(); }
    public long nextId() { return idGen.get(); }
}
