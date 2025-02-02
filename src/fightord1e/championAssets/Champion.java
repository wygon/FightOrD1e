//Szymon Wygoński
package fightord1e.championAssets;

public abstract class Champion {

    protected String name;
    protected double healthPoints;
    protected double attackDamage;
    protected double magicDamage;
    protected double physicalResist;
    protected double magicResist;
    protected int distancePoint;
    protected Ability[] abilities;
    protected boolean poison;
    protected double poisonDmg;
    protected int poisonMoveNumber;
    protected double lrHP;
    protected double damageDealt;
    protected SpellType specialSpellType;

    public Champion(String name, double healthPoints, double attackDamage, double magicDamage, double physicalResist, double magicResist, int distancePoint, Ability[] abilities) {
        this.name = name;
        this.healthPoints = healthPoints;
        this.attackDamage = attackDamage;
        this.magicDamage = magicDamage;
        this.physicalResist = physicalResist;
        this.magicResist = magicResist;
        this.distancePoint = distancePoint;
        this.abilities = abilities;
        this.poison = false;
        this.poisonDmg = 0;
        this.poisonMoveNumber = 0;
        this.lrHP = healthPoints;
        this.damageDealt = 0;
    }

    public Champion() {
    }

    public String lessStats() {
        String ls = "";
        ls += "\nHP:  " + healthPoints;
        ls += "\nAbilities left : ";
        for (Ability a : abilities) {
            ls += "\n";
            ls += a.getName() + " ";
            ls += a.getUsesLeft();
        }
        return ls;
    }

    public String printAbilities() {
        String abstr = "";
        for (Ability ability : abilities) {
            abstr += ability + "\n";
        }
        return abstr;
    }

    @Override
    public String toString() {
        String a = "\n" + name;
        a += "\n Attack Damage " + attackDamage;
        a += "\n Magic Damage: " + magicDamage;
        a += "\n Health Points: " + healthPoints;
        a += "\n Physical Resist: " + physicalResist;
        a += "\n Magic Resist: " + magicResist;
        return a;
    }

    public String getName() {
        return name;
    }

    public double getHP() {
        return healthPoints;
    }

    public double getAttackDamage() {
        return attackDamage;
    }

    public double getMagicDamage() {
        return magicDamage;
    }

    public double getPhysicalResist() {
        return physicalResist;
    }

    public double getMagicResist() {
        return magicResist;
    }

    public int getDistancePoint() {
        return distancePoint;
    }

    public Ability[] getAbilities() {
        return abilities;
    }

    public double getPoisonDmg() {
        return poisonDmg;
    }

    public boolean isPoison() {
        return poison;
    }

    public int getPoisonMove() {
        return poisonMoveNumber;
    }

    public double getLastRoundHP() {
        return lrHP;
    }

    public double getDamageDealt() {
        return damageDealt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAttackDamage(double attackDamage) {
        this.attackDamage = attackDamage;
    }

    public void setHP(double hp) {
        healthPoints = hp;
    }

    public void setMagicDamage(double magicDamage) {
        this.magicDamage = magicDamage;
    }

    public void setPhysicalResist(double physicalResist) {
        this.physicalResist = physicalResist;
    }

    public void setMagicResist(double magicResist) {
        this.magicResist = magicResist;
    }

    public void setDistancePoint(int distancePoint) {
        this.distancePoint = distancePoint;
    }

    public void setPoison(boolean poison) {
        this.poison = poison;
    }

    public void setPoisonMove(int poisonMoveNumber) {
        this.poisonMoveNumber = poisonMoveNumber;
    }

    public void setLastRoundHP(double lrHP) {
        this.lrHP = lrHP;
    }

    public void setDamageDealt(double damageDealt) {
        this.damageDealt = damageDealt;
    }

    public void setAbilities(Ability[] abilities) {
        this.abilities = abilities;
    }

    public void addAttackDamage(double attackDamage) {
        this.attackDamage += attackDamage;
    }

    public void addHP(double healthPoints) {
        this.healthPoints += healthPoints;
    }

    public void addMagicDamage(double magicDamage) {
        this.magicDamage += magicDamage;
    }

    public void addPhysicalResist(double physicalResist) {
        this.physicalResist += physicalResist;
    }

    public void addMagicResist(double magicResist) {
        this.magicResist += magicResist;
    }

    public void addPoisonDmg(double poisonDmg) {
        this.poisonDmg += poisonDmg;
    }

    public void addDistancePoint(int distancePoint) {
        this.distancePoint += distancePoint;
    }

    public void addDamageDealt(double damageDealt) {
        this.damageDealt += damageDealt;
    }

    public abstract double getSpecialSpellValue();

    public abstract int getSpecialSpellMove();

    public abstract SpellType getSpecialSpellType();

    public void setSpecialSpellValue(double value){}

    public void setSpecialSpellMove(int value){};

    public abstract void setSpecialSpellType(SpellType type);
}
