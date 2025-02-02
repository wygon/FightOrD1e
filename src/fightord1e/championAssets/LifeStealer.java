package fightord1e.championAssets;

public class LifeStealer extends Champion {

    private boolean lifesteal;
    private double lifestealValue;
    private int lifestealMoveNumber;

    public LifeStealer(String name, double healthPoints, double attackDamage, double magicDamage, double physicalResist, double magicResist, int distancePoint, Ability[] abilities, boolean lifesteal, double lifestealValue, int lifestealMoveNumber) {
        super(name, healthPoints, attackDamage, magicDamage, physicalResist, magicResist, distancePoint, abilities);
        this.lifesteal = lifesteal;
        this.lifestealValue = lifestealValue;
        this.lifestealMoveNumber = lifestealMoveNumber;
        this.specialSpellType = SpellType.OFF;
    }

    public LifeStealer() {
        this.lifesteal = false;
        this.lifestealValue = 0;
        this.lifestealMoveNumber = 0;
        this.specialSpellType = SpellType.OFF;
    }

    @Override
    public String toString() {
        return super.toString() + "\n Lifesteal: " + lifesteal;
    }

    @Override
    public double getSpecialSpellValue() {
        return lifestealValue;
    }

    @Override
    public int getSpecialSpellMove() {
        return lifestealMoveNumber;
    }

    @Override
    public SpellType getSpecialSpellType() {
        return specialSpellType;
    }

    @Override
    public void setSpecialSpellValue(double lifestealValue) {
        this.lifestealValue = lifestealValue;
    }

    @Override
    public void setSpecialSpellMove(int lifestealMoveNumber) {
        this.lifestealMoveNumber = lifestealMoveNumber;
    }

    @Override
    public void setSpecialSpellType(SpellType specialSpellType) {
        this.specialSpellType = specialSpellType;
    }

}
