package task3.t3;

class TankAssemblyLine implements IAssemblyLine {
    private ILineStep hullStep;
    private ILineStep engineStep;
    private ILineStep turretStep;

    public TankAssemblyLine(ILineStep hullStep, ILineStep engineStep, ILineStep turretStep) {
        this.hullStep = hullStep;
        this.engineStep = engineStep;
        this.turretStep = turretStep;
    }

    @Override
    public IProduct assembleProduct(IProduct product) {
        System.out.println("\n Начало сборки");
        System.out.println("=" .repeat(40));

        // Сборка и установка корпуса
        IProductPart hull = hullStep.buildProductPart();
        product.installFirstPart(hull);

        // Сборка и установка двигателя
        IProductPart engine = engineStep.buildProductPart();
        product.installSecondPart(engine);

        // Сборка и установка башни
        IProductPart turret = turretStep.buildProductPart();
        product.installThirdPart(turret);

        System.out.println("=" .repeat(40));
        System.out.println("Сборка танка завершена");

        return product;
    }
}