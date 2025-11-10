package task5.services;

import task5.entity.*;
import task5.entity.enums.RoomStatus;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class RoomService {
    private final List<Room> rooms = new ArrayList<>();

    public void addRoom(Room room) {
        rooms.add(room);
        System.out.println("Добавлен номер " + room.getNumber());
    }

    public void checkIn(int roomNumber, Guest guest, LocalDate checkInDate, int durationDays) {
        Optional<Room> op = rooms.stream().filter(r -> r.getNumber() == roomNumber).findFirst();
        if (op.isEmpty()) {
            System.out.println("Номер " + roomNumber + " не найден.");
            return;
        }
        Room room = op.get();
        if (room.getStatus() != RoomStatus.AVAILABLE) {
            System.out.println("Номер " + roomNumber + " недоступен для заселения (статус: " + room.getStatus() + ").");
            return;
        }
        room.setCurrentGuest(guest);
        room.setStatus(RoomStatus.OCCUPIED);
        StayRecord rec = new StayRecord(guest, checkInDate, durationDays);
        room.getHistory().add(rec);
        System.out.println("Гость " + guest.getName() + " заселён в номер " + roomNumber +
                " на " + durationDays + " дн. (выезд: " + rec.getCheckOutDate() + ")");
    }

    public void checkOut(int roomNumber) {
        Optional<Room> op = rooms.stream().filter(r -> r.getNumber() == roomNumber).findFirst();
        if (op.isEmpty()) {
            System.out.println("Номер " + roomNumber + " не найден.");
            return;
        }
        Room room = op.get();
        if (room.getStatus() != RoomStatus.OCCUPIED) {
            System.out.println("Номер " + roomNumber + " не занят.");
            return;
        }
        Guest guest = room.getCurrentGuest();
        List<StayRecord> hist = room.getHistory();
        if (!hist.isEmpty()) {
            StayRecord last = hist.get(hist.size() - 1);
            last.setCheckOutDate(LocalDate.now());
        }
        room.setCurrentGuest(null);
        room.setStatus(RoomStatus.AVAILABLE);
        System.out.println("Гость " + (guest != null ? guest.getName() : "неизвестный") + " выселен из номера " + roomNumber);
    }

    public void updateRoomPrice(int roomNumber, double newPrice) {
        rooms.stream()
                .filter(r -> r.getNumber() == roomNumber)
                .findFirst()
                .ifPresentOrElse(r -> {
                    r.setPrice(newPrice);
                    System.out.println("Цена номера " + roomNumber + " обновлена до " + newPrice);
                }, () -> System.out.println("Номер " + roomNumber + " не найден."));
    }

    public void updateRoomStatus(int roomNumber, RoomStatus status) {
        rooms.stream()
                .filter(r -> r.getNumber() == roomNumber)
                .findFirst()
                .ifPresentOrElse(r -> {
                    r.setStatus(status);
                    System.out.println("Статус номера " + roomNumber + " изменён на " + status);
                }, () -> System.out.println("Номер " + roomNumber + " не найден."));
    }


    public void showAllRooms(String criterion) {
        Comparator<Room> comp = switch (criterion) {
            case "capacity" -> Comparator.comparing(Room::getCapacity);
            case "stars" -> Comparator.comparing(Room::getStars);
            default -> Comparator.comparing(Room::getPrice);
        };
        rooms.stream().sorted(comp).forEach(r -> System.out.println(
                "Номер " + r.getNumber() + " | цена: " + r.getPrice() +
                        " | вместимость: " + r.getCapacity() + " | звёзд: " + r.getStars() +
                        " | статус: " + r.getStatus() +
                        (r.getCurrentGuest() != null ? " | гость: " + r.getCurrentGuest().getName() : "")
        ));
    }

    public void showFreeRooms(String criterion) {
        Comparator<Room> comp = switch (criterion) {
            case "capacity" -> Comparator.comparing(Room::getCapacity);
            case "stars" -> Comparator.comparing(Room::getStars);
            default -> Comparator.comparing(Room::getPrice);
        };
        rooms.stream().filter(r -> r.getStatus() == RoomStatus.AVAILABLE)
                .sorted(comp)
                .forEach(r -> System.out.println("Номер " + r.getNumber() + " | цена: " + r.getPrice() +
                        " | вместимость: " + r.getCapacity() + " | звёзд: " + r.getStars()));
    }


    public void showGuestsWithRooms(String sortBy) {

        List<GuestRoomInfo> list = new ArrayList<>();
        for (Room r : rooms) {
            if (r.getCurrentGuest() != null) {
                StayRecord last = r.getHistory().isEmpty() ? null : r.getHistory().get(r.getHistory().size() - 1);
                list.add(new GuestRoomInfo(r.getCurrentGuest(), r.getNumber(), last != null ? last.getCheckOutDate() : null));
            }
        }
        if (sortBy.equalsIgnoreCase("name")) {
            list.sort(Comparator.comparing(g -> g.guest.getName()));
        } else if (sortBy.equalsIgnoreCase("date")) {
            list.sort(Comparator.comparing(g -> g.checkOutDate != null ? g.checkOutDate : LocalDate.MAX));
        }
        list.forEach(g -> System.out.println("Id:" + g.guest.getId() + " " + g.guest.getName() + " — комн. " + g.roomNumber +
                (g.checkOutDate != null ? " (выезд: " + g.checkOutDate + ")" : " (дата выезда не задана)")));
    }

    private static class GuestRoomInfo {
        Guest guest; int roomNumber; LocalDate checkOutDate;
        GuestRoomInfo(Guest guest, int roomNumber, LocalDate checkOutDate) {
            this.guest = guest; this.roomNumber = roomNumber; this.checkOutDate = checkOutDate;
        }
    }

    public long countFreeRooms() {
        return rooms.stream().filter(r -> r.getStatus() == RoomStatus.AVAILABLE).count();
    }

    public long countGuests() {
        return rooms.stream().filter(r -> r.getStatus() == RoomStatus.OCCUPIED).count();
    }


    public void showRoomsFreeOn(LocalDate date) {
        System.out.println("=== Номера, свободные к " + date + " ===");
        for (Room r : rooms) {
            if (r.getStatus() == RoomStatus.AVAILABLE) {
                System.out.println("Номер " + r.getNumber() + " уже свободен.");
            } else if (r.getStatus() == RoomStatus.OCCUPIED) {
                List<StayRecord> hist = r.getHistory();
                if (!hist.isEmpty()) {
                    StayRecord last = hist.get(hist.size() - 1);
                    LocalDate co = last.getCheckOutDate();
                    if (co != null && !co.isAfter(date)) {
                        System.out.println("Номер " + r.getNumber() + " будет свободен к " + date + " (выезд " + co + ")");
                    } else if (co == null) {
                    }
                }
            }
        }
    }


    public double calculateRoomBill(int roomNumber) {
        Optional<Room> op = rooms.stream().filter(r -> r.getNumber() == roomNumber).findFirst();
        if (op.isEmpty()) {
            System.out.println("Номер не найден.");
            return 0.0;
        }
        Room r = op.get();
        if (r.getCurrentGuest() == null) {
            System.out.println("В номере никого нет.");
            return 0.0;
        }
        List<StayRecord> hist = r.getHistory();
        if (hist.isEmpty()) {
            System.out.println("Нет записи проживания.");
            return 0.0;
        }
        StayRecord last = hist.get(hist.size() - 1);
        LocalDate ci = last.getCheckInDate();
        LocalDate co = last.getCheckOutDate() != null ? last.getCheckOutDate() : LocalDate.now();
        long days = ChronoUnit.DAYS.between(ci, co);
        if (days <= 0) days = 1;
        double sum = days * r.getPrice();
        System.out.println("Сумма за номер (без услуг) для номера " + roomNumber + ": " + sum);
        return sum;
    }

    public void showLast3Guests(int roomNumber) {
        rooms.stream().filter(r -> r.getNumber() == roomNumber).findFirst().ifPresentOrElse(r -> {
            List<StayRecord> hist = r.getHistory();
            int from = Math.max(0, hist.size() - 3);
            for (int i = hist.size() - 1; i >= from; i--) {
                StayRecord s = hist.get(i);
                System.out.println(s.getGuest().getName() + " с " + s.getCheckInDate() + " по " + s.getCheckOutDate());
            }
        }, () -> System.out.println("Номер " + roomNumber + " не найден."));
    }

    public void showRoomDetails(int roomNumber) {
        rooms.stream().filter(r -> r.getNumber() == roomNumber).findFirst().ifPresentOrElse(r -> {
            System.out.println("=== Детали номера " + r.getNumber() + " ===");
            System.out.println("Цена: " + r.getPrice());
            System.out.println("Вместимость: " + r.getCapacity());
            System.out.println("Звёзд: " + r.getStars());
            System.out.println("Статус: " + r.getStatus());
            if (r.getCurrentGuest() != null) System.out.println("Текущий гость: " + r.getCurrentGuest().getName());
            if (!r.getHistory().isEmpty()) {
                System.out.println("История (последние 5):");
                r.getHistory().stream()
                        .sorted(Comparator.comparing(StayRecord::getCheckOutDate, Comparator.nullsLast(Comparator.naturalOrder())).reversed())
                        .limit(5)
                        .forEach(s -> System.out.println(" - " + s.getGuest().getName() + " с " + s.getCheckInDate() + " по " + s.getCheckOutDate()));
            } else {
                System.out.println("История отсутствует.");
            }
        }, () -> System.out.println("Номер " + roomNumber + " не найден."));
    }

    public List<Room> getRooms() { return rooms; }
}
