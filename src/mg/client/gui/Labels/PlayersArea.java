/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.client.GUI.Labels;

import chatclient.business.Client;
import chatclient.display.ChatClientUI;
import chatclient.exceptions.InexistentClientException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import mg.client.GUI.Lobby;
import mg.client.RankData;
import mg.client.persistence.DAO;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;

/**
 *
 * @author Vitor
 */
public class PlayersArea extends MouseOverArea implements Runnable {

    private static final int MAX_SHOW_PLAYERS = 7;
    private int initial;
    private int posx;
    private int posy;
    private final int width;
    private final int height;
    private final Client client;

    private final ArrayList<PlayerLabel> onlinePlayers;
    private final ArrayList<RankData> players;
    private static Image background;
    private static Image scrollbar;
    private static Image scrollDetail;

    static {
        try {
            background = new Image("/resources/lobby/players_bg.png");
            scrollbar = new Image("/resources/lobby/scrollbase.png");
            scrollDetail = new Image("/resources/lobby/scrolldetail.png");

        } catch (SlickException ex) {
            Logger.getLogger(RankArea.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public PlayersArea(GUIContext container, int posx, int posy, final Client client) {
        super(container, background, posx, posy, null);
        this.width = 319;
        this.height = 195;
        this.posx = posx;
        this.posy = posy;
        this.client = client;
        this.initial = 0;
        this.onlinePlayers = new ArrayList<PlayerLabel>();
        this.players = new ArrayList<RankData>();
        
        new Thread(this).start();

        Timer updateClientsCall = new Timer();
        updateClientsCall.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    client.updateClients();
                } catch (InexistentClientException ex) {
                    client.addMessage("Error: Cliente alvo desconhecido!");
                } catch (IOException ex) {
                    Logger.getLogger(ChatClientUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }, 0, Lobby.ONE_MINUTE);
    }

    @Override
    public void render(GUIContext container, Graphics g) {
        background.draw(posx, posy);
        for (int i = 0; i < MAX_SHOW_PLAYERS && i < onlinePlayers.size(); i++) {
            try {
                onlinePlayers.get(i + initial).setLocation(posx + 8, posy + (29 + i * (onlinePlayers.get(i).getHeight() + 3)));
                onlinePlayers.get(i + initial).render(container, g);
            } catch (SlickException ex) {
                Logger.getLogger(RankArea.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        scrollbar.draw(posx + 310, posy + 22, 7, 170);
        if (onlinePlayers.size() > MAX_SHOW_PLAYERS) {
            int detailSize = 170 / ((onlinePlayers.size() + 1) - MAX_SHOW_PLAYERS);
            scrollDetail.draw(posx + 310, posy + 22 + (initial * detailSize), 7, detailSize);
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

    public void addPlayer(String playername, int playerLevel) {
        onlinePlayers.add(new PlayerLabel(container, playername, playerLevel));
    }

    @Override
    public void mouseWheelMoved(int change) {
        if (!isMouseOver()) {
            return;
        }

        if (change > 0 && onlinePlayers.size() > MAX_SHOW_PLAYERS && initial > 0) {
            initial--;
        } else if (change < 0 && onlinePlayers.size() > MAX_SHOW_PLAYERS && initial < onlinePlayers.size() - MAX_SHOW_PLAYERS) {
            initial++;
        }
    }

    public void update() {
        onlinePlayers.clear();
        for (RankData player : players) {
            addPlayer(player.getPlayer(), player.getLevel());
        }
    }

    @Override
    public void run() {
        try {
            while (true) {

                Object atualizaClientes = client.getStick();

                synchronized (atualizaClientes) {
                    atualizaClientes.wait();
                    String clientes[] = client.showClients();

                    for (String cliente : clientes) {
                        if (!cliente.equals(client.getClientNick())) {
                            players.add(DAO.getRankByName(cliente));
                        }
                    }
                    
                    synchronized(this){
                        this.notify();
                    }
                }
            }
        } catch (InterruptedException ex) {
        } catch (SQLException ex) {
            Logger.getLogger(PlayersArea.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
