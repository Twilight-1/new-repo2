package task6.controllers;

import task6.entity.Room;
import task6.builders.RoomBuilder;
import task6.services.RoomService;
import task6.Views.ConsoleView;

import java.util.List;

/**
 * Контроллер: минимальная логика, 2-4 строки на метод.
 */
public class RoomController {
    private final RoomService roomService;
    private final RoomBuilder builder;
    private final ConsoleView view;

    public RoomController(RoomService roomService, RoomBuilder builder, ConsoleView view) {
        this.roomService = roomService;
        this.builder = builder;
        this.view = view;
    }

    public void addRoomFromConsole() {
        Room r = builder.buildFromConsole();
        roomService.addOrUpdate(r);
        view.print("Room added/updated: " + r.getNumber());
    }

    public void showAllRooms(String sortBy) {
        List<Room> list = roomService.getAllSorted(sortBy);
        view.printRooms(list);
    }

    public void showFreeRooms(String sortBy) {
        List<Room> list = roomService.getFreeRoomsSorted(sortBy);
        view.printRooms(list);
    }

    public void showRoomDetails(int number) {
        roomService.findByNumber(number).ifPresentOrElse(r -> view.printRoomDetails(r), () -> view.print("Room not found"));
    }

    public void updatePrice(String roomId, double newPrice) {
        roomService.updateRoomPrice(roomId, newPrice);
        view.print("Price updated");
    }
}

