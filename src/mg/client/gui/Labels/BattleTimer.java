/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.client.GUI.Labels;

import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class BattleTimer extends AbstractComponent {

    private static Image background;
    private int x;
    private int y;
    private int battleTime;
    private final Timer timer;

    static {
        try {
            background = new Image("/resources/map/interface/time_bg.png");
        } catch (SlickException ex) {
            Logger.getLogger(BattleTimer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private final Font font;
    private final TrueTypeFont ttf;

    public BattleTimer(GUIContext container, int x, int y, int battleTime) {
        super(container);
        this.x = x;
        this.y = y;
        this.battleTime = battleTime;

        font = new Font("Georgia", Font.PLAIN, 22);
        ttf = new TrueTypeFont(font, true);

        this.timer = new Timer();
    }

    @Override
    public void render(GUIContext container, Graphics g) throws SlickException {
        background.draw(x, y);
        g.setFont(ttf);
        g.drawString(Integer.toString(battleTime), x + 15, y + 15);
    }

    @Override
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int getWidth() {
        return background.getWidth();
    }

    @Override
    public int getHeight() {
        return background.getHeight();
    }

    public void startTimer() {
        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                BattleTimer.this.battleTime--;
            }
        }, 1000, 1000);
    }
    
    public int getTime(){
        return battleTime;
    }

}
