package task4.t1;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        HotelService hotel = new HotelService();

        // Добавляем номера
        hotel.addRoom(new Room(101, 2500, 2, 3, RoomStatus.AVAILABLE));
        hotel.addRoom(new Room(102, 3500, 3, 4, RoomStatus.AVAILABLE));
        hotel.addRoom(new Room(103, 2000, 1, 2, RoomStatus.UNDER_MAINTENANCE));
        hotel.addRoom(new Room(104, 4000, 4, 5, RoomStatus.AVAILABLE));

        // Добавляем услуги
        Service breakfast = new Service("Завтрак", 500);
        Service spa = new Service("СПА", 1500);
        Service cleaning = new Service("Уборка", 300);
        hotel.addService(breakfast);
        hotel.addService(spa);
        hotel.addService(cleaning);

        // Создаём постояльцев
        Guest g1 = new Guest("Вова Вист", "P001");
        Guest g2 = new Guest("Петя Кадилак", "P002");
        Guest g3 = new Guest("Паша Техник", "P003");
        Guest g4 = new Guest("Жорик Вартанов", "P004");

        // Заселяем
        hotel.checkIn(101, g1, LocalDate.now().minusDays(5));
        hotel.checkIn(102, g2, LocalDate.now().minusDays(2));
        hotel.checkIn(104, g3, LocalDate.now().minusDays(1));

        // Использование услуг
        hotel.addServiceUsage(g1, breakfast, LocalDate.now().minusDays(4));
        hotel.addServiceUsage(g1, spa, LocalDate.now().minusDays(3));
        hotel.addServiceUsage(g2, cleaning, LocalDate.now().minusDays(1));
        hotel.addServiceUsage(g3, breakfast, LocalDate.now());

        // Демонстрация
        System.out.println("=== Все номера (по цене) ===");
        hotel.showAllRooms("price");

        System.out.println("\n=== Свободные номера (по вместимости) ===");
        hotel.showFreeRooms("capacity");

        System.out.println("\n=== Постояльцы (по алфавиту) ===");
        hotel.showGuestsSortedByName();

        System.out.println("\n=== Постояльцы (по дате выезда) ===");
        hotel.showGuestsSortedByCheckoutDate();

        System.out.println("\n=== Общее число свободных номеров ===");
        System.out.println(hotel.getRoomService().countFreeRooms());

        System.out.println("\n=== Общее число постояльцев ===");
        System.out.println(hotel.getRoomService().countGuests());

        System.out.println("\n=== Номера, свободные через 3 дня ===");
        hotel.getRoomService().showRoomsFreeOn(LocalDate.now().plusDays(3));

        System.out.println("\n=== Сумма оплаты за номер 101 ===");
        hotel.getRoomService().calculateBill(101);

        System.out.println("\n=== Последние 3 постояльца номера 101 ===");
        hotel.getRoomService().showLast3Guests(101);

        System.out.println("\n=== Услуги гостя Вова Вист (по цене) ===");
        hotel.showGuestServices(g1, "price");

        System.out.println("\n=== Услуги гостя Вова Вист (по дате) ===");
        hotel.showGuestServices(g1, "date");

        System.out.println("\n=== Общая стоимость услуг гостя Вова Вист ===");
        System.out.println(hotel.getTotalServiceCost(g1) + " руб.");

        System.out.println("\n=== Изменение цены номера 102 ===");
        hotel.updateRoomPrice(102, 4200);
        hotel.showAllRooms("price");

        System.out.println("\n=== Изменение статуса номера 103 ===");
        hotel.updateRoomStatus(103, RoomStatus.AVAILABLE);
        hotel.showAllRooms("status");

        System.out.println("\n=== Выселение гостя из номера 102 ===");
        hotel.checkOut(102);

        System.out.println("\n=== Свободные номера после выселения ===");
        hotel.showFreeRooms("price");

        System.out.println("\n=== Детали номера 101 ===");
        hotel.getRoomService().showRoomDetails(101);
    }
}

