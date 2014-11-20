/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.client.GUI.Buttons;

import java.util.logging.Level;
import java.util.logging.Logger;
import mg.client.Entities.Player;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;

/**
 *
 * @author Vitor
 */
public class DuelButton extends MouseOverArea {

    private static Image imageActualBtn;
    private static Image imageActiveBtn;
    private static Image imageInactiveBtn;
    private final Player player;
    private final String status;
    private boolean active = false;

    static {
        try {
            imageInactiveBtn = new Image("/resources/lobby/duel_inactive.png");
            imageActiveBtn = new Image("/resources/lobby/duel_active.png");
            imageActualBtn = imageInactiveBtn;
        } catch (SlickException ex) {
            Logger.getLogger(SendButton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DuelButton(GUIContext container, Player c, String status) {
        super(container, imageActualBtn, 0, 0, null);
        this.player = c;
        this.status = status;
    }

    @Override
    public void render(GUIContext container, Graphics g) {
        if (active) {
            imageActualBtn = imageActiveBtn;
        } else {
            imageActualBtn = imageInactiveBtn;
        }
        
        imageActualBtn.draw(this.getX(), this.getY());
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean state) {
        this.active = state;
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {
        if (isMouseOver() && isActive()) {
            System.out.println("Bora duelar poar");
        }
    }
}
