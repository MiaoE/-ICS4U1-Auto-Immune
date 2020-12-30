import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;//temporary input
import java.util.concurrent.TimeUnit;
import java.util.InputMismatchException;

/**
 * [Main.java]
 * The game starts with randomly placed {@code Obstacle} on a board.
 * The objective of the game is to protect vitals at all cost.
 * {@code Enemy} places their units on one side of the board, and {@code Player} places their units in any of
 * the specified interactive tiles.
 *
 * Each unit has their own abilities and characteristics.
 * For instance, some units can move far but deals minimal damage while some other units can only move
 * one tile but deals a lot of damage. Furthermore, some units can heal their allies or perform their unique ability.
 *
 * At the end, the player has to strategically place their units and kill enemy players before they
 * destroy a specified number of vitals.
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 1.0 20/12/29
 */
public class Main {
    public static GameObject[][] board;
    public static int size;

    /**
     * main
     * The method that executes the program.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        ArrayList<Player> playerList;
        ArrayList<Enemy> enemyList;
        size = 8;
        board = new GameObject[size][size];

        //Creates map
        generateObstacles(board, 6, 2);
        enemyList = generateEnemy(board, 3);

        //Player places unit
        printBoard(board);
        playerList = placePlayer(board, 2);
        printBoard(board);
        waitSecond(1);

        //Starts main program
        gameLoop(board, enemyList, playerList);
    }

    /**
     * gameLoop
     * The main loop of the game.
     *
     * @param board     the board
     * @param enemyList the arrayList of enemy
     */
    public static void gameLoop(GameObject[][] board, ArrayList<Enemy> enemyList, ArrayList<Player> playerList) {
        boolean running = true;
        while (running) {
            //Enemy's turn
            executeEnemyAttack(board, playerList, enemyList);
            enemyMove(board, enemyList);
            enemyAttack(board, enemyList);
            printBoard(board);
            //certain condition: running = false;

            //Player's turn
            playerAction(board, enemyList);
            resetPlayers(playerList);
        }
    }

    /**
     * executeEnemyAttack
     * Executes the attack of the selected enemy.
     * This method should include enemy list as enemies will be pushed.
     * Enemies won't target themselves and their allies but they can be damaged if pushed by player.
     *
     * @param board      game board
     * @param playerList list of players
     * @param enemyList  list of enemies
     */
    private static void executeEnemyAttack(GameObject[][] board, ArrayList<Player> playerList, ArrayList<Enemy> enemyList) {
        for (Enemy enemy : enemyList) {
            int x = enemy.getAttackX();
            int y = enemy.getAttackY();

            if (x == -1 || y == -1) {//enemy doesn't attack
                continue;//goes to the next enemy
            }
            System.out.println("Enemy at " + enemy.getX() + " " + enemy.getY() + " attacks " + x + " " + y);
            GameObject object = board[y][x];
            if (object instanceof Damageable && enemy instanceof Attackable) {
                ((Damageable) object).damageTaken(((Attackable) enemy).attack());//takes damage

                //removes the object from board if health <= 0
                if (((Damageable) object).getHealth() <= 0) {//if object is destroyed or killed
                    board[y][x] = null;
                    System.out.println("Object at " + x + " " + y + " is destroyed or killed");
                    if (object instanceof Player) {
                        playerList.remove(object);
                    }
                }
            }
        }
    }

    /**
     * enemyMove
     * Moves each of the alive enemies.
     *
     * @param board     the board
     * @param enemyList the list of enemies
     */
    private static void enemyMove(GameObject[][] board, ArrayList<Enemy> enemyList) {
        for (Enemy enemy : enemyList) {
            int[] ans = findEnemyXY(board, enemy);

            board[enemy.getY()][enemy.getX()] = null;//makes prev position null
            enemy.move(ans[0], ans[1]);//changes x and y
            board[ans[1]][ans[0]] = enemy;//changes position on the game board
        }
    }

    /**
     * findEnemyXY
     * Helper method for {@code enemyMove} method.
     * This method gets the x and y coordinates of an empty spot for the enemy unit to move to
     * (currently random x and y that is not another {@code Enemy}, will be changed).
     *
     * @param board the board
     * @param enemy the enemy
     * @return an array of length 2 containing the x and y coordinates
     * @see Main#enemyMove(GameObject[][], ArrayList)
     */
    private static int[] findEnemyXY(GameObject[][] board, Enemy enemy) {
        int[] ans = new int[2];
        Random random = new Random();
        do {//finds random x and y for enemy
            ans[0] = random.nextInt(8);
            ans[1] = random.nextInt(8);
        } while (board[ans[1]][ans[0]] != null);
        return ans;
    }

    /**
     * enemyAttack
     * Defines how and where the enemy will attack next round:
     * Each enemy will select a coordinate to attack.
     * If any of the {@code attackX} and {@code attackY} variables are -1 then enemy will not attack next round,
     * otherwise enemy will attack the specified tile next round.
     *
     * @param board     game board
     * @param enemyList the list of enemies
     */
    private static void enemyAttack(GameObject[][] board, ArrayList<Enemy> enemyList) {
        Random rand = new Random();

        for (Enemy enemy : enemyList) {
            int x;
            int y;
            do {
                x = rand.nextInt(size);
                y = rand.nextInt(size);
            } while (board[y][x] instanceof Enemy);//won't attack own ally
            enemy.setAttackX(x);
            enemy.setAttackY(y);
            System.out.println("enemy at " + enemy.getX() + " " + enemy.getY() + " will attack " + x + " " + y);
        }
    }

    /**
     * playerAction
     * Allows players to select a unit and choose a specified action for that unit.
     * Player's turn will not end until the player insists on ending their turn.
     *
     * @param board     the game board
     * @param enemyList the enemy list
     */
    public static void playerAction(GameObject[][] board, ArrayList<Enemy> enemyList) {
        Scanner in = new Scanner(System.in);
        boolean end = false;
        int ans;
        do {
            System.out.println("1.select unit\n2.quit");

            //only exists for v1, as inputs are going to be buttons for future versions
            try {
                ans = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Input mismatch");
                in.next();
                continue;
            }

            if (ans == 1) {//select unit
                Player unit = selectPlayer(board);

                System.out.println("1.attack\n2.move");

                try {
                    ans = in.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Input mismatch");
                    in.next();
                    continue;
                }

                if (ans == 1 && unit instanceof Attackable) {//attack
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
     * selectPlayer
     * A helper method for {@code playerAction} method.
     * Select the player unit according to the coordinate on the board.
     *
     * @param board the game board
     * @return the selected player
     * @see Main#playerAction(GameObject[][], ArrayList)
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
     * Player will select a x and y coordinate to attack,
     * Player unit will attack the coordinate if the unit has not attacked previously in the same turn.
     *
     * @param board     the game board
     * @param player    the player attacking
     * @param enemyList the enemyList
     */
    public static void playerAttack(GameObject[][] board, Player player, ArrayList<Enemy> enemyList) {
        if (player.isAttacked()) {//if player has attacked already this round
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
                System.out.println("Player at " + player.getX() + " " + player.getY() + " attacks " + x + " " + y);
                if (object instanceof Damageable && player instanceof Attackable) {
                    ((Damageable) object).damageTaken(((Attackable) player).attack());
                    if (((Damageable) object).getHealth() <= 0) {//if object is destroyed or killed
                        board[y][x] = null;
                        System.out.println("Object at " + x + " " + y + " is destroyed or killed");
                        if (object instanceof Enemy) {//removes enemy from enemy list
                            enemyList.remove(object);
                        }
                    }
                }
                player.setAttacked(true);
                printBoard(board);
                return;//if object gets attacked
            }
        } while (true);
    }

    /**
     * playerMove
     * Moves the Player unit to the inputted coordinate.
     *
     * @param board the game board
     * @param unit  the unit to move
     */
    public static void playerMove(GameObject[][] board, Player unit) {
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
                printBoard(board);
                return;
            }
        } while (true);
    }

    /**
     * resetPlayers
     * Resets the moved and attacked boolean variables.
     * This method runs after the Player ends their turn.
     *
     * @param playerList the list of players
     */
    private static void resetPlayers(ArrayList<Player> playerList) {
        for (Player unit : playerList) {
            unit.setAttacked(false);
            unit.setMoved(false);
        }
    }

    /**
     * Places the player at a desired location.
     * Places the player on the board at a desired empty coordinate.
     * This method is only ran at the beginning of the program, when the game is setting up
     *
     * For player movement, visit {@code playerMove} method.
     *
     * @param board the board
     * @see Main#playerMove(GameObject[][], Player)
     */
    private static ArrayList<Player> placePlayer(GameObject[][] board, int playerNum) {
        Scanner in = new Scanner(System.in);
        boolean placed;
        ArrayList<Player> playerList = new ArrayList<>();
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
     * generateObstacles
     * Adds {@code Obstacle} to the game board.
     *
     * @param board          the board
     * @param obstructionNum the number of obstacles to add
     * @param vitalNum       the number of vitals to add
     * @see Obstacle
     */
    private static void generateObstacles(GameObject[][] board, int obstructionNum, int vitalNum) {
        Random random = new Random();
        int x, y;
        for (int i = 0; i < obstructionNum; i++) {
            do {
                x = random.nextInt(8);
                y = random.nextInt(8);
            } while (board[y][x] != null);
            board[y][x] = new Obstruction(x, y, 3);
        }

        for (int i = 0; i < vitalNum; i++) {
            do {
                x = random.nextInt(8);
                y = random.nextInt(8);
            } while (board[y][x] != null);
            board[y][x] = new Vital(x, y, 2);
        }
    }

    /**
     * generateEnemy
     * Adds {@code Enemy} to the game board. Additionally,
     * the method creates and returns an ArrayList containing the enemies generated.
     *
     * @param board    the board
     * @param enemyNum the number of enemies to be created
     * @return the ArrayList of enemies
     * @see Enemy
     */
    private static ArrayList<Enemy> generateEnemy(GameObject[][] board, int enemyNum) {
        Random random = new Random();
        ArrayList<Enemy> enemyList = new ArrayList<>();
        int x, y;
        for (int i = 0; i < enemyNum; i++) {
            do {
                x = random.nextInt(8);
                y = random.nextInt(8);
            } while (board[y][x] != null);
            Enemy warrior = new Warrior(x, y, 3, 1, 1);

            board[y][x] = warrior;
            enemyList.add(warrior);
        }
        return enemyList;
    }

    /**
     * printBoard
     * Prints the updated game board to the console.
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

    /**
     * waitSecond
     * Stops the program for a number of seconds
     *
     * @param second number of seconds
     */
    public static void waitSecond(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException ignored) {
        }
    }
}
