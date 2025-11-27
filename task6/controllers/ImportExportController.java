package task6.controllers;

import task6.services.*;
import task6.Views.ConsoleView;

import java.io.File;


public class ImportExportController {
    private final ImportExportService ie;
    private final ServiceUsageService usageService;
    private final ConsoleView view;

    public ImportExportController(ImportExportService ie, ServiceUsageService us, ConsoleView v) {
        this.ie = ie; this.usageService = us; this.view = v;
    }

    public void exportGuests(String path) {
        ie.exportGuests(new File(path));
        view.print("Exported guests");
    }
    public void importGuests(String path) {
        ie.importGuests(new File(path));
        view.print("Imported guests");
    }

    public void exportServices(String path) {
        ie.exportServices(new File(path));
        view.print("Exported services");
    }
    public void importServices(String path) {
        ie.importServices(new File(path));
        view.print("Imported services");
    }

    public void exportRooms(String path) {
        ie.exportRooms(new File(path));
        view.print("Exported rooms");
    }
    public void importRooms(String path) {
        ie.importRooms(new File(path));
        view.print("Imported rooms");
    }

    public void exportStayRecords(String path) {
        ie.exportStayRecords(new File(path));
        view.print("Exported stay records");
    }
    public void importStayRecords(String path) {
        ie.importStayRecords(new File(path));
        view.print("Imported stay records");
    }

    public void exportUsages(String path) {
        ie.exportUsages(new File(path));
        view.print("Exported usages");
    }
    public void importUsages(String path) {
        ie.importUsages(new File(path));
        view.print("Imported usages");
    }
}

