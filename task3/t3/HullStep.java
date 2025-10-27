package task3.t3;

class HullStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Шаг 1: Производим корпус танка...");
        Hull hull = new Hull("Модульный корпус 600мм");
        System.out.println("Корпус готов: " + hull.hull);
        return hull;
    }
}

