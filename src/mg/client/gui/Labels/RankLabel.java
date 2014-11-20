/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.client.GUI.Labels;

import java.awt.Font;
import mg.client.RankData;
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
public class RankLabel extends AbstractComponent {

    private int posx;
    private int posy;
    private final int width;
    private final int height;
    private final Font font;
    private final TrueTypeFont ttf;
    private static Image[] rankBadge;
    private static Image BACKGROUND;
    private final RankData rankPosition;
    private RankArea rank;

    static {
        try {
            BACKGROUND = new Image("/resources/lobby/invite_label.png");
            rankBadge = new Image[]{new Image("/resources/lobby/rank1.png"),
                new Image("/resources/lobby/rank2.png"),
                new Image("/resources/lobby/rank3.png"),
                new Image("/resources/lobby/rank_another.png")};
        } catch (SlickException ex) {

        }
    }

    public RankLabel(GUIContext container, RankArea area, RankData rankPosition) {
        super(container);
        this.width = 298;
        this.height = 19;
        this.rankPosition = rankPosition;
        this.font = new Font("Georgia", Font.PLAIN, 12);
        this.ttf = new TrueTypeFont(font, true);
        this.rank = area;
    }

    @Override
    public void render(GUIContext container, Graphics g) throws SlickException {
        BACKGROUND.draw(posx, posy);
        g.setColor(Color.black);
        g.setFont(ttf);
        g.drawString(rankPosition.getPlayer() + "      Lvl: " + rankPosition.getLevel(), this.getX(), this.getY());
        if(rankPosition.getRank() < 4){
            g.drawImage(rankBadge[rankPosition.getRank() - 1], posx + 270, posy);
        } else {
            g.drawImage(rankBadge[3], posx + 270, posy);
        }
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

    public RankArea getArea() {
        return rank;
    }

}
