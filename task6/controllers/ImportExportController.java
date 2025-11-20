package task6.controllers;

import task6.services.*;
import task6.Views.ConsoleView;

import java.io.File;


public class ImportExportController {
    private final ImportExportService ieService;
    private final ServiceUsageService usageService;
    private final ConsoleView view;

    public ImportExportController(ImportExportService ieService, ServiceUsageService usageService, ConsoleView view) {
        this.ieService = ieService;
        this.usageService = usageService;
        this.view = view;
    }

    public void exportGuests(String path) {
        ieService.exportGuests(new File(path));
        view.print("Guests exported to " + path);
    }

    public void importGuests(String path) {
        ieService.importGuests(new File(path));
        view.print("Guests imported from " + path);
    }

    public void exportServices(String path) {
        ieService.exportServices(new File(path));
        view.print("Services exported to " + path);
    }

    public void importServices(String path) {
        ieService.importServices(new File(path));
        view.print("Services imported from " + path);
    }

    public void exportRooms(String path) {
        ieService.exportRooms(new File(path));
        view.print("Rooms exported to " + path);
    }

    public void importRooms(String path) {
        ieService.importRooms(new File(path));
        view.print("Rooms imported from " + path);
    }

    public void exportStayRecords(String path) {
        ieService.exportStayRecords(new File(path));
        view.print("StayRecords exported to " + path);
    }

    public void importStayRecords(String path) {
        ieService.importStayRecords(new File(path));
        view.print("StayRecords imported from " + path);
    }

    public void exportUsages(String path) {
        ieService.exportUsages(new File(path), usageService);
        view.print("Service usages exported to " + path);
    }

    public void importUsages(String path) {
        ieService.importUsages(new File(path), usageService);
        view.print("Service usages imported from " + path);
    }
}


