package task6.entity;

public class Guest {
    private String id;
    private String name;

    public Guest(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
