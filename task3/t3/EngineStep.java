package task3.t3;

class EngineStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Шаг 2: Производим двигатель танка...");
        Engine engine = new Engine("ГТД-1500");
        System.out.println("Двигатель готов: " + engine.engine);
        return engine;
    }
}