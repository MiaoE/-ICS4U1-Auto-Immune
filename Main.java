import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;//temporary input
import java.util.concurrent.TimeUnit;

/**
 * [Main.java]
 *
 * @author Eric Miao
 */
public class Main {
    public static GameObject[][] board;
    public static int size;

    public static void main(String[] argument) {
        ArrayList<Warrior> enemyList = createEnemyList(3);
        size = 8;
        board = new GameObject[size][size];
        generateMap();
        generateObstacles(board, 6);
        generateEnemies(board, enemyList);
        generateVitals(board, 2);

        printBoard(board);
        placePlayer(board);
        printBoard(board);

        gameLoop(board, enemyList);
        //new GameFrame();
    }

    public static void gameLoop(GameObject[][] board, ArrayList<Warrior> enemyList) {
        while (true) {
            for (Warrior warrior : enemyList) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException ignored) {
                }
                printBoard(board);
                enemyMove(board, warrior);
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
        return;
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
     * Places the player at a desired location
     *
     * @param board the board
     */
    private static void placePlayer(GameObject[][] board) {
        Scanner in = new Scanner(System.in);
        boolean placed = false;
        while (!placed) {
            int x = in.nextInt();
            int y = in.nextInt();
            if (x >= size || y >= size || x < 0 || y < 0) {
                System.out.println("Out of bound");
            } else if (board[y][x] != null) {
                System.out.println("Tile occupied");
            } else {
                board[y][x] = new Soldier(x, y, 3, 1, 1);
                placed = true;
            }
        }
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
