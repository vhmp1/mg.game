/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.client.Entities;

import chatclient.business.messages.CommandMessage;
import chatclient.business.messages.CommandMessage.Commands;
import java.util.Timer;
import java.util.TimerTask;
import javax.smartcardio.CommandAPDU;
import mg.client.Maps.Map;
import mg.client.Net.GameClient;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import sun.tools.tree.CommaExpression;

/**
 *
 * @author Vitor
 */
public abstract class Player {

    public static final int IDDLE = 0;
    public static final int MOVE_RIGHT = 1;
    public static final int MOVE_LEFT = 2;
    public static final int JUMP = 3;
    static final int JUMP_RIGHT = 4;
    static final int JUMP_LEFT = 5;
    public static final int ATTACK = 6;
    static final int ATTACK_RIGHT = 7;
    static final int ATTACK_LEFT = 8;
    public static final int DASH = 9;
    static final int DASH_LEFT = 10;
    static final int DASH_RIGHT = 11;
    public static final int JUMP_ATTACK = 12;
    static final int JUMP_ATTACK_LEFT = 13;
    static final int JUMP_ATTACK_RIGHT = 14;
    public static final int HIT = 15;
    static final int HIT_LEFT = 16;
    static final int HIT_RIGHT = 17;
    public static final int ABILLITY_A = 18;
    static final int ABILLTY_A_LEFT = 19;
    static final int ABILLTY_A_RIGHT = 20;
    public static final int ABILLITY_S = 21;
    static final int ABILLTY_S_LEFT = 22;
    static final int ABILLTY_S_RIGHT = 23;
    public static final int ABILLITY_D = 24;
    static final int ABILLTY_D_LEFT = 25;
    static final int ABILLTY_D_RIGHT = 26;
    public static final int GRAPPLE = 27;
    static final int GRAPPLE_LEFT = 28;
    static final int GRAPPLE_RIGHT = 29;
    public static final int DASH_ATTACK = 30;
    static final int DASH_ATTACK_LEFT = 31;
    static final int DASH_ATTACK_RIGHT = 32;
    public static final int ABILLITY = 33;
    static final int A = 0;
    static final int S = 1;
    static final int D = 2;
    public static final int JUMP_MAX_STRENGHT = 30;
    int old_state;
    int old_side;
    private final String nickname;
    private final String Class;
    private int level;
    private Long exp;
    //Stats of character
    private int life;
    private int mana;
    private int strength;
    private int intelligence;
    private int dexterity;
    private int physicalResist;
    private int magicResist;
    private long maxExp;
    int actualLife;
    int actualMana;
    //Character animation
    private int posx;
    private int posy;
    private int absoluteX;
    private int absoluteY;
    private int width;
    private int height;
    private final Animation[] animationList;
    private Animation status;
    private boolean jumping;
    private boolean attacking;
    private boolean dashing;
    boolean doingAbillty;
    private boolean knockback;
    private int attackAnimationTime;
    private int dashAnimationTime;
    private int abillityAnimationTime;
    private int knockBackAnimationTime;
    private Map map;
    private Player enemy;
    private int lastKey;
    private Timer dashTimer;
    public static final float GRAVITY = 1f;
    private int velocity;
    private int gravityAcceleration;
    private static final float animationTime = 18.5f;
    private int jumpStrenght = 15;
    private GameClient client;
    //Character Input Listener
    private Input input;
    private int id;

    public Player(String nickname, String Class, int level, Long exp, int life, int mana, int strength, int intelligence, int dexterity, int physicalResist, int magicResist, Animation[] animationList) {
        this.nickname = nickname;
        this.Class = Class;
        this.level = level;
        this.exp = exp;
        this.life = life;
        this.mana = mana;
        this.strength = strength;
        this.intelligence = intelligence;
        this.dexterity = dexterity;
        this.physicalResist = physicalResist;
        this.magicResist = magicResist;
        this.maxExp = 200;
        for (int i = 0; i < level; i++) {
            this.maxExp += (3 * level * maxExp) / 2;
        }

        this.animationList = animationList;

    }

    public Player(String nickname, String Class, int life, int mana, int strength, int intelligence, int dexterity, int physicalResist, int magicResist, Animation[] animationList) {
        this.nickname = nickname;
        this.Class = Class;
        this.level = 1;
        this.exp = 0L;
        this.life = life;
        this.mana = mana;
        this.strength = strength;
        this.intelligence = intelligence;
        this.dexterity = dexterity;
        this.physicalResist = physicalResist;
        this.magicResist = magicResist;
        this.maxExp = 200;

        this.animationList = animationList;
    }

    public String getNickname() {
        return this.nickname;
    }

    public int getStrength() {
        return strength;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getPhysicalResist() {
        return physicalResist;
    }

    public int getMagicResist() {
        return magicResist;
    }

    public int getLevel() {
        return level;
    }

    public long getExp() {
        return exp;
    }

    public String getFormatExp() {
        return String.format("%d/%d", exp, maxExp);
    }

    //Methods for increase each status 
    public void increaseStrenght() {
        this.strength++;
    }

    public void increaseIntelligence() {
        this.intelligence++;
    }

    public void increaseDexterity() {
        this.dexterity++;
    }

    public void increasePhysicalResist() {
        this.physicalResist++;
    }

    public void increaseMagicResist() {
        this.magicResist++;
    }

    public void increaseLevel() {
        this.level++;
        this.exp = 0L;
        this.maxExp += (3 * level * maxExp) / 2;
    }

    public void increaseExp(Long exp) {
        this.exp += exp;
        if (this.exp >= this.maxExp) {
            this.exp -= maxExp;
            increaseLevel();
        }
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public String getCaste() {
        return this.Class;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        System.out.println(id);
        this.id = id;
    }

    public void init(int posx, int posy, Input input, Map map, Player enemy, GameClient client) {
        this.posx = posx;
        this.posy = posy;
        this.absoluteX = posx;
        this.absoluteY = posy;

        this.client = client;

        this.width = 28;
        this.height = 64;

        this.velocity = 15 + dexterity / 4;

//        this.jumpStrenght = posy;
        this.status = this.animationList[IDDLE];
        this.attacking = false;
        this.jumping = false;

        this.actualLife = life;
        this.actualMana = mana;

        this.input = input;

        this.map = map;
        this.enemy = enemy;

        this.dashTimer = new Timer();
        this.dashTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                lastKey = 0;
            }
        }, 1500);

    }

    public int getWidth() {
        return status.getWidth();
    }

    public int getHeight() {
        return status.getHeight();
    }

    public int getPosx() {
        return posx;
    }

    public void increasePosx(float posx) {
        absoluteX += posx;
        this.posx += posx;
    }

    public int getPosy() {
        return posy;
    }

    public void increasePosy(float posy) {
        absoluteY += posy;
        this.posy += posy;
    }

    public Animation getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status.stop();
        if (status == JUMP) {
            if (old_side == 1) {
                status = JUMP_LEFT;
            } else {
                status = JUMP_RIGHT;
            }
        } else if (status == ATTACK) {
            if (old_side == 1) {
                if (jumping || old_state == JUMP_ATTACK_LEFT) {
                    status = JUMP_ATTACK_LEFT;
                } else if (dashing || old_state == DASH_ATTACK_LEFT) {
                    status = DASH_ATTACK_LEFT;
                } else {
                    status = ATTACK_LEFT;
                }
            } else {
                if (old_side == 1) {
                    status = JUMP_ATTACK_RIGHT;
                } else if (dashing || old_state == DASH_ATTACK_RIGHT) {
                    status = DASH_ATTACK_RIGHT;
                } else {
                    status = ATTACK_RIGHT;
                }
            }
        } else if (status == DASH) {
            if (old_side == 1) {
                status = DASH_LEFT;
            } else {
                status = DASH_RIGHT;
            }
        } else if (status == GRAPPLE) {
            if (old_side == 1) {
                status = GRAPPLE_LEFT;
            } else {
                status = GRAPPLE_RIGHT;
            }
        } else if (status == ABILLITY_A) {
            if (old_side == 1) {
                status = ABILLTY_A_LEFT;
            } else {
                status = ABILLTY_A_RIGHT;
            }
        } else if (status == ABILLITY_S) {
            if (old_side == 1) {
                status = ABILLTY_S_LEFT;
            } else {
                status = ABILLTY_S_RIGHT;
            }
        } else if (status == ABILLITY_D) {
            if (old_side == 1) {
                status = ABILLTY_D_LEFT;
            } else {
                status = ABILLTY_D_RIGHT;
            }
        } else if (status == ABILLITY) {
            if (old_state == ABILLTY_A_LEFT) {
                status = ABILLTY_A_LEFT;
            } else if (old_state == ABILLTY_A_RIGHT) {
                status = ABILLTY_A_RIGHT;
            } else if (old_state == ABILLTY_S_LEFT) {
                status = ABILLTY_S_LEFT;
            } else if (old_state == ABILLTY_S_RIGHT) {
                status = ABILLTY_S_RIGHT;
            } else if (old_state == ABILLTY_D_LEFT) {
                status = ABILLTY_D_LEFT;
            } else if (old_state == ABILLTY_D_RIGHT) {
                status = ABILLTY_D_RIGHT;
            }
        }

        if (status == MOVE_LEFT || status == JUMP_LEFT || status == ATTACK_LEFT || status == DASH_LEFT) {
            old_side = 1;
        } else {
            old_side = 0;
        }

        this.status = animationList[status];
        this.status.start();

        if (status != IDDLE) {
            old_state = status;
        }

    }

    public boolean isAttacking() {
        return attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public void setKnockback(boolean knockback) {
        this.knockback = knockback;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public boolean isDashing() {
        return dashing;
    }

    public void setDashing(boolean dashing) {
        this.dashing = dashing;
    }

    public boolean isDoingAbillty() {
        return doingAbillty;
    }

    public void setDoingAbillty(boolean doingAbillty) {
        this.doingAbillty = doingAbillty;
    }

    public String dashDirection() {
        if (old_state == MOVE_LEFT || old_state == JUMP_LEFT || old_state == ATTACK_LEFT || old_state == DASH_LEFT) {
            return "LEFT";
        } else {
            return "RIGHT";
        }
    }

    public void removeHP(int receivedDamage) {
        this.actualLife -= receivedDamage;
    }

    public int getActualLife() {
        return actualLife;
    }

    public int getActualMana() {
        return actualMana;
    }

    public int getJumpHeight() {
        return status.getHeight() * 6 / 4;
    }

    public int getTilePositionX() {
        return Math.round((posx + width) / 32);
    }

    public int getTilePositionY() {
        return Math.round((posy + height) / 32);
    }

    public abstract void doAbillityA();

    public abstract void doAbillityS();

    public abstract void doAbillityD();

    public void update(int delta) {
        velocity = delta + dexterity / 4;

        if (knockback) {
            if (knockBackAnimationTime > (status.getFrameCount() * status.getDuration(0)) / 2 - velocity) {
                knockBackAnimationTime = 0;
                client.sendStatus(Commands.KNOCKBACK.ordinal(), id, false);
            } else {
                knockback(old_side);
                knockBackAnimationTime += animationTime;
            }

            return;
        } else if (doingAbillty) {
            this.setStatus(Player.ABILLITY);
            if (abillityAnimationTime > (status.getFrameCount() * status.getDuration(0)) / 2 + animationTime) {
                doingAbillty = false;
                abillityAnimationTime = 0;
            } else {
                abillityAnimationTime += animationTime;
            }

            status.update(velocity);
            return;
        }

        if (jumping) {
            this.setStatus(Player.JUMP);
            client.sendMovement(2, id);
        } else {
            this.setStatus(Player.JUMP);
            client.sendMovement(3, id);
        }

        if (dashing) {
            this.setStatus(Player.DASH);
            if (dashAnimationTime >= (status.getFrameCount() * status.getDuration(0)) / 2 + velocity) {
                dash(old_side);
                client.sendStatus(Commands.DASH.ordinal(), id, false);
                dashAnimationTime = 0;
            } else {
                client.sendStatus(Commands.DASH.ordinal(), id, true);
            }
        }

        if (attacking) {
            if (this.testCollision(enemy) && (input.isKeyPressed(Input.KEY_LEFT) || input.isKeyPressed(Input.KEY_RIGHT))) {
                this.setStatus(Player.GRAPPLE);
            } else {
                this.setStatus(Player.ATTACK);
            }

            if (attackAnimationTime >= (status.getFrameCount() * status.getDuration(0)) / 2 + velocity) {
                client.sendStatus(Commands.ATTACK.ordinal(), id, false);
                this.width = 30;
                attackAnimationTime = 0;
            } else {
                client.sendStatus(Commands.ATTACK.ordinal(), id, true);
                attackAnimationTime += animationTime;
                this.width = 81;
            }

        }


        posx = absoluteX + map.getX();
        posy = absoluteY + map.getY();
        status.update((int) animationTime);
    }

    public void move(int side) {
        switch (side) {
            case 0:
                setStatus(Player.MOVE_LEFT);
                if (this.posx > 0 && !map.mapCollision(this, Map.LEFT) && !collideLeft(enemy)) {
                    increasePosx(-velocity / 5f);
                    map.setLocation((int) (map.getX() + velocity / 10f), map.getY());
                }
                break;
            case 1:
                setStatus(Player.MOVE_RIGHT);
                if (this.posx < 800 - getWidth() && !map.mapCollision(this, Map.RIGHT) && !collideRight(enemy)) {
                    increasePosx(velocity / 5f);
                    map.setLocation((int) (map.getX() - velocity / 10f), map.getY());
                }
                break;
            case 2:
                this.setStatus(Player.JUMP);
                this.increasePosy(-velocity / 5f);
                map.setLocation(map.getX(), (int) (map.getY() + velocity / 10f));
                break;
            case 3:
                this.increasePosy(gravityAcceleration);
                this.setStatus(Player.JUMP);
                break;  
        }

        status.update((int) animationTime);
    }

    public void dash(int side) {
        switch (side) {
            case 0:
                this.increasePosx(-velocity / 5f * 2);
                map.setLocation((int) (map.getX() + velocity / 5f), map.getY());
                dashAnimationTime += animationTime;
                break;
            case 1:
                increasePosx(velocity / 5f * 2);
                map.setLocation((int) (map.getX() - velocity / 5f), map.getY());
                dashAnimationTime += animationTime;
                break;
        }
    }

    public void knockback(int side) {
        switch (side) {
            case 0:
                this.setStatus(Player.HIT_LEFT);
                this.increasePosx(-velocity / 10f);
                break;
            case 1:
                this.setStatus(Player.HIT_RIGHT);
                this.increasePosx(velocity / 10f);
                break;
        }
    }

    public void updatePos(Map map) {
        posx = absoluteX + map.getX();
        posy = absoluteY + map.getY();
    }

    public void render() {
        status.draw(posx, posy);
    }

    public int getVelocity() {
        return velocity;
    }

    public int getGravityAcceleration() {
        return gravityAcceleration;
    }

    public boolean collideLeft(Player e) {
        Rectangle enemyRect = new Rectangle(e.getPosx(), e.getPosy(), e.getWidth(), e.getHeight());
        Rectangle collision = new Rectangle(this.posx, this.posy, 1, this.height);

        return collision.intersects(enemyRect);
    }

    public boolean collideRight(Player e) {
        Rectangle enemyRect = new Rectangle(e.getPosx(), e.getPosy(), e.getWidth(), e.getHeight());
        Rectangle collision = new Rectangle(this.posx + this.height, this.posy, 1, this.height);

        return collision.intersects(enemyRect);
    }

    public boolean testCollision(Player enemy) {
        return collideLeft(enemy) || collideRight(enemy);
    }
}
