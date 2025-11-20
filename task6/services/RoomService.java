package task6.services;

import task6.entity.*;
import task6.entity.enums.RoomStatus;
import task6.util.AppException;
import task6.util.EntityNotFoundException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.time.temporal.ChronoUnit;

public class RoomService {
    private final Map<String, Room> rooms = new LinkedHashMap<>(); // id -> Room

    public Room addOrUpdate(Room room) {
        if (room.getId() == null || room.getId().isBlank()) throw new AppException("Room id required");
        rooms.put(room.getId(), room);
        return room;
    }

    public Optional<Room> getById(String id) { return Optional.ofNullable(rooms.get(id)); }

    public Optional<Room> findByNumber(int number) {
        return rooms.values().stream().filter(r -> r.getNumber() == number).findFirst();
    }

    public List<Room> getAll() { return new ArrayList<>(rooms.values()); }

    public List<Room> getAllSorted(String criterion) {
        Comparator<Room> cmp = switch (criterion) {
            case "capacity" -> Comparator.comparing(Room::getCapacity);
            case "stars" -> Comparator.comparing(Room::getStars);
            default -> Comparator.comparing(Room::getPrice);
        };
        return rooms.values().stream().sorted(cmp).collect(Collectors.toList());
    }

    public List<Room> getFreeRoomsSorted(String criterion) {
        Comparator<Room> cmp = switch (criterion) {
            case "capacity" -> Comparator.comparing(Room::getCapacity);
            case "stars" -> Comparator.comparing(Room::getStars);
            default -> Comparator.comparing(Room::getPrice);
        };
        return rooms.values().stream()
                .filter(r -> r.getStatus() == RoomStatus.AVAILABLE)
                .sorted(cmp)
                .collect(Collectors.toList());
    }

    public void updateRoomPrice(String roomId, double newPrice) {
        Room r = rooms.get(roomId);
        if (r == null) throw new EntityNotFoundException("Room not found: " + roomId);
        r.setPrice(newPrice);
    }

    public void updateRoomStatus(String roomId, RoomStatus status) {
        Room r = rooms.get(roomId);
        if (r == null) throw new EntityNotFoundException("Room not found: " + roomId);
        r.setStatus(status);
    }

    public void checkIn(int roomNumber, Guest guest, LocalDate checkInDate, int days) {
        if (guest == null) throw new AppException("Guest required for check-in");
        Room room = findByNumber(roomNumber).orElseThrow(() -> new EntityNotFoundException("Room not found: " + roomNumber));
        if (room.getStatus() != RoomStatus.AVAILABLE) throw new AppException("Room not available: status=" + room.getStatus());
        room.setCurrentGuestId(guest.getId());
        room.setStatus(RoomStatus.OCCUPIED);
        String recId = UUID.randomUUID().toString();
        StayRecord rec = new StayRecord(recId, guest.getId(), checkInDate, days);
        room.getHistory().add(rec);
    }

    public void checkOut(int roomNumber) {
        Room room = findByNumber(roomNumber).orElseThrow(() -> new EntityNotFoundException("Room not found: " + roomNumber));
        if (room.getStatus() != RoomStatus.OCCUPIED) throw new AppException("Room is not occupied");
        List<StayRecord> hist = room.getHistory();
        if (!hist.isEmpty()) {
            StayRecord last = hist.get(hist.size() - 1);
            last.setCheckOutDate(LocalDate.now());
        }
        room.setCurrentGuestId(null);
        room.setStatus(RoomStatus.AVAILABLE);
    }

    public long countFreeRooms() {
        return rooms.values().stream().filter(r -> r.getStatus() == RoomStatus.AVAILABLE).count();
    }

    public long countGuests() {
        return rooms.values().stream().filter(r -> r.getStatus() == RoomStatus.OCCUPIED).count();
    }


    public List<Room> roomsFreeByDate(LocalDate date) {
        return rooms.values().stream().filter(r -> {
            if (r.getStatus() == RoomStatus.AVAILABLE) return true;
            if (r.getStatus() == RoomStatus.OCCUPIED) {
                List<StayRecord> h = r.getHistory();
                if (h.isEmpty()) return false;
                StayRecord last = h.get(h.size() - 1);
                LocalDate co = last.getCheckOutDate();
                if (co != null) return !co.isAfter(date);
                // если co == null — используем durationDays predictive (checkIn + durationDays)
                LocalDate predicted = last.getCheckInDate().plusDays(last.getDurationDays());
                return !predicted.isAfter(date);
            }
            return false;
        }).collect(Collectors.toList());
    }

    public double calculateRoomBill(int roomNumber) {
        Room r = findByNumber(roomNumber).orElseThrow(() -> new EntityNotFoundException("Room not found: " + roomNumber));
        if (r.getCurrentGuestId() == null) throw new AppException("No guest in room");
        List<StayRecord> h = r.getHistory();
        if (h.isEmpty()) throw new AppException("No stay record found");
        StayRecord last = h.get(h.size() - 1);
        LocalDate ci = last.getCheckInDate();
        LocalDate co = last.getCheckOutDate() != null ? last.getCheckOutDate() : LocalDate.now();
        long days = ChronoUnit.DAYS.between(ci, co);
        if (days <= 0) days = 1;
        return days * r.getPrice();
    }

    public List<StayRecord> lastNGuests(int roomNumber, int n) {
        Room r = findByNumber(roomNumber).orElseThrow(() -> new EntityNotFoundException("Room not found: " + roomNumber));
        List<StayRecord> h = r.getHistory();
        int from = Math.max(0, h.size() - n);
        return new ArrayList<>(h.subList(from, h.size()));
    }

    public Optional<Room> findRoomByNumber(int number) { return findByNumber(number); }

    public void clearAll() { rooms.clear(); }
}

