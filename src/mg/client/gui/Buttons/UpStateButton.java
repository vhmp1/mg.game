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
public class UpStateButton extends MouseOverArea {

    private static Image imageBtnActual;
    private static Image imageBtnActive;
    private static Image imageBtnInactive;
    private final Player player;
    private final String status;
    private boolean active = false;

    static {
        try {
            imageBtnInactive = new Image("/resources/lobby/up_btn_inactive.png");
            imageBtnActive = new Image("/resources/lobby/up_btn_active.png");
            imageBtnActual = imageBtnInactive;
        } catch (SlickException ex) {
            Logger.getLogger(SendButton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public UpStateButton(GUIContext container, int x, int y, Player c, String status) {
        super(container, imageBtnActual, x, y, null);
        player = c;
        this.status = status;
    }

    @Override
    public void render(GUIContext container, Graphics g) {

        if (active) {
            imageBtnActual = imageBtnActive;
        } else {
            imageBtnActual = imageBtnInactive;
        }

        imageBtnActual.draw(this.getX(), this.getY());
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
//            switch (status) {
//                case "Strenght":
//                    player.increaseStrenght();
//                    break;
//                case "Intelligence":
//                    player.increaseIntelligence();
//                    break;
//                case "Dexterity":
//                    player.increaseDexterity();
//                    break;
//                case "Physical Resist":
//                    player.increasePhysicalResist();
//                    break;
//                case "Magic Resist":
//                    player.increaseMagicResist();
//                    break;
//            }
        }
    }

}
