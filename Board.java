package com.summative.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Board {

    private boolean[][] board;
    //private Texture sprite;

    public Board() {//GameObject[][] board) {
        //this.board = board;
    }

    public void draw(SpriteBatch batch, int tileSize) {
        batch.end();
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin();
        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Vector2[] points = {cartToIso(new Vector2(j * tileSize, i * tileSize)),
                        cartToIso(new Vector2(j * tileSize + tileSize, i * tileSize)),
                        cartToIso(new Vector2(j * tileSize + tileSize, i * tileSize + tileSize)),
                        cartToIso(new Vector2(j * tileSize, i * tileSize + tileSize))};
                float[] vertices = {points[0].x + 50, points[0].y + 350, points[1].x + 50, points[1].y + 350,
                        points[2].x + 50, points[2].y + 350, points[3].x + 50, points[3].y + 350};
                shapeRenderer.setColor(0, 0, 0, 1);
                shapeRenderer.polygon(vertices);

                /*shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.rect(j*tileSize, i*tileSize, tileSize, tileSize, Color.BLUE, Color.LIME, Color.RED, Color.CYAN);*/
            }
        }


        shapeRenderer.end();
        batch.begin();
    }

    public void render(int tileSize){
        //for(int i = 0; i < board.length; i++)

    }

    public Vector2 cartToIso(Vector2 cart) {
        return new Vector2(cart.x + cart.y, (cart.y - cart.x) / 2);
    }

}
