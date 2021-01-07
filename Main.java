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
 * <p>
 * Each unit has their own abilities and characteristics.
 * For instance, some units can move far but deals minimal damage while some other units can only move
 * one tile but deals a lot of damage. Furthermore, some units can heal their allies or perform their unique ability.
 * <p>
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
        waitSecond(1);

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
        do{
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
        }while(!playerList.isEmpty() || !vitalList.isEmpty());
    }

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
        int[] tempArr;
        Random rand = new Random();
        ArrayList<int[]> options;

        for (Enemy enemy : enemyList) {
            options = new ArrayList<>();
            for (Player player : playerList) {
                tempArr = enemyAttackable(player, enemy);
                if (tempArr != null) {
                    options.add(tempArr);
                }
            }
            for (Vital vital : vitalList) {
                tempArr = enemyAttackable(vital, enemy);
                if (tempArr != null) {
                    options.add(tempArr);
                }
            }
            //if enemy has 0 attack options it will result in an error (can't 0 bound random)
            //must make option for where enemy has no options
            //if this is the case enemy should move towards the player of vitals
            //for now if statement to check
            //if no options enemy will stay still
            if(options.size() != 0) {
                tempArr = options.get(rand.nextInt(options.size()));

                board[enemy.getY()][enemy.getX()] = null;//makes prev position null
                enemy.move(tempArr[0], tempArr[1]);//changes x and y
                board[tempArr[1]][tempArr[0]] = enemy;//changes position on the game board

                enemy.setAttackX(tempArr[2]);//changes attack X and Y
                enemy.setAttackY(tempArr[3]);
                System.out.println("enemy at " + enemy.getX() + " " + enemy.getY() + " will attack " + enemy.getAttackX() + " " + enemy.getAttackY());
            }
        }
    }

    /**
     * enemyAttackable
     * this method determines whether the inputted enemy can attack the inputted object
     * if not then the method returns null
     * error: can go out of bounds for melee units
     *
     * @param object the object getting attacked
     * @param enemy  the attacker
     * @return a size 4 array containing where the enemy will move and attack
     */
    private static int[] enemyAttackable(GameObject object, Enemy enemy) {
        int[] point = new int[4];

        int xTwo = enemy.getX();
        int yTwo = enemy.getY();
        int x = object.getX();
        int y = object.getY();

        point[2] = x;
        point[3] = y;
        if (enemy.getAttackRange() == 1) {//melee attacker
            //for loop to check directly adjacent tiles instead of multiple if statements
            if ((x + 1 < size) && (board[y][x+1] == null) && Math.sqrt(Math.pow((x + 1) - xTwo, 2) + Math.pow(y - yTwo, 2)) <= enemy.getMovementRange()) {
                point[0] = x + 1;
                point[1] = y;
                return point;
            } else if ((x - 1 > -1) && (board[y][x-1] == null) && Math.sqrt(Math.pow((x - 1) - xTwo, 2) + Math.pow(y - yTwo, 2)) <= enemy.getMovementRange()) {
                point[0] = x - 1;
                point[1] = y;
                return point;
            } else if ((y + 1 < size) && (board[y+1][x] == null) && Math.sqrt(Math.pow((x) - xTwo, 2) + Math.pow((y + 1) - yTwo, 2)) <= enemy.getMovementRange()) {
                point[0] = x;
                point[1] = y + 1;
                return point;
            } else if ((y - 1 > -1) && (board[y-1][x] == null) && Math.sqrt(Math.pow((x) - xTwo, 2) + Math.pow((y - 1) - yTwo, 2)) <= enemy.getMovementRange()) {
                point[0] = x;
                point[1] = y - 1;
                return point;
            }
        } else {//ranged attacker
            for (int i = 0; i < size; i++) {
                if ((board[y][i] == null) && Math.sqrt(Math.pow((i) - xTwo, 2) + Math.pow((y), 2)) <= enemy.getMovementRange()) {//horizontal axis
                    point[0] = i;
                    point[1] = y;
                    return point;
                } else if ((board[i][x] == null) && Math.sqrt(Math.pow((x), 2) + Math.pow((i) - yTwo, 2)) <= enemy.getMovementRange()) {//vertical axis
                    point[0] = x;
                    point[1] = i;
                    return point;
                }
            }
        }
        return null;//if enemy can't attack the object
    }

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
                unit.setX(x);
                unit.setY(y);
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
     * Adds {@code Obstacle} to the game board.
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
     * Adds {@code Enemy} to the game board. Additionally,
     * the method creates and returns an ArrayList containing the enemies generated.
     *
     * @param enemyNum the number of enemies to be created
     * @return the ArrayList of enemies
     * @see Enemy
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
            if(i == 2){
                Enemy temp = new EnemyArtillery(x, y, 2, 4, 2, 1);
                enemyList.add(temp);
                board[y][x] = temp;
            }else{ //--------end
                Enemy warrior = new EnemyWarrior(x, y, 3, 4, 1, 1);
                board[y][x] = warrior;
                enemyList.add(warrior);
            }
        }
        return enemyList;
    }

    /**
     * printBoard
     * Prints the updated game board to the console.
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
                } else if (index instanceof EnemyArtillery){
                    System.out.printf("%s ", "e");
                }else if (index instanceof Obstruction) {//obstacle
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
