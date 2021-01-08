import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;//temporary input
import java.util.concurrent.TimeUnit;
import java.util.InputMismatchException;
import java.lang.Math;

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
 * @version 2.0 21/01/07
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
        ArrayList<Vital> vitalList;
        size = 8;
        board = new GameObject[size][size];

        //Creates map
        generateObstacles(6);
        enemyList = generateEnemy(3);
        vitalList = generateVitals(3);

        //Player places unit
        printBoard();
        playerList = placePlayer();
        printBoard();

        //Starts main program
        gameLoop(enemyList, playerList, vitalList);
    }

    /**
     * gameLoop
     * The main loop of the game.
     *
     * @param enemyList the arrayList of enemy
     */
    public static void gameLoop(ArrayList<Enemy> enemyList, ArrayList<Player> playerList, ArrayList<Vital> vitalList) {
        do {
            //Enemy's turn
            executeEnemyAttack(playerList, enemyList);
            //enemyMove(enemyList);
            //enemyAttack(enemyList);
            enemyAttack(enemyList, playerList, vitalList);
            printBoard();
            //certain condition: running = false;

            //Player's turn
            playerAction(enemyList);
            resetPlayers(playerList);
        } while (!playerList.isEmpty() || !vitalList.isEmpty());
    }

    //working on this section
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * enemyAttack
     * creates an array list containing possible moves for the enemy
     * selects an option from the list and executes the move
     *
     * @param enemyList  list of enemies
     * @param playerList list of players
     * @param vitalList  list of vitals
     */
    private static void enemyAttack(ArrayList<Enemy> enemyList, ArrayList<Player> playerList, ArrayList<Vital> vitalList) {
        Point[] option;
        Random rand = new Random();
        ArrayList<Point[]> options;

        for (Enemy enemy : enemyList) {
            options = new ArrayList<>();
            for (Player player : playerList) {
                //checks if player is within range
                if(Math.sqrt(Math.pow(player.getX()-enemy.getX(), 2) + Math.pow(player.getY()-enemy.getY(), 2)) <= enemy.getMovementRange()) {
                    options.add(enemyAttackable(player, enemy));
                }
            }
            for (Vital vital : vitalList) {
                if(Math.sqrt(Math.pow(vital.getX()-enemy.getX(), 2) + Math.pow(vital.getY()-enemy.getY(), 2)) <= enemy.getMovementRange()) {
                    options.add(enemyAttackable(vital, enemy));
                }
            }
            //if enemy has 0 attack options it will result in an error (can't 0 bound random)
            //must make option for where enemy has no options
            //if this is the case enemy should move towards the player of vitals
            //for now if statement to check
            //if no options enemy will stay still
            if (options.size() != 0) {
                option = options.get(rand.nextInt(options.size()));

                board[enemy.getY()][enemy.getX()] = null;//makes prev position null
                enemy.move(option[0].getX(), option[0].getY());//changes x and y
                board[option[0].getY()][option[0].getX()] = enemy;//changes position on the game board

                enemy.setAttack(option[1]);//changes attack X and Y
                System.out.println("enemy at " + enemy.getCoordinate().toString() + " will attack " + enemy.getAttack().toString());
            }
        }
    }

    /**
     * enemyAttackable
     * This method determines whether the inputted enemy can attack the inputted object
     * if not then the method returns null
     * error: can go out of bounds for melee units
     *
     * @param object the object getting attacked
     * @param enemy  the attacker
     * @return a size 2 Point array, [0] move location, [1] attack location
     */
    private static Point[] enemyAttackable(GameObject object, Enemy enemy) {
        Point[] point = new Point[2];

        int xE = enemy.getX();
        int yE = enemy.getY();
        Point enemyCord = enemy.getCoordinate();
        Point objectCord = object.getCoordinate();
        int xO = object.getX();
        int yO = object.getY();

        point[1] = object.getCoordinate();
        if (enemy.getAttackRange() == 1) {//melee attacker
            //for loop to check directly adjacent tiles instead of multiple if statements
            if ((xO + 1 < size) && (board[yO][xO + 1] == null)) {
                point[0] = xO + 1;
                point[1] = yO;
                return point;
            } else if ((xO - 1 > -1) && (board[yO][xO - 1] == null)) {
                point[0] = xO - 1;
                point[1] = yO;
                return point;
            } else if ((yO + 1 < size) && (board[yO + 1][xO] == null)) {
                point[0] = xO;
                point[1] = yO + 1;
                return point;
            } else if ((yO - 1 > -1) && (board[yO - 1][xO] == null)) {
                point[0] = xO;
                point[1] = yO - 1;
                return point;
            }
        } else {//ranged attacker
            for (int i = 0; i < size; i++) {
                if ((board[yO][i] == null)) {//horizontal axis
                    point[0] = i;
                    point[1] = yO;
                    return point;
                } else if ((board[i][xO] == null)) {//vertical axis
                    point[0] = xO;
                    point[1] = i;
                    return point;
                }
            }
        }
        return null;//if enemy can't attack the object
    }


    private static Point getShortestdistance() {}

    /**
     * inBound
     * Helper method for {@code enemyAttackable} method.
     * Checks if a set of coordinate is within the board.
     *
     * @param point the coordinate to check
     * @return true if the coordinates are within boundary, false otherwise
     */
    private static boolean inBound(Point point) {
        return (point.getX() >= 0 && point.getY() >= 0 && point.getX() < size && point.getY() < size);
    }

    //------------------------------------------------------------------------^^^^-------------------------------------------------------------------------------------

    /**
     * executeEnemyAttack
     * Executes the attack of the selected enemy.
     * This method should include enemy list as enemies will be pushed.
     * Enemies won't target themselves and their allies but they can be damaged if pushed by player.
     *
     * @param playerList list of players
     * @param enemyList  list of enemies
     */
    private static void executeEnemyAttack(ArrayList<Player> playerList, ArrayList<Enemy> enemyList) {
        for (Enemy enemy : enemyList) {
            Point attack = enemy.getAttack();

            if (attack == null) {//enemy doesn't attack
                continue;//goes to the next enemy
            }
            System.out.println("Enemy " + enemy.getCoordinate().toString() + " attacks " + attack.toString());
            GameObject object = board[attack.getY()][attack.getX()];
            if (object instanceof Damageable && enemy instanceof Attackable) {
                ((Damageable) object).damageTaken(((Attackable) enemy).attack());//takes damage

                //removes the object from board if health <= 0
                if (((Damageable) object).getHealth() <= 0) {//if object is destroyed or killed
                    board[attack.getY()][attack.getX()] = null;
                    System.out.println("Object at " + object.getCoordinate().toString() + " is obliterated");
                    if (object instanceof Player) {
                        playerList.remove(object);
                    }
                }
            }
        }
    }

    /**
     * playerAction
     * Allows players to select a unit and choose a specified action for that unit.
     * Player's turn will not end until the player insists on ending their turn.
     *
     * @param enemyList the enemy list
     */
    public static void playerAction(ArrayList<Enemy> enemyList) {
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
                Player unit = selectPlayer();

                System.out.println("1.attack\n2.move");

                try {
                    ans = in.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Input mismatch");
                    in.next();
                    continue;
                }

                if (ans == 1 && unit instanceof Attackable) {//attack
                    playerAttack(unit, enemyList);
                } else if (ans == 2) {//move
                    playerMove(unit);
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
     * @return the selected player
     * @see Main#playerAction(ArrayList)
     */
    private static Player selectPlayer() {
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
     * @param player    the player attacking
     * @param enemyList the enemyList
     */
    private static void playerAttack(Player player, ArrayList<Enemy> enemyList) {
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
            } else if (x != player.getX() && y != player.getY()) {
                System.out.println("out of range");
            } else {//if in bounds
                int attackRange = player.getAttackRange();

                if (attackRange == 1) {//melee
                    if (!(Math.abs(player.getX() - x) <= attackRange && Math.abs(player.getY() - y) <= attackRange)) {
                        System.out.println("out of range");
                        return;
                    }
                }

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

                    player.setAttacked(true);
                    printBoard();
                    return;//if object gets attacked
                }
            }
        } while (true);
    }

    /**
     * playerMove
     * Moves the Player unit to the inputted coordinate.
     *
     * @param unit the unit to move
     */
    private static void playerMove(Player unit) {
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
            } else if (Math.sqrt(Math.pow(x - unit.getX(), 2) + Math.pow(y - unit.getY(), 2)) > unit.getMovementRange()) {
                System.out.println("Out of movement range");
            } else {
                board[unit.getY()][unit.getX()] = null;
                board[y][x] = unit;
                unit.move(x, y);
                unit.setMoved(true);
                printBoard();
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
     * <p>
     * For player movement, visit {@code playerMove} method.
     *
     * @see Main#playerMove(Player)
     */
    private static ArrayList<Player> placePlayer() {
        Scanner in = new Scanner(System.in);
        boolean placed;
        ArrayList<Player> playerList = new ArrayList<>();
        //change the 1 to 2
        for (int i = 0; i < 2; i++) {//creates each player
            placed = false;
            while (!placed) {
                int x = in.nextInt();
                int y = in.nextInt();
                if (x >= size || y >= size || x < 0 || y < 0) {
                    System.out.println("Out of bound");
                } else if (board[y][x] != null) {
                    System.out.println("Tile occupied");
                } else {
                    if (i == 0) {
                        PlayerWarrior unit = new PlayerWarrior(x, y, 3, 6, 1, 10);
                        board[y][x] = unit;
                        playerList.add(unit);
                        placed = true;
                    } else if (i == 1) {
                        PlayerArtillery unit = new PlayerArtillery(x, y, 2, 7, 2, 10);
                        board[y][x] = unit;
                        playerList.add(unit);
                        placed = true;
                    }
                }
            }
        }
        return playerList;
    }

    /**
     * generateObstacles
     * Adds subclasses of {@code Obstacle} class to the game board.
     *
     * @param obstructionNum the number of obstacles to add
     * @see Obstacle
     */
    private static void generateObstacles(int obstructionNum) {
        Random random = new Random();
        int x, y;
        for (int i = 0; i < obstructionNum; i++) {
            do {
                x = random.nextInt(8);
                y = random.nextInt(8);
            } while (board[y][x] != null);
            board[y][x] = new Obstruction(x, y, 3);
        }

    }

    /**
     * generateVitals
     * Adds {@code Vital} object to the game board.
     * The method creates and returns an ArrayList containing the vitals.
     *
     * @param vitalNum the number of vitals to be created
     * @return the ArrayList of vitals
     */
    private static ArrayList<Vital> generateVitals(int vitalNum) {
        Random random = new Random();
        int x, y;
        ArrayList<Vital> vitalList = new ArrayList<>();
        for (int i = 0; i < vitalNum; i++) {
            do {
                x = random.nextInt(8);
                y = random.nextInt(8);
            } while (board[y][x] != null);
            Vital vital = new Vital(x, y, 2);
            board[y][x] = vital;
            vitalList.add(vital);
        }
        return vitalList;
    }

    /**
     * generateEnemy
     * Adds units from {@code Enemy} class to the game board.
     * The method creates and returns an ArrayList containing the enemy units.
     *
     * @param enemyNum the number of enemies to be created
     * @return the ArrayList of enemies
     */
    private static ArrayList<Enemy> generateEnemy(int enemyNum) {
        Random random = new Random();
        ArrayList<Enemy> enemyList = new ArrayList<>();
        int x, y;
        for (int i = 0; i < enemyNum; i++) {
            do {
                x = random.nextInt(8);
                y = random.nextInt(8);
            } while (board[y][x] != null);
            //TEMPORARY, this is just to add an artillery enemy
            if (i == 2) {
                Enemy artillery = new EnemyArtillery(x, y, 2, 4, 2, 1, 2);
                board[y][x] = artillery;
                enemyList.add(artillery);
            } else {
                Enemy warrior = new EnemyWarrior(x, y, 3, 4, 1, 1, 1);
                board[y][x] = warrior;
                enemyList.add(warrior);
            }
        }
        return enemyList;
    }

    /**
     * printBoard
     * Prints the game board to the console.
     * Temporary method for debugging......
     */
    public static void printBoard() {
        for (GameObject[] row : board) {
            for (GameObject index : row) {
                if (index == null) {//grass
                    System.out.printf("%s ", "-");
                } else if (index instanceof PlayerWarrior) {
                    System.out.printf("%s ", "W");
                } else if (index instanceof PlayerArtillery) {
                    System.out.printf("%s ", "A");
                } else if (index instanceof EnemyWarrior) {
                    System.out.printf("%s ", "E");
                } else if (index instanceof EnemyArtillery) {
                    System.out.printf("%s ", "e");
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
