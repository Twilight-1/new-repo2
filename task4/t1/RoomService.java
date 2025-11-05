package task4.t1;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;


public class RoomService {
    private List<Room> rooms = new ArrayList<>();

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void checkIn(int roomNumber, Guest guest, LocalDate checkInDate) {
        for (Room room : rooms) {
            if (room.getNumber() == roomNumber && room.getStatus() == RoomStatus.AVAILABLE) {
                room.setCurrentGuest(guest);
                room.setStatus(RoomStatus.OCCUPIED);
                room.getHistory().add(new StayRecord(guest, checkInDate, null));
                System.out.println("Гость " + guest.getName() + " заселён в номер " + roomNumber);
                return;
            }
        }
        System.out.println("Ошибка заселения: номер не доступен.");
    }

    public void checkOut(int roomNumber) {
        for (Room room : rooms) {
            if (room.getNumber() == roomNumber && room.getStatus() == RoomStatus.OCCUPIED) {
                Guest guest = room.getCurrentGuest();
                List<StayRecord> history = room.getHistory();
                StayRecord lastStay = history.get(history.size() - 1);
                lastStay.setCheckOutDate(LocalDate.now());
                room.setCurrentGuest(null);
                room.setStatus(RoomStatus.AVAILABLE);
                System.out.println("Гость " + guest.getName() + " выселен из номера " + roomNumber);
                return;
            }
        }
        System.out.println("Ошибка выселения: номер не занят.");
    }

    public void updateRoomPrice(int roomNumber, double newPrice) {
        rooms.stream()
                .filter(r -> r.getNumber() == roomNumber)
                .findFirst()
                .ifPresent(r -> {
                    r.setPrice(newPrice);
                    System.out.println("Цена номера " + roomNumber + " обновлена до " + newPrice);
                });
    }

    public void updateRoomStatus(int roomNumber, RoomStatus newStatus) {
        rooms.stream()
                .filter(r -> r.getNumber() == roomNumber)
                .findFirst()
                .ifPresent(r -> {
                    r.setStatus(newStatus);
                    System.out.println("Статус номера " + roomNumber + " изменён на " + newStatus);
                });
    }

    public void showRoomsSortedBy(String criterion) {
        Comparator<Room> comparator = switch (criterion) {
            case "capacity" -> Comparator.comparing(Room::getCapacity);
            case "stars" -> Comparator.comparing(Room::getStars);
            case "status" -> Comparator.comparing(Room::getStatus);
            default -> Comparator.comparing(Room::getPrice);
        };
        rooms.stream().sorted(comparator)
                .forEach(r -> System.out.println("Номер " + r.getNumber() + ", цена: " + r.getPrice() +
                        ", вместимость: " + r.getCapacity() + ", звёзд: " + r.getStars() +
                        ", статус: " + r.getStatus()));
    }

    public void showFreeRooms(String criterion) {
        Comparator<Room> comparator = switch (criterion) {
            case "capacity" -> Comparator.comparing(Room::getCapacity);
            case "stars" -> Comparator.comparing(Room::getStars);
            case "status" -> Comparator.comparing(Room::getStatus);
            default -> Comparator.comparing(Room::getPrice);
        };
        rooms.stream()
                .filter(r -> r.getStatus() == RoomStatus.AVAILABLE)
                .sorted(comparator)
                .forEach(r -> System.out.println("Свободный номер " + r.getNumber() + ", цена: " + r.getPrice()));
    }

    public void showGuestsSortedByName() {
        rooms.stream()
                .filter(r -> r.getCurrentGuest() != null)
                .map(Room::getCurrentGuest)
                .sorted(Comparator.comparing(Guest::getName))
                .forEach(g -> System.out.println(g.getName()));
    }

    public void showGuestsSortedByCheckoutDate() {
        rooms.stream()
                .flatMap(r -> r.getHistory().stream())
                .sorted(Comparator.comparing(
                        h -> h.getCheckOutDate() != null ? h.getCheckOutDate() : LocalDate.MAX))
                .forEach(h -> {
                    String outDate = h.getCheckOutDate() != null ? h.getCheckOutDate().toString() : "ещё живёт";
                    System.out.println(h.getGuest().getName() + " — " + outDate);
                });
    }

    public long countFreeRooms() {
        return rooms.stream().filter(r -> r.getStatus() == RoomStatus.AVAILABLE).count();
    }

    public long countGuests() {
        return rooms.stream().filter(r -> r.getStatus() == RoomStatus.OCCUPIED).count();
    }

    public void showRoomsFreeOn(LocalDate date) {
        rooms.stream()
                .filter(r -> r.getStatus() == RoomStatus.AVAILABLE
                        || r.getHistory().stream()
                        .anyMatch(h -> {
                            LocalDate co = h.getCheckOutDate();
                            return co != null && !co.isAfter(date);
                        }))
                .forEach(r -> System.out.println("Номер " + r.getNumber() + " будет свободен к " + date));
    }

    public void calculateBill(int roomNumber) {
        rooms.stream()
                .filter(r -> r.getNumber() == roomNumber && r.getCurrentGuest() != null)
                .findFirst()
                .ifPresent(r -> {
                    StayRecord stay = r.getHistory().get(r.getHistory().size() - 1);
                    LocalDate checkIn = stay.getCheckInDate();
                    LocalDate checkOut = LocalDate.now();
                    long days = ChronoUnit.DAYS.between(checkIn, checkOut);
                    if (days == 0) days = 1; // минимум 1 день
                    double sum = r.getPrice() * days;
                    System.out.println("Сумма оплаты за номер " + roomNumber + ": " + sum);
                });
    }

    public void showLast3Guests(int roomNumber) {
        rooms.stream()
                .filter(r -> r.getNumber() == roomNumber)
                .findFirst()
                .ifPresent(r -> {
                    int size = r.getHistory().size();
                    r.getHistory().stream()
                            .skip(Math.max(0, size - 3))
                            .forEach(h -> {
                                String outDate = h.getCheckOutDate() != null ? h.getCheckOutDate().toString() : "ещё живёт";
                                System.out.println(h.getGuest().getName() + " с " + h.getCheckInDate() + " по " + outDate);
                            });
                });
    }

    public void showRoomDetails(int roomNumber) {
        rooms.stream()
                .filter(r -> r.getNumber() == roomNumber)
                .findFirst()
                .ifPresent(r -> {
                    System.out.println("Номер: " + r.getNumber() + ", цена: " + r.getPrice() +
                            ", статус: " + r.getStatus() + ", вместимость: " + r.getCapacity());
                    if (r.getCurrentGuest() != null)
                        System.out.println("Заселён: " + r.getCurrentGuest().getName());
                });
    }

    public List<Room> getRooms() {
        return rooms;
    }
}
