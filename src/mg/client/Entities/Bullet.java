/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.client.Entities;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Vitor
 */
public class Bullet {

    @SuppressWarnings("Convert2Diamond")
    private static final ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    private static Animation MAGE_ABILLITY_S;
    private static Animation MAGE_ABILLITY_D;
    private static Animation FIGHTER_ABILLITY_D;

    private static final int MAX_LIFE_TIME = 2000;
    public static final int TYPE_MAGE_ABILLITY_S = 0;
    public static final int TYPE_MAGE_ABILLITY_D = 1;
    public static final int TYPE_FIGHTER_ABILLITY_D = 2;

    
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    
    private final Animation animation;
    private final Vector2f pos;
    private final Vector2f speed;
    private final int type;
    private int lifeTime;
    private static int[] damage;

    static {
        try {
            Image spritesheet = new Image("/resources/character/bullet.png");
            MAGE_ABILLITY_S = new Animation(new SpriteSheet(spritesheet.getSubImage(0, 0, 342, 38), 57, 38), 200);
            MAGE_ABILLITY_D = new Animation(new SpriteSheet(spritesheet.getSubImage(0, 38, 854, 130), 122, 130), 200);
            FIGHTER_ABILLITY_D = new Animation(new SpriteSheet(spritesheet.getSubImage(0, 168, 549, 69), 61, 69), 200);
            damage = new int[]{5,20,20};
        } catch (SlickException ex) {
            Logger.getLogger(Bullet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Bullet(Vector2f pos, int type, int side, Player owner) {
        this.pos = pos;

        this.type = type;
        this.lifeTime = 0;

        switch (type) {
            case TYPE_MAGE_ABILLITY_S:
                animation = MAGE_ABILLITY_S;
                break;
            case TYPE_MAGE_ABILLITY_D:
                animation = MAGE_ABILLITY_D;
                break;
            case TYPE_FIGHTER_ABILLITY_D:
                animation = FIGHTER_ABILLITY_D;
                break;
            default:
                animation = null;
        }

        switch (side) {
            case LEFT:
                this.speed = new Vector2f(-250, 0);
                break;
            case RIGHT:
                this.speed = new Vector2f(250, 0);
                break;
            default:
                this.speed = new Vector2f(-1, 0);
        }

    }

    public static void addBullet(Bullet b) {
        bullets.add(b);
    }

    public static ArrayList<Bullet> getBulletList() {
        return (ArrayList<Bullet>) bullets.clone();
    }

    public void update(int delta) {
        if (lifeTime >= MAX_LIFE_TIME) {
            bullets.remove(this);
        } else {
            if (type != TYPE_MAGE_ABILLITY_D) {
                pos.add(speed.copy().scale(delta / 1000.0f));
            }

            lifeTime += delta;
        }

        animation.update(delta);
    }

    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        animation.draw(pos.getX() - 10, pos.getY() - 10, animation.getWidth(), animation.getHeight());
    }
    
    public void collision(Player e){
        Rectangle enemy = new Rectangle(e.getPosx(), e.getPosy(), e.getWidth(),e.getHeight());
        Rectangle bullet = new Rectangle(pos.getX(), pos.getY(), animation.getWidth(), animation.getHeight());
       
       if(bullet.intersects(enemy)){
            e.removeHP(damage[type]);
       }     
    }

}
