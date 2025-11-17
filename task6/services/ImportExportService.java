package task6.services;

import task6.entity.*;
import task6.entity.enums.RoomStatus;
import task6.util.CsvParseException;
import task6.util.AppException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;

public class ImportExportService {
    private final GuestService guestService;
    private final ServiceService serviceService;
    private final RoomService roomService;

    public ImportExportService(GuestService gs, ServiceService ss, RoomService rs) {
        this.guestService = gs;
        this.serviceService = ss;
        this.roomService = rs;
    }

    // ----- EXPORT -----
    public void exportGuests(File file) {
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            pw.println("id;name");
            for (Guest g : guestService.getAll()) {
                pw.printf("%s;%s%n", g.getId(), escape(g.getName()));
            }
        } catch (IOException e) {
            throw new AppException("Failed to export guests: " + e.getMessage(), e);
        }
    }

    public void exportServices(File file) {
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            pw.println("id;name;price");
            for (Service s : serviceService.getAll()) {
                pw.printf("%s;%s;%.2f%n", s.getId(), escape(s.getName()), s.getPrice());
            }
        } catch (IOException e) {
            throw new AppException("Failed to export services: " + e.getMessage(), e);
        }
    }

    public void exportRooms(File file) {
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            pw.println("id;number;price;capacity;stars;status;currentGuestId");
            for (Room r : roomService.getAll()) {
                pw.printf("%s;%d;%.2f;%d;%d;%s;%s%n",
                        r.getId(), r.getNumber(), r.getPrice(), r.getCapacity(), r.getStars(),
                        r.getStatus().name(),
                        r.getCurrentGuestId() == null ? "" : r.getCurrentGuestId());
            }
        } catch (IOException e) {
            throw new AppException("Failed to export rooms: " + e.getMessage(), e);
        }
    }

    public void exportStayRecords(File file) {
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            pw.println("id;roomId;guestId;checkIn;durationDays;checkOut");
            for (Room r : roomService.getAll()) {
                for (StayRecord s : r.getHistory()) {
                    pw.printf("%s;%s;%s;%s;%d;%s%n",
                            s.getId(), r.getId(), s.getGuestId(), s.getCheckInDate(), s.getDurationDays(),
                            s.getCheckOutDate() == null ? "" : s.getCheckOutDate());
                }
            }
        } catch (IOException e) {
            throw new AppException("Failed to export stayrecords: " + e.getMessage(), e);
        }
    }

    public void exportUsages(File file, ServiceUsageService usageService) {
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            pw.println("id;guestId;serviceId;date");
            for (ServiceUsage u : usageService.getAll()) {
                pw.printf("%s;%s;%s;%s%n", u.getId(), u.getGuestId(), u.getServiceId(), u.getDate());
            }
        } catch (IOException e) {
            throw new AppException("Failed to export usages: " + e.getMessage(), e);
        }
    }

    // ----- IMPORT -----
    public void importGuests(File file) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line = br.readLine(); // header
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] cols = line.split(";", -1);
                if (cols.length < 2) throw new CsvParseException("Guest CSV malformed: " + line);
                String id = cols[0].trim();
                String name = unescape(cols[1].trim());
                guestService.addOrUpdate(new Guest(id, name));
            }
        } catch (IOException e) {
            throw new AppException("Failed to import guests: " + e.getMessage(), e);
        }
    }

    public void importServices(File file) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line = br.readLine(); // header
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] cols = line.split(";", -1);
                if (cols.length < 3) throw new CsvParseException("Service CSV malformed: " + line);
                String id = cols[0].trim();
                String name = unescape(cols[1].trim());
                double price = Double.parseDouble(cols[2].trim());
                serviceService.addOrUpdate(new Service(id, name, price));
            }
        } catch (IOException e) {
            throw new AppException("Failed to import services: " + e.getMessage(), e);
        }
    }

    public void importRooms(File file) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line = br.readLine(); // header
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] cols = line.split(";", -1);
                if (cols.length < 7) throw new CsvParseException("Room CSV malformed: " + line);
                String id = cols[0].trim();
                int number = Integer.parseInt(cols[1].trim());
                double price = Double.parseDouble(cols[2].trim());
                int cap = Integer.parseInt(cols[3].trim());
                int stars = Integer.parseInt(cols[4].trim());
                RoomStatus status = RoomStatus.valueOf(cols[5].trim());
                String currentGuestId = cols[6].trim();
                Room room = new Room(id, number, price, cap, stars, status);
                if (!currentGuestId.isBlank()) room.setCurrentGuestId(currentGuestId);
                roomService.addOrUpdate(room);
            }
        } catch (IOException e) {
            throw new AppException("Failed to import rooms: " + e.getMessage(), e);
        }
    }

    public void importStayRecords(File file) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line = br.readLine(); // header
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] cols = line.split(";", -1);
                if (cols.length < 6) throw new CsvParseException("StayRecord CSV malformed: " + line);
                String id = cols[0].trim();
                String roomId = cols[1].trim();
                String guestId = cols[2].trim();
                LocalDate checkIn = LocalDate.parse(cols[3].trim());
                int duration = Integer.parseInt(cols[4].trim());
                String coStr = cols[5].trim();
                StayRecord rec = new StayRecord(id, guestId, checkIn, duration);
                if (!coStr.isBlank()) rec.setCheckOutDate(LocalDate.parse(coStr));
                // attach to room
                roomService.getById(roomId).ifPresentOrElse(r -> r.getHistory().add(rec),
                        () -> { throw new CsvParseException("Room id referenced in stay record not found: " + roomId); });
            }
        } catch (IOException e) {
            throw new AppException("Failed to import stayrecords: " + e.getMessage(), e);
        }
    }

    public void importUsages(File file, ServiceUsageService usageService) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line = br.readLine(); // header
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] cols = line.split(";", -1);
                if (cols.length < 4) throw new CsvParseException("Usage CSV malformed: " + line);
                String id = cols[0].trim();
                String guestId = cols[1].trim();
                String serviceId = cols[2].trim();
                LocalDate date = LocalDate.parse(cols[3].trim());
                // check existence of guest and service
                if (guestService.getById(guestId).isEmpty()) throw new CsvParseException("Guest referenced not found: " + guestId);
                if (serviceService.getById(serviceId).isEmpty()) throw new CsvParseException("Service referenced not found: " + serviceId);
                usageService.addUsageDirect(new ServiceUsage(id, guestId, serviceId, date));
            }
        } catch (IOException e) {
            throw new AppException("Failed to import usages: " + e.getMessage(), e);
        }
    }

    private String escape(String s) { return s.replace(";", "\\;"); }
    private String unescape(String s) { return s.replace("\\;", ";"); }
}
