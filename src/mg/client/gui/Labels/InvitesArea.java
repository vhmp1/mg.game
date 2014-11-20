/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.client.GUI.Labels;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;

/**
 *
 * @author Vitor
 */
public class InvitesArea extends MouseOverArea {

    private static final int MAX_SHOW_INVITES = 7;
    private int initial = 0;
    private int posx;
    private int posy;
    private final int width;
    private final int height;
    private ArrayList<InviteLabel> invites;
    private static Image background;
    private static Image scrollbar;
    private static Image scrollDetail;

    static {
        try {
            background = new Image("/resources/lobby/invites_bg.png");
            scrollbar = new Image("/resources/lobby/scrollbase.png");
            scrollDetail = new Image("/resources/lobby/scrolldetail.png");
        } catch (SlickException ex) {

        }
    }

    public InvitesArea(GUIContext container, int posx, int posy) {
        super(container, background, posx, posy);
        this.width = 319;
        this.height = 195;
        this.posx = posx;
        this.posy = posy;
        invites = new ArrayList<InviteLabel>();

    }

    @Override
    public void render(GUIContext container, Graphics g) {
        background.draw(posx, posy);
        for (int i = 0; i < MAX_SHOW_INVITES && i < invites.size(); i++) {
            try {
                invites.get(i + initial).setLocation(posx + 8, posy + (29 + i * (invites.get(i).getHeight() + 3)));
                invites.get(i + initial).render(container, g);
            } catch (SlickException ex) {
                Logger.getLogger(InvitesArea.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        scrollbar.draw(posx + 307, posy + 22, 7, 170);
        if (invites.size() > MAX_SHOW_INVITES) {
            int detailSize = 170 / ((invites.size() + 1) - MAX_SHOW_INVITES);
            scrollDetail.draw(posx + 307, posy + 22 + (initial * detailSize), 7, detailSize);
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

    public void addInvite(InviteLabel i) {
        invites.add(i);
    }

    public void removeInvite(InviteLabel i) {
        for (InviteLabel in : invites) {
            if (in.equals(i)) {
                invites.remove(i);
                break;
            }
        }
    }

    @Override
    public void mouseWheelMoved(int change) {
        if(!isMouseOver()){
            return;
        }
        
        if (change > 0 && invites.size() > MAX_SHOW_INVITES && initial > 0) {
            initial--;
        } else if (change < 0 && invites.size() > MAX_SHOW_INVITES && initial < invites.size() - MAX_SHOW_INVITES) {
            initial++;
        }
    }

}
