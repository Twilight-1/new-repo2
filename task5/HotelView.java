package task5;

import java.util.Scanner;

public class HotelView {
    private final HotelController controller;
    private final Scanner scanner = new Scanner(System.in);

    public HotelView(HotelController controller) {
        this.controller = controller;
    }

    public void start() {
        controller.populateDemoData();
        while (true) {
            System.out.println("\n=== ЭЛЕКТРОННЫЙ АДМИНИСТРАТОР ГОСТИНИЦЫ ===");
            System.out.println("1. Показать все номера (сортировка)");
            System.out.println("2. Показать свободные номера (сортировка)");
            System.out.println("3. Показать список постояльцев и их номера");
            System.out.println("4. Добавить номер");
            System.out.println("5. Добавить услугу");
            System.out.println("6. Заселить гостя");
            System.out.println("7. Выселить гостя");
            System.out.println("8. Показать номера, свободные через N дней");
            System.out.println("9. Рассчитать счёт по номеру (включая услуги)");
            System.out.println("10. Показать 3 последних постояльца номера");
            System.out.println("11. Показать услуги гостя");
            System.out.println("12. Показать цены услуг (по цене)");
            System.out.println("13. Изменить статус номера");
            System.out.println("14. Изменить цену номера");
            System.out.println("15. Изменить цену услуги");
            System.out.println("16. Детали номера");
            System.out.println("0. Выход");
            System.out.print("Выбор: ");
            int choice = scanner.nextInt();
            try {
                switch (choice) {
                    case 1 -> controller.showAllRooms();
                    case 2 -> controller.showFreeRooms();
                    case 3 -> controller.showGuests();
                    case 4 -> controller.addRoom();
                    case 5 -> controller.addService();
                    case 6 -> controller.checkInGuest();
                    case 7 -> controller.checkOutGuest();
                    case 8 -> controller.showRoomsFreeInDays();
                    case 9 -> controller.calculateBill();
                    case 10 -> controller.showLast3Guests();
                    case 11 -> controller.showGuestServices();
                    case 12 -> controller.showServicesByPrice();
                    case 13 -> controller.updateRoomStatus();
                    case 14 -> controller.updateRoomPrice();
                    case 15 -> controller.updateServicePrice();
                    case 16 -> controller.showRoomDetails();
                    case 0 -> { System.out.println("Выход..."); return; }
                    default -> System.out.println("Неверный выбор");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
                scanner.nextLine();
            }
        }
    }
}
