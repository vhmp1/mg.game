/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mg.client.GUI.Labels;

import java.awt.Font;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;

/**
 *
 * @author Vitor
 */
public class InfoLabel extends AbstractComponent{

    private String text;
    private int posx;
    private int posy;
    private final int width;
    private final int height;
    private final Font font;
    private final TrueTypeFont ttf;
    
    public InfoLabel(GUIContext container, int width, int height) {
        super(container);
        this.width = width;
        this.height = height;
        text = "Poar";
        font = new Font("Georgia", Font.PLAIN, 10);
        ttf = new TrueTypeFont(font, true);
    }

    @Override
    public void render(GUIContext container, Graphics g) throws SlickException {
        g.setColor(Color.black);
        g.setFont(ttf);
        g.drawString(this.text, this.getX(), this.getY());
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
    
    public void setText(String text){
        this.text = text;
    }
    
}
