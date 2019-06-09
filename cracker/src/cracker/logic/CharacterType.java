package cracker.logic;

public enum CharacterType {
    KNIGHT ("Knight"), WIZARD("Wizard"), ARCHER("Archer");
    private final String name;

    CharacterType(String name) {
        this.name = name;
    }
}
