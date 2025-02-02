package fightord1e.championAssets;

public class Tank extends Champion {

    private double thornsValue;
    private int thornsMove;

    public Tank(String name, double healthPoints, double attackDamage, double magicDamage, double physicalResist, double magicResist, int distancePoint, Ability[] abilities, double thornsValue, int thornsMove) {
        super(name, healthPoints, attackDamage, magicDamage, physicalResist, magicResist, distancePoint, abilities);
        this.thornsValue = thornsValue;
        this.thornsMove = thornsMove;
        this.specialSpellType = SpellType.OFF;
    }

    public Tank() {
        this.thornsValue = 0;
        this.thornsMove = 0;
        this.specialSpellType = SpellType.OFF;
    }

    @Override
    public String toString() {
        return super.toString() + "\n Thorns: " + thornsValue;
    }

    @Override
    public double getSpecialSpellValue() {
        return thornsValue;
    }

    @Override
    public int getSpecialSpellMove() {
        return thornsMove;
    }

    @Override
    public SpellType getSpecialSpellType() {
        return specialSpellType;
    }

    @Override
    public void setSpecialSpellValue(double thornsValue) {
        this.thornsValue = thornsValue;
    }

    @Override
    public void setSpecialSpellMove(int thornsMove) {
        this.thornsMove = thornsMove;
    }

    @Override
    public void setSpecialSpellType(SpellType specialSpellType) {
        this.specialSpellType = specialSpellType;
    }
}
