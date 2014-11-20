/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.client.persistence;

import mg.client.Entities.Player;
import mg.client.Entities.Warrior;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import mg.client.Entities.Fighter;
import mg.client.Entities.Mage;
import mg.client.RankData;

/**
 *
 * @author Vitor
 */
public class DAO {

    private static final Connection CONNECTION = ConnectionFactory.getConnection();

    public static boolean login(String username, String password) throws SQLException {
        String dbPass = "";

        PreparedStatement stmt = CONNECTION.prepareStatement("SELECT pass FROM LoginData WHERE username = ?");
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            dbPass = rs.getString("pass");
        }

        rs.close();
        stmt.close();

        return dbPass.equals(password);
    }

    public static boolean register(String username, String pass) {
        try {
            PreparedStatement stmt = CONNECTION.prepareStatement("INSERT INTO LoginData(username, pass, firstlogin) VALUES(?,?,?)");
            stmt.setString(1, username);
            stmt.setString(2, pass);
            stmt.setString(3, "T");
            stmt.execute();

        } catch (SQLException ex) {
            return false;
        }

        return true;
    }

    public static boolean isFirstLogin(String username) throws SQLException {

        boolean firstLogin = false;

        PreparedStatement stmt = CONNECTION.prepareStatement("SELECT firstlogin FROM LoginData WHERE username = ?");
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            firstLogin = rs.getString("firstlogin").equals("T");
        }

        rs.close();
        stmt.close();

        return firstLogin;

    }

    public static void createCaracter(String username, String nickname, String characterClass) throws SQLException {
        PreparedStatement stmt = CONNECTION.prepareStatement("UPDATE LoginData SET firstlogin = 'F' WHERE username = ?");
        stmt.setString(1, username);
        stmt.execute();
        stmt.close();

        Player c;
//        switch (characterClass) {
//            case "warrior":
                c = new Warrior(nickname);
//                break;
//            case "mage":
//                c = new Mage(nickname);
//                break;
//            case "fighter":
//                c = new Fighter(nickname);
//                break;
//            default:
//                throw new SQLException();
//        }

        stmt = CONNECTION.prepareStatement("INSERT INTO characterdata VALUES(?,?,?,?,?,?,?,?,?,?)");
        stmt.setString(1, username);
        stmt.setString(2, nickname);
        stmt.setString(3, characterClass);
        stmt.setInt(4, c.getLevel());
        stmt.setLong(5, c.getExp());
        stmt.setInt(6, c.getStrength());
        stmt.setInt(7, c.getIntelligence());
        stmt.setInt(8, c.getDexterity());
        stmt.setInt(9, c.getPhysicalResist());
        stmt.setInt(10, c.getMagicResist());

        stmt.execute();
        stmt.close();

    }

    public static void updateCharacter(Player character) throws SQLException {
        PreparedStatement stmt = CONNECTION.prepareStatement("UPDATE characterdata SET level = ?, exp = ?, strength = ?, intelligence = ?, dexterity = ?, physicalResist = ?, magicResist = ? WHERE nickname = ?");

        stmt.setInt(1, character.getLevel());
        stmt.setLong(2, character.getExp());
        stmt.setInt(3, character.getStrength());
        stmt.setInt(4, character.getIntelligence());
        stmt.setInt(5, character.getDexterity());
        stmt.setInt(6, character.getPhysicalResist());
        stmt.setInt(7, character.getMagicResist());
        stmt.setString(8, character.getNickname());

        stmt.execute();
        stmt.close();
    }

    public static Player getCharacterByOwner(String username) throws SQLException {
        PreparedStatement stmt = CONNECTION.prepareStatement("SELECT * FROM characterdata WHERE owner = ?");
        stmt.setString(1, username);

        ResultSet rs = stmt.executeQuery();
        Player character = null;
        if (rs.next()) {

//            switch (rs.getString("class")) {
//                case "warrior":
//                    character = new Warrior(rs.getString("nickname"), rs.getInt("level"),
//                            rs.getLong("exp"), rs.getInt("life"),
//                            rs.getInt("mana"), rs.getInt("strength"),
//                            rs.getInt("intelligence"), rs.getInt("dexterity"),
//                            rs.getInt("physicalResist"), rs.getInt("magicResist"));
//                    break;
//                case "mage":
//                    character = new Mage(rs.getString("nickname"), rs.getInt("level"),
//                            rs.getLong("exp"), rs.getInt("life"),
//                            rs.getInt("mana"), rs.getInt("strength"),
//                            rs.getInt("intelligence"), rs.getInt("dexterity"),
//                            rs.getInt("physicalResist"), rs.getInt("magicResist"));
//                    break;
//                case "fighter":
//                    character = new Fighter(rs.getString("nickname"), rs.getInt("level"),
//                            rs.getLong("exp"), rs.getInt("life"),
//                            rs.getInt("mana"), rs.getInt("strength"),
//                            rs.getInt("intelligence"), rs.getInt("dexterity"),
//                            rs.getInt("physicalResist"), rs.getInt("magicResist"));
//                    break;
//                default:
                    character = new Fighter(rs.getString("nickname"), rs.getInt("level"),
                            rs.getLong("exp"), rs.getInt("life"),
                            rs.getInt("mana"), rs.getInt("strength"),
                            rs.getInt("intelligence"), rs.getInt("dexterity"),
                            rs.getInt("physicalResist"), rs.getInt("magicResist"));
//            }

        }

        rs.close();
        stmt.close();
        return character;
    }
    
    public static Player getCharacter(String nickname) throws SQLException {
        PreparedStatement stmt = CONNECTION.prepareStatement("SELECT * FROM characterdata WHERE nickname = ?");
        stmt.setString(1, nickname);

        ResultSet rs = stmt.executeQuery();
        Player character = null;
        if (rs.next()) {

//            switch (rs.getString("class")) {
//                case "warrior":
//                    character = new Warrior(rs.getString("nickname"), rs.getInt("level"),
//                            rs.getLong("exp"), rs.getInt("life"),
//                            rs.getInt("mana"), rs.getInt("strength"),
//                            rs.getInt("intelligence"), rs.getInt("dexterity"),
//                            rs.getInt("physicalResist"), rs.getInt("magicResist"));
//                    break;
//                case "mage":
//                    character = new Mage(rs.getString("nickname"), rs.getInt("level"),
//                            rs.getLong("exp"), rs.getInt("life"),
//                            rs.getInt("mana"), rs.getInt("strength"),
//                            rs.getInt("intelligence"), rs.getInt("dexterity"),
//                            rs.getInt("physicalResist"), rs.getInt("magicResist"));
//                    break;
//                case "fighter":
//                    character = new Fighter(rs.getString("nickname"), rs.getInt("level"),
//                            rs.getLong("exp"), rs.getInt("life"),
//                            rs.getInt("mana"), rs.getInt("strength"),
//                            rs.getInt("intelligence"), rs.getInt("dexterity"),
//                            rs.getInt("physicalResist"), rs.getInt("magicResist"));
//                    break;
//                default:
                    character = new Fighter(rs.getString("nickname"), rs.getInt("level"),
                            rs.getLong("exp"), rs.getInt("life"),
                            rs.getInt("mana"), rs.getInt("strength"),
                            rs.getInt("intelligence"), rs.getInt("dexterity"),
                            rs.getInt("physicalResist"), rs.getInt("magicResist"));
//            }

        }

        rs.close();
        stmt.close();
        return character;
    }

    public static ArrayList<RankData> getRank() throws SQLException {
        PreparedStatement stmt = CONNECTION.prepareStatement("SELECT * FROM rankdata ORDER BY rank DESC LIMIT 10");
        ResultSet rs = stmt.executeQuery();

        ArrayList<RankData> rank = new ArrayList<RankData>();

        while (rs.next()) {
            rank.add(new RankData(rs.getString("nickname"), rs.getInt("level"), rs.getInt("rank"), rs.getInt("wins"), rs.getInt("loses")));
        }

        return rank;
    }
    
     public static RankData getRankByName(String name) throws SQLException {
        PreparedStatement stmt = CONNECTION.prepareStatement("SELECT * FROM rankdata WHERE nickname = ?");
        stmt.setString(1, name);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new RankData(rs.getString("nickname"), rs.getInt("level"), rs.getInt("rank"), rs.getInt("wins"), rs.getInt("loses"));
        } 
        
        throw new SQLException();

    }

    public static void updateRank(RankData r) throws SQLException {
        PreparedStatement stmt = CONNECTION.prepareStatement("UPDATE rankdata SET rank = ?, wins = ?, loses = ? WHERE nickname = ?");
        stmt.setInt(1, r.getRank());
        stmt.setInt(2, r.getWins());
        stmt.setInt(3, r.getLoses());
        stmt.setString(4, r.getPlayer());
    }

}
