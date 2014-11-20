/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mg.client.GUI.Buttons;

import java.util.logging.Level;
import java.util.logging.Logger;
import mg.client.GUI.Labels.ConfigArea;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;

/**
 *
 * @author Vitor
 */
public class ApplyButton extends MouseOverArea{
     private static Image imageBtn;
     private final ConfigArea controller;

    static {
        try {
            imageBtn = new Image("/resources/lobby/aplly_btn.png");
        } catch (SlickException ex) {
            Logger.getLogger(SendButton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ApplyButton(GUIContext container, int x, int y, ConfigArea controller) {
        super(container, imageBtn, x, y, null);
        this.controller = controller;
    }

    @Override
    public void render(GUIContext container, Graphics g) {    
        imageBtn.draw(this.getX(), this.getY());
    }
    
     @Override
    public void mouseClicked(int button, int x, int y, int clickCount){
        if(isMouseOver() && button == 0){
            controller.apply();
        }
    }
}
