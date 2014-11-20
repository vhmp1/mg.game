/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.client.GUI.Labels;

import java.util.logging.Level;
import java.util.logging.Logger;
import mg.client.GUI.Buttons.ApplyButton;
import mg.client.GUI.Buttons.ExitButton;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;

/**
 *
 * @author Vitor
 */
public class ConfigArea extends AbstractComponent {

    public static Image bg;
    public static Image dim;
    public int x;
    public int y;
    private final Music music;
    private final Music fx;
    public final Controller soundController;
    public final Controller fxController;
    public final ApplyButton aplly;
    public final ExitButton exit;
    public boolean render;

    static {
        try {
            bg = new Image("/resources/lobby/config_bg.png");
            dim = new Image("/resources/lobby/dim.png");
        } catch (SlickException ex) {
            Logger.getLogger(ConfigArea.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ConfigArea(GUIContext container, int x, int y, Music music, Music fx) {
        super(container);
        this.x = x;
        this.y = y;
        this.music = music;
        this.fx = fx;
        soundController = new Controller(container, x + 15, y + 58);
        fxController = new Controller(container, x + 15, y + 100);
        aplly = new ApplyButton(container, x + 174, y + 139, this);
        exit = new ExitButton(container, x + 218, y + 139, this);
        render = false;
    }

    @Override
    public void render(GUIContext container, Graphics g) throws SlickException {
        dim.draw();
        bg.draw(x, y);
        soundController.render(container, g);
        fxController.render(container, g);
        aplly.render(container, g);
        exit.render(container, g);
    }

    public void apply() {   
        music.setVolume(soundController.getActualValue());     
        //fx.setVolume(fxController.getActualValue());
    }
    
    public void exit() {
        this.render = false;
    }
        
    public void enter() {
        this.render = true;
    }

    public boolean isRender() {
        return render;
    }

    @Override
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getWidth() {
        return bg.getWidth();
    }

    @Override
    public int getHeight() {
        return bg.getHeight();
    }

}
