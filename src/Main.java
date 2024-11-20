import java.util.Scanner;
import java.util.HashMap;

public class Main {

    static String currentRoom = "Entrance";
    static HashMap<String, String> inventory = new HashMap<>();
    static int playerHealth = 100;
    static boolean hasTreasure = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Text Adventure Game!");
        System.out.println("Your goal is to reach the Treasure Room and collect the treasure.");
        System.out.println("Type 'help' to see the list of commands.\n");

        while (true) {
            if (playerHealth <= 0) {
                System.out.println("Game Over! You have no health left.");
                break;
            }

            if (hasTreasure) {
                System.out.println("You Win! You have found the treasure.");
                break;
            }

            System.out.println("You are in the " + currentRoom + ".");
            describeRoom(currentRoom);

            System.out.print("\n> ");
            String command = scanner.nextLine().toLowerCase();

            processCommand(command);
        }

        scanner.close();
    }
    public static void processCommand(String command) {
        switch (command) {
            case "go north":
            case "go south":
            case "go east":
            case "go west":
                move(command);
                break;
            case "check inventory":
                checkInventory();
                break;
            case "take key":
                takeItem("key");
                break;
            case "take potion":
                takeItem("potion");
                break;
            case "use key":
                useKey();
                break;
            case "use potion":
                usePotion();
                break;
            case "talk":
                talkToNPC();
                break;
            case "attack":
                attackEnemy();
                break;
            case "run":
                System.out.println("You run away safely.");
                break;
            case "help":
                showHelp();
                break;
            default:
                System.out.println("Invalid command. Type 'help' to see the list of commands.");
                break;
        }
    }

    public static void describeRoom(String room) {
        switch (room) {
            case "Entrance":
                System.out.println("You are at the Entrance. Exits: North.");
                break;
            case "Forest":
                System.out.println("You are in a dark Forest. Exits: South, East.");
                System.out.println("You see a potion on the ground.");
                break;
            case "Dungeon":
                System.out.println("You are in a creepy Dungeon. Exits: West.");
                System.out.println("You see an NPC here. Try talking to them.");
                System.out.println("You see a key on the ground");
                break;
            case "Treasure Room":
                System.out.println("You are in the Treasure Room! The treasure chest is locked. Use the key to open it.");
                break;
            default:
                System.out.println("You are in an unknown place.");
                break;
        }
    }

    public static void move(String direction) {
        switch (currentRoom) {
            case "Entrance":
                if (direction.equals("go north")) {
                    currentRoom = "Forest";
                } else {
                    System.out.println("You can't go that way.");
                }
                break;
            case "Forest":
                if (direction.equals("go south")) {
                    currentRoom = "Entrance";
                } else if (direction.equals("go east")) {
                    currentRoom = "Dungeon";
                } else {
                    System.out.println("You can't go that way.");
                }
                break;
            case "Dungeon":
                if (direction.equals("go west")) {
                    currentRoom = "Forest";
                } else if (direction.equals("go north")) {
                    currentRoom = "Treasure Room";
                } else {
                    System.out.println("You can't go that way.");
                }
                break;
            case "Treasure Room":
                System.out.println("There's no exit here. Focus on the treasure!");
                break;
            default:
                System.out.println("You can't go that way.");
                break;
        }
    }

    public static void checkInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Your inventory is empty.");
        } else {
            System.out.println("Your inventory: " + inventory.keySet());
        }
    }

    public static void takeItem(String item) {
        if (currentRoom.equals("Forest") && item.equals("potion")) {
            inventory.put(item, "Restores 20 health");
            System.out.println("You picked up a potion.");
        } else if (currentRoom.equals("Dungeon") && item.equals("key")) {
            inventory.put(item, "Opens the treasure chest");
            System.out.println("You picked up a key.");
        } else {
            System.out.println("There's no " + item + " here.");
        }
    }

    public static void useKey() {
        if (currentRoom.equals("Treasure Room") && inventory.containsKey("key")) {
            System.out.println("You use the key to open the treasure chest.");
            hasTreasure = true;
        } else if (!inventory.containsKey("key")) {
            System.out.println("You don't have a key.");
        } else {
            System.out.println("You can't use the key here.");
        }
    }

    public static void usePotion() {
        if (inventory.containsKey("potion")) {
            playerHealth = Math.min(playerHealth + 20, 100);
            inventory.remove("potion");
            System.out.println("You used a potion. Your health is now " + playerHealth + ".");
        } else {
            System.out.println("You don't have a potion.");
        }
    }

    public static void talkToNPC() {
        if (currentRoom.equals("Dungeon")) {
            System.out.println("NPC: Beware of enemies in the Treasure Room. Good luck!");
        } else {
            System.out.println("There's no one to talk to here.");
        }
    }

    public static void attackEnemy() {
        if (currentRoom.equals("Treasure Room")) {
            System.out.println("You attack the enemy and defeat it, but lose 20 health.");
            playerHealth -= 20;
            System.out.println("Your health is now " + playerHealth + ".");
        } else {
            System.out.println("There's no enemy to attack here.");
        }
    }

    public static void showHelp() {
        System.out.println("Available commands:");
        System.out.println("go north, go south, go east, go west - Move between rooms");
        System.out.println("check inventory - Check your inventory");
        System.out.println("take key, take potion - Collect items");
        System.out.println("use key, use potion - Use items");
        System.out.println("talk - Talk to NPCs");
        System.out.println("attack, run - Combat commands");
    }
}
