/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mg.client;

/**
 *
 * @author Vitor
 */
public class RankData {
    private final String nickname;
    private final int level;
    private int rank;
    private int wins;
    private int loses;
    
    public RankData(String nickname, int level, int rank, int wins, int loses){
        this.nickname = nickname;
        this.level = level;
        this.rank = rank;
        this.wins = wins;
        this.loses = loses;
    }

    public String getPlayer() {
        return nickname;
    }

    public int getLevel() {
        return level;
    }
  
    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLoses() {
        return loses;
    }

    public void setLoses(int loses) {
        this.loses = loses;
    }
    
    
}
