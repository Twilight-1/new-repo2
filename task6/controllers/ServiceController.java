package task6.controllers;

import task6.builders.ServiceBuilder;
import task6.entity.Service;
import task6.services.ServiceService;
import task6.Views.ConsoleView;
import java.util.List;

public class ServiceController {
    private final ServiceService serviceService;
    private final ServiceBuilder builder;
    private final ConsoleView view;

    public ServiceController(ServiceService ss, ServiceBuilder b, ConsoleView v) {
        this.serviceService = ss;
        this.builder = b;
        this.view = v;
    }

    public void addService() {
        Service s = builder.buildFromConsole();
        serviceService.addOrUpdate(s);
        view.print("Service added: " + s.getName());
    }

    public void listServices() {
        List<Service> all = serviceService.getAll();
        view.printServices(all);
    }
}
