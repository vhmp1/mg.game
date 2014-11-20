/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.client.GUI;

import chatclient.business.Client;
import chatclient.business.Services;
import chatclient.exceptions.InexistentClientException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mg.client.GUI.Buttons.ConfigButton;
import mg.client.GUI.Labels.ChatArea;
import mg.client.GUI.Labels.ConfigArea;
import mg.client.GUI.Labels.InfoArea;
import mg.client.GUI.Labels.InviteLabel;
import mg.client.GUI.Labels.InvitesArea;
import mg.client.GUI.Labels.PlayersArea;
import mg.client.GUI.Labels.RankArea;
import mg.client.GUI.Labels.RankLabel;
import mg.client.Entities.Player;
import mg.client.RankData;
import mg.client.Sound.MusicPlayer;
import mg.client.persistence.DAO;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.loading.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Vitor
 */
public class Lobby extends BasicGameState {

    public static final int ONE_MINUTE = 60000;
    private int updateTime;
    private Image background;
    private Image bg;
    //Information of the character
    private Image infoArea;
    //Buttons 
    private InfoArea info;
    private InvitesArea invites;
    private RankArea rank;
    private PlayersArea onlinePlayers;
    private ChatArea chat;
    private AbstractComponent socialArea;
    private Player c;
    private final Client client;
    private ConfigButton configBtn;
    private ConfigArea config;
    private Music music;

    public Lobby(Player player) {
        //this.c = player;
        try {
            this.client = new Client("0.0.0.0", 8885);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        updateTime = 0;
    }

    @Override
    public int getID() {
        return 2;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {

        LoadingList.setDeferredLoading(true);

        background = new Image("/resources/lobby/background.png");
        bg = new Image("/resources/lobby/bg.jpg");
        infoArea = new Image("/resources/lobby/info_area.png");

        try {
            c = DAO.getCharacterByOwner("vhmp");
            client.setNick("vhmp");
            client.writeMessage("vhmp", Services.eHello.getByte());
            synchronized (client) {
                client.wait();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InexistentClientException ex) {
            Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
        }

        info = new InfoArea(container, background.getWidth() / 2 - infoArea.getWidth() / 2, 5, c);
        invites = new InvitesArea(container, 27, 143);
        invites.addInvite(new InviteLabel(container, invites, "Torugo"));
        invites.addInvite(new InviteLabel(container, invites, "Torugo"));
        invites.addInvite(new InviteLabel(container, invites, "Torugo"));
        invites.addInvite(new InviteLabel(container, invites, "Torugo"));
        invites.addInvite(new InviteLabel(container, invites, "Torugo"));
        invites.addInvite(new InviteLabel(container, invites, "Torugo"));
        invites.addInvite(new InviteLabel(container, invites, "Torugo"));
        invites.addInvite(new InviteLabel(container, invites, "Torugo"));

        rank = new RankArea(container, 456, 143);

        onlinePlayers = new PlayersArea(container, 456, 143, client);
        chat = new ChatArea(container, 25, 371, client);

        socialArea = onlinePlayers;

        music = MusicPlayer.getMusic();

        config = new ConfigArea(container, 253, 96, music, null);
        configBtn = new ConfigButton(container, 765, 16, config);

    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {

        bg.draw();
        background.draw();
        info.render(container, g);
        invites.render(container, g);
        socialArea.render(container, g);
        chat.render(container, g);
        configBtn.render(container, g);

        if (config.isRender()) {
            config.render(container, g);
        }
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        if (updateTime <= 0) {
            onlinePlayers.update();
            updateRank(container);
            updateTime = ONE_MINUTE;
        } else {
            updateTime -= delta;
        }
    }

    @Override
    public void mouseClicked(int button, int x, int y, int clickCount) {

        if (x > rank.getX() && x < rank.getX() + 49 && y > rank.getY() && y < rank.getY() + 20) {
            socialArea = onlinePlayers;
        } else if (x > rank.getX() + 49 && x < rank.getX() + 2 * 49 && y > rank.getY() && y < rank.getY() + 20) {
            socialArea = rank;
        }

        if (x > chat.getX() && x < chat.getX() + 94 && y > chat.getY() && y < chat.getY() + 27) {
            chat.setAll(true);
        } else if (x > chat.getX() + 94 && x < chat.getX() + 2 * 94 && y > chat.getY() && y < chat.getY() + 27) {
            chat.setAll(false);
        }
        
        if(x > 38 && x < 569 && y > 550 && y < 567){
            chat.setMessageFocus(true);
        } else {
            chat.setMessageFocus(false);
        }
           
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) {
        music.loop();
        music.play();
        chat.setMessageFocus(true);
    }

    public void updateRank(GameContainer container) {
        try {
            for (RankData r : DAO.getRank()) {
                rank.addRank(new RankLabel(container, rank, r));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Lobby.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updatePlayers(ArrayList<RankData> players) {
        for (RankData player : players) {
            onlinePlayers.addPlayer(player.getPlayer(), player.getLevel());
        }
    }
}
