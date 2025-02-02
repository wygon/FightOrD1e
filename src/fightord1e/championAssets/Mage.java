package fightord1e.championAssets;

public class Mage extends Champion {

    public Mage(String name, double healthPoints, double attackDamage, double magicDamage, double physicalResist, double magicResist, int distancePoint, Ability[] abilities) {
        super(name, healthPoints, attackDamage, magicDamage, physicalResist, magicResist, distancePoint, abilities);
        this.specialSpellType = SpellType.LUCK;
    }

    public Mage() {
        this.specialSpellType = SpellType.LUCK;
    }

    @Override
    public String toString() {
        return super.toString() + "\n I AM LUCKY!: ";
    }

    @Override
    public double getSpecialSpellValue() {
        return 0;
    }

    @Override
    public int getSpecialSpellMove() {
        return 0;
    }

    @Override
    public SpellType getSpecialSpellType() {
        return specialSpellType;
    }

    @Override
    public void setSpecialSpellType(SpellType specialSpellType) {
        this.specialSpellType = specialSpellType;
    }

}
