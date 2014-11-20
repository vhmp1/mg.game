/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.client.Maps;

import java.io.IOException;
import java.util.ArrayList;
import mg.client.Entities.Player;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author Vitor
 */
public class Map extends TiledMap {

    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;

    private final int id;
    private final Image background;
    private final ArrayList<Rectangle> blocks = new ArrayList<Rectangle>();
    private final Graphics g;
    
    private int xOffset;
    private int yOffset;
    private int oldX;
    private int oldY;

    public Map(String ref, int id, Image background, Graphics g) throws SlickException {
        super(ref);
        this.id = id;
        this.g = g;
        this.background = background;
    }

    public void init(GameContainer gc) throws SlickException, IOException {
        int layer = this.getLayerIndex("Objects");
        for (int i = 0; i < this.getWidth(); i++) {
            for (int j = 0; j < this.getHeight(); j++) {
                if (getTileId(i, j, layer) != 0) {
                    blocks.add(new Rectangle(i * 32, j * 32, 32, 32));                   
                }
            }
        }
    }

    @Override
    public void render(int x, int y) {
        background.draw(0,0);
        this.render(xOffset, yOffset, 0, 0, width, height);
    }

    public boolean mapCollision(Player p, int move) {
        Rectangle nextPositionPlayer;

        switch (move) {
            case LEFT:
                nextPositionPlayer = new Rectangle(p.getPosx() - (p.getVelocity() / 5f), p.getPosy() + 3, 1, p.getHeight() - 3);
                break;
            case RIGHT:
                nextPositionPlayer = new Rectangle((p.getPosx() + (p.getVelocity() / 5f)) + p.getHeight(), p.getPosy() + 3, 1, p.getHeight() - 3);
                break;
            case DOWN:
                nextPositionPlayer = new Rectangle(p.getPosx() + 3, (p.getPosy() + p.getGravityAcceleration()) + p.getHeight(), p.getWidth() - 3, 1);
                break;
            default:
                return false;

        }

        for (Rectangle block : blocks) {
            if (block.intersects(nextPositionPlayer)) {
                return true;
            }
        }

        return false;
    }

    public void setLocation(int newX, int newY) {       
        if (newX < 0 && newX > 800 - width * 32) {
            oldX = xOffset;
            xOffset = newX;
            updateCollisionBlocksX();
        }

        if (newY < 0 && newY > 600 - height * 32) {
            oldY = yOffset;
            yOffset = newY;
            updateCollisionBlocksY();
        }
    }

    public int getX() {
        return xOffset;
    }

    public int getY() {
        return yOffset;
    }
        
    public void updateCollisionBlocksX() {
        for(Rectangle block : blocks) {
              block.setX((xOffset - oldX) + block.getX());     
          } 
    }
    
    public void updateCollisionBlocksY() {
        for(Rectangle block : blocks) {
              block.setY((yOffset - oldY) + block.getY());     
          } 
    }

    public int getId() {
        return id;
    }
    
}
