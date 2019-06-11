package cracker.logic;

public enum TowerType {
    ARROW("Arrow Tower", 40, 300, 1000, 100, ProjectileType.ARROW),
    BOMB("Bomb Tower", 100, 100, 1000, 100, ProjectileType.ARROW),
    MAGIC("Magic Tower", 100, 150, 1000, 100, ProjectileType.ARROW);

    private final ProjectileType projectileType;
    private final String name;
    private final double damage;
    private final double range;
    private final long reload;
    private final long cost;

    TowerType(String name, double damage, double range, long reload, long cost, ProjectileType projectileType) {
        this.name = name;
        this.damage = damage;
        this.range = range;
        this.reload = reload;
        this.cost = cost;
        this.projectileType = projectileType;
    }

    public static TowerType findTowerType(String id) {
        for (TowerType type : values()) {
            if (("add" + type.toString() + "button").equalsIgnoreCase(id)) {
                return type;
            }
        }
        return null;
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
