package task6.services;

import task6.entity.*;
import task6.entity.enums.RoomStatus;
import task6.exceptions.ConfigProxy;
import task6.exceptions.RoomException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.concurrent.atomic.AtomicLong;;

public class RoomService {
    private final Map<Long, Room> store = new LinkedHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);

    private final ConfigProxy config;

    public RoomService(ConfigProxy config) {
        this.config = config;
    }

    public void setNextId(long next) { idGen.set(next); }

    public Room addOrUpdate(Room r) {
        if (r == null) throw new RoomException("Room is null");
        if (r.getId() <= 0) {
            long id = idGen.getAndIncrement();
            r.setId(id);
            store.put(id, r);
            return r;
        } else {
            store.put(r.getId(), r);
            enforceHistoryLimit(r);
            return r;
        }
    }

    private void enforceHistoryLimit(Room r) {
        int limit = config.getStayHistoryLimit();
        List<StayRecord> h = r.getHistory();
        while (h.size() > limit) {
            h.remove(0);
        }
    }

    public Optional<Room> getById(long id) { return Optional.ofNullable(store.get(id)); }

    public Optional<Room> findByNumber(int number) {
        return store.values().stream().filter(r -> r.getNumber() == number).findFirst();
    }

    public List<Room> getAllSorted(String criterion) {
        Comparator<Room> cmp = switch (criterion) {
            case "capacity" -> Comparator.comparing(Room::getCapacity);
            case "stars" -> Comparator.comparing(Room::getStars);
            default -> Comparator.comparing(Room::getPrice);
        };
        return store.values().stream().sorted(cmp).collect(Collectors.toList());
    }

    public List<Room> getFreeRoomsSorted(String criterion) {
        Comparator<Room> cmp = switch (criterion) {
            case "capacity" -> Comparator.comparing(Room::getCapacity);
            case "stars" -> Comparator.comparing(Room::getStars);
            default -> Comparator.comparing(Room::getPrice);
        };
        return store.values().stream()
                .filter(r -> r.getStatus() == RoomStatus.AVAILABLE)
                .sorted(cmp)
                .collect(Collectors.toList());
    }

    public void updateRoomPrice(long roomId, double newPrice) {
        Room r = store.get(roomId);
        if (r == null) throw new RoomException("Room not found by id: " + roomId);
        r.setPrice(newPrice);
    }

    public void updateRoomStatus(long roomId, RoomStatus status) {
        if (!config.isAllowChangeRoomStatus()) throw new RoomException("Change room status disabled by configuration");
        Room r = store.get(roomId);
        if (r == null) throw new RoomException("Room not found by id: " + roomId);
        r.setStatus(status);
    }

    public void checkIn(int roomNumber, Guest guest, LocalDate checkInDate, int days, GuestService guestService) {
        if (guest == null) throw new RoomException("Guest is required");
        if (guest.getId() <= 0 || guestService.getById(guest.getId()).isEmpty()) {
            guestService.addOrUpdate(guest);
        }
        Room room = findByNumber(roomNumber).orElseThrow(() -> new RoomException("Room by number not found: " + roomNumber));
        if (room.getStatus() != RoomStatus.AVAILABLE) throw new RoomException("Room not available");
        room.setCurrentGuestId(guest.getId());
        room.setStatus(RoomStatus.OCCUPIED);
        long recId = idGen.getAndIncrement();
        long stayId = System.currentTimeMillis();
        StayRecord sr = new StayRecord(stayId, guest.getId(), checkInDate, days);
        room.getHistory().add(sr);
        enforceHistoryLimit(room);
    }

    public void checkOut(int roomNumber) {
        Room room = findByNumber(roomNumber).orElseThrow(() -> new RoomException("Room not found: " + roomNumber));
        if (room.getStatus() != RoomStatus.OCCUPIED) throw new RoomException("Room not occupied");
        List<StayRecord> h = room.getHistory();
        if (!h.isEmpty()) {
            StayRecord last = h.get(h.size()-1);
            last.setCheckOutDate(LocalDate.now());
        }
        room.setCurrentGuestId(null);
        room.setStatus(RoomStatus.AVAILABLE);
    }

    public long countFreeRooms() {
        return store.values().stream().filter(r -> r.getStatus() == RoomStatus.AVAILABLE).count();
    }

    public long countGuests() {
        return store.values().stream().filter(r -> r.getStatus() == RoomStatus.OCCUPIED).count();
    }

    public List<Room> roomsFreeByDate(LocalDate date) {
        return store.values().stream().filter(r -> {
            if (r.getStatus() == RoomStatus.AVAILABLE) return true;
            if (r.getStatus() == RoomStatus.OCCUPIED) {
                List<StayRecord> h = r.getHistory();
                if (h.isEmpty()) return false;
                StayRecord last = h.get(h.size() - 1);
                if (last.getCheckOutDate() != null) return !last.getCheckOutDate().isAfter(date);
                return !last.getCheckInDate().plusDays(last.getDurationDays()).isAfter(date);
            }
            return false;
        }).collect(Collectors.toList());
    }

    public double calculateRoomBill(int roomNumber) {
        Room r = findByNumber(roomNumber).orElseThrow(() -> new RoomException("Room not found"));
        if (r.getCurrentGuestId() == null) throw new RoomException("No guest in room");
        List<StayRecord> h = r.getHistory();
        if (h.isEmpty()) throw new RoomException("No stay record");
        StayRecord last = h.get(h.size()-1);
        LocalDate ci = last.getCheckInDate();
        LocalDate co = last.getCheckOutDate() != null ? last.getCheckOutDate() : LocalDate.now();
        long days = java.time.temporal.ChronoUnit.DAYS.between(ci, co);
        if (days <= 0) days = 1;
        return days * r.getPrice();
    }

    public List<StayRecord> lastNGuests(int roomNumber, int n) {
        Room r = findByNumber(roomNumber).orElseThrow(() -> new RoomException("Room not found"));
        List<StayRecord> h = r.getHistory();
        int from = Math.max(0, h.size() - n);
        return new ArrayList<>(h.subList(from, h.size()));
    }

    public void clearAll() { store.clear(); }

    public List<Room> findAll() { return new ArrayList<>(store.values()); }

    public long nextId() { return idGen.get(); }
}

