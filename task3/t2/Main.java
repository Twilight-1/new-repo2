package task3.t2;


public class Main {
    public static void main(String[] args) {
        Rose ros1 = new Rose(36.5, Color.Red);
        Lily lily = new Lily(20, Color.Green);
        Tulip tulip1 = new Tulip(55.56, Color.Yellow);
        Tulip tulip2 = new Tulip(55.56, Color.Blue);
        Bouquet bouquet = new Bouquet();
        bouquet.addFlower(ros1);
        bouquet.addFlower(lily);
        bouquet.addFlower(tulip1);
        bouquet.addFlower(tulip2);

        bouquet.printBouquet();
    }
}
