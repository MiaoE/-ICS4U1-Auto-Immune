package com.summative.game;

import com.badlogic.gdx.ScreenAdapter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

/**
 * [EndScreen.java]
 * The end screen is the screen that appears after {@link GameScreen}
 * It summarizes the details and shows the statistics of the performance of the last game.
 * It also shows whether the user won the game or lost the game.
 * When left mouse button is pressed anywhere, {@link MyGame} returns the screen to {@link TitleScreen}
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 4.3 2021/01/25
 */
public class EndScreen extends ScreenAdapter {

    MyGame game;
    boolean win;
    String stat;
    Texture winner = new Texture(Gdx.files.internal("Winner.png"));
    Texture loser = new Texture(Gdx.files.internal("Loser.png"));

    /**
     * EndScreen Constructor
     * Contains all the information needed to display the statistics.
     *
     * @param game         the Game object
     * @param win          whether the user won the game or not
     * @param enemyKilled  the number of enemy units killed
     * @param vitalsAlive  the number of vitals alive
     * @param playersAlive the number of players alive
     */
    public EndScreen(MyGame game, boolean win, int enemyKilled, int vitalsAlive, int playersAlive) {
        this.game = game;
        this.win = win;

        stat = "Game Stat:\nNumber of Enemies Killed: " + enemyKilled + "\nNumber of Vitals Alive: " + vitalsAlive + "\nActive Player Units: " + playersAlive;
    }

    /**
     * show
     * This method sets up a type of action listener while the game is displayed on screen.
     * It is ran only once when player opens the application or maximizes the application.
     *
     * @see EndScreen#hide()
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (button == Buttons.LEFT) {
                    game.setScreen(new TitleScreen(game));
                }
                return true;
            }
        });
    }

    /**
     * render
     * This method is the pivot to drawing objects on the screen. This method is constantly running every tick.
     *
     * @param delta the serial number of the screen
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.25f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        if (win) {
            game.batch.draw(winner, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - 150);
        } else {
            game.batch.draw(loser, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() - 150);
        }

        game.font.draw(game.batch, stat, Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .5f);
        game.font.draw(game.batch, "Click anywhere to continue", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .25f);
        game.batch.end();
    }

    /**
     * hide
     * This method saves the computer memory by disabling action listener while the application is minimized.
     *
     * @see EndScreen#show()
     */
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}
