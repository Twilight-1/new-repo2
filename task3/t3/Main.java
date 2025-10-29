package task3.t3;

public class Main {
    public static void main(String[] args) {
        System.out.println("Тестовая сборка танка");
        System.out.println("=" .repeat(50));

        // Создаем шаги сборки
        ILineStep hullStep = new HullStep();
        ILineStep engineStep = new EngineStep();
        ILineStep turretStep = new TurretStep();

        // Создаем сборочную линию
        IAssemblyLine assemblyLine = new TankAssemblyLine(hullStep, engineStep, turretStep);

        // Создаем заготовку танка
        Tank tank = new Tank("Объект 640 «Чёрный Орёл»");

        // Запускаем сборку
        IProduct assembledTank = assemblyLine.assembleProduct(tank);

        // Выводим информацию о собранном танке (теперь через приведение типа)
        if (assembledTank instanceof Tank) {
            Tank concreteTank = (Tank) assembledTank;
            System.out.println(concreteTank.getInfo());

            // Дополнительная проверка
            if (concreteTank.isComplete()) {
                System.out.println("\nТанк полностью собран и готов к бою!");
            } else {
                System.out.println("\nТанк не полностью собран!");
            }
        }
    }
}