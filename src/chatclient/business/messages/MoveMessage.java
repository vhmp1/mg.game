/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient.business.messages;

import chatserver.exceptions.MalformedMessageException;
import chatserver.uteis.Uteis;
import java.io.IOException;
import mg.client.Net.GameClient;

/**
 *
 * @author Vitor
 */
public class MoveMessage extends CommandMessage {

    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;

    public MoveMessage(int dataSize, byte[] data) {
        super(Service.MOVE.getByte(), dataSize, data);
    }

    @Override
    public void handleMessage(GameClient client) throws IOException, MalformedMessageException {
        byte[] tmp = new byte[4];
        System.arraycopy(data, 0, tmp, 0, 4);
        int id = Uteis.converteVetorBytesEmInt(tmp);
        System.arraycopy(data, 4, tmp, 0, 4);
        int side = Uteis.converteVetorBytesEmInt(tmp);
        client.getPlayer(id).move(side);
    }

}
