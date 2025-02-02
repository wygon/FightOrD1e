//Szymon Wygoński
package fightord1e.championAssets;

public class Ability {

    private String name;
    private String description;
    private double value;
    private String type;
    private int usesLeft;

    public Ability(String name, String description, double value, String type, int usesLeft) {
        this.name = name;
        this.description = description;
        this.value = value;
        this.type = type;
        this.usesLeft = usesLeft;
    }

    public static Ability fromString(String data) {
        String[] parts = data.split(",");
        return new Ability(parts[0], parts[1], Double.parseDouble(parts[2]), parts[3], Integer.parseInt(parts[4]));
    }

    @Override
    public String toString() {
        String a = "\n";
        a += "\n Name: " + name;
        a += "\n Description: " + description;
        a += "\n Value: " + value;
        a += "\n Type: " + type;
        a += "\n Uses: " + usesLeft;
        return a;
    }

    public boolean isAvailable() {
        return usesLeft > 0;
    }

    public double getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public int getUsesLeft() {
        return usesLeft;
    }

    public String getType() {
        return type;
    }

    public void addUsesLeft(int ul) {
        usesLeft += ul;
    }

}
