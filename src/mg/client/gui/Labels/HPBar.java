/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.client.GUI.Labels;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;
import sun.java2d.pipe.DrawImage;

/**
 *
 * @author Vitor
 */
public class HPBar extends AbstractComponent {

    private static Image bg;
    private static Image hp;
    private int widith;
    private int heigth;
    private int y;
    private int x;
    private int hpValue;
    private final int hpTotal;
    private final int player;

    static {
        try {
            bg = new Image("/resources/map/interface/hp_total.png");
            hp = new Image("/resources/map/interface/hp_actual.png");
        } catch (SlickException ex) {
            Logger.getLogger(HPBar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public HPBar(GUIContext container, int x, int y, int hpTotal, int hpValue, int player) {
        super(container);
        this.x = x;
        this.y = y;
        this.hpTotal = hpTotal;
        this.hpValue = hpValue;
        this.player = player;
    }

    public void setHpValue(int hpValue) {
        this.hpValue = hpValue;
    }

    @Override
    public void render(GUIContext container, Graphics g) throws SlickException {
        if (player == BattleInfoArea.PLAYER_ONE) {
            if (hpValue > 0) {
                hp.draw(x + (hpTotal - hpValue) * hp.getWidth() / hpTotal, y,
                        x + hp.getWidth(), y + hp.getHeight(),
                       (hpTotal - hpValue) * hp.getWidth() / hpTotal, 0,
                        hp.getWidth(), hp.getHeight());
            }

            bg.draw(x, y);
        } else {
            if (hpValue > 0) {
                (hp.getFlippedCopy(true, false)).draw(x, y,
                        x + hpValue * hp.getWidth() / hpTotal, y + hp.getHeight(),
                        0, 0,
                        hpValue * hp.getWidth() / hpTotal, hp.getHeight());
            }

            (bg.getFlippedCopy(true, false)).draw(x, y);
        }
    }

    @Override
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return this.y;
    }

    @Override
    public int getY() {
        return this.x;
    }

    @Override
    public int getWidth() {
        return this.widith;
    }

    @Override
    public int getHeight() {
        return this.heigth;
    }

}
