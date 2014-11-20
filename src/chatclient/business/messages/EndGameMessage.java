/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package chatclient.business.messages;

import chatclient.uteis.Uteis;
import chatserver.exceptions.MalformedMessageException;
import java.io.IOException;
import mg.client.Net.GameClient;

/**
 *
 * @author Vitor
 */
public class EndGameMessage extends CommandMessage{

    public EndGameMessage(int dataSize, byte[] data) {
        super(Service.END_BATTLE.getByte(), dataSize, data);
    }

    @Override
    public void handleMessage(GameClient client) throws IOException, MalformedMessageException {
        //winner is the first id
        
        byte[] tmp = new byte[4];
        System.arraycopy(tmp, 0, data, 0, 4);
        
        int winner = Uteis.converteVetorBytesEmInt(tmp);
        
        System.arraycopy(tmp, 0, data, 4, 4);
        int loser = Uteis.converteVetorBytesEmInt(tmp);
        
        if(winner != client.getFirstPlayer().getId()){
            client.removePlayer(winner);
        } else if(loser != client.getFirstPlayer().getId()){
            client.removePlayer(loser);          
        }
        
    }
    
}
