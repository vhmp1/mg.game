/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient.business.messages;

import chatserver.exceptions.MalformedMessageException;
import chatserver.uteis.Uteis;
import java.io.IOException;
import java.net.InetAddress;
import mg.client.Net.GameClient;

/**
 *
 * @author Vitor
 */
public class LoginMessage extends CommandMessage {

    private final InetAddress ip;
    private final int port;

    public LoginMessage(InetAddress ip, int port, int dataSize, byte[] data) {
        super(Service.LOGIN.getByte(), dataSize, data);
        this.ip = ip;
        this.port = port;
    }

    public LoginMessage(int dataSize, byte[] data) {
        super(Service.LOGIN.getByte(), dataSize, data);
        ip = null;
        port = 0;
    }

    @Override
    public void handleMessage(GameClient client) throws IOException, MalformedMessageException {
        byte[] tmp = new byte[4];
        System.arraycopy(data, 0, tmp, 0, 4);   
        client.getFirstPlayer().setId(Uteis.converteVetorBytesEmInt(tmp));
    }
}
