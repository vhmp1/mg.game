/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient.business.messages;

import chatserver.exceptions.MalformedMessageException;
import chatserver.uteis.Uteis;
import java.io.IOException;
import mg.client.Entities.Player;
import mg.client.Net.GameClient;

/**
 *
 * @author Vitor
 */
public class AbillityMessage extends CommandMessage {

    public AbillityMessage(int dataSize, byte[] data) {
        super(Service.ABILLITY.getByte(), dataSize, data);
    }

    @Override
    public void handleMessage(GameClient client) throws IOException, MalformedMessageException {
        byte[] tmp = new byte[4];
        System.arraycopy(data, 0, tmp, 0, 4);
        Player p = client.getPlayer(Uteis.converteVetorBytesEmInt(tmp));

        System.arraycopy(data, 4, tmp, 0, 4);
        int abillity = Uteis.converteVetorBytesEmInt(tmp);

        switch (abillity) {
            case Player.ABILLITY_A:
                p.doAbillityA();
                break;
            case Player.ABILLITY_S:
                p.doAbillityS();
                break;
            case Player.ABILLITY_D:
                p.doAbillityD();
                break;
        }

    }

}
