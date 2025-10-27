package task3.t4;
import java.util.*;

public class Hotel {
    private final List<Room> rooms = new ArrayList<>();
    private final List<Service> services = new ArrayList<>();

    public void addRoom(Room room) {
        rooms.add(room);
        System.out.println("Добавлен номер " + room.getNumber() + ", цена: " + room.getPrice());
    }

    public void removeRoom(int roomNumber) {
        rooms.removeIf(r -> r.getNumber() == roomNumber);
        System.out.println("Номер " + roomNumber + " удалён из системы.");
    }

    public void checkIn(Guest guest, int roomNumber) {
        rooms.stream()
                .filter(r -> r.getNumber() == roomNumber)
                .findFirst()
                .ifPresentOrElse(
                        r -> r.checkIn(guest),
                        () -> System.out.println("Номер не найден!")
                );
    }

    public void checkOut(int roomNumber) {
        rooms.stream()
                .filter(r -> r.getNumber() == roomNumber)
                .findFirst()
                .ifPresentOrElse(
                        Room::checkOut,
                        () -> System.out.println("Номер не найден!")
                );
    }

    public void setRoomStatus(int roomNumber, RoomStatus status) {
        rooms.stream()
                .filter(r -> r.getNumber() == roomNumber)
                .findFirst()
                .ifPresentOrElse(
                        r -> {
                            r.setStatus(status);
                            System.out.println("Статус номера " + roomNumber + " изменён на " + status);
                        },
                        () -> System.out.println("Номер не найден!")
                );
    }

    public void updateRoomPrice(int roomNumber, double newPrice) {
        rooms.stream()
                .filter(r -> r.getNumber() == roomNumber)
                .findFirst()
                .ifPresentOrElse(
                        r -> {
                            r.setPrice(newPrice);
                            System.out.println("Цена номера " + roomNumber + " обновлена: " + newPrice);
                        },
                        () -> System.out.println("Номер не найден!")
                );
    }

    public void addService(Service service) {
        services.add(service);
        System.out.println("Добавлена услуга: " + service.getName() + " (" + service.getPrice() + " руб.)");
    }

    public void updateServicePrice(String name, double newPrice) {
        services.stream()
                .filter(s -> s.getName().equalsIgnoreCase(name))
                .findFirst()
                .ifPresentOrElse(
                        s -> {
                            s.setPrice(newPrice);
                            System.out.println("Цена услуги '" + name + "' обновлена: " + newPrice);
                        },
                        () -> System.out.println("Услуга не найдена!")
                );
    }

    public void showAllRooms() {
        System.out.println("Список всех номеров:");
        for (Room r : rooms) {
            System.out.println("Номер " + r.getNumber() + ", цена: " + r.getPrice() + ", статус: " + r.getStatus());
        }
    }

    public void showAllServices() {
        System.out.println("Список всех услуг:");
        for (Service s : services) {
            System.out.println(s.getName() + " (" + s.getPrice() + " руб.)");
        }
    }
}


