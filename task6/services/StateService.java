package task6.services;

import task6.entity.*;
import task6.exceptions.StorageException;

import java.io.*;
import java.util.*;

public class StateService {
    private final GuestService guestService;
    private final ServiceService serviceService;
    private final RoomService roomService;
    private final ServiceUsageService usageService;

    public StateService(GuestService gs, ServiceService ss, RoomService rs, ServiceUsageService us) {
        this.guestService = gs; this.serviceService = ss; this.roomService = rs; this.usageService = us;
    }

    public static class HotelState implements Serializable {
        private static final long serialVersionUID = 1L;
        public List<Guest> guests = new ArrayList<>();
        public List<Service> services = new ArrayList<>();
        public List<Room> rooms = new ArrayList<>();
        public List<ServiceUsage> usages = new ArrayList<>();

        public long nextGuestId, nextServiceId, nextRoomId, nextUsageId;
    }

    public void saveState(File file) {
        HotelState hs = new HotelState();
        hs.guests = guestService.getAll();
        hs.services = serviceService.getAll();
        hs.rooms = roomService.findAll();
        hs.usages = usageService.getAll();
        hs.nextGuestId = guestService.nextId();
        hs.nextServiceId = serviceService.nextId();
        hs.nextRoomId = roomService.nextId();
        hs.nextUsageId = usageService.nextId();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(hs);
        } catch (IOException e) { throw new StorageException("Failed to save state: " + e.getMessage(), e); }
    }

    public void loadState(File file) {
        if (!file.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            HotelState hs = (HotelState) ois.readObject();
            // restore
            guestService.clearAll();
            serviceService.clearAll();
            roomService.clearAll();
            usageService.clearAll();
            // add loaded
            for (Guest g : hs.guests) guestService.addOrUpdate(g);
            for (Service s : hs.services) serviceService.addOrUpdate(s);
            for (Room r : hs.rooms) roomService.addOrUpdate(r);
            for (ServiceUsage u : hs.usages) usageService.addOrUpdate(u);
            // set next ids
            guestService.setNextId(hs.nextGuestId);
            serviceService.setNextId(hs.nextServiceId);
            roomService.setNextId(hs.nextRoomId);
            usageService.setNextId(hs.nextUsageId);
        } catch (IOException | ClassNotFoundException e) {
            throw new StorageException("Failed to load state: " + e.getMessage(), e);
        }
    }
}

