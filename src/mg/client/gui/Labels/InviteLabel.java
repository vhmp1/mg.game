/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mg.client.GUI.Labels;

import java.awt.Font;
import mg.client.GUI.Buttons.AcceptDuelButton;
import mg.client.GUI.Buttons.DeclineDuelButton;
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
public class InviteLabel extends AbstractComponent{
    
    private String text;
    private int posx;
    private int posy;
    private final int width;
    private final int height;
    private final Font font;
    private final AcceptDuelButton acceptBtn;
    private final DeclineDuelButton declineBtn;
    private final TrueTypeFont ttf;
    private static Image BACKGROUND;
    private InvitesArea area;
    
    static {
        try{
            BACKGROUND = new Image("/resources/lobby/invite_label.png");
        } catch(SlickException ex){
            
        }
    }
    
    public InviteLabel(GUIContext container, InvitesArea area, String playerName) {
        super(container);
        this.width = 298;
        this.height = 19;
        text = playerName;
        font = new Font("Georgia", Font.PLAIN, 12);
        ttf = new TrueTypeFont(font, true);
        acceptBtn = new AcceptDuelButton(container, posx + 218, posy + 3, null, playerName, this);
        declineBtn = new DeclineDuelButton(container, posx + 257, posy + 3, null, playerName, this);
        this.area = area;
    }

    @Override
    public void render(GUIContext container, Graphics g) throws SlickException {
        BACKGROUND.draw(posx, posy);
        g.setColor(Color.black);
        g.setFont(ttf);
        g.drawString(this.text, this.getX(), this.getY());
        acceptBtn.setLocation(posx + 218,  posy + 3);
        declineBtn.setLocation( posx + 257, posy + 3);
        acceptBtn.render(container, g);
        declineBtn.render(container, g);
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
    
    public InvitesArea getArea(){
        return area;
    }
    
    
}
