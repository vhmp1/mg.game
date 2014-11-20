/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mg.client.GUI.Buttons;

import java.util.logging.Level;
import java.util.logging.Logger;
import mg.client.GUI.Labels.InviteLabel;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;

/**
 *
 * @author Vitor
 */
public class AcceptDuelButton extends MouseOverArea{
     private static Image imageBtn;
     private final String playerDuel;
     private final InviteLabel label;

    static {
        try {
            imageBtn = new Image("/resources/lobby/accept_btn.png");
        } catch (SlickException ex) {
            Logger.getLogger(SendButton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public AcceptDuelButton(GUIContext container, int x, int y, ComponentListener listener, String playerDuel, InviteLabel label) {
        super(container, imageBtn, x, y, listener);
        this.playerDuel = playerDuel;
        this.label = label;
    }

    @Override
    public void render(GUIContext container, Graphics g) {    
        imageBtn.draw(this.getX(), this.getY());
    }
}
