package cracker.logic;

public enum TowerType {
    ARROW_TOWER("Arrow Tower", 100, 10, 1, 100, ProjectileType.ARROW);

    private final ProjectileType projectileType;
    private final String name;
    private final double damage;
    private final double range;
    private final long reload;
    private final long cost;

    TowerType(String name, double damage, double range, long reload, long cost, ProjectileType projectileType ) {
        this.name = name;
        this.damage = damage;
        this.range = range;
        this.reload = reload;
        this.cost = cost;
        this. projectileType =  projectileType;
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

    public double getReload() {
        return reload;
    }

    public long getCost() {
        return cost;
    }

    public ProjectileType getProjectileType() {
        return projectileType;
    }
}
