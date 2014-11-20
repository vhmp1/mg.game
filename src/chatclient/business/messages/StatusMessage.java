/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient.business.messages;

import chatclient.uteis.Uteis;
import chatserver.exceptions.MalformedMessageException;
import java.io.IOException;
import mg.client.Entities.Player;
import mg.client.Net.GameClient;

/**
 *
 * @author Vitor
 */
public class StatusMessage extends CommandMessage {

    public StatusMessage(int dataSize, byte[] data) {
        super(Service.STATUS.getByte(), dataSize, data);
    }

    @Override
    public void handleMessage(GameClient client) throws IOException, MalformedMessageException {
         byte[] tmp = new byte[4];
        System.arraycopy(tmp, 0, data, 0, 4);
        int id = Uteis.converteVetorBytesEmInt(tmp);
        Player p = client.getPlayer(id);
        
        
        System.arraycopy(tmp, 0, data, 4, 4);
        int status = Uteis.converteVetorBytesEmInt(tmp);
        
        System.arraycopy(tmp, 0, data, 8, 4);
        boolean state =  Uteis.converteVetorBytesEmInt(tmp) == 1;

        switch(Commands.getValue(status)) {
            case ATTACK:
                p.setAttacking(state);
                break;
            case DASH:
                p.setDashing(state);
                break;
            case JUMP:
                p.setJumping(state);
                break;
            case KNOCKBACK:
                p.setKnockback(state);
                break;
            default:
                break;

        }

    }

}
