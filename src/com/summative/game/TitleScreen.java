package com.summative.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * [TitleScreen.java]
 * The title screen is the first screen the user will see, and it acts as the title screen.
 * The class contains a play button, which will execute the {@link GameScreen} class;
 * contains a tutorial image, and contains a quit button to exit the program.
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 4.3 2021/01/25
 */
public class TitleScreen extends ScreenAdapter {

    MyGame game;
    boolean instructionScreen = false;
    Texture title = new Texture(Gdx.files.internal("Title.png"));
    Texture tutorial = new Texture(Gdx.files.internal("Tutorial.png"));

    float pButtonX = Gdx.graphics.getWidth() * .1f;
    float iButtonX = Gdx.graphics.getWidth() * .4f;
    float qButtonX = Gdx.graphics.getWidth() * .75f;
    float buttonsY = Gdx.graphics.getHeight() * .2f;

    /**
     * TitleScreen Constructor
     * Contains an inherited Game object which is capable of switching the screen.
     *
     * @param game the Game object
     */
    public TitleScreen(MyGame game) {
        this.game = game;
    }

    /**
     * show
     * This method sets up a type of action listener while the game is displayed on screen.
     * It is ran only once when player opens the application or maximizes the application.
     *
     * @see TitleScreen#hide()
     */
    @Override
    public void show(){
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if(button == Buttons.LEFT) {
                    if(instructionScreen) {
                        instructionScreen = false;
                        return true;
                    }
                    if(Gdx.input.getX() <= pButtonX + 160 && Gdx.input.getX() >= pButtonX &&
                          Gdx.input.getY() <= Gdx.graphics.getHeight() - buttonsY+30 && Gdx.input.getY() >= Gdx.graphics.getHeight() - buttonsY+30 - 90) {
                        //System.out.println("Play Game");
                        game.setScreen(new GameScreen(game));
                    } else if (Gdx.input.getX() <= iButtonX + 310 && Gdx.input.getX() >= iButtonX &&
                          Gdx.input.getY() <= Gdx.graphics.getHeight() - buttonsY+30 && Gdx.input.getY() >= Gdx.graphics.getHeight() - buttonsY+30 - 90) {
                        //System.out.println("Show Tutorial");
                        instructionScreen = true;
                    } else if (Gdx.input.getX() <= qButtonX + 160 && Gdx.input.getX() >= qButtonX &&
                          Gdx.input.getY() <= Gdx.graphics.getHeight() - buttonsY+30 && Gdx.input.getY() >= Gdx.graphics.getHeight() - buttonsY+30 - 90) {
                        //System.out.println("Quit");
                        game.dispose();
                        System.exit(0);
                    }
                }
                return false;
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
        Gdx.gl.glClearColor(.2f, .8f, .2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.shapeRenderer.setAutoShapeType(true);
        game.shapeRenderer.begin();
        game.shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.setColor(Color.FOREST);
        game.shapeRenderer.rect(pButtonX, buttonsY-30, 160, 90);
        game.shapeRenderer.rect(iButtonX, buttonsY-30, 310, 90);
        game.shapeRenderer.rect(qButtonX, buttonsY-30, 160, 90);
        game.shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        game.shapeRenderer.setColor(Color.BLACK);
        game.shapeRenderer.rect(pButtonX, buttonsY-30, 160, 90);
        game.shapeRenderer.rect(iButtonX, buttonsY-30, 310, 90);
        game.shapeRenderer.rect(qButtonX, buttonsY-30, 160, 90);
        game.shapeRenderer.end();

        game.batch.begin();
        game.batch.draw(title, Gdx.graphics.getWidth() / 2f - 404, Gdx.graphics.getHeight() * 0.3f, title.getWidth() * 4, title.getHeight() * 4);
        game.batch.setColor(Color.WHITE);
        game.font.setColor(Color.WHITE);
        game.font.getData().setScale(4);
        game.font.draw(game.batch, "PLAY", pButtonX+10, buttonsY+40);
        game.font.draw(game.batch, "TUTORIAL", iButtonX+10, buttonsY+40);
        game.font.draw(game.batch, "QUIT", qButtonX+10, buttonsY+40);

        if(instructionScreen) {
            game.batch.draw(tutorial, Gdx.graphics.getWidth() / 2f - Gdx.graphics.getHeight() / 2f, 0, Gdx.graphics.getHeight(), Gdx.graphics.getHeight());
        }
        game.batch.end();
    }

    /**
     * hide
     * This method saves the computer memory by disabling action listener while the application is minimized.
     *
     * @see TitleScreen#show()
     */
    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}
