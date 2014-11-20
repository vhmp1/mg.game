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
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;

/**
 *
 * @author Vitor
 */
public class Controller extends MouseOverArea {

    private static Image bg;
    private static Image head;
    private int x;
    private int y;
    private final int width;
    private final int height;
    private int value;

    static {
        try {
            bg = new Image("/resources/lobby/controller_bg.png");
            head = new Image("/resources/lobby/controller_head.png");
        } catch (SlickException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Controller(GUIContext container, int x, int y) {
        super(container, bg, x, y, null);
        this.x = x;
        this.y = y;
        this.value = 50;
        this.height = head.getHeight();
        this.width = bg.getWidth();
    }

    public float getActualValue() {
        return (float) value / width;
    }
    
    @Override
    public void render(GUIContext container, Graphics g) {
        bg.draw(x, y + head.getHeight() / 2 - 2);
        head.draw(x + value, y);
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
        if (oldy > this.y && oldy < y + head.getHeight()) {
            if (newx > x && newx < x + bg.getWidth() - head.getWidth() / 2) {
                value = newx - x;
            }
        }
    }
    
    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }
    
}
