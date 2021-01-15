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
        ArrayList<SpawnTile> spawnList = new ArrayList<>();
        size = 8;
        board = new GameObject[size][size];

        //Creates map
        generateObstacles(6);
        generateKillTiles(4);
        enemyList = generateEnemy(3);
        vitalList = generateVitals(3);

        //Player places unit
        printBoard();
        playerList = placePlayer();
        printBoard();
        waitSecond(1);

        //Starts main program
        gameLoop(enemyList, playerList, vitalList, spawnList);
    }

    /**
     * gameLoop
     * The main loop of the game.
     *
     * @param enemyList  the arrayList of enemy
     * @param playerList the list of players
     * @param vitalList  the list of vitals
     * @param spawnList  the list of spawns
     */
    public static void gameLoop(ArrayList<Enemy> enemyList, ArrayList<Player> playerList, ArrayList<Vital> vitalList, ArrayList<SpawnTile> spawnList) {
        boolean win = true;
        for (int i = 0; i < 5; i++) {
            //Enemy's turn
            spawnEnemies(enemyList, spawnList);
            waitSecond(1);
            generateEnemySpawns(enemyList, spawnList);
            executeEnemyAttack(playerList, enemyList, vitalList);
            enemyAttack(enemyList, playerList, vitalList);
            printBoard();

            //win / lose condition checker
            if (playerList.isEmpty() || vitalList.isEmpty()) {
                win = false;
                break;
            }

            //Player's turn
            playerAction(enemyList, playerList, vitalList);
            resetPlayers(playerList);

        }
        if (win) {
            System.out.println("Winner!");
        } else {
            System.out.println("loser");
        }
    }

    /**
     * generateEnemySpawns
     * will choose spawn locations for new enemies
     * method will contain an algorithm to find the number of enemies to spawn
     * right now method will generate maxEnemyNum - number of enemies on board
     *
     * @param enemyList the list of enemies
     * @param spawnList the list of enemy spawns
     */
    private static void generateEnemySpawns(ArrayList<Enemy> enemyList, ArrayList<SpawnTile> spawnList) {
        Random rand = new Random();
        int maxEnemyNum = 4;
        int x, y;

        for (int i = 0; i < maxEnemyNum - enemyList.size(); i++) {
            do {
                x = rand.nextInt(8);
                y = rand.nextInt(8);
            } while (board[y][x] != null);
            SpawnTile temp = new SpawnTile(x, y, new EnemyWarrior(x, y, 3, 5, false, 3, 3));
            board[y][x] = temp;
            spawnList.add(temp);
        }
    }

    /**
     * spawnEnemies
     * spawns enemies onto the game board
     *
     * @param enemyList the list of enemies
     * @param spawnList the list of enemies to spawn
     */
    private static void spawnEnemies(ArrayList<Enemy> enemyList, ArrayList<SpawnTile> spawnList) {
        for (SpawnTile tile : spawnList) {
            board[tile.getY()][tile.getX()] = tile.getEnemy();
            enemyList.add(tile.getEnemy());
        }
        spawnList.clear();
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
        for (Enemy enemy : enemyList) {
            ArrayList<Point[]> options = new ArrayList<>();
            GameObject closestObject = null;
            for (Player player : playerList) {
                //checks if player is within range
                if (distance(player.getCoordinate(), enemy.getCoordinate()) <= enemy.getMovementRange()) {
                    options.add(enemyAttackable(player, enemy));
                }

                if (closestObject == null) {
                    closestObject = player;
                } else if (distance(player.getCoordinate(), enemy.getCoordinate()) < distance(closestObject.getCoordinate(), enemy.getCoordinate())) {
                    closestObject = player;
                }
            }
            for (Vital vital : vitalList) {
                if (distance(vital.getCoordinate(), enemy.getCoordinate()) <= enemy.getMovementRange()) {
                    options.add(enemyAttackable(vital, enemy));
                }

                if (closestObject == null) {
                    closestObject = vital;
                } else if (distance(vital.getCoordinate(), enemy.getCoordinate()) < distance(closestObject.getCoordinate(), enemy.getCoordinate())) {
                    closestObject = vital;
                }
            }

            if (options.size() != 0) {
                Random rand = new Random();
                Point[] option = options.get(rand.nextInt(options.size()));

                board[enemy.getY()][enemy.getX()] = null;//makes prev position null
                enemy.move(option[0].getX(), option[0].getY());//changes x and y
                board[option[0].getY()][option[0].getX()] = enemy;//changes position on the game board

                enemy.setAttack(option[1]);//changes attack X and Y
                System.out.println("enemy at " + enemy.getCoordinate().toString() + " will attack " + enemy.getAttack().toString());
            } else {
                Point moveTo = new Point((enemy.getX() + closestObject.getX()) / 2, (enemy.getY() + closestObject.getY()) / 2);
                if (distance(enemy.getCoordinate(), moveTo) < enemy.getMovementRange()) {
                    board[enemy.getY()][enemy.getX()] = null;//makes prev position null
                    enemy.move(moveTo.getX(), moveTo.getY());//changes x and y
                    board[moveTo.getY()][moveTo.getX()] = enemy;//changes position on the game board

                    enemy.setAttack(new Point(-1, -1));
                } else {

                }
            }
        }
    }

    private static double distance(Point a, Point b) {
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
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
        if (!enemy.getAttackRange()) {//melee attacker
            //for loop to check directly adjacent tiles instead of multiple if statements
            if ((xO + 1 < size) && (board[yO][xO + 1] == null)) {
                point[0] = new Point(xO + 1, yO);
                return point;
            } else if ((xO - 1 > -1) && (board[yO][xO - 1] == null)) {
                point[0] = new Point(xO - 1, yO);
                return point;
            } else if ((yO + 1 < size) && (board[yO + 1][xO] == null)) {
                point[0] = new Point(xO, yO + 1);
                return point;
            } else if ((yO - 1 > -1) && (board[yO - 1][xO] == null)) {
                point[0] = new Point(xO, yO - 1);
                return point;
            }
        } else {//ranged attacker
            for (int i = 0; i < size; i++) {
                if ((board[yO][i] == null)) {//horizontal axis
                    point[0] = new Point(i, yO);
                    return point;
                } else if ((board[i][xO] == null)) {//vertical axis
                    point[0] = new Point(xO, i);
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
    private static void executeEnemyAttack(ArrayList<Player> playerList, ArrayList<Enemy> enemyList, ArrayList<Vital> vitalList) {
        for (Enemy enemy : enemyList) {//for each loop is wack, if you remove something it breaks
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
                    } else if (object instanceof Enemy) {
                        enemyList.remove(object);
                    } else if (object instanceof Vital) {
                        vitalList.remove(object);
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
    public static void playerAction(ArrayList<Enemy> enemyList, ArrayList<Player> playerList, ArrayList<Vital> vitalList) {
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
                    playerAttack(unit, enemyList, playerList, vitalList);
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
     * @see Main#playerAction(ArrayList, ArrayList, ArrayList)
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
    private static void playerAttack(Player player, ArrayList<Enemy> enemyList, ArrayList<Player> playerList, ArrayList<Vital> vitalList) {
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
            } else if (x != player.getX() && y != player.getY()) {//if tile is not adjacent
                System.out.println("out of range");
            } else {//if in bounds
                //checks if attack position is within melee range
                //Why does this use !
                if (!player.getAttackRange()) {//melee
                    if (!((Math.abs(player.getX() - x)) <= 1 && Math.abs(player.getY() - y) <= 1)) {
                        System.out.println("out of range");
                        return;
                    }
                }

                GameObject object = board[y][x];
                System.out.println("Player at " + player.getX() + " " + player.getY() + " attacks " + x + " " + y);
                if (object instanceof Damageable && player instanceof Attackable) {
                    ((Damageable) object).damageTaken(((Attackable) player).attack());
                    if (object instanceof Movable) {
                        takeKnockback(player, object, enemyList, playerList);
                    }
                    if (((Damageable) object).getHealth() <= 0) {//if object is destroyed or killed
                        board[y][x] = null;
                        System.out.println("Object at " + x + " " + y + " is destroyed or killed");
                        if (object instanceof Enemy) {//removes enemy from enemy list
                            enemyList.remove(object);
                        } else if (object instanceof Player) {
                            playerList.remove(object);
                        } else if (object instanceof Vital) {
                            vitalList.remove(object);
                        }
                    }
                }
                player.setAttacked(true);
                printBoard();
                return;//if object gets attacked
            }
        } while (true);
    }

    /**
     * takeKnockback
     * Will move a unit int knockback units away from its original position
     * If the knocked back unit is an enemy, the location of its attack will change accordingly
     *
     * @param player     the object exerting the knockback
     * @param object     the object getting knocked back
     * @param enemyList  list of enemies
     * @param playerList list of players
     */
    private static void takeKnockback(Player player, GameObject object, ArrayList<Enemy> enemyList, ArrayList<Player> playerList) {
        //when this method is called the object are already adjacent to one another
        int attackerX = player.getX();//the object attacking
        int attackerY = player.getY();
        int attackeeX = object.getX();//the object getting attacked
        int attackeeY = object.getY();

        int direction;//if the movement is in a positive or negative direction
        boolean vertical;//if the movement is vertical

        if (attackerX == attackeeX && attackerY < attackeeY) {//DOWN
            direction = 1;
            vertical = true;
        } else if (attackerX == attackeeX && attackerY > attackeeY) {//UP
            direction = -1;
            vertical = true;
        } else if (attackerY == attackeeY && attackerX > attackeeX) {//LEFT
            direction = -1;
            vertical = false;
        } else {//RIGHT
            direction = 1;
            vertical = false;
        }

        int x, y;
        for (int i = 0; i <= player.getKnockback(); i++) {//loops 'knockback' amount of times
            if (vertical) {//vertical knockback
                x = attackeeX;
                y = attackeeY + (i * direction);
            } else {//horizontal knockback
                x = attackeeX + (i * direction);
                y = attackerY;
            }

            if (x >= size || y >= size || y < 0 || x < 0) {//checks if values are in bound
                break;
            }
            if (board[y][x] == null) {// if the tile can be moved on
                //moves the object onto that tile
                board[attackeeY][attackeeX] = null;
                ((Movable) object).move(x, y);
                board[y][x] = object;

                if (object instanceof Enemy && object instanceof Attackable) {//if object is an attackable enemy
                    if (((Enemy) object).getAttack().getX() != -1) {//if there is an attack
                        if (vertical) {//vertical push
                            ((Enemy) object).setAttack(new Point(((Enemy) object).getAttack().getX(), ((Enemy) object).getAttack().getY() + (i * direction)));
                        } else {//horizontal push
                            ((Enemy) object).setAttack(new Point(((Enemy) object).getAttack().getX() + (i * direction), ((Enemy) object).getAttack().getY()));
                        }
                    }
                }
            } else if (board[y][x] instanceof KillTile) {
                if (object instanceof Enemy) {
                    enemyList.remove(object);//remove object from list
                } else if (object instanceof Player) {
                    playerList.remove(object);//remove object from list
                }
                board[object.getY()][object.getX()] = null;//remove object from board
                System.out.println("object at " + attackeeX + " " + attackeeY + " is killed by kill tile at " + x + " " + y);
            }
        }

        if (object instanceof Enemy) {//prints out new position and attack position
            System.out.println("object at " + object.getX() + " " + object.getY() + " will attack at " + ((Enemy) object).getAttack().getX() + " " + ((Enemy) object).getAttack().getY());
        }

//        //this can definitely be done more efficiently
//        //Haven't tested it out but I am pretty sure that this method will result in error if an enemies attack is pushed out of bounds
//        //Perhaps one for loop then you change the values of i depend on the direct pushed
//        if (attackerX == attackeeX && attackerY < attackeeY) {//DOWN
//
//            for (int i = attackeeY; i <= attackeeY + player.getKnockback(); i++) {
//                if (i > size - 1) {//checks if out of bounds
//                    break;
//                }
//                if (board[i][attackeeX] == null) {//if the tile can be moved on
//                    board[attackeeY][attackeeX] = null;
//                    ((Movable) object).move(attackeeX, i);
//                    board[i][attackeeX] = object;
//
//                    if (object instanceof Enemy && object instanceof Attackable) {//if object is an attackable enemy
//                        if (((Enemy) object).getAttack().getX() != -1) {//if there is an attack
//                            ((Enemy) object).setAttack(new Point(((Enemy) object).getAttack().getX(), ((Enemy) object).getAttack().getY() + (i - attackeeY)));
//                        }
//                    }
//                } else if (board[i][attackeeX] instanceof KillTile) {
//                    if (object instanceof Enemy) {
//                        enemyList.remove(object);//remove object from list
//                    } else if (object instanceof Player) {
//                        playerList.remove(object);//remove object from list
//                    }
//                    board[object.getY()][object.getX()] = null;//remove object from board
//                }
//            }
//
//        } else if (attackerX == attackeeX && attackerY > attackeeY) {//UP
//
//            int num = -1;
//
//            for (int i = attackeeY; i >= attackeeY - player.getKnockback(); i--) {
//                if (i < 0) {
//                    break;
//                }
//                if (board[i][attackeeX] == null) {
//                    board[attackeeY][attackeeX] = null;
//                    ((Movable) object).move(attackeeX, i);
//                    board[i][attackeeX] = object;
//
//                    if (object instanceof Enemy && object instanceof Attackable) {
//                        if (((Enemy) object).getAttack().getX() != -1) {
//                            ((Enemy) object).setAttack(new Point(((Enemy) object).getAttack().getX(), ((Enemy) object).getAttack().getY() - (i * -1 + attackeeY)));
//                        }
//                    }
//                } else if (board[i][attackeeX] instanceof KillTile) {
//                    if (object instanceof Enemy) {
//                        enemyList.remove(object);//remove object from list
//                    } else if (object instanceof Player) {
//                        playerList.remove(object);//remove object from list
//                    }
//                    board[object.getY()][object.getX()] = null;//remove object from board
//                }
//            }
//
//        } else if (attackerY == attackeeY && attackerX > attackeeX) {//LEFT
//
//            for (int i = attackeeX; i >= attackeeX - player.getKnockback(); i--) {
//                if (i < 0) {
//                    break;
//                }
//                if (board[attackeeY][i] == null) {
//                    board[attackeeY][attackeeX] = null;
//                    ((Movable) object).move(i, attackerY);
//                    board[attackeeY][i] = object;
//
//                    if (object instanceof Enemy && object instanceof Attackable) {
//                        if (((Enemy) object).getAttack().getX() != -1) {
//                            ((Enemy) object).setAttack(new Point(((Enemy) object).getAttack().getX() - (i * -1 + attackeeX), ((Enemy) object).getAttack().getY()));
//                        }
//                    }
//                } else if (board[attackeeY][i] instanceof KillTile) {
//                    if (object instanceof Enemy) {
//                        enemyList.remove(object);//remove object from list
//                    } else if (object instanceof Player) {
//                        playerList.remove(object);//remove object from list
//                    }
//                    board[object.getY()][object.getX()] = null;//remove object from board
//                }
//            }
//
//        } else if (attackeeY == attackerY && attackerX < attackeeX) {//RIGHT
//
//            for (int i = attackeeX; i <= attackeeX + player.getKnockback(); i++) {
//                if (i > size - 1) {
//                    break;
//                }
//                if (board[attackeeY][i] == null) {
//                    board[attackeeY][attackeeX] = null;
//                    ((Movable) object).move(i, attackerY);
//                    board[attackeeY][i] = object;
//
//                    if (object instanceof Enemy && object instanceof Attackable) {
//                        if (((Enemy) object).getAttack().getX() != -1) {
//                            ((Enemy) object).setAttack(new Point(((Enemy) object).getAttack().getX() + (i - attackeeX), ((Enemy) object).getAttack().getY()));
//                        }
//                    }
//                } else if (board[attackeeY][i] instanceof KillTile) {
//                    if (object instanceof Enemy) {
//                        enemyList.remove(object);//remove object from list
//                    } else if (object instanceof Player) {
//                        playerList.remove(object);//remove object from list
//                    }
//                    board[object.getY()][object.getX()] = null;//remove object from board
//                }
//            }
//        }
//        if (object instanceof Enemy) {//prints out new position and attack position
//            System.out.println("object at " + object.getX() + " " + object.getY() + " will attack at " + ((Enemy) object).getAttack().getX() + " " + ((Enemy) object).getAttack().getY());
//        }
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
                        PlayerWarrior unit = new PlayerWarrior(x, y, 3, 6, false, 2, 1);
                        board[y][x] = unit;
                        playerList.add(unit);
                        placed = true;
                    } else if (i == 1) {
                        PlayerArtillery unit = new PlayerArtillery(x, y, 2, 7, true, 1, 1);
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
     * generateKillTiles
     * Adds {@code KillTile} to the game board.
     *
     * @param killTileNum the number of kill tiles to generate
     * @see Obstacle
     */
    private static void generateKillTiles(int killTileNum) {
        Random random = new Random();
        int x, y;
        for (int i = 0; i < killTileNum; i++) {
            do {
                x = random.nextInt(8);
                y = random.nextInt(8);
            } while (board[y][x] != null);
            board[y][x] = new KillTile(x, y);
        }
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
            if (i == 2) {
                Enemy temp = new EnemyArtillery(x, y, 2, 4, true, 1, 1);
                enemyList.add(temp);
                board[y][x] = temp;
            } else { //--------end
                Enemy warrior = new EnemyWarrior(x, y, 3, 4, false, 1, 2);
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
                } else if (index instanceof EnemyArtillery) {
                    System.out.printf("%s ", "e");
                } else if (index instanceof Obstruction) {//obstacle
                    System.out.printf("%s ", "O");
                } else if (index instanceof Vital) {//vital
                    System.out.printf("%s ", "V");
                } else if (index instanceof SpawnTile) {
                    System.out.printf("%s ", "S");
                } else if (index instanceof Hole) {
                    System.out.printf("%s ", "H");
                } else if (index instanceof KillTile) {
                    System.out.printf("%s ", "K");
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
