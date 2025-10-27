package task3.t2;

import java.util.ArrayList;
import java.util.List;

class Bouquet {
    private final List<Flower> flowers = new ArrayList<>();

    // Добавление цветов в букет
    public void addFlower(Flower flower) {
        flowers.add(flower);
    }

    // Расчет общей стоимости
    public double getTotalPrice() {
        return flowers.stream().mapToDouble(Flower::getPrice).sum();
    }

    // Получение информации о букете
    public void printBouquet() {
        System.out.println("Букет содержит:");
        flowers.forEach(f -> System.out.println("- " + f.getName() + "- " + f.getColor() + ": " + f.getPrice() + " руб."));
        System.out.println("Общая стоимость: " + getTotalPrice() + " руб.");
    }
}

