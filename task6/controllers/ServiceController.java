package task6.controllers;

import task6.builders.ServiceBuilder;
import task6.entity.Service;
import task6.services.ServiceService;
import task6.Views.ConsoleView;
import task6.services.ServiceUsageService;
import java.util.List;

public class ServiceController {
    private final ServiceService serviceService;
    private final ServiceUsageService usageService;
    private final ServiceBuilder builder;
    private final ConsoleView view;

    public ServiceController(ServiceService serviceService, ServiceUsageService usageService, ServiceBuilder builder, ConsoleView view) {
        this.serviceService = serviceService;
        this.usageService = usageService;
        this.builder = builder;
        this.view = view;
    }

    public void addService() {
        Service s = builder.buildFromConsole();
        serviceService.addOrUpdate(s);
        view.print("Service added: " + s.getName());
    }

    public void showServices() {
        List<Service> services = serviceService.getAll();
        view.printServices(services);
    }
}
