package task6.services;

import task6.entity.*;
import task6.entity.enums.RoomStatus;
import task6.exceptions.CsvException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public class ImportExportService {
    private final GuestService guestService;
    private final ServiceService serviceService;
    private final RoomService roomService;
    private final ServiceUsageService usageService;

    public ImportExportService(GuestService gs, ServiceService ss, RoomService rs, ServiceUsageService us) {
        this.guestService = gs;
        this.serviceService = ss;
        this.roomService = rs;
        this.usageService = us;
    }

    public void exportGuests(File f) {
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8))) {
            pw.println("id;name");
            for (Guest g : guestService.getAll()) pw.printf("%d;%s%n", g.getId(), escape(g.getName()));
        } catch (IOException e) { throw new CsvException("Export guests failed: " + e.getMessage(), e); }
    }
    public void importGuests(File f) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8))) {
            String header = br.readLine(); String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] cols = line.split(";", -1);
                if (cols.length < 2) throw new CsvException("Malformed guest CSV: " + line);
                long id = Long.parseLong(cols[0].trim());
                String name = unescape(cols[1].trim());
                Guest g = new Guest(id, name);
                guestService.addOrUpdate(g);
            }
        } catch (IOException e) { throw new CsvException("Import guests failed: " + e.getMessage(), e); }
    }

    public void exportServices(File f) {
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8))) {
            pw.println("id;name;price");
            for (Service s : serviceService.getAll()) pw.printf("%d;%s;%.2f%n", s.getId(), escape(s.getName()), s.getPrice());
        } catch (IOException e) { throw new CsvException("Export services failed: " + e.getMessage(), e); }
    }
    public void importServices(File f) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8))) {
            String header = br.readLine(); String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] cols = line.split(";", -1);
                if (cols.length < 3) throw new CsvException("Malformed service CSV: " + line);
                long id = Long.parseLong(cols[0].trim());
                String name = unescape(cols[1].trim());
                double price = Double.parseDouble(cols[2].trim());
                Service s = new Service(id, name, price);
                serviceService.addOrUpdate(s);
            }
        } catch (IOException e) { throw new CsvException("Import services failed: " + e.getMessage(), e); }
    }

    public void exportRooms(File f) {
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8))) {
            pw.println("id;number;price;capacity;stars;status;currentGuestId");
            for (Room r : roomService.findAll()) {
                pw.printf("%d;%d;%.2f;%d;%d;%s;%s%n",
                        r.getId(), r.getNumber(), r.getPrice(), r.getCapacity(), r.getStars(),
                        r.getStatus().name(), r.getCurrentGuestId() == null ? "" : String.valueOf(r.getCurrentGuestId()));
            }
        } catch (IOException e) { throw new CsvException("Export rooms failed: " + e.getMessage(), e); }
    }
    public void importRooms(File f) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8))) {
            String header = br.readLine(); String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] cols = line.split(";", -1);
                if (cols.length < 7) throw new CsvException("Malformed room CSV: " + line);
                long id = Long.parseLong(cols[0].trim());
                int number = Integer.parseInt(cols[1].trim());
                double price = Double.parseDouble(cols[2].trim());
                int cap = Integer.parseInt(cols[3].trim());
                int stars = Integer.parseInt(cols[4].trim());
                RoomStatus status = RoomStatus.valueOf(cols[5].trim());
                String cur = cols[6].trim();
                Room r = new Room(id, number, price, cap, stars, status);
                if (!cur.isEmpty()) r.setCurrentGuestId(Long.parseLong(cur));
                roomService.addOrUpdate(r);
            }
        } catch (IOException e) { throw new CsvException("Import rooms failed: " + e.getMessage(), e); }
    }

    public void exportStayRecords(File f) {
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8))) {
            pw.println("id;roomId;guestId;checkIn;durationDays;checkOut");
            for (Room r : roomService.findAll()) {
                for (StayRecord s : r.getHistory()) {
                    pw.printf("%d;%d;%d;%s;%d;%s%n",
                            s.getId(), r.getId(), s.getGuestId(),
                            s.getCheckInDate(), s.getDurationDays(),
                            s.getCheckOutDate() == null ? "" : s.getCheckOutDate().toString());
                }
            }
        } catch (IOException e) { throw new CsvException("Export stayrecords failed: " + e.getMessage(), e); }
    }
    public void importStayRecords(File f) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8))) {
            String header = br.readLine(); String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] cols = line.split(";", -1);
                if (cols.length < 6) throw new CsvException("Malformed stay CSV: " + line);
                long id = Long.parseLong(cols[0].trim());
                long roomId = Long.parseLong(cols[1].trim());
                long guestId = Long.parseLong(cols[2].trim());
                LocalDate ci = LocalDate.parse(cols[3].trim());
                int dur = Integer.parseInt(cols[4].trim());
                String co = cols[5].trim();
                StayRecord sr = new StayRecord(id, guestId, ci, dur);
                if (!co.isEmpty()) sr.setCheckOutDate(LocalDate.parse(co));
                Room r = roomService.getById(roomId).orElseThrow(() -> new CsvException("Room not found for stay record: " + roomId));
                r.getHistory().add(sr);
            }
        } catch (IOException e) { throw new CsvException("Import stayrecords failed: " + e.getMessage(), e); }
    }

    public void exportUsages(File f) {
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8))) {
            pw.println("id;guestId;serviceId;date");
            for (ServiceUsage u : usageService.getAll()) {
                pw.printf("%d;%d;%d;%s%n", u.getId(), u.getGuestId(), u.getServiceId(), u.getDate());
            }
        } catch (IOException e) { throw new CsvException("Export usages failed: " + e.getMessage(), e); }
    }
    public void importUsages(File f) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8))) {
            String header = br.readLine(); String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] cols = line.split(";", -1);
                if (cols.length < 4) throw new CsvException("Malformed usage CSV: " + line);
                long id = Long.parseLong(cols[0].trim());
                long guestId = Long.parseLong(cols[1].trim());
                long serviceId = Long.parseLong(cols[2].trim());
                LocalDate d = LocalDate.parse(cols[3].trim());
                // check references
                if (guestService.getById(guestId).isEmpty()) throw new CsvException("Guest not found for usage: " + guestId);
                if (serviceService.getById(serviceId).isEmpty()) throw new CsvException("Service not found for usage: " + serviceId);
                usageService.addOrUpdate(new ServiceUsage(id, guestId, serviceId, d));
            }
        } catch (IOException e) { throw new CsvException("Import usages failed: " + e.getMessage(), e); }
    }

    private String escape(String s){ return s.replace(";", "\\;"); }
    private String unescape(String s){ return s.replace("\\;", ";"); }
}
