package task3.t3;

class TurretStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Шаг 3: Производим башню танка...");
        Turret turret = new Turret("Модульная башня с пушкой 2А46М-1");
        System.out.println(" Башня готова: " + turret.turret);
        return turret;
    }
}
