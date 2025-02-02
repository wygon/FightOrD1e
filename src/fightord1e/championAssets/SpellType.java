package fightord1e.championAssets;

public enum SpellType {
    LIFESTEAL("LIFESTEAL"),
    THORNS("THORNS"),
    LUCK("LUCK"),
    OFF("NULL");

    private final String name;

    SpellType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
