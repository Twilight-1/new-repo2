package task6.controllers;

import task6.entity.Room;
import task6.builders.RoomBuilder;
import task6.services.RoomService;
import task6.Views.ConsoleView;
import java.util.List;

public class RoomController {
    private final RoomService roomService;
    private final RoomBuilder builder;
    private final ConsoleView view;

    public RoomController(RoomService rs, RoomBuilder rb, ConsoleView v) {
        this.roomService = rs; this.builder = rb; this.view = v;
    }

    public void addRoom() {
        Room r = builder.buildFromConsole();
        roomService.addOrUpdate(r);
        view.print("Room added (number=" + r.getNumber() + ")");
    }

    public void listRooms(String sortBy) {
        List<Room> list = roomService.getAllSorted(sortBy);
        view.printRooms(list);
    }

    public void listFree(String sortBy) {
        List<Room> list = roomService.getFreeRoomsSorted(sortBy);
        view.printRooms(list);
    }

    public void roomDetails(int number) {
        roomService.findByNumber(number).ifPresentOrElse(r -> view.printRoomDetails(r), () -> view.print("Room not found"));
    }
}
