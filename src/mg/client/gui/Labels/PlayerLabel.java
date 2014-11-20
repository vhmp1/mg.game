/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.client.GUI.Labels;

import java.awt.Font;
import mg.client.GUI.Buttons.DuelButton;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;

/**
 *
 * @author Vitor
 */
public class PlayerLabel extends AbstractComponent {

    private int posx;
    private int posy;
    private final int width;
    private final int height;   
    private final DuelButton duel;
    private static Font font;
    private static TrueTypeFont ttf;
    private static Image BACKGROUND;
    private final String playername;
    private final int playerLevel;

    static {
        try {
            BACKGROUND = new Image("/resources/lobby/invite_label.png");
            font = new Font("Georgia", Font.PLAIN, 12);
            ttf = new TrueTypeFont(font, true);
        } catch (SlickException ex) {

        }
    }

    public PlayerLabel(GUIContext container, String playername, int playerLevel) {
        super(container);
        this.width = 298;
        this.height = 19;
        this.playername = playername;
        this.duel = new DuelButton(container, null, playername);
        this.playerLevel = playerLevel;
    }

    @Override
    public void render(GUIContext container, Graphics g) throws SlickException {
        BACKGROUND.draw(posx, posy);
        g.setColor(Color.black);
        g.setFont(ttf);
        g.drawString(playername + "      Lvl: " + Integer.toString(playerLevel), this.getX(), this.getY());
        duel.setLocation(posx + 253, posy + 3);
        duel.render(container, g);

    }

    @Override
    public void setLocation(int x, int y) {
        this.posx = x;
        this.posy = y;
    }

    @Override
    public int getX() {
        return this.posx;
    }

    @Override
    public int getY() {
        return this.posy;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

   
}

