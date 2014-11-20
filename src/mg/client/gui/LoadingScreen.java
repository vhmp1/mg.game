/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.client.GUI;

import java.io.IOException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

/**
 *
 * @author Vitor
 */
public class LoadingScreen extends BasicGameState {

    private DeferredResource nextResource;
    private boolean started;

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {

    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        if (nextResource != null) {
            g.drawString("Loading: " + nextResource.getDescription(), 25, 100);
        }

        int total = LoadingList.get().getTotalResources();
        int loaded = LoadingList.get().getTotalResources() - LoadingList.get().getRemainingResources();

        float bar = loaded / (float) total;
        g.fillRect(25, 150, loaded * ((container.getWidth() - 50) / total), 20);
        g.drawRect(25, 150, container.getWidth() - 50, 20);

        if (started) {
            g.drawString("LOADING COMPLETE", 25, 500);
        }
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        if (nextResource != null) {
            try {
                nextResource.load();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                }
            } catch (IOException e) {
                throw new SlickException("Failed to load: " + nextResource.getDescription(), e);
            }

            nextResource = null;
        }

        if (LoadingList.get().getRemainingResources() > 0) {
            nextResource = LoadingList.get().getNext();
        } else {
            if (!started) {
                started = true;
            }

            game.enterState(1, new FadeOutTransition(), new FadeInTransition());

        }
    }

}
