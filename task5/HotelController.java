package task5;

import task5.entity.*;
import task5.entity.enums.RoomStatus;
import task5.services.*;

import java.time.LocalDate;
import java.util.Scanner;

public class HotelController {
    private final HotelService hotel;
    private final Scanner scanner = new Scanner(System.in);

    public HotelController(HotelService hotel) {
        this.hotel = hotel;
    }

    public void populateDemoData() {
        hotel.addRoom(new Room(101, 2500, 2, 3, RoomStatus.AVAILABLE));
        hotel.addRoom(new Room(102, 3500, 3, 4, RoomStatus.AVAILABLE));
        hotel.addRoom(new Room(103, 2000, 1, 2, RoomStatus.AVAILABLE));
        hotel.addRoom(new Room(104, 4200, 4, 5, RoomStatus.AVAILABLE));

        hotel.addService(new Service("Завтрак", 500));
        hotel.addService(new Service("СПА", 1500));
        hotel.addService(new Service("Парковка", 200));

        // создаём гостей
        Guest g1 = new Guest("Вова Вист", "P001");
        Guest g2 = new Guest("Жора Обжора", "P002");
        Guest g3 = new Guest("Паша Техник", "P003");

        // заселяем гостей
        hotel.checkIn(101, g1, LocalDate.now().minusDays(5), 7); // на 7 дней
        hotel.checkIn(102, g2, LocalDate.now().minusDays(3), 4);
        hotel.checkIn(104, g3, LocalDate.now().minusDays(1), 6);

        // назначаем услуги
        hotel.addServiceUsage(g1, new Service("Завтрак", 500), LocalDate.now().minusDays(4));
        hotel.addServiceUsage(g1, new Service("СПА", 1500), LocalDate.now().minusDays(2));
        hotel.addServiceUsage(g2, new Service("Уборка", 300), LocalDate.now().minusDays(1));
    }

    public void addRoom() {
        System.out.print("Номер комнаты: ");
        int num = scanner.nextInt();
        System.out.print("Цена: ");
        double price = scanner.nextDouble();
        System.out.print("Вместимость: ");
        int cap = scanner.nextInt();
        System.out.print("Звёзды: ");
        int stars = scanner.nextInt();
        hotel.addRoom(new Room(num, price, cap, stars, RoomStatus.AVAILABLE));
    }

    public void addService() {
        System.out.print("Название услуги: ");
        String name = scanner.nextLine();
        System.out.print("Цена услуги: ");
        double price = scanner.nextDouble();
        hotel.addService(new Service(name, price));
    }

    public void showAllRooms() {
        System.out.print("Сортировать по (price/capacity/stars): ");
        String crit = scanner.nextLine();
        hotel.showAllRooms(crit);
    }

    public void showFreeRooms() {
        System.out.print("Сортировать по (price/capacity/stars): ");
        String crit = scanner.nextLine();
        hotel.showFreeRooms(crit);
    }

    public void showGuests() {
        System.out.print("Сортировать постояльцев по (name/date): ");
        String crit = scanner.nextLine();
        hotel.showGuestsWithRooms(crit);
    }

    public void checkInGuest() {
        System.out.print("Номер комнаты: ");
        int room = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Имя гостя: ");
        String name = scanner.nextLine();
        System.out.print("ID гостя: ");
        String id = scanner.nextLine();
        System.out.print("На сколько дней (целое число): ");
        int days = scanner.nextInt();
        hotel.checkIn(room, new Guest(name, id), LocalDate.now(), days);
    }

    public void checkOutGuest() {
        System.out.print("Номер для выселения: ");
        int room = scanner.nextInt();
        hotel.checkOut(room);
    }

    public void updateRoomStatus() {
        System.out.print("Номер: ");
        int num = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Новый статус (AVAILABLE/OCCUPIED/UNDER_MAINTENANCE): ");
        String st = scanner.nextLine();
        try {
            RoomStatus rs = RoomStatus.valueOf(st);
            hotel.updateRoomStatus(num, rs);
        } catch (IllegalArgumentException e) {
            System.out.println("Неправильный статус.");
        }
    }

    public void updateRoomPrice() {
        System.out.print("Номер: ");
        int num = scanner.nextInt();
        System.out.print("Новая цена: ");
        double p = scanner.nextDouble();
        hotel.updateRoomPrice(num, p);
    }

    public void updateServicePrice() {
        System.out.print("Название услуги: ");
        String name = scanner.nextLine();
        System.out.print("Новая цена: ");
        double p = scanner.nextDouble();
        hotel.updateServicePrice(name, p);
    }

    public void showRoomsFreeInDays() {
        System.out.print("Через сколько дней показать номера: ");
        int days = scanner.nextInt();
        hotel.showRoomsFreeOn(LocalDate.now().plusDays(days));
    }

    public void calculateBill() {
        System.out.print("Номер для расчёта счёта: ");
        int num = scanner.nextInt();
        double roomSum = hotel.calculateRoomBill(num);
        RoomService rs = hotel.getRoomService();
        Room room = rs.getRooms().stream().filter(r -> r.getNumber() == num).findFirst().orElse(null);
        if (room != null && room.getCurrentGuest() != null) {
            double services = hotel.getTotalServiceCost(room.getCurrentGuest());
            System.out.println("Услуги: " + services + " руб.");
            System.out.println("Итого: " + (roomSum + services) + " руб.");
        } else {
            System.out.println("Гость не найден в номере или нет услуг.");
        }
    }

    public void showLast3Guests() {
        System.out.print("Номер: ");
        int num = scanner.nextInt();
        hotel.showLast3Guests(num);
    }

    public void showGuestServices() {
        System.out.print("ID гостя: ");
        String id = scanner.nextLine();

        Guest guest = findGuestById(id);
        if (guest == null) {
            System.out.println("Гость не найден.");
            return;
        }
        System.out.print("Сортировать по (price/date): ");
        String crit = scanner.nextLine();
        hotel.showGuestServices(guest, crit);
    }

    private Guest findGuestById(String id) {
        for (Room r : hotel.getRoomService().getRooms()) {
            if (r.getCurrentGuest() != null && r.getCurrentGuest().getId().equals(id)) return r.getCurrentGuest();
            for (StayRecord s : r.getHistory()) {
                if (s.getGuest() != null && s.getGuest().getId().equals(id)) return s.getGuest();
            }
        }

        for (ServiceUsage u : hotel.getUsageService().getUsagesForGuest(new Guest("", id))) {
            if (u.getGuest() != null && u.getGuest().getId().equals(id)) return u.getGuest();
        }
        return null;
    }

    public void showServicesByPrice() {
        hotel.showServicesByPrice();
    }

    public void showRoomDetails() {
        System.out.print("Номер: ");
        int num = scanner.nextInt();
        hotel.showRoomDetails(num);
    }
}
