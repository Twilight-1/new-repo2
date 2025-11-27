package task6.exceptions;


import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private final boolean allowChangeRoomStatus;
    private final int stayHistoryLimit;
    private final String stateFile;

    private Config(boolean allowChangeRoomStatus, int stayHistoryLimit, String stateFile) {
        this.allowChangeRoomStatus = allowChangeRoomStatus;
        this.stayHistoryLimit = stayHistoryLimit;
        this.stateFile = stateFile;
    }

    public boolean isAllowChangeRoomStatus() { return allowChangeRoomStatus; }
    public int getStayHistoryLimit() { return stayHistoryLimit; }
    public String getStateFile() { return stateFile; }

    public static Config load(String path) {
        try (InputStream in = new FileInputStream(path)) {
            Properties p = new Properties();
            p.load(in);
            boolean allow = Boolean.parseBoolean(p.getProperty("allow.change.room.status", "true"));
            int limit = Integer.parseInt(p.getProperty("stay.history.limit", "3"));
            String stateFile = p.getProperty("state.file", "hotel_state.dat");
            return new Config(allow, limit, stateFile);
        } catch (Exception e) {
            throw new ConfigException("Failed to load config: " + e.getMessage(), e);
        }
    }
}