package task6;

import task6.builders.*;
import task6.controllers.*;
import task6.services.*;
import task6.Views.ConsoleView;
import task6.exceptions.Config;
import task6.exceptions.ConfigProxy;
import task6.exceptions.StorageException;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // load config (file path can be passed in args[0] else use "./config.properties")
        String configPath = args.length > 0 ? args[0] : "D:/Projects/IdeaProjects/MyNewProject/src/task6/config.properties";
        Config cfg = Config.load(configPath);

        // services
        GuestService guestService = new GuestService();
        ServiceService serviceService = new ServiceService();
        RoomService roomService = new RoomService(new ConfigProxy(cfg));
        ServiceUsageService usageService = new ServiceUsageService();
        ImportExportService ieService = new ImportExportService(guestService, serviceService, roomService, usageService);

        StateService stateService = new StateService(guestService, serviceService, roomService, usageService);

        // load state if exists
        File stateFile = new File(cfg.getStateFile());
        try {
            stateService.loadState(stateFile);
            System.out.println("State loaded from " + stateFile.getAbsolutePath());
        } catch (Exception e) {
            System.out.println("No state loaded or failed: " + e.getMessage());
        }

        // view and builders
        ConsoleView view = new ConsoleView();
        Scanner scanner = new Scanner(System.in);
        GuestBuilder guestBuilder = new GuestBuilder(scanner);
        ServiceBuilder serviceBuilder = new ServiceBuilder(scanner);
        RoomBuilder roomBuilder = new RoomBuilder(scanner);

        // controllers
        RoomController roomCtrl = new RoomController(roomService, roomBuilder, view);
        GuestController guestCtrl = new GuestController(guestService, roomService, guestBuilder, view);
        ServiceController srvCtrl = new ServiceController(serviceService, serviceBuilder, view);
        ImportExportController ieCtrl = new ImportExportController(ieService, usageService, view);

        HotelController facade = new HotelController(guestService, roomService, usageService, serviceService, view);

        MainMenuController mainMenu = new MainMenuController(roomCtrl, guestCtrl, srvCtrl, ieCtrl, facade, view);

        mainMenu.start();
        try {
            stateService.saveState(stateFile);
            System.out.println("State saved to " + stateFile.getAbsolutePath());
        } catch (Exception e) {
            throw new StorageException("Failed to save state on exit: " + e.getMessage(), e);
        }
    }
}


