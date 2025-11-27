package task6.controllers;

import task6.builders.GuestBuilder;
import task6.entity.Guest;
import task6.services.GuestService;
import task6.services.RoomService;
import task6.Views.ConsoleView;

import java.util.List;

public class GuestController {
    private final GuestService guestService;
    private final RoomService roomService;
    private final GuestBuilder builder;
    private final ConsoleView view;

    public GuestController(GuestService gs, RoomService rs, GuestBuilder b, ConsoleView v) {
        this.guestService = gs; this.roomService = rs; this.builder = b; this.view = v;
    }

    public void addGuest() {
        Guest g = builder.buildFromConsole();
        guestService.addOrUpdate(g);
        view.print("Guest added: " + g.getName() + " (id:" + g.getId() + ")");
    }

    public void listGuests(String sortBy) {
        List<Guest> all = guestService.getAll();
        if ("name".equalsIgnoreCase(sortBy)) all.sort((a,b)->a.getName().compareToIgnoreCase(b.getName()));
        view.printGuestsWithRooms(all, roomService);
    }
}
