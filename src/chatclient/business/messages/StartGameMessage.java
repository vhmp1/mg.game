/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chatclient.business.messages;

import chatserver.exceptions.MalformedMessageException;
import chatserver.uteis.Uteis;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mg.client.Entities.Player;
import mg.client.Net.GameClient;
import mg.client.persistence.DAO;


/**
 *
 * @author Vitor
 */
public class StartGameMessage extends CommandMessage{
    
    public StartGameMessage(int dataSize, byte[] data) {
        super(Service.START_BATTLE.getByte(), dataSize, data);
    }

    @Override
    public void handleMessage(GameClient client) throws IOException, MalformedMessageException {
        byte[] tmp = new byte[4];
        System.arraycopy(data, 0, tmp, 0, 4);
        int id = Uteis.converteVetorBytesEmInt(tmp);
        
        tmp = new byte[dataSize - 4];
        System.arraycopy(data, 4, tmp, 0, data.length - 4);

        String player2 = Uteis.converteVetorBytesEmString(tmp);
        System.out.println(player2);
        try {
            Player enemy = DAO.getCharacter(player2);
            enemy.setId(id);
            client.addPlayer(enemy);
        } catch (SQLException ex) {
            Logger.getLogger(StartGameMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
}
