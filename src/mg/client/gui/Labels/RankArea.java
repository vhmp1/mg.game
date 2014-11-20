/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mg.client.GUI.Labels;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;


/**
 *
 * @author Vitor
 */
public class RankArea extends MouseOverArea{    
    private static final int MAX_SHOW_RANK = 7;
    private int initial = 0;
    private int posx;
    private int posy;
    private final int width;
    private final int height;
    private ArrayList<RankLabel> rank;
    private static Image background;
    private static Image scrollbar;
    private static Image scrollDetail;

    static {
        try {      
            background = new Image("/resources/lobby/rank_bg.png");
            scrollbar = new Image("/resources/lobby/scrollbase.png");
            scrollDetail = new Image("/resources/lobby/scrolldetail.png");
        } catch (SlickException ex) {
            Logger.getLogger(RankArea.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public RankArea (GUIContext container, int posx, int posy) {
        super(container, background, posx, posy, null);
        this.width = 319;
        this.height = 195;
        this.posx = posx;
        this.posy = posy;
        rank = new ArrayList<RankLabel>();

    }

    @Override
    public void render(GUIContext container, Graphics g) {
        background.draw(posx, posy);
        for (int i = 0; i < MAX_SHOW_RANK && i < rank.size(); i++) {
            try {
                rank.get(i + initial).setLocation(posx + 8, posy + (29 + i * (rank.get(i).getHeight() + 3)));
                rank.get(i + initial).render(container, g);
            } catch (SlickException ex) {
                Logger.getLogger(RankArea.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
         scrollbar.draw(posx + 310, posy + 22, 7, 170);
        if(rank.size() > MAX_SHOW_RANK){
            int detailSize = 170 / ((rank.size() + 1) - MAX_SHOW_RANK);
            scrollDetail.draw(posx + 310, posy + 22 + (initial * detailSize), 7, detailSize );
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

    public void addRank(RankLabel i) {
        rank.add(i);     
    }

    @Override
    public void mouseWheelMoved(int change){
        if(!isMouseOver()){
            return;
        }
        
        if(change > 0 && rank.size() > MAX_SHOW_RANK && initial > 0){
            initial--;
        } else if(change < 0 && rank.size() > MAX_SHOW_RANK && initial < rank.size() - MAX_SHOW_RANK){
            initial++;
        }
    }

}
