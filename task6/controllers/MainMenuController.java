package task6.controllers;

import task6.Views.ConsoleView;
import java.util.Scanner;


public class MainMenuController {
    private final RoomController roomController;
    private final GuestController guestController;
    private final ServiceController serviceController;
    private final ImportExportController ieController;
    private final HotelController facade;
    private final ConsoleView view;
    private final Scanner scanner = new Scanner(System.in);

    public MainMenuController(RoomController rc, GuestController gc, ServiceController sc, ImportExportController iec, HotelController facade, ConsoleView v) {
        this.roomController = rc; this.guestController = gc; this.serviceController = sc; this.ieController = iec; this.facade = facade; this.view = v;
    }

    public void start() {
        while (true) {
            view.printMainMenu();
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> roomMenu();
                    case "2" -> guestMenu();
                    case "3" -> serviceMenu();
                    case "4" -> importExportMenu();
                    case "5" -> facade.populateDemoData();
                    case "0" -> { view.print("Exit"); return; }
                    default -> view.print("Unknown option");
                }
            } catch (Exception e) {
                view.printError(e.getMessage());
            }
        }
    }

    private void roomMenu() {
        view.printRoomMenu();
        String c = scanner.nextLine().trim();
        switch (c) {
            case "1" -> { view.print("Sort by (price/capacity/stars):"); roomController.listRooms(scanner.nextLine().trim()); }
            case "2" -> { view.print("Sort by (price/capacity/stars):"); roomController.listFree(scanner.nextLine().trim()); }
            case "3" -> { view.print("Room number:"); int num = Integer.parseInt(scanner.nextLine().trim()); roomController.roomDetails(num); }
            case "4" -> roomController.addRoom();
            case "5" -> {
                view.print("Room number:"); int num = Integer.parseInt(scanner.nextLine().trim());
                view.print("Guest id (or blank to create):"); String gid = scanner.nextLine().trim();
                view.print("Days:"); int days = Integer.parseInt(scanner.nextLine().trim());
                facade.checkInByNumber(num, gid, days);
            }
            case "6" -> { view.print("Room number to checkout:"); int num2 = Integer.parseInt(scanner.nextLine().trim()); facade.checkOutByNumber(num2); }
            case "7" -> { view.print("Room number for bill:"); int num3 = Integer.parseInt(scanner.nextLine().trim()); facade.calculateBillForRoom(num3); }
            default -> view.print("Back");
        }
    }

    private void guestMenu() {
        view.printGuestMenu();
        String c = scanner.nextLine().trim();
        switch (c) {
            case "1" -> guestController.addGuest();
            case "2" -> { view.print("Sort by name/date:"); guestController.listGuests(scanner.nextLine().trim()); }
            default -> view.print("Back");
        }
    }

    private void serviceMenu() {
        view.printServiceMenu();
        String c = scanner.nextLine().trim();
        switch (c) {
            case "1" -> serviceController.addService();
            case "2" -> serviceController.listServices();
            default -> view.print("Back");
        }
    }

    private void importExportMenu() {
        view.printImportExportMenu();
        String c = scanner.nextLine().trim();
        switch (c) {
            case "1" -> { view.print("Export guests to path:"); ieController.exportGuests(scanner.nextLine().trim()); }
            case "2" -> { view.print("Import guests from path:"); ieController.importGuests(scanner.nextLine().trim()); }
            case "3" -> { view.print("Export services to path:"); ieController.exportServices(scanner.nextLine().trim()); }
            case "4" -> { view.print("Import services from path:"); ieController.importServices(scanner.nextLine().trim()); }
            case "5" -> { view.print("Export rooms to path:"); ieController.exportRooms(scanner.nextLine().trim()); }
            case "6" -> { view.print("Import rooms from path:"); ieController.importRooms(scanner.nextLine().trim()); }
            case "7" -> { view.print("Export stayrecords to path:"); ieController.exportStayRecords(scanner.nextLine().trim()); }
            case "8" -> { view.print("Import stayrecords from path:"); ieController.importStayRecords(scanner.nextLine().trim()); }
            case "9" -> { view.print("Export usages to path:"); ieController.exportUsages(scanner.nextLine().trim()); }
            case "10" -> { view.print("Import usages from path:"); ieController.importUsages(scanner.nextLine().trim()); }
            default -> view.print("Back");
        }
    }
}
