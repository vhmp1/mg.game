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

/**
 *
 * @author Vitor
 */
public class ManaBar extends AbstractComponent {

    private static Image bg;
    private static Image mana;
    private int widith;
    private int heigth;
    private int y;
    private int x;
    private int manaValue;
    private final int manaTotal;
    private final int player;

    static {
        try {
            bg = new Image("/resources/map/interface/mana_total.png");
            mana = new Image("/resources/map/interface/mana_actual.png");
        } catch (SlickException ex) {
            Logger.getLogger(HPBar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ManaBar(GUIContext container, int x, int y, int manaTotal, int manaValue, int player) {
        super(container);
        this.x = x;
        this.y = y;
        this.manaTotal = manaTotal;
        this.manaValue = manaValue;
        this.player = player;
    }

    public void setManaValue(int manaValue) {
        this.manaValue = manaValue;
    }

    @Override
    public void render(GUIContext container, Graphics g) throws SlickException {

        if (player == BattleInfoArea.PLAYER_ONE) {
            if (manaValue > 0) {
                mana.draw(x + (manaTotal - manaValue) * mana.getWidth() / manaTotal, y,
                        x + mana.getWidth(), y + mana.getHeight(),
                        (manaTotal - manaValue) * mana.getWidth() / manaTotal, 0,
                        mana.getWidth(), mana.getHeight());
            }
            bg.draw(x, y);
        } else {
            if (manaValue > 0) {
                (mana.getFlippedCopy(true, false)).draw(x, y,
                        x + manaValue * mana.getWidth() / manaTotal, y + mana.getHeight(),
                        0, 0,
                        manaValue * mana.getWidth() / manaTotal, mana.getHeight());
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
