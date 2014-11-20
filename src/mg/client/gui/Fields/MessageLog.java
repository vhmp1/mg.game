/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.client.GUI.Fields;

import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;

/**
 *
 * @author Vitor
 */
public class MessageLog extends MouseOverArea {

    private static final int MAX_SHOW_MESSAGES = 10;
    private int initial = 0;
    private int posx;
    private int posy;
    private final int width;
    private final int height;
    private final ArrayList<String> messages;
    private static Image background;
    private static Image scrollbar;
    private static Image scrollDetail;

    static {
        try {
            background = new Image("/resources/lobby/field_bg.png");
            scrollbar = new Image("/resources/lobby/scrollbase.png");
            scrollDetail = new Image("/resources/lobby/scrolldetail.png");
        } catch (SlickException ex) {

        }
    }

    public MessageLog(GUIContext container, int posx, int posy) {
        super(container, background, posx, posy);
        this.width = 712;
        this.height = 131;
        this.posx = posx;
        this.posy = posy;
        this.messages = new ArrayList<String>();

    }

    @Override
    public void render(GUIContext container, Graphics g) {
        background.draw(posx, posy);
        
        for (int i = 0; i < MAX_SHOW_MESSAGES && i < messages.size(); i++) {
            if (messages.get(i + initial).startsWith("Whisper")) {
                g.setColor(new Color(139, 0, 139));
            } else if (messages.get(i + initial).startsWith("Error")) {
                g.setColor(Color.red);
            } else if (messages.get(i + initial).startsWith("System")) {
                g.setColor(new Color(0, 139, 139));
            } else {
                g.setColor(Color.black);
            }
            
            g.drawString(messages.get(i + initial), posx + 5, posy + 2 + (12 * i));
            g.setColor(Color.black);
        }

        scrollbar.draw(posx + 705, posy, 7, 131);
        if (messages.size() > MAX_SHOW_MESSAGES) {
            int detailSize = 131 / ((messages.size() + 1) - MAX_SHOW_MESSAGES);
            scrollDetail.draw(posx + 705, posy + (initial * detailSize), 7, detailSize);
        }
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

    public void addMessage(String message) {

        messages.add(message);
        
        if (messages.size() > MAX_SHOW_MESSAGES) {
            if (messages.size() > 50) {
                messages.remove(0);
            } else {
                initial++;
            }
        }
    }

    @Override
    public void mouseWheelMoved(int change) {

        if (!isMouseOver()) {
            return;
        }

        if (change > 0 && messages.size() > MAX_SHOW_MESSAGES && initial > 0) {
            initial--;
        } else if (change < 0 && messages.size() > MAX_SHOW_MESSAGES && initial < messages.size() - MAX_SHOW_MESSAGES) {
            initial++;
        }
    }

}
