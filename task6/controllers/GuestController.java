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

    public GuestController(GuestService guestService, RoomService roomService, GuestBuilder builder, ConsoleView view) {
        this.guestService = guestService;
        this.roomService = roomService;
        this.builder = builder;
        this.view = view;
    }

    public void addGuestFromConsole() {
        Guest g = builder.buildFromConsole();
        guestService.addOrUpdate(g);
        view.print("Guest added: " + g.getName());
    }

    public void showGuests(String sortBy) {
        List<Guest> guests = guestService.getAll();
        if ("name".equalsIgnoreCase(sortBy)) {
            guests.sort((a,b)->a.getName().compareToIgnoreCase(b.getName()));
        }
        view.printGuestsWithRooms(guests, roomService);
    }
}

