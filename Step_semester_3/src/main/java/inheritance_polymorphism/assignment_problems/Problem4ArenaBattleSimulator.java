package inheritance_polymorphism.assignment_problems;

interface Attackable {
    String attack();
    String attack(String weaponName);
}

interface Defendable { String defend(); }

abstract class GameCharacter {
    private static int nextId = 1000;
    private final String characterId = "CH-" + (++nextId);
    public abstract String getSpecialMove();
    public String getCharacterId() { return characterId; }
}

class Warrior extends GameCharacter implements Attackable, Defendable {
    private final String name;
    public Warrior(String name) { this.name = name; }
    public String attack() { return name + " strikes with a blade"; }
    public String attack(String weaponName) {
        return name + " strikes with an " + weaponName;
    }
    public String defend() { return name + " raises a shield"; }
    public String getSpecialMove() { return name + " unleashes Whirlwind Slash"; }
}

class Trap implements Defendable {
    private final String trapType;
    public Trap(String trapType) { this.trapType = trapType; }
    public String defend() { return trapType + " triggers automatically"; }
}

public class Problem4ArenaBattleSimulator {
    static void resolveDefense(Defendable[] combatants) {
        for (Defendable x : combatants) System.out.println(x.defend());
    }

    public static void main(String[] args) {
        Warrior w = new Warrior("Kael");
        Trap t = new Trap("Spike Pit");
        System.out.println(w.attack());
        System.out.println(w.attack("Iron Sword"));
        System.out.println(w.getSpecialMove());
        resolveDefense(new Defendable[]{w, t});
    }
}