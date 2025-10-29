package task3.t3;

class Tank implements IProduct {
    private Hull hull;
    private Engine engine;
    private Turret turret;
    private String model;

    public Tank(String model) {
        this.model = model;
        System.out.println("Создан проект танка: " + model);
    }

    @Override
    public void installFirstPart(IProductPart part) {
        if (part instanceof Hull) {
            this.hull = (Hull) part;
            System.out.println("Установлен корпус: " + hull.hull);
        }
    }

    @Override
    public void installSecondPart(IProductPart part) {
        if (part instanceof Engine) {
            this.engine = (Engine) part;
            System.out.println("Установлен двигатель: " + engine.engine);
        }
    }

    @Override
    public void installThirdPart(IProductPart part) {
        if (part instanceof Turret) {
            this.turret = (Turret) part;
            System.out.println("Установлена башня: " + turret.turret);
        }
    }

    // Метод getInfo() больше не требуется в интерфейсе, но оставим для внутреннего использования
    public String getInfo() {
        StringBuilder info = new StringBuilder();
        info.append("\n=== Информация о танке ===\n");
        info.append("Модель: ").append(model).append("\n");

        if (hull != null) {
            info.append("Корпус: ").append(hull.hull).append("\n");
        }
        if (engine != null) {
            info.append("Двигатель: ").append(engine.engine).append("\n");
        }
        if (turret != null) {
            info.append("Башня: ").append(turret.turret).append("\n");
        }

        info.append("Статус: ").append(isComplete() ? "Сборка завершена" : " Сборка в процессе");
        return info.toString();
    }

    public boolean isComplete() {
        return hull != null && engine != null && turret != null;
    }

    // Геттеры
    public Hull getHull() { return hull; }
    public Engine getEngine() { return engine; }
    public Turret getTurret() { return turret; }
    public String getModel() { return model; }
}