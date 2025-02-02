package fightord1e.championAssets;

public class Assasin extends Mage{
    
    public Assasin(String name, double healthPoints, double attackDamage, double magicDamage, double physicalResist, double magicResist, int distancePoint, Ability[] abilities) {
        super(name, healthPoints, attackDamage, magicDamage, physicalResist, magicResist, distancePoint, abilities);
        this.specialSpellType = SpellType.CRIT;
    }

    public Assasin() {
        this.specialSpellType = SpellType.CRIT;
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