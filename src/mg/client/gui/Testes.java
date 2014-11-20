/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.client.GUI;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mg.client.Entities.Bullet;
import mg.client.Entities.Fighter;
import mg.client.Entities.InputHandler;
import mg.client.GUI.Labels.BattleInfoArea;
import mg.client.Entities.Player;
import mg.client.Entities.Warrior;
import mg.client.GUI.Buttons.ConfigButton;
import mg.client.GUI.Labels.ConfigArea;
import mg.client.Maps.ForestMap;
import mg.client.Maps.Map;
import mg.client.Net.GameClient;
import mg.client.Sound.MusicPlayer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 *
 * @author Vitor
 */
public class Testes extends BasicGameState {

    private Map map;
    private Image background;
    private Player p;
    // private final GameClient client;
    private Player e;
    private Player g;
    private BattleInfoArea info;
    private int objectLayer;
    private Thread moviment;
    private GameClient client;
    private ConfigButton configBtn;
    private ConfigArea config;
    private Music music;

    private InputHandler in;

    private int mapXOffset;
    private int mapYOffset;

    public Testes(Player player, GameClient client) {
        this.client = client;
    }

    @Override
    public int getID() {
        return 1;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        //background = new Image("/resources/map/stage1/bg.jpg");

        //MusicPlayer.setMusic(MusicPlayer.GAME_MUSIC);
        music = MusicPlayer.getMusic();

        config = new ConfigArea(container, 253, 96, music, null);
        configBtn = new ConfigButton(container, 765, 16, config);

        p = new Warrior("vhmp");
        g = new Fighter("sad");
        e = new Warrior("poar");

        client.addPlayer(p);
        client.sendLogin(p.getNickname());
        map = new ForestMap(container.getGraphics());

        g.init(150, 600 - 10 * 32, null, map, e, client);
        e.init(400, 600 - 10 * 32, null, map, e, client);
        p.init(20, 600 - 10 * 32, container.getInput(), map, e, client);

        info = new BattleInfoArea(container, 13, 17, p, e);
        in = new InputHandler(client, p, container.getInput());

        try {
            map.init(container);
        } catch (IOException ex) {
            Logger.getLogger(Testes.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        client.startGame(p.getId(), 0, map.getId());
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        map.render(0, 0);
        info.render(container, g);
        
        p.render();
        this.g.render();
        e.render();

        //this.g.getStatus().draw(this.g.getPosx(), this.g.getPosy());
        configBtn.render(container, g);

        if (config.isRender()) {
            config.render(container, g);
        }

        for (Bullet b : Bullet.getBulletList()) {
            b.render(container, game, g);
            b.collision(e);
        }

    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        if (info.getTime() <= 0 || p.getActualLife() <= 0 || e.getActualLife() <= 0) {
            game.enterState(2, new FadeOutTransition(), new FadeInTransition());
        }

        info.updateValues();

        p.setStatus(Player.IDDLE);
        g.setStatus(Player.IDDLE);
        e.setStatus(Player.IDDLE);

        p.update(delta);
        g.update(delta);
        e.update(delta);
        e.updatePos(map);
        g.updatePos(map);

        in.update();

        for (Bullet b : Bullet.getBulletList()) {
            b.update(delta);
        }

        if (container.getInput().isKeyDown(Input.KEY_X)) {
            game.enterState(2, new FadeOutTransition(), new FadeInTransition());
        }
////
//        if (input.isKeyPressed(Input.KEY_V)) {
//            e.setAttacking(true);
//            e.setStatus(Player.ATTACK);
//        }
//
//        if (input.isKeyPressed(Input.KEY_B)) {
//            e.setAttacking(false);
//            e.setStatus(Player.IDDLE);
//        }

        test();
    }

    public void test() {
        if (p.testCollision(e)) {
            if (p.isAttacking() && !e.isAttacking()) {
                System.out.println("Enemy damaged");
                e.removeHP(15);
                if (!p.isDoingAbillty()) {
                    e.setKnockback(true);
                }
            } else if (!p.isAttacking() && e.isAttacking()) {
                System.out.println("You are damaged");
                p.removeHP(10);
                if (!e.isDoingAbillty()) {
                    p.setKnockback(true);
                }
            } else if (!p.isAttacking() && !e.isAttacking()) {

            } else {
                System.out.println("Both are damaged");
            }
        }

    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) {
        this.info.startTimer();
        this.music.loop();
        this.music.play();
    }
}
