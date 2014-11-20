/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.client.Entities;

import java.util.logging.Level;
import java.util.logging.Logger;
import static mg.client.Entities.Player.ABILLITY_A;
import static mg.client.Entities.Player.ATTACK_RIGHT;
import static mg.client.Entities.Player.IDDLE;
import static mg.client.Entities.Player.MOVE_LEFT;
import static mg.client.Entities.Player.MOVE_RIGHT;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author Vitor
 */
public class Fighter extends Player {

    public static final Image spriteSheet;
    public static final int[] manaCosts;
    private static final Animation[] animationList;

    private static final int BASE_LIFE = 500;
    private static final int BASE_MANA = 200;
    private static final int BASE_STRENGTH = 20;
    private static final int BASE_INTELLIGENCE = 20;
    private static final int BASE_DEXTERITY = 20;
    private static final int BASE_PHYSICAL_RESIST = 20;
    private static final int BASE_MAGIC_RESIST = 20;

    private final int width = 30;
    private final int height = 64;

    static {
        try {
            spriteSheet = new Image("/resources/character/fighter/unique.png");
            manaCosts = new int[]{10, 20, 30};
            animationList = new Animation[34];
            animationList[IDDLE] = new Animation(new SpriteSheet(spriteSheet.getSubImage(0, 0, 364, 64), 52, 64), 250);
            animationList[MOVE_RIGHT] = new Animation(new SpriteSheet(spriteSheet.getSubImage(0, 64, 312, 64), 52, 64), 250);
            animationList[MOVE_LEFT] = new Animation(new SpriteSheet(spriteSheet.getSubImage(0, 64, 312, 64).getFlippedCopy(true, false), 52, 64), 250);
            animationList[ATTACK_RIGHT] = new Animation(new SpriteSheet(spriteSheet.getSubImage(0, 128, 268, 64), 67, 64), 150);
            animationList[ATTACK_LEFT] = new Animation(new SpriteSheet(spriteSheet.getSubImage(0, 128, 268, 64).getFlippedCopy(true, false), 67, 64), 150);
            animationList[JUMP_RIGHT] = new Animation(new SpriteSheet(spriteSheet.getSubImage(0, 192, 372, 64), 62, 64), 300);
            animationList[JUMP_LEFT] = new Animation(new SpriteSheet(spriteSheet.getSubImage(0, 192, 372, 64).getFlippedCopy(true, false), 62, 64), 300);
            animationList[DASH_RIGHT] = new Animation(new SpriteSheet(spriteSheet.getSubImage(0, 256, 138, 64), 69, 64), 150);
            animationList[DASH_LEFT] = new Animation(new SpriteSheet(spriteSheet.getSubImage(0, 256, 138, 64).getFlippedCopy(true, false), 69, 64), 150);
            animationList[JUMP_ATTACK_RIGHT] = new Animation(new SpriteSheet(spriteSheet.getSubImage(0, 320, 150, 64), 50, 64), 150);
            animationList[JUMP_ATTACK_LEFT] = new Animation(new SpriteSheet(spriteSheet.getSubImage(0, 320, 150, 64).getFlippedCopy(true, false), 50, 64), 150);
            animationList[HIT_RIGHT] = new Animation(new SpriteSheet(spriteSheet.getSubImage(0, 384, 454, 64), 76, 64), 150);
            animationList[HIT_LEFT] = new Animation(new SpriteSheet(spriteSheet.getSubImage(0, 384, 454, 64).getFlippedCopy(true, false), 76, 64), 150);
            animationList[ABILLTY_A_RIGHT] = new Animation(new SpriteSheet(spriteSheet.getSubImage(0, 448, 1030, 64), 103, 64), 200);
            animationList[ABILLTY_A_LEFT] = new Animation(new SpriteSheet(spriteSheet.getSubImage(0, 448, 1030, 64).getFlippedCopy(true, false), 103, 64), 200);
            animationList[ABILLTY_S_RIGHT] = new Animation(new SpriteSheet(spriteSheet.getSubImage(0, 512, 704, 64), 88, 64), 200);
            animationList[ABILLTY_S_LEFT] = new Animation(new SpriteSheet(spriteSheet.getSubImage(0, 512, 704, 64).getFlippedCopy(true, false), 88, 64), 200);
            animationList[ABILLTY_D_RIGHT] = new Animation(new SpriteSheet(spriteSheet.getSubImage(0, 576, 819, 64), 91, 64), 200);
            animationList[ABILLTY_D_LEFT] = new Animation(new SpriteSheet(spriteSheet.getSubImage(0, 576, 819, 64).getFlippedCopy(true, false), 91, 64), 200);
            animationList[GRAPPLE_RIGHT] = new Animation(new SpriteSheet(spriteSheet.getSubImage(0, 640, 360, 64), 45, 64), 200);
            animationList[GRAPPLE_LEFT] = new Animation(new SpriteSheet(spriteSheet.getSubImage(0, 640, 360, 64).getFlippedCopy(true, false), 45, 64), 200);
            animationList[DASH_ATTACK_RIGHT] = new Animation(new SpriteSheet(spriteSheet.getSubImage(0, 704, 576, 64), 72, 64), 150);
            animationList[DASH_ATTACK_LEFT] = new Animation(new SpriteSheet(spriteSheet.getSubImage(0, 704, 576, 64).getFlippedCopy(true, false), 72, 64), 150);

            animationList[IDDLE].setPingPong(true);
            animationList[MOVE_LEFT].setPingPong(true);
            animationList[MOVE_RIGHT].setPingPong(true);
            animationList[DASH_LEFT].setPingPong(true);
            animationList[DASH_RIGHT].setPingPong(true);
        } catch (SlickException ex) {
            Logger.getLogger(Warrior.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException();
        }
    }

    public Fighter(String nickname) {
        super(nickname, "fighter", BASE_LIFE, BASE_MANA, BASE_STRENGTH, BASE_INTELLIGENCE, BASE_DEXTERITY, BASE_PHYSICAL_RESIST, BASE_MAGIC_RESIST, animationList);
    }

    public Fighter(String nickname, int level, Long exp, int life, int mana, int strength, int intelligence, int dexterity, int physicalResist, int magicResist) {
        super(nickname, "fighter", level, exp, life, mana, strength, intelligence, dexterity, physicalResist, magicResist, animationList);
    }

    @Override
    public void doAbillityA() {
        if (actualMana > manaCosts[A]) {
            this.actualMana -= manaCosts[A];
            this.setStatus(ABILLITY_A);
            this.doingAbillty = true;
        }
    }

    @Override
    public void doAbillityS() {
        if (actualMana > manaCosts[S]) {
            this.actualMana -= manaCosts[S];
            this.setStatus(ABILLITY_S);
            this.doingAbillty = true;
        }
    }

    @Override
    public void doAbillityD() {
        if (actualMana > manaCosts[D]) {
            this.actualMana -= manaCosts[D];
            this.doingAbillty = true;

            if (old_state == MOVE_LEFT || old_state == JUMP_LEFT || old_state == ATTACK_LEFT || old_state == DASH_LEFT) {
                Bullet.addBullet(new Bullet(new Vector2f(getPosx(), getPosy() + height / 2), Bullet.TYPE_FIGHTER_ABILLITY_D, Bullet.LEFT, this));
            } else {
                Bullet.addBullet(new Bullet(new Vector2f(getPosx(), getPosy() + height / 2), Bullet.TYPE_FIGHTER_ABILLITY_D, Bullet.RIGHT, this));
            }

            this.setStatus(ABILLITY_D);

        }
    }

}
