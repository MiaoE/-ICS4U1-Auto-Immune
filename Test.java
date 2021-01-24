package com.summative.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Test extends ApplicationAdapter{
    SpriteBatch batch;
    Texture texture;

    @Override
    public void create() {
        batch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("FleshTile-export.png"));
        System.out.println(texture.getWidth());
        System.out.println(texture.getHeight());
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(.25f, .25f, .25f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
//        for(int i = 0; i < 8; i++){
//            for(int j = 0; j < 8; j++){
//                batch.draw(texture, 0, 0);
//            }
//        }
        draw(batch);
        batch.end();

    }

    private void draw(SpriteBatch batch){
        batch.draw(new Texture("Vital.png"), 0, 0);
    }

    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
    }
}
