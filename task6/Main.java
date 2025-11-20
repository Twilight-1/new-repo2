package task6;

import task6.Views.ConsoleView;
import task6.builders.*;
import task6.controllers.*;
import task6.services.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // services
        GuestService guestService = new GuestService();
        ServiceService serviceService = new ServiceService();
        RoomService roomService = new RoomService();
        ServiceUsageService usageService = new ServiceUsageService();
        ImportExportService ieService = new ImportExportService(guestService, serviceService, roomService);

        // view
        ConsoleView view = new ConsoleView();

        // builders (scanner passed so builder can read console)
        Scanner scanner = new Scanner(System.in);
        GuestBuilder guestBuilder = new GuestBuilder(scanner);
        ServiceBuilder serviceBuilder = new ServiceBuilder(scanner);
        RoomBuilder roomBuilder = new RoomBuilder(scanner);

        // controllers
        RoomController roomController = new RoomController(roomService, roomBuilder, view);
        GuestController guestController = new GuestController(guestService, roomService, guestBuilder, view);
        ServiceController serviceController = new ServiceController(serviceService, usageService, serviceBuilder, view);
        ImportExportController ieController = new ImportExportController(ieService, usageService, view);

        HotelController facade = new HotelController(guestService, roomService, usageService, serviceService);

        MainMenuController mainCtrl = new MainMenuController(roomController, guestController, serviceController, ieController, facade, view);

        // Launch
        mainCtrl.startMainMenu();
    }
}

