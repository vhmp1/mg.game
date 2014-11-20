/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mg.client.Entities;

import org.newdawn.slick.Animation;

/**
 *
 * @author Vitor
 */
public class Enemy {
    private int posx;
    private int posy;
    private int width;
    private int height;
    private int baseJump;
    private final Animation[] animationList;
    private Animation status;
    private boolean jumping;
    private boolean attacking;
    private boolean dashing;
    private boolean doingAbillty;
    private boolean knockback;

    
   Enemy(Animation[] animationList, int posx, int posy){
        this.animationList = animationList;
        this.posx = posx;
        this.posy = posy;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public void setDashing(boolean dashing) {
        this.dashing = dashing;
    }

    public void setDoingAbillty(boolean doingAbillty) {
        this.doingAbillty = doingAbillty;
    }

    public void setKnockback(boolean knockback) {
        this.knockback = knockback;
    }

    public void setPosx(int posx) {
        this.posx = posx;
    }

    public void setPosy(int posy) {
        this.posy = posy;
    }

    public int getPosx() {
        return posx;
    }

    public int getPosy() {
        return posy;
    }
    
    
    
}
