package task6.services;

import task6.entity.Guest;
import java.util.*;
import task6.exceptions.GuestException;
import java.util.concurrent.atomic.AtomicLong;

public class GuestService {
    private final Map<Long, Guest> store = new LinkedHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);

    public void setNextId(long next) { idGen.set(next); }

    public Guest addOrUpdate(Guest g) {
        if (g == null) throw new GuestException("Guest is null");
        if (g.getId() <= 0) {
            long id = idGen.getAndIncrement();
            g.setId(id);
            store.put(id, g);
            return g;
        } else {
            store.put(g.getId(), g);
            return g;
        }
    }

    public Optional<Guest> getById(long id) { return Optional.ofNullable(store.get(id)); }
    public List<Guest> getAll() { return new ArrayList<>(store.values()); }
    public void clearAll() { store.clear(); }
    public long nextId() { return idGen.get(); }
}