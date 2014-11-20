/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.client.GUI.Labels;

import chatclient.business.Client;
import chatclient.business.Services;
import chatclient.exceptions.InexistentClientException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mg.client.GUI.Buttons.SendButton;
import mg.client.GUI.Fields.MessageLog;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.TextField;

/**
 *
 * @author Vitor
 */
public class ChatArea extends AbstractComponent implements Runnable {

    private int posx;
    private int posy;
    private final int width;
    private final int height;
    private static Image backgroundRender;
    private static Image backgroundAll;
    private static Image backgroundPrivate;
    private final TextField message;
    private final MessageLog logAll;
    private final MessageLog logPrivate;
    private final SendButton send;
    private MessageLog logRender;
    private boolean all;
    private boolean autocomplete;
    private final Client client;
    private static final java.awt.Font font;
    private static final TrueTypeFont ttf;

    static {
        font = new java.awt.Font("Georgia", java.awt.Font.PLAIN, 10);
        ttf = new TrueTypeFont(font, true);

        try {
            backgroundAll = new Image("/resources/lobby/chat_bg.png");
            backgroundPrivate = new Image("/resources/lobby/chatw_bg.png");
        } catch (SlickException ex) {
        }
    }

    public ChatArea(GUIContext container, int posx, int posy, Client client) {
        super(container);
        this.width = 751;
        this.height = 211;
        this.posx = posx;
        this.posy = posy;
        this.message = new TextField(container, ttf, 38, 550, 643, 23);
        this.message.setBackgroundColor(Color.transparent);
        this.logAll = new MessageLog(container, posx + 14, posy + 40);
        this.logPrivate = new MessageLog(container, posx + 14, posy + 40);
        this.send = new SendButton(container, posx + 670, posy + 179, null, this);
        this.all = true;
        this.client = client;

        logRender = logAll;
        backgroundRender = backgroundAll;

        Thread t = new Thread(this);
        t.start();

    }

    @Override
    public void render(GUIContext container, Graphics g) throws SlickException {
        if (all) {
            backgroundRender = backgroundAll;
            logRender = logAll;
        } else {
            backgroundRender = backgroundPrivate;
            logRender = logPrivate;
        }

        backgroundRender.draw(posx, posy);
        send.render(container, g);
        logRender.render(container, g);
        message.render(container, g);

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

    public void setAll(boolean status) {
        this.all = status;
    }

    @Override
    public void run() {
        while (true) {
            if (client.listMessagesIsEmpty()) {
                synchronized (client) {
                    try {
                        client.wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MessageLog.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            String receivedMessage = client.readMessage();

            if (receivedMessage.startsWith("/w")) {
                logPrivate.addMessage(receivedMessage);
            }

            logAll.addMessage(receivedMessage);
        }
    }

    @Override
    public void keyPressed(int key, char code) {
    }

    @Override
    public void keyReleased(int key, char code) {
        if (key == Input.KEY_ENTER && message.hasFocus()) {
            sendMessage();
        }
        
        if ((message.getText().equals("/s") || message.getText().equals("/w"))) {
            autocomplete = true;
            System.out.println("autocomplete");
        }
        
        if (autocomplete) {
            if (message.getText().startsWith("/w")) {
                message.setText("/w \"\"");
                message.setFocus(true);
                message.setCursorPos(message.getText().length() - 1);
            } else if (message.getText().startsWith("/s")) {
                message.setText("/s \"\"");
                message.setFocus(true);
                message.setCursorPos(message.getText().length() - 1);
            }
            autocomplete = false;
        }
    }

    public void sendMessage() {
        try {
            client.writeMessage(message.getText(), Services.eSendMessage.getByte());
        } catch (IOException ex) {
            Logger.getLogger(ChatArea.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InexistentClientException ex) {
            client.addMessage("System: User offline or inexistent!");
        }
        message.setText("");
    }

    public void setMessageFocus(boolean focus) {
        message.setFocus(focus);
        message.setCursorPos(0);
    }
    
}
