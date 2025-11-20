package task6.services;

import task6.entity.Guest;
import java.util.*;
import task6.util.AppException;

public class GuestService {
    private final Map<String, Guest> guests = new LinkedHashMap<>();

    public Guest addOrUpdate(Guest g) {
        if (g.getId() == null || g.getId().isBlank()) throw new AppException("Guest id is required");
        guests.put(g.getId(), g);
        return g;
    }

    public Optional<Guest> getById(String id) { return Optional.ofNullable(guests.get(id)); }
    public List<Guest> getAll() { return new ArrayList<>(guests.values()); }
    public boolean exists(String id) { return guests.containsKey(id); }
    public void clearAll() { guests.clear(); }
}
