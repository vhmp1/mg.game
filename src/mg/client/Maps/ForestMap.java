/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mg.client.Maps;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Vitor
 */
public class ForestMap extends Map{
    
    private static final String ref = "/resources/map/stage1/4.tmx";
    private static final int id = 1;
    private static Image bg;
    
    static{
        try {
            bg = new Image("/resources/map/stage1/bg.jpg");
        } catch (SlickException ex) {
            Logger.getLogger(ForestMap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ForestMap(Graphics g) throws SlickException {
        super(ref, id, bg, g);
    }
    
}
