package task6.controllers;

import task6.Views.ConsoleView;
import java.util.Scanner;

public class MainMenuController {
    private final RoomController roomController;
    private final GuestController guestController;
    private final ServiceController serviceController;
    private final ImportExportController ieController;
    private final HotelController hotelController; // small helper for checkin/checkout/calc
    private final ConsoleView view;
    private final Scanner scanner = new Scanner(System.in);

    public MainMenuController(RoomController rc, GuestController gc, ServiceController sc,
                              ImportExportController iec, HotelController facade, ConsoleView view) {
        this.roomController = rc;
        this.guestController = gc;
        this.serviceController = sc;
        this.ieController = iec;
        this.hotelController = facade;
        this.view = view;
    }

    public void startMainMenu() {
        while (true) {
            view.printMainMenu();
            int choice = Integer.parseInt(scanner.nextLine().trim());
            try {
                switch (choice) {
                    case 1 -> roomSubMenu();
                    case 2 -> guestSubMenu();
                    case 3 -> serviceSubMenu();
                    case 4 -> importExportSubMenu();
                    case 0 -> { view.print("Exit"); return; }
                    default -> view.print("Invalid choice");
                }
            } catch (Exception e) {
                view.printError(e.getMessage());
            }
        }
    }

    private void roomSubMenu() {
        view.printRoomMenu();
        int c = Integer.parseInt(scanner.nextLine().trim());
        switch (c) {
            case 1 -> { view.print("Sort by (price/capacity/stars):"); roomController.showAllRooms(scanner.nextLine().trim()); }
            case 2 -> { view.print("Sort by (price/capacity/stars):"); roomController.showFreeRooms(scanner.nextLine().trim()); }
            case 3 -> { view.print("Room number:"); int num = Integer.parseInt(scanner.nextLine().trim()); roomController.showRoomDetails(num); }
            case 4 -> roomController.addRoomFromConsole();
            case 5 -> { view.print("Room number:"); int num = Integer.parseInt(scanner.nextLine().trim()); view.print("Days:"); int days = Integer.parseInt(scanner.nextLine().trim());
                view.print("Guest id (or blank to create):"); String gid = scanner.nextLine().trim();
                hotelController.checkInByNumber(num, gid, days); }
            case 6 -> { view.print("Room number to checkout:"); int num2 = Integer.parseInt(scanner.nextLine().trim()); hotelController.checkOutByNumber(num2); }
            case 7 -> { view.print("Room number:"); int num3 = Integer.parseInt(scanner.nextLine().trim()); hotelController.calculateBillForRoom(num3); }
            default -> view.print("Back");
        }
    }

    private void guestSubMenu() {
        view.printGuestMenu();
        int c = Integer.parseInt(scanner.nextLine().trim());
        switch (c) {
            case 1 -> guestController.addGuestFromConsole();
            case 2 -> { view.print("Sort by (name/date):"); guestController.showGuests(scanner.nextLine().trim()); }
            default -> view.print("Back");
        }
    }

    private void serviceSubMenu() {
        view.printServiceMenu();
        int c = Integer.parseInt(scanner.nextLine().trim());
        switch (c) {
            case 1 -> serviceController.addService();
            case 2 -> serviceController.showServices();
            default -> view.print("Back");
        }
    }

    private void importExportSubMenu() {
        view.printImportExportMenu();
        int c = Integer.parseInt(scanner.nextLine().trim());
        switch (c) {
            case 1 -> { view.print("Export guests to path:"); ieController.exportGuests(scanner.nextLine().trim()); }
            case 2 -> { view.print("Import guests from path:"); ieController.importGuests(scanner.nextLine().trim()); }
            case 3 -> { view.print("Export services to path:"); ieController.exportServices(scanner.nextLine().trim()); }
            case 4 -> { view.print("Import services from path:"); ieController.importServices(scanner.nextLine().trim()); }
            case 5 -> { view.print("Export rooms to path:"); ieController.exportRooms(scanner.nextLine().trim()); }
            case 6 -> { view.print("Import rooms from path:"); ieController.importRooms(scanner.nextLine().trim()); }
            case 7 -> { view.print("Export stayrecords to path:"); ieController.exportStayRecords(scanner.nextLine().trim()); }
            case 8 -> { view.print("Import stayrecords from path:"); ieController.importStayRecords(scanner.nextLine().trim()); }
            case 9 -> { view.print("Export usages to path:"); ieController.exportUsages(scanner.nextLine().trim()); }
            case 10 -> { view.print("Import usages from path:"); ieController.importUsages(scanner.nextLine().trim()); }
            default -> view.print("Back");
        }
    }
}

