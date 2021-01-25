package com.summative.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * [EnemyDestructor.java]
 * An object representing an destructor unit of the {@code Enemy} side.
 * Destructor unit will create holes on the tiles it attacks
 *
 * @author Ayden Gao
 * @author Eric Miao
 * @version 4.3 2021/01/25
 */
public class EnemyDestructor extends Enemy implements Attackable{

    /**
     * EnemyDestructor Constructor
     * Contains all the essential information to create a unit.
     * @param x the x coordinate
     * @param y the y coordinate
     * @param health health of the unit
     * @param movementRange movement range
     * @param attackRange attack range
     * @param weight weight
     * @param attackDamage the attack damage
     */
    EnemyDestructor(int x, int y, int health, double movementRange, boolean attackRange, int weight, int attackDamage){
        super(x, y, new Texture(Gdx.files.internal("EnemyDestructor.png")), health, movementRange, attackRange, weight, attackDamage);
    }
}
