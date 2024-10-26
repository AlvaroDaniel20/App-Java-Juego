import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;


public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Player player = null;
        Enemy enemy = new Enemy("Villano");

        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- MENU DEL JUEGO ---");
            System.out.println("1. Seleccionar Personaje");
            System.out.println("2. Ver Estadisticas del Personaje");
            System.out.println("3. Iniciar Batalla");
            System.out.println("4. Salir");
            System.out.println("Elige una opción: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    player = selectCharacter(scanner);
                    break;
                case 2:
                    if (player != null) {
                        showPlayerStats(player);
                    } else {
                        System.out.println("¡Primero debes seleccionar un personaje!");
                    }
                    break;
                case 3:
                    if (player != null) {
                        BattleSystem.startBattle(player, enemy, scanner);
                    } else {
                        System.out.println("¡Primero debes seleccionar un personaje!");
                    }
                    break;
                case 4:
                    System.out.println("Gracias por jugar. ¡Hasta la proxima!");
                    exit = true;
                    break;
                default:
                    System.out.println("Opción no válida, intenta de nuevo.");
            }
        }
        scanner.close();
    }

    private static Player selectCharacter(Scanner scanner) {
        System.out.println("\n--- SELECCION DE PERSONAJE ---");
        System.out.println("1. Jugador Guerrero");
        System.out.println("2. Jugador Mago");
        System.out.println("3. Jugador Arquero");
        System.out.print("Elige tu personaje: ");
        int characterChoice = scanner.nextInt();
        Player player;

        switch (characterChoice) {
            case 1:
                player = new Player("Guerrero");
                break;
            case 2:
                player = new Player("Mago");
                break;
            case 3:
                player = new Player("Arquero");
                break;
            default:
                System.out.println("Opción no válida, seleccionando Guerrero por defecto.");
                player = new Player("Guerrero");
                break;
        }
        System.out.println("¡Has seleccionado a " + player.getName() + "!");
        return player;
    }

    private static void showPlayerStats(Player player) {
        System.out.println("\n--- ESTADISTICAS DEL JUGADOR ---");
        System.out.println("Nombre: " + player.getName());
        System.out.println("HP: " + player.getCurrentHp() + "/" + player.getMaxHp());
        System.out.println("Ataque: " + player.getAttack());
        System.out.println("Defense: " + player.getDefense());
        System.out.println("Ataque Especial: " + player.getSpecialAttack());
        System.out.println("Defensa Especial: " + player.getSpecialDefense());
    }
}

abstract class Character {
    protected String name;
    protected int maxHp;
    protected int currentHp;
    protected int attack;
    protected int defense;
    protected int specialAttack;
    protected int specialDefense;

    public Character(String name, int maxHp, int attack, int defense, int specialAttack, int specialDefense){
        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.attack = attack;
        this.defense = defense;
        this.specialAttack = specialAttack;
        this.specialDefense = specialDefense;
    }
    
    public String getName() {return this.name;}
    public int getMaxHp() {return this.maxHp;}
    public int getCurrentHp() {return this.currentHp;}
    public int getAttack() {return this.attack;}
    public int getDefense() {return this.defense;}
    public int getSpecialAttack() {return this.specialAttack;}
    public int getSpecialDefense() {return this.specialDefense;}

    // Metodo para recibir daño
    public void takeDamage(int damage) {
        int reducedDamage = applyDefense(damage);
        this.currentHp = Math.max(0, this.currentHp - reducedDamage);
        System.out.println(this.name + " recibe " + damage + " puntos de daño!");
        System.out.println("HP actual: " + this.currentHp + "/" + this.maxHp);
    }

    // Metodo para curar
    public void heal(int amount) {
        int healedAmount = Math.min(amount, maxHp - currentHp);
        this.currentHp += healedAmount;
        System.out.println(this.name + " se ha curado: " + healedAmount + " HP!");
        System.out.println("HP actual: " + this.currentHp + "/" + this.maxHp);
    }

    private int applyDefense(int damage) {
        double defenseRandom = getRandomFactor();
        int effectiveDefense = (int) (this.defense * defenseRandom);
        int reducedDamage = (int) Math.max(1, damage -  effectiveDefense * 0.5);
        return reducedDamage;
    }

    protected double getRandomFactor() {
        return 0.85 + Math.random() * 0.15;
    }
}

class Player extends Character {
    private static final int BASE_HP = 100;
    private static final int BASE_ATTACK = 35;
    private static final int BASE_DEFENSE = 15;
    private static final int BASE_SPECIAL_ATTACK = 45;
    private static final int BASE_SPECIAL_DEFENSE = 30;

    private int turns;
    private Queue<String> actionHistory = new LinkedList<>();

    public Player(String name) {
        super(name, BASE_HP, BASE_ATTACK, BASE_DEFENSE, BASE_SPECIAL_ATTACK, BASE_SPECIAL_DEFENSE);
        this.turns = 0;
    }

    public void incrementTurn() {
        this.turns++;
    }

    public int getTurns() {
        return this.turns;
    }

    public boolean isSpecialAttackAvailable() {
        if (this.turns >= 2) {
            Random random = new Random();
            return random.nextDouble() < 0.3;
        }
        return false;
    }

    public void basicAttack(Character target) {
        int damage = calculateDamage(this.attack, target.getDefense());
        target.takeDamage(damage);
        recordAction("attack");
        System.out.println(this.name + " realiza un ataque basico y causa: " + damage + " puntos de daño!");
    }
    
    public void specialAttack(Character target) {
        int damage = calculateDamage(this.specialAttack, target.getDefense());
        target.takeDamage(damage);
        recordAction("specialAttack");
        System.out.println(this.name + " realiza un ataque especial y causa: " + damage + " puntos de daño!");
    }

    public void defend() {
        recordAction("defense");
        System.out.println(this.name + " adquiere una postura defensiva!");
        this.defense += 8;
    }

    public void boostAttack(int amount) {
        this.attack += amount;
        System.out.println(this.name + " ahora tiene " + this.attack + " puntos de ataque.");
    }

    public void boostDefense(int amount) {
        this.defense += amount;
        System.out.println(this.name + " ahora tiene " + this.defense + " puntos de defensa.");
    }

    // Transformacion  || Terminar de ajustar esta funcion
    public void superGuerrero(int amount) {
        this.attack += this.attack * 0.25;
        this.defense += this.attack * 0.20;
        System.out.println(this.name + " se ha transformado en Super Guerrero");
    }

    public boolean isSuperGuerreroAvailable() {
        if (this.turns >= 5) {
            Random random = new Random();
            return random.nextDouble() < 0.4;
        }
        return false;
    }


    // Graba las acciones del jugador
    private void recordAction(String action) {
        if (actionHistory.size() >= 3) {
            actionHistory.poll();
        }
        actionHistory.add(action);
    }

    public Queue<String> getActionHistory() {
        return new LinkedList<>(actionHistory);
    }

    // Calcula el daño que hara el jugador
    private int calculateDamage(int attackPower, int targetDefense) {
        double attackRandom = getRandomFactor();
        return (int) Math.max(1, (attackPower * attackRandom - targetDefense * 0.5));
    }

}

class Enemy extends Character {
    private static final int BASE_HP = 120;
    private static final int BASE_ATTACK = 35;
    private static final int BASE_DEFENSE = 20;
    private static final int BASE_SPECIAL_ATTACK = 40;
    private static final int BASE_SPECIAL_DEFENSE = 30;

    private int consecutiveBasicAttacks = 0;
    private int consecutiveDefenses = 0;
    private int consecutiveDebuff = 0;
    private static final int MAX_CONSECUTIVES_ACTIONS = 2;

    public Enemy(String name) {
        super(name, BASE_HP, BASE_ATTACK, BASE_DEFENSE, BASE_SPECIAL_ATTACK, BASE_SPECIAL_DEFENSE); 
    }

    public void takeAction(Player player) {
        Queue<String> playerActions = player.getActionHistory();
        boolean playerIsAggressive = playerActions.stream().allMatch(action -> action.equals("attack"));

        if (playerIsAggressive) {
            applyAttackDebuff(player);
        } else if (this.currentHp < this.maxHp * 0.3 && Math.random() < 0.5) {
            healAction();
        } else if (player.getCurrentHp() < player.getMaxHp() * 0.3 && Math.random() < 0.5) {
            specialAttack(player);
        } else if (this.currentHp < this.maxHp * 0.5 && Math.random() < 0.5) {
            defend(player);
        } else {
            basicAttack(player);
        }
    }

    public void applyAttackDebuff(Player player) {
        if (consecutiveDebuff < MAX_CONSECUTIVES_ACTIONS) {
            player.attack -= 5;
            System.out.println(this.name + " usa un hechizo debilitador y reduce el ataque de " + player.getName() + " en 5 puntos!");
            consecutiveDebuff++;
            consecutiveBasicAttacks = 0;
            consecutiveDefenses = 0;
        } else {
            basicAttack(player);
        }
        
    }

    public void basicAttack(Player player) {
        if (consecutiveBasicAttacks < MAX_CONSECUTIVES_ACTIONS) {
            int damage = calculateDamage(this.attack, player.getDefense());
            player.takeDamage(damage);
            System.out.println(this.name + " realiza un ataque basico y causa: " + damage + " puntos de daño!");
            consecutiveBasicAttacks++;
            consecutiveDefenses = 0;
        } else {
            defend(player);
        }
    }

    public void specialAttack(Player player) {
        int damage = calculateDamage(this.specialAttack, player.getSpecialDefense());
        player.takeDamage(damage);
        System.out.println(this.name + " usa su habilidad especial y causa: " + damage + " puntos de daño");
        consecutiveBasicAttacks = 0;
        consecutiveDefenses = 0;
    }

    public void defend(Player player) {
        if (consecutiveDefenses < MAX_CONSECUTIVES_ACTIONS) {
            System.out.println(this.name + " adquiere una postura defensiva!");
            this.defense += 8;
            consecutiveDefenses++;
            consecutiveBasicAttacks = 0;
        } else {
            basicAttack(player);
        }
        
    }

    public void healAction() {
        int healAmount = (int) (this.maxHp * 0.25);
        this.heal(healAmount);
        System.out.println(this.name + " usa una pocion y recupera " + healAmount + " HP!");
        consecutiveBasicAttacks = 0;
        consecutiveDefenses = 0;
    }

    private int calculateDamage(int attackPower, int targetDefense) {
        double attackRandom = getRandomFactor();
        return (int) Math.max(1, (attackPower * attackRandom - targetDefense * 0.5));
    }
}

class Item {
    private String name;
    private String type;
    private int potency;

    public Item(String name, String type, int potency) {
        this.name = name;
        this.type = type;
        this.potency = potency;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getPotency() {
        return potency;
    }

    public void use(Player player) {
        switch (type) {
            case "attack":
                player.boostAttack(potency);
                System.out.println(player.getName() + " ha usado " + name + " y su ataque a aumentado en " + potency + " puntos!");
                break;
            case "defense":
                player.boostDefense(potency);
                System.out.println(player.getName() + " ha usado " + name + " y su defensa a aumentado en " + potency + " puntos!");
                break;
            case "heal":
                player.heal(potency);
                System.out.println(player.getName() + " ha usado " + name + " y ha recuperado " + potency + " HP!");
                break;
            default:
                break;
        }
    }
}

abstract class BattleSystem {
    public static void startBattle(Player player, Enemy enemy, Scanner scanner) {
        Random random = new Random();

        System.out.println("¡La batalla entre " + player.getName() + " y " + enemy.getName() + " ha comenzado!");

        while (player.getCurrentHp() > 0 && enemy.getCurrentHp() > 0) {
            player.incrementTurn();

            System.out.println("\n------------------------------------------------");
            System.out.println("\nTu turno, " + player.getName() + ":");

            if (random.nextDouble() < 0.2) {
                Item item = generateRandomItem();
                System.out.println("¡Haz encontrado un objeto especial: " + item.getName() + "!");
                System.out.println("Deseas usarlo ahora? (1: Si | 2: No)");
                int useItem = scanner.nextInt();
                if (useItem == 1) {
                    item.use(player);
                }
            }

            System.out.println("1. Ataque basico");

            if (player.isSpecialAttackAvailable()) {
                System.out.println("2. Ataque especial");
            }

            System.out.println("3. Defensa");

            if (player.isSuperGuerreroAvailable()) {
                System.out.println("4. Super Guerrero");
            }

            System.out.println("Elige tu acción:");
            int action = scanner.nextInt();
            System.out.println();

            switch (action) {
                case 1:
                    player.basicAttack(enemy);
                    break;
                case 2:
                    player.specialAttack(enemy);
                    break;
                case 3:
                    player.defend();
                    break;
                case 4:
                    player.superGuerrero(action);           // Terminar de ajustar esta funcion
                    break;
                default:
                    System.out.println("Acción no valida, pierdes el turno.");
                    break;
            }
            if (enemy.getCurrentHp() <= 0) {
                System.out.println("--------" + enemy.getName() + " ha sido derrotado. ¡Ganaste la batalla!--------");
                break;
            }

            System.out.println("\n------------------------------------------------");
            System.out.println("\nTurno de " + enemy.getName() + ":");
            enemy.takeAction(player);

            if (player.getCurrentHp() <= 0 ) {
                System.out.println("--------" + player.getName() + " ha sido derrotado. ¡Perdiste la batalla!--------");
                break;
            }
        }
    }

    private static Item generateRandomItem() {
        Random random = new Random();
        int itemType = random.nextInt(3);
        switch (itemType) {
            case 0:
                return new Item("Potenciador de ataque" , "attack" , 10 + random.nextInt(11));
            case 1:
                return new Item("Escudo reforzado" , "defense" , 5 + random.nextInt(6));
            case 2:
                return new Item("Poción de salud", "heal", random.nextInt(21));
            default:
                return null;
        }
    }
}