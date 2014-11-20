/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mg.client.Sound;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Vitor
 */
public class MusicPlayer {
    
    private static Music actualMusic;
    private static final Music[] listMusics;
    
    public static final int LOGIN_MUSIC = 0;
    public static final int LOBBY_MUSIC = 1;
    public static final int GAME_MUSIC = 2;
    
    
    static {
        listMusics = new Music[1];
        try {
            listMusics[LOGIN_MUSIC] = new Music("/resources/sound/login.ogg");
            actualMusic = listMusics[LOGIN_MUSIC];
        } catch (SlickException ex) {
            Logger.getLogger(MusicPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Music getMusic(){
        return actualMusic;
    }
    
    public static void setMusic(int music){
        actualMusic = listMusics[music];
    }
    
    
}
