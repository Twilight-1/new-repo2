package task3.t2;

public abstract class Flower {
    private final String name;
    private final double price;
    private final Color color;

    protected Flower(String name,double price, Color color) {
        this.name = name;
        this.price = price;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public double getPrice() {
        return price;
    }
}
