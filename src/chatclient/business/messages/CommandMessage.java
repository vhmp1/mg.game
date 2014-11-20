/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient.business.messages;

import chatclient.business.Client;
import chatclient.exceptions.InexistentClientException;
import chatserver.exceptions.MalformedMessageException;
import java.io.IOException;
import mg.client.Net.GameClient;

/**
 *
 * @author Vitor
 */
public abstract class CommandMessage extends Message {

    public static enum Service {

        LOGIN((byte) 0x30),
        START_BATTLE((byte) 0x31),
        END_BATTLE((byte) 0x32),
        MOVE((byte) 0x33),
        STATUS((byte) 0x34),
        ABILLITY((byte) 0x35);

        private final byte service;

        private Service(byte b) {
            this.service = b;
        }

        public byte getByte() {
            return service;
        }

        public static Service byteToService(byte b) {
            switch (b) {
                case 0x30:
                    return LOGIN;
                case 0x31:
                    return START_BATTLE;
                case 0x32:
                    return END_BATTLE;
                case 0x33:
                    return MOVE;
                case 0x34:
                    return STATUS;
                case 0x35:
                    return ABILLITY;
                default:
                    return null;
            }
        }
    }

    public static enum Commands {

        JUMP,
        DASH,
        ATTACK,
        KNOCKBACK,
        FALL;

        public static Commands getValue(int value) {
            switch (value) {
                case 0:
                    return JUMP;
                case 1:
                    return DASH;
                case 2:
                    return ATTACK;
                case 3:
                    return KNOCKBACK;
                case 4:
                    return FALL;
                default:
                    return null;
            }
        }
    }

    public CommandMessage(byte service, int dataSize, byte[] data) {
        super(service, dataSize, data);
    }

    abstract public void handleMessage(GameClient client) throws IOException, MalformedMessageException;

    @Override
    public void handleMessage(Client client) throws IOException, InexistentClientException {

    }

}
