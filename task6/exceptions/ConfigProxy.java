package task6.exceptions;

public class ConfigProxy {
    private final Config cfg;
    public ConfigProxy(Config cfg) { this.cfg = cfg; }
    public boolean isAllowChangeRoomStatus() { return cfg.isAllowChangeRoomStatus(); }
    public int getStayHistoryLimit() { return cfg.getStayHistoryLimit(); }
}
