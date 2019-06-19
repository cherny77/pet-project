package cracker.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Mods {
	private CharacterType characterType;
	private Complexity complexity;
	private DamageMode damageMode;
	private TowerCostMode towerCostMode;

	private Mods() {
		characterType = CharacterType.KNIGHT;
		complexity = Complexity.NORMAL;
		damageMode = new DamageMode();
		towerCostMode = new TowerCostMode();
	}

	public static Mods getInstance() {
		return ModsProvider.INSTANCE;
	}

	public DamageMode getDamageMode() {
		return damageMode;
	}

	public TowerCostMode getTowerCostMode() {
		return towerCostMode;
	}

	public CharacterType getCharacterType() {
		return characterType;
	}

	public void setCharacterType(CharacterType characterType) {
		this.characterType = characterType;
	}

	public Complexity getComplexity() {
		return complexity;
	}

	public void setComplexity(Complexity complexity) {
		this.complexity = complexity;
	}

	private static class ModsProvider {
		private static final Mods INSTANCE = new Mods();
	}

	public class DamageMode {
		private final Map<CharacterType, Double> characterTypes;
		private final Map<Complexity, Double> complexities;
		private final Map<ProjectileType, Map<MobType, Double>> projectileToMobs;

		public DamageMode() {
			characterTypes = new HashMap<>();
			characterTypes.put(CharacterType.ARCHER, 1d);
			characterTypes.put(CharacterType.KNIGHT, 1d);
			characterTypes.put(CharacterType.WIZARD, 1d);

			complexities = new HashMap<>();
			complexities.put(Complexity.EASY, 1.1d);
			complexities.put(Complexity.NORMAL, 1d);
			complexities.put(Complexity.HARD, 0.9d);

			projectileToMobs = new HashMap<>();
			projectileToMobs.put(ProjectileType.BOMB, new HashMap<>());
			projectileToMobs.get(ProjectileType.BOMB).put(MobType.SKELETON, 0.9d);
			projectileToMobs.get(ProjectileType.BOMB).put(MobType.SLIME, 1.1d);
			projectileToMobs.get(ProjectileType.BOMB).put(MobType.GHOST, 1d);

			projectileToMobs.put(ProjectileType.ARROW, new HashMap<>());
			projectileToMobs.get(ProjectileType.ARROW).put(MobType.SKELETON, 1d);
			projectileToMobs.get(ProjectileType.ARROW).put(MobType.SLIME, 0.9d);
			projectileToMobs.get(ProjectileType.ARROW).put(MobType.GHOST, 1d);

			projectileToMobs.put(ProjectileType.MAGIC, new HashMap<>());
			projectileToMobs.get(ProjectileType.MAGIC).put(MobType.SKELETON, 1d);
			projectileToMobs.get(ProjectileType.MAGIC).put(MobType.SLIME, 0.9d);
			projectileToMobs.get(ProjectileType.MAGIC).put(MobType.GHOST, 1.1d);
		}

		public double mode(ProjectileType projectileType, MobType mobType) {
			return characterTypes.get(characterType) * complexities.get(complexity) *
					projectileToMobs.get(projectileType).get(mobType);
		}

		public Map<CharacterType, Double> getCharacterTypes() {
			return Collections.unmodifiableMap(characterTypes);
		}

		public Map<Complexity, Double> getComplexities() {
			return Collections.unmodifiableMap(complexities);
		}

		public Map<ProjectileType, Map<MobType, Double>> getProjectileToMobs() {
			return Collections.unmodifiableMap(projectileToMobs);
		}
	}

	public class TowerCostMode {
		private final Map<Complexity, Double> complexities;
		private final Map<CharacterType, Map<TowerType, Double>> characterToTowers;

		public TowerCostMode() {
			complexities = new HashMap<>();
			complexities.put(Complexity.EASY, 0.9d);
			complexities.put(Complexity.NORMAL, 1d);
			complexities.put(Complexity.HARD, 1.1d);

			characterToTowers = new HashMap<>();
			characterToTowers.put(CharacterType.KNIGHT, new HashMap<>());
			characterToTowers.get(CharacterType.KNIGHT).put(TowerType.ARROW, 1.2d);
			characterToTowers.get(CharacterType.KNIGHT).put(TowerType.BOMB, 0.7d);
			characterToTowers.get(CharacterType.KNIGHT).put(TowerType.MAGIC, 1.2d);

			characterToTowers.put(CharacterType.ARCHER, new HashMap<>());
			characterToTowers.get(CharacterType.ARCHER).put(TowerType.ARROW, 0.8d);
			characterToTowers.get(CharacterType.ARCHER).put(TowerType.BOMB, 1d);
			characterToTowers.get(CharacterType.ARCHER).put(TowerType.MAGIC, 1.2d);

			characterToTowers.put(CharacterType.WIZARD, new HashMap<>());
			characterToTowers.get(CharacterType.WIZARD).put(TowerType.ARROW, 1.2d);
			characterToTowers.get(CharacterType.WIZARD).put(TowerType.BOMB, 1.2d);
			characterToTowers.get(CharacterType.WIZARD).put(TowerType.MAGIC, 0.7d);

		}

		public double mode(TowerType towerType) {
			return characterToTowers.get(characterType).get(towerType) * complexities.get(complexity);
		}

		public Map<CharacterType, Map<TowerType, Double>> getCharacterToTowers() {
			return characterToTowers;
		}

		public Map<Complexity, Double> getComplexities() {
			return Collections.unmodifiableMap(complexities);
		}
	}

}
