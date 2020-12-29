import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;//temporary input
import java.util.concurrent.TimeUnit;

/**
 * [Main.java]
 *
 * @author Ayden Gao
 * @author Eric Miao
 */
public class Main {
    public static GameObject[][] board;
    public static int size;

    /**
     * main
     * The method that executes the program
     *
     * @param argument arguments
     */
    public static void main(String[] argument) {
        ArrayList<Warrior> enemyList = createEnemyList(3);
        ArrayList<Soldier> playerList;
        size = 8;
        board = new GameObject[size][size];
        generateMap();
        generateObstacles(board, 6);
        generateEnemies(board, enemyList);
        generateVitals(board, 2);

        printBoard(board);
        playerList = placePlayer(board, 2);
        printBoard(board);

        gameLoop(board, enemyList, playerList);
        //new GameFrame();
    }

    /**
     * gameLoop
     * The main loop of the game
     *
     * @param board     the board
     * @param enemyList the arrayList of enemy
     */
    public static void gameLoop(GameObject[][] board, ArrayList<Warrior> enemyList, ArrayList<Soldier> playerList) {
        boolean running = true;
        while (running) {
            for (Warrior warrior : enemyList) {//loops through each item in list
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ignored) {
                }
                //attack goes here
                executeEnemyAttack(board, warrior, enemyList);
                enemyMove(board, warrior);
                enemyAttack(board, warrior);
                printBoard(board);
                //certain condition: running = false;
            }
            playerAction(board, enemyList);
            resetPlayers(playerList);
        }
    }

    /**
     * playerAction
     * allows players to select a unit and move and attack with it
     *
     * @param board     the game board
     * @param enemyList the enemy list
     */
    public static void playerAction(GameObject[][] board, ArrayList enemyList) {
        Scanner in = new Scanner(System.in);
        boolean end = false;
        int ans;
        do {
            System.out.println("1.select unit\n2.quit");
            ans = in.nextInt();
            if (ans == 1) {//select unit
                Soldier unit = (Soldier) selectPlayer(board);

                System.out.println("1.attack\n2.move");
                ans = in.nextInt();

                if (ans == 1) {//attack
                    playerAttack(board, unit, enemyList);
                } else if (ans == 2) {//move
                    playerMove(board, unit);
                }

            } else if (ans == 2) {//quit
                end = true;
            }
        } while (!end);
    }

    /**
     * select player
     * Allows user to select the player from the board
     *
     * @param board the game board
     * @return the selected player
     */
    public static Player selectPlayer(GameObject[][] board) {
        Scanner in = new Scanner(System.in);
        do {
            int x = in.nextInt();
            int y = in.nextInt();
            if (x >= size || y >= size || x < 0 || y < 0) {
                System.out.println("out of bounds");
            } else if (board[y][x] instanceof Player) {
                return (Player) board[y][x];
            }
        } while (true);
    }

    /**
     * playerAttack
     * attacks with the selected player
     *
     * @param board     the game board
     * @param player    the player attacking
     * @param enemyList the enemyList
     */
    public static void playerAttack(GameObject[][] board, Soldier player, ArrayList enemyList) {
        if (player.isAttacked()) {//if player has attacked already
            System.out.println("this player has already attacked");
            return;
        }

        Scanner in = new Scanner(System.in);
        do {
            int x = in.nextInt();
            int y = in.nextInt();
            if (x >= size || y >= size || x < 0 || y < 0) {
                System.out.println("out of bounds");
            } else {//if in bounds
                GameObject object = board[y][x];
                if (object instanceof Damageable) {
                    ((Damageable) object).damageTaken(player.attack());

                    if (((Damageable) object).getHealth() <= 0) {//if object is killed
                        board[y][x] = null;
                        if (object instanceof Enemy) {//removes enemy from enemy list
                            for (int i = 0; i < enemyList.size(); i++) {
                                if (enemyList.get(i).equals(object)) {
                                    enemyList.remove(i);
                                    break;
                                }
                            }
                        }
                    }
                }
                player.setAttacked(true);
                return;//if object gets attacked
            }
        } while (true);
    }

    /**
     * playerMove
     * moves the player selected based on user input
     *
     * @param board the game board
     * @param unit  the unit to move
     */
    public static void playerMove(GameObject[][] board, Soldier unit) {
        if (unit.isMoved()) {//if player has moved already
            System.out.println("player has moved already");
            return;
        }

        Scanner in = new Scanner(System.in);
        do {
            int x = in.nextInt();
            int y = in.nextInt();
            if (x >= size || y >= size || x < 0 || y < 0) {
                System.out.println("out of bounds");
            } else if (board[y][x] != null) {
                System.out.println("occupied");
            } else {
                board[unit.getY()][unit.getX()] = null;
                board[y][x] = unit;
                unit.setX(x);
                unit.setY(y);
                unit.setMoved(true);
            }
        } while (true);
    }

    /**
     * resetPlayers
     * resets the moved and attacked boolean variables
     *
     * @param playerList the list of players
     */
    private static void resetPlayers(ArrayList<Soldier> playerList) {
        for (int i = 0; i < playerList.size(); i++) {
            Soldier unit = playerList.get(i);
            unit.setAttacked(false);
            unit.setMoved(false);
        }
    }

    /**
     * enemyAttack
     * how the enemy will attack, enemy select a coordinate to attack
     * if attack x and y is -1 then enemy doesn't attack
     * if not -1 enemy will attack the square.
     */
    private static void enemyAttack(GameObject[][] board, Warrior enemy) {
        Random rand = new Random();
        int x = rand.nextInt(size);
        int y = rand.nextInt(size);
        enemy.setAttackX(x);
        enemy.setAttackY(y);
        System.out.println("enemyAttackX: " + x);
        System.out.println("enemyAttackY: " + y);
    }

    /**
     * executeEnemyAttack
     * executes the attack of the selected enemy
     * THis method should include enemy list as we want enemies to be pushed
     * Enemies won't target them selves but they could if pushed by player
     */
    private static void executeEnemyAttack(GameObject[][] board, Warrior enemy, ArrayList enemyList) {
        int x = enemy.getAttackX();
        int y = enemy.getAttackY();

        if (x == -1 || y == -1) {//enemy doesn't attack
            return;
        }

        GameObject object = board[y][x];
        if (object instanceof Damageable) {
            ((Damageable) object).damageTaken(enemy.attack());//takes damage

            if (((Damageable) object).getHealth() <= 0) {//if object is killed
                board[y][x] = null;
                if (object instanceof Enemy) {//removes enemy from enemy list
                    for (int i = 0; i < enemyList.size(); i++) {
                        if (enemyList.get(i).equals(object)) {
                            enemyList.remove(i);
                            break;
                        }
                    }
                }
            }
        }
    }


    /**
     * enemyMove
     * moves the inputted enemy on the game board
     *
     * @param board the board
     * @param enemy the enemy to move
     */
    private static void enemyMove(GameObject[][] board, Warrior enemy) {
        int[] ans = findEnemyXY(board, enemy);

        board[enemy.getY()][enemy.getX()] = null;//makes prev position null
        enemy.move(ans[0], ans[1]);//changes x and y
        board[ans[1]][ans[0]] = enemy;//changes position on the game board
    }

    //this method will be changed to get actual good x and y cords for enemy

    /**
     * findEnemyXY
     * gets the x and y coordinates of the inputted enemy
     * (currently random x and y)
     *
     * @param board the board
     * @param enemy the enemy to find x and y for
     * @return an array of length 2 containing the x and y coordinates
     */
    private static int[] findEnemyXY(GameObject[][] board, Warrior enemy) {
        int[] ans = new int[2];
        Random random = new Random();
        do {//finds random x and y for enemy
            ans[0] = random.nextInt(8);
            ans[1] = random.nextInt(8);
        } while (board[ans[1]][ans[0]] != null);
        return ans;
    }

    /**
     * Places the player at a desired location.
     * This method does not move the player, it simply places a unit on the board.
     * For player movement, visit {@code playerMove} method.
     * <p>
     * ***Ive edited this method so it creates a player arrayList
     *
     * @param board the board
     * @see Main#playerMove(GameObject[][], Soldier)
     */
    private static ArrayList<Soldier> placePlayer(GameObject[][] board, int playerNum) {
        Scanner in = new Scanner(System.in);
        boolean placed;
        ArrayList<Soldier> playerList = new ArrayList<>();
        for (int i = 0; i < playerNum; i++) {//creates each player
            placed = false;
            while (!placed) {
                int x = in.nextInt();
                int y = in.nextInt();
                if (x >= size || y >= size || x < 0 || y < 0) {
                    System.out.println("Out of bound");
                } else if (board[y][x] != null) {
                    System.out.println("Tile occupied");
                } else {
                    Soldier unit = new Soldier(x, y, 3, 20, 1);
                    board[y][x] = unit;
                    playerList.add(unit);
                    placed = true;
                }
            }
        }
        return playerList;
    }

    /**
     * Creates the game board
     */
    private static void generateMap() {
        for (GameObject[] gameObjects : board) {
            //matrix[i][j] = random.nextInt(10);
            Arrays.fill(gameObjects, null);
        }
    }

    /**
     * Adds obstacles to the game board
     *
     * @param board       the board
     * @param obstacleNum the number of obstacles to add
     */
    private static void generateObstacles(GameObject[][] board, int obstacleNum) {
        Random random = new Random();
        int x, y;
        for (int i = 0; i < obstacleNum; i++) {
            do {
                x = random.nextInt(8);
                y = random.nextInt(8);
            } while (board[y][x] != null);
            board[y][x] = new Obstruction(x, y, 3);
        }
    }

    /**
     * Adds vitals to the game board
     *
     * @param board    the board
     * @param vitalNum the number of vitals to add
     */
    private static void generateVitals(GameObject[][] board, int vitalNum) {
        Random random = new Random();
        int x, y;
        for (int i = 0; i < vitalNum; i++) {
            do {
                x = random.nextInt(8);
                y = random.nextInt(8);
            } while (board[y][x] != null);
            board[y][x] = new Vital(x, y, 2);
        }
    }

    /**
     * Adds enemies to the game board
     *
     * @param board     the board
     * @param enemyList the list of enemies to add
     */
    private static void generateEnemies(GameObject[][] board, ArrayList<Warrior> enemyList) {
        Random random = new Random();
        int x, y;
        for (Warrior warrior : enemyList) {
            do {
                x = random.nextInt(8);
                y = random.nextInt(8);
            } while (board[y][x] != null);

            //changes x and y of enemy objects
            warrior.setX(x);
            warrior.setY(y);
            board[y][x] = warrior;
        }
    }

    /**
     * Creates an arrayList containing the enemies on the board
     *
     * @param enemyNum the number of enemies to be created
     * @return the ArrayList of enemies
     */
    private static ArrayList<Warrior> createEnemyList(int enemyNum) {
        ArrayList<Warrior> enemyList = new ArrayList<>();
        for (int i = 0; i < enemyNum; i++) {
            enemyList.add(new Warrior(0, 0, 3, 1, 1));
        }
        return enemyList;
    }

    /**
     * Prints the updated game board
     *
     * @param board the board
     */
    public static void printBoard(GameObject[][] board) {
        for (GameObject[] row : board) {
            for (GameObject index : row) {
                if (index == null) {//grass
                    System.out.printf("%s ", "-");
                } else if (index instanceof Player) {
                    System.out.printf("%s ", "P");
                } else if (index instanceof Enemy) {
                    System.out.printf("%s ", "E");
                } else if (index instanceof Obstruction) {//obstacle
                    System.out.printf("%s ", "O");
                } else {//vital
                    System.out.printf("%s ", "V");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}
