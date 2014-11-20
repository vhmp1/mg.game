/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.client.GUI;

import mg.client.Entities.Player;
import mg.client.Net.GameClient;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Vitor
 */
public class Game extends StateBasedGame {

    private final Player player;
    private final GameClient client;

    public Game(String name) {
        super(name);
        this.player = null;
        this.client = new GameClient();
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        // container.getGraphics().drawImage(new Image("/resources/load/bg.jpg"), 0, 0);
        new Thread(client).start();
        this.addState(new LoadingScreen());
        this.addState(new Testes(player, client));
        this.addState(new Lobby(player));
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new Game("Downs of Gods"));
        app.setDisplayMode(800, 600, false);
        app.setTargetFrameRate(60);
        app.setMinimumLogicUpdateInterval(14);
        app.setAlwaysRender(true);
        app.start();
        app.getGraphics().drawImage(new Image("/resources/load/bg.jpg"), 0, 0);
    }
}
