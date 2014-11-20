/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mg.client.GUI.Labels;

import java.awt.Font;
import mg.client.Entities.Player;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;

/**
 *
 * @author Vitor
 */
public class BattleInfoArea extends AbstractComponent{
    
    public static final int PLAYER_ONE = 1;
    public static final int PLAYER_TWO = 2;
    private int widith;
    private int heigth;
    private int y;
    private int x;
    private final HPBar p1hp;
    private final ManaBar p1mana;
    private final HPBar p2hp;
    private final ManaBar p2mana;
    private final Player p1;
    private final Player p2;
    private final BattleTimer timer;    
    private final Font font;
    private final TrueTypeFont ttf;
    
    public BattleInfoArea(GUIContext container, int x, int y, Player p1, Player p2) {
        super(container);
        this.x = x;
        this.y = y;
        this.p1 = p1;
        this.p2 = p2;
        
        this.p1hp = new HPBar(container, x + 8, 0, p1.getLife(), p1.getActualLife(), PLAYER_ONE);
        this.p1mana = new ManaBar(container, x + 28, y + 13, p1.getMana(), p1.getActualMana(), PLAYER_ONE);      
        
        this.p2hp = new HPBar(container, x + 375, 0, p1.getLife(), p1.getActualLife(), PLAYER_TWO);
        this.p2mana = new ManaBar(container, x + 375, y + 13, p1.getMana(), p1.getActualMana(), PLAYER_TWO);  
        
        this.timer = new BattleTimer(container, x + 361, y - 12, 99);
        
        font = new Font("Georgia", Font.PLAIN, 14);
        ttf = new TrueTypeFont(font, true);
    }
    
    
    
    @Override
    public void render(GUIContext container, Graphics g) throws SlickException {
        p1hp.render(container, g);
        p1mana.render(container, g);
        
        p2hp.render(container, g);
        p2mana.render(container, g);
        
        timer.render(container, g);
        
        g.setFont(ttf);
        
        g.drawString(p1.getNickname(), x + 50, y + 45);
        g.drawString(p2.getNickname(), x + 650, y + 45);
    }
    
    public void updateValues(){
        p1hp.setHpValue(p1.getActualLife());
        p1mana.setManaValue(p1.getActualMana());
        
        p2hp.setHpValue(p2.getActualLife());
        p2mana.setManaValue(p2.getActualMana());
    }
    
    @Override
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return this.y;
    }

    @Override
    public int getY() {
        return this.x;
    }

    @Override
    public int getWidth() {
        return this.widith;
    }

    @Override
    public int getHeight() {
        return this.heigth;
    }
    
    public void startTimer() {
        timer.startTimer();
    }
    
    public int getTime(){
        return timer.getTime();
    }
}
