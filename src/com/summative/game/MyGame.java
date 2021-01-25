package com.summative.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * [MyGame.java]
 * The main program responsible for switching screens from Title -> Game -> End.
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 4.3 2021/01/25
 */
public class MyGame extends Game {
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    BitmapFont font;

    /**
     * create
     * Creates necessary tools for drawing on the screen.
     */
    @Override
    public void create () {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        setScreen(new TitleScreen(this));
    }

    /**
     * dispose
     * Disposes the drawing tools.
     */
    @Override
    public void dispose () {
        batch.dispose();
        shapeRenderer.dispose();
        font.dispose();
    }
}
