package task6.entity;

import java.io.Serializable;

public class Service implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private double price;

    public Service(long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
}
