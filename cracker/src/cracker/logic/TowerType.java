package cracker.logic;

public enum TowerType {
    Type1("Sniper Tower", 100, 10, 1, 100);

    private final String name;
    private final double damage;
    private final double range;
    private final double fireRate;
    private final long cost;

    TowerType(String name, double damage, double range, double fireRate, long cost) {
        this.name = name;
        this.damage = damage;
        this.range = range;
        this.fireRate = fireRate;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getDamage() {
        return damage;
    }

    public double getRange() {
        return range;
    }

    public double getFireRate() {
        return fireRate;
    }

    public long getCost() {
        return cost;
    }
}
